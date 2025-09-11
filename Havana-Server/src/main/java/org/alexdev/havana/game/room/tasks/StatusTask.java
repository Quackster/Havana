package org.alexdev.havana.game.room.tasks;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.pets.Pet;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomUserStatus;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.messages.outgoing.rooms.user.TYPING_STATUS;
import org.alexdev.havana.util.DateUtil;
import org.oldskooler.simplelogger4j.SimpleLog;

import java.util.ArrayList;
import java.util.List;

public class StatusTask implements Runnable {
    private final Room room;

    public StatusTask(Room room) {
        this.room = room;
    }

    @Override
    public void run() {
        if (this.room.getEntities().size() == 0) {
            return;
        }

        for (Entity entity : this.room.getEntities()) {
            if (entity != null) {
                if (entity.getRoomUser().getRoom() != null && entity.getRoomUser().getRoom() == this.room) {
                    this.processEntity(entity);
                }
            }
        }
    }

    /**
     * Process entity.
     *
     * @param entity the entity
     */
    private void processEntity(Entity entity) {
        try {
            List<RoomUserStatus> toRemove = new ArrayList<>();

            if (entity.getType() == EntityType.PLAYER ||
                    entity.getType() == EntityType.BOT ||
                    entity.getType() == EntityType.PET) {

                processHeadRotation(entity);
                processPoolQueue(entity);
            }

            if (entity.getType() == EntityType.PET) {
                Pet pet = (Pet) entity;

                if (pet.getRoomUser().getTask() != null)
                    pet.getRoomUser().getTask().tick();
            }

            if (entity.getType() == EntityType.PLAYER) {
                processChatBubble((Player)entity);
            }

            /*if (entity.getRoomUser().getDrinkCycleRefresh().get() != -1 && DateUtil.getCurrentTimeSeconds() >= entity.getRoomUser().getDrinkCycleRefresh().get()) {
                if (entity.getRoomUser().getDrinkLoopCount().decrementAndGet() > 0) {
                    int loopCount = entity.getRoomUser().getDrinkLoopCount().get();
                    entity.getRoomUser().carryItem(entity.getRoomUser().getCarryId(), entity.getRoomUser().getCarryName());
                    entity.getRoomUser().getDrinkLoopCount().set(loopCount);
                } else {
                    entity.getRoomUser().carryItem(0, null);
                    entity.getRoomUser().getDrinkCycleRefresh().set(-1);
                    entity.getRoomUser().getDrinkLoopCount().set(-1);
                    entity.getRoomUser().setNeedsUpdate(true);
                }
            }*/

            for (var kvp : entity.getRoomUser().getStatuses().entrySet()) {
                String key = kvp.getKey();
                RoomUserStatus rus = kvp.getValue();

                if (rus.getActionSwitchCountdown() > 0) {
                    rus.setActionSwitchCountdown(rus.getActionSwitchCountdown() - 1);
                } else if (rus.getActionSwitchCountdown() == 0) {
                    rus.setActionSwitchCountdown(-1);
                    rus.setActionCountdown(rus.getSecActionSwitch());

                    // Swap back to original key and update status
                    rus.swapKeyAction();
                    entity.getRoomUser().setNeedsUpdate(true);
                }

                if (rus.getActionCountdown() > 0) {
                    rus.setActionCountdown(rus.getActionCountdown() - 1);
                } else if (rus.getActionCountdown() == 0) {
                    rus.setActionCountdown(-1);
                    rus.setActionSwitchCountdown(rus.getSecSwitchLifetime());

                    // Swap key to action and update status
                    rus.swapKeyAction();
                    entity.getRoomUser().setNeedsUpdate(true);
                }

                if (rus.getLifetimeCountdown() > 0) {
                    rus.setLifetimeCountdown(rus.getLifetimeCountdown() - 1);
                } else if (rus.getLifetimeCountdown() == 0) {
                    rus.setLifetimeCountdown(-1);
                    toRemove.add(rus);

                    entity.getRoomUser().setNeedsUpdate(true);
                }
            }

            boolean refreshUser = false;

            for (RoomUserStatus keyRemove : toRemove) {
                entity.getRoomUser().getStatuses().remove(keyRemove.getKey().getStatusCode());

                if (keyRemove.getKey() == StatusType.CARRY_DRINK) {
                    refreshUser = true;
                }
            }

            if (refreshUser) {
                entity.getRoomUser().refreshUser();
            }

        } catch (Exception ex) {
            SimpleLog.of(StatusTask.class).error("StatusTask crashed: ", ex);
        }
    }

    /**
     * Make the user walk to the next tile on a pool lido queue, if they're in the diving deck and
     * they have tickets.
     *
     * @param player the player to force walking
     */
    public static void processPoolQueue(Entity player) {
        if (player.getDetails().getTickets() == 0 || player.getDetails().getPoolFigure().isEmpty()) {
            return;
        }

        if (player.getRoomUser().getRoom() != null && !player.getRoomUser().getRoom().getModel().getName().equals("pool_b")) {
            return;
        }

        if (player.getRoomUser().isWalking()) {
            return;
        }

        var currentItem = player.getRoomUser().getCurrentItem();

        if (currentItem != null) {
            if (currentItem.getDefinition().getSprite().contains("queue_tile2")) {
                Position front = currentItem.getPosition().getSquareInFront();
                player.getRoomUser().walkTo(front.getX(), front.getY());
            }
        }
    }

    /**
     * Check the talk bubble timer expiry.
     *
     * @param player the player to check for
     */
    public static void processChatBubble(Player player) {
        if (player.getRoomUser().getTimerManager().getChatBubbleTimer() != -1 &&
                DateUtil.getCurrentTimeSeconds() > player.getRoomUser().getTimerManager().getChatBubbleTimer()) {

            player.getRoomUser().setTyping(false);
            player.getRoomUser().getTimerManager().stopChatBubbleTimer();
            player.getRoomUser().getRoom().send(new TYPING_STATUS(player.getRoomUser().getInstanceId(), false));
        }
    }


    /**
     * Check head rotation expiry.
     * 
     * @param player the player to check for
     */
    public static void processHeadRotation(Entity player) {
        if (player.getRoomUser().getTimerManager().getLookTimer() != -1 &&
                DateUtil.getCurrentTimeSeconds() > player.getRoomUser().getTimerManager().getLookTimer()) {

            player.getRoomUser().getTimerManager().stopLookTimer();
            player.getRoomUser().getPosition().setHeadRotation(player.getRoomUser().getPosition().getBodyRotation());
            player.getRoomUser().setNeedsUpdate(true);
        }
    }
}
