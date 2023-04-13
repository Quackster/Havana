package org.alexdev.havana.game;

import org.alexdev.havana.dao.mysql.ClubGiftDao;
import org.alexdev.havana.dao.mysql.CurrencyDao;
import org.alexdev.havana.dao.mysql.EffectDao;
import org.alexdev.havana.game.catalogue.RareManager;
import org.alexdev.havana.game.catalogue.collectables.CollectablesManager;
import org.alexdev.havana.game.club.ClubSubscription;
import org.alexdev.havana.game.effects.Effect;
import org.alexdev.havana.game.events.EventsManager;
import org.alexdev.havana.game.games.GameManager;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.ItemManager;
import org.alexdev.havana.game.moderation.ChatManager;
import org.alexdev.havana.game.moderation.cfh.CallForHelpManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.log.Log;
import org.alexdev.havana.messages.incoming.catalogue.GET_CATALOG_INDEX;
import org.alexdev.havana.messages.outgoing.effects.AVATAR_EFFECT_EXPIRED;
import org.alexdev.havana.messages.outgoing.user.currencies.ActivityPointNotification;
import org.alexdev.havana.messages.outgoing.user.currencies.CREDIT_BALANCE;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.config.GameConfiguration;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class GameScheduler implements Runnable {
    private AtomicLong tickRate = new AtomicLong();

    private ScheduledExecutorService schedulerService;
    private ScheduledFuture<?> gameScheduler;

    private BlockingQueue<Map.Entry<Player, Integer>> creditsHandoutQueue;
    private BlockingQueue<Map.Entry<Player, Integer>> pixelsHandoutQueue;
    private BlockingQueue<Item> itemSavingQueue;
    private BlockingQueue<Long> itemDeletionQueue;

    private static GameScheduler instance;

    private GameScheduler() {
        this.schedulerService =  Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        this.gameScheduler = this.schedulerService.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS);
        this.creditsHandoutQueue = new LinkedBlockingQueue<>();
        this.pixelsHandoutQueue = new LinkedBlockingQueue<>();
        this.itemSavingQueue = new LinkedBlockingDeque<>();
        this.itemDeletionQueue = new LinkedBlockingDeque<>();
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        try {
            if (this.tickRate.get() % 10 == 0) {
                PlayerManager.getInstance().checkPlayerPeak();
            }

            for (Player player : PlayerManager.getInstance().getPlayers()) {
                if (player.getRoomUser().getRoom() != null) {

                    // If their sleep timer is now lower than the current time, make them sleep.
                    if (DateUtil.getCurrentTimeSeconds() > player.getRoomUser().getTimerManager().getSleepTimer()) {
                        if (!player.getRoomUser().isSleeping()) {
                            player.getRoomUser().stopCarrying();
                            player.getRoomUser().sleep(true);
                        }
                    }

                    // If their afk timer is up, send them out.
                    if (DateUtil.getCurrentTimeSeconds() > player.getRoomUser().getTimerManager().getAfkTimer()) {
                        player.getRoomUser().kick(true, false);
                    }

                    // If they're not sleeping (aka, active) and their next handout expired, give them their credits!
                    /*if (player.getDetails().isCreditsEligible() && (DateUtil.getCurrentTimeSeconds() > player.getDetails().getNextHandout())) {
                        if (!this.creditsHandoutQueue.contains(player)) {
                            this.creditsHandoutQueue.put(player);
                            player.getDetails().setCreditsEligible(false);
                        }
                    }*/
                }/* else {
                    if (player.getDetails().isCreditsEligible()) {
                        player.getDetails().resetNextHandout();
                    }
                }*/

                if (player.getDetails().isCreditsEligible() && (DateUtil.getCurrentTimeSeconds() > player.getDetails().getNextHandout())) {
                    int credits = GameConfiguration.getInstance().getInteger("daily.credits.amount");

                    if (credits > 0) {
                        if (this.creditsHandoutQueue.stream().noneMatch(entry -> entry.getKey() == player)) {
                            this.queuePlayerCredits(player, credits);
                            player.getDetails().setCreditsEligible(false);
                        }
                    }
                }

                // Do pixels
                TimeUnit pixelsReceived = TimeUnit.valueOf(GameConfiguration.getInstance().getString("pixels.received.timeunit"));
                int intervalInSeconds = (int) pixelsReceived.toSeconds(GameConfiguration.getInstance().getInteger("pixels.received.interval"));
                int pixelsToGive = 15;

                if (DateUtil.getCurrentTimeSeconds() > player.getDetails().getLastPixelsTime() && player.getRoomUser().getRoom() != null) {
                    if (player.getRoomUser().getPixelAvailableTick().getAndDecrement() > 0) {
                        if (this.pixelsHandoutQueue.stream().noneMatch(entry -> entry.getKey() == player)) {
                            this.queuePlayerPixels(player, pixelsToGive);
                            player.getDetails().setLastPixelsTime(DateUtil.getCurrentTimeSeconds() + intervalInSeconds);
                        }
                    }
                }

                // Check effect expiry
                List<Effect> effectsToRemove = new ArrayList<>();

                for (Effect effect : player.getEffects()) {
                    if (!effect.isActivated()) {
                        continue;
                    }

                    if (DateUtil.getCurrentTimeSeconds() > effect.getExpireDate()) {
                        effectsToRemove.add(effect);
                        player.send(new AVATAR_EFFECT_EXPIRED(effect.getEffectId()));

                        if (player.getRoomUser().getRoom() != null && player.getRoomUser().isUsingEffect() && player.getRoomUser().getEffectId() == effect.getEffectId()) {
                            player.getRoomUser().stopEffect();
                        }
                    }
                }

                for (Effect effect : effectsToRemove) {
                    player.getEffects().remove(effect);
                    EffectDao.deleteEffect(effect.getId());
                }
            }

            // Resend the catalogue index every 15 minutes to clear page cache
            if (this.tickRate.get() % TimeUnit.MINUTES.toSeconds(15) == 0) {
                for (Player player : PlayerManager.getInstance().getPlayers()) {
                    new GET_CATALOG_INDEX().handle(player, null);
                }
            }

            // Save credits every 30 seconds
            //if (this.tickRate.get() % 30 == 0) {
            List<Map.Entry<Player, Integer>> creditsHandout = new ArrayList<>();
            List<Map.Entry<Player, Integer>> pixelHandout = new ArrayList<>();

            this.creditsHandoutQueue.drainTo(creditsHandout);
            this.pixelsHandoutQueue.drainTo(pixelHandout);

            if (creditsHandout.size() > 0) {
                Map<PlayerDetails, Integer> playerDetailsToSave = new LinkedHashMap<>();

                for (Map.Entry<Player, Integer> entry : creditsHandout) {
                    Player p = entry.getKey();
                    int amount = entry.getValue();
                    playerDetailsToSave.put(p.getDetails(), amount);
                }

                CurrencyDao.increaseCredits(playerDetailsToSave);

                for (Map.Entry<Player, Integer> entry : creditsHandout) {
                    Player p = entry.getKey();
                    CurrencyDao.updateEligibleCredits(p.getDetails().getId(), false);
                    p.send(new CREDIT_BALANCE(p.getDetails().getCredits()));
                }
            }

            if (pixelHandout.size() > 0) {
                Map<PlayerDetails, Integer> playerDetailsToSave = new LinkedHashMap<>();

                for (var kvp : pixelHandout) {
                    var details = kvp.getKey().getDetails();
                    playerDetailsToSave.put(details, kvp.getValue());
                }

                CurrencyDao.increasePixels(playerDetailsToSave);

                for (var kvp : pixelHandout) {
                    var p = kvp.getKey();
                    //p.send(new ActivityPointNotification(p.getDetails().getPixels(), ActivityPointNotification.ActivityPointAlertType.PIXELS_SOUND)); // Alert pixel sound
                    p.send(new ActivityPointNotification(p.getDetails().getPixels(), ActivityPointNotification.ActivityPointAlertType.PIXELS_RECEIVED)); // Alert pixels received
                }
            }
            //}

            // Cycle through HC gifts
            if (this.tickRate.get() % TimeUnit.SECONDS.toSeconds(1) == 0) {
                ClubGiftDao.incrementGiftData(ClubSubscription.getClubGiftSeconds());
            }

            // Purge expired rows
            if (this.tickRate.get() % TimeUnit.DAYS.toSeconds(1) == 0) {
                EventsManager.getInstance().removeExpiredEvents();
            }

            // Item saving queue ticker every 10 seconds
            if (this.tickRate.get() % 10 == 0) {
                if (this.itemSavingQueue != null) {
                    this.performItemSaving();
                }
            }

            // Item deletion queue ticker every 1 second
            if (this.tickRate.get() % 5 == 0) {
                if (this.itemSavingQueue != null) {
                    this.performItemDeletion();
                }
            }

            // Check expired rentals every 60 seconds
            if (this.tickRate.get() % 60 == 0) {
                ItemManager.getInstance().checkExpiredRentals();
            }

            // Delete expired CFH's every 60 seconds
            if (this.tickRate.get() % 60 == 0) {
                CallForHelpManager.getInstance().purgeExpiredCfh();
            }

            // Save chat messages every 60 seconds
            if (this.tickRate.get() % 60 == 0) {
                ChatManager.getInstance().performChatSaving();
            }

            // Call GC because why the fuck not
            if (this.tickRate.get() % 15 == 0) {
                System.gc();
            }

            // Check xp expiry once every day
            if (this.tickRate.get() % 5 == 0) {
                GameManager.getInstance().checkXpExpiry();
            }

            CollectablesManager.getInstance().checkExpiries();
            RareManager.getInstance().performRareManagerJob(this.tickRate);
        } catch (Exception ex) {
            Log.getErrorLogger().error("GameScheduler crashed: ", ex);
        }

        this.tickRate.incrementAndGet();
    }

    /**
     * Add player to queue to give credits to.
     *
     * @param player the player to modify
     * @param credits the credits
     */
    public void queuePlayerCredits(Player player, int credits) {
        this.creditsHandoutQueue.add(new AbstractMap.SimpleEntry<>(player, credits));
    }

    public void queuePlayerPixels(Player player, int pixels) {
        this.pixelsHandoutQueue.add(new AbstractMap.SimpleEntry<>(player, pixels));
    }

    /**
     * Queue item to be saved.
     *
     * @param item the item to save
     */
    public void queueSaveItem(Item item) {
        this.itemSavingQueue.removeIf(i -> i.getDatabaseId() == item.getDatabaseId());
        this.itemSavingQueue.add(item);
    }

    /**
     * Queue item to be deleted.
     *
     * @param itemId the item to delete
     */
    public void queueDeleteItem(Long itemId) {
        this.itemDeletionQueue.removeIf(i -> i.equals(itemId));
        this.itemDeletionQueue.add(itemId);
    }

    /**
     * Method to perform item saving.
     */
    public void performItemSaving() {
        ItemManager.getInstance().performItemSaving(this.itemSavingQueue);
    }

    /**
     * Method to perform item deletion.
     */
    public void performItemDeletion() {
        ItemManager.getInstance().performItemDeletion(this.itemDeletionQueue);
    }

    /**
     * Gets the scheduler service.
     *
     * @return the scheduler service
     */
    public ScheduledExecutorService getService() {
        return schedulerService;
    }

    /**
     * Gets the instance
     *
     * @return the instance
     */
    public static GameScheduler getInstance() {
        if (instance == null) {
            instance = new GameScheduler();
        }

        return instance;
    }
}
