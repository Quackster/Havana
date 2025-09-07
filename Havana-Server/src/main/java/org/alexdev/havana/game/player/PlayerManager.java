package org.alexdev.havana.game.player;

import org.alexdev.havana.Havana;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.GameScheduler;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;
import org.alexdev.havana.game.texts.TextsManager;
import org.alexdev.havana.messages.outgoing.openinghours.INFO_HOTEL_CLOSED;
import org.alexdev.havana.messages.outgoing.openinghours.INFO_HOTEL_CLOSING;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.config.GameConfiguration;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PlayerManager {
    private static PlayerManager instance;
    private List<Player> players;

    private long dailyPlayerPeak;
    private long allTimePlayerPeak;

    private boolean isMaintenanceShutdown;
    private Duration maintenanceAt;

    private ScheduledFuture<?> shutdownTimeout;

    public PlayerManager() {
        this.players = new CopyOnWriteArrayList<>();
        this.dailyPlayerPeak = GameConfiguration.getInstance().getInteger("players.daily.peak");
        this.allTimePlayerPeak = GameConfiguration.getInstance().getInteger("players.all.time.peak");
    }

    /**
     * Checks and sets the daily player peak.
     */
    public void checkPlayerPeak() {
        var playerSize = PlayerManager.getInstance().getPlayers().size();

        if (!GameConfiguration.getInstance().getString("players.daily.peak.date").equals(DateUtil.getCurrentDate(DateUtil.SHORT_DATE))) {
            this.dailyPlayerPeak = PlayerManager.getInstance().getPlayers().size();

            GameConfiguration.getInstance().updateSetting("players.daily.peak", String.valueOf(this.dailyPlayerPeak));
            GameConfiguration.getInstance().updateSetting("players.daily.peak.date", DateUtil.getCurrentDate(DateUtil.SHORT_DATE));
        } else {
            int newSize = PlayerManager.getInstance().getPlayers().size();

            if (newSize > this.dailyPlayerPeak) {
                this.dailyPlayerPeak = newSize;
                GameConfiguration.getInstance().updateSetting("players.daily.peak", String.valueOf(this.dailyPlayerPeak));
            }
        }

        if (playerSize > this.allTimePlayerPeak) {
            this.allTimePlayerPeak = playerSize;
            GameConfiguration.getInstance().updateSetting("players.all.time.peak", String.valueOf(this.allTimePlayerPeak));
        }
    }

    /**
     * Get a player by user id.
     *
     * @param userId the user id to get with
     * @return the player, else null if not found
     */
    public Player getPlayerById(int userId) {
        for (Player player : this.players) {
            if (player.getDetails().getId() == userId) {
                return player;
            }
        }

        return null;
    }

    /**
     * Get the player by name.
     *
     * @param username the name to get with
     * @return the player, else null if not found
     */
    public Player getPlayerByName(String username) {
        for (Player player : this.players) {
            if (player.getDetails().getName().equalsIgnoreCase(username)) {
                return player;
            }
        }

        return null;
    }

    /**
     * Get a player data by user id.
     *
     * @param userId the user id to get with
     * @return the player data, else if offline will query the database
     */
    public PlayerDetails getPlayerData(int userId) {
        Player player = getPlayerById(userId);

        if (player != null) {
            return player.getDetails();
        }

        return PlayerDao.getDetails(userId);
    }

    /**
     * Get a player data by username.
     *
     * @param username the username to get with
     * @return the player data, else if offline will query the database
     */
    public PlayerDetails getPlayerData(String username) {
        Player player = getPlayerByName(username);

        if (player != null) {
            return player.getDetails();
        }

        return PlayerDao.getDetails(username);
    }

    /**
     * Remove player from map, this is handled automatically when
     * the socket is closed.
     *
     * @param player the player to remove
     */
    public void removePlayer(Player player) {
        if (player.getDetails().getName() == null) {
            return;
        }

        this.players.remove(player);
    }

    /**
     * Remove player from map, this is handled automatically when
     * the player is logged in.
     *
     * @param player the player to remove
     */
    public void addPlayer(Player player) {
        if (player.getDetails() == null) {
            return;
        }

        this.players.add(player);
    }

    /**
     * Disconnect a session by user id.
     *
     * @param userId the user id of the session to disconnect
     */
    public void disconnectSession(int userId) {
        for (Player player : this.players) {
            if (player.getDetails().getId() == userId) {
                player.kickFromServer();
            }
        }
    }

    /**
     * Get if the player is online.
     *
     * @param userId the id of the user to check
     * @return true, if successful
     */
    public boolean isPlayerOnline(int userId) {
        for (Player player : this.players) {
            if (player.getDetails().getId() != userId) {
                continue;
            }

            if (!player.getDetails().isOnlineStatusVisible()) {
                return false;
            }

            return true;
        }

        return false;
    }

    /**
     * Start shutdown timeout
     *
     * @param maintenanceAt when to shutdown
     */
    public void planMaintenance(Duration maintenanceAt) {
        // Interrupt current timeout to set new maintenance countdown
        if (this.shutdownTimeout != null) {
            this.shutdownTimeout.cancel(true);
        }

        // Start timeout that will trigger the shutdown hook
        this.shutdownTimeout = GameScheduler.getInstance().getService().schedule(() -> {
            System.exit(0);
        }, maintenanceAt.toMillis(), TimeUnit.MILLISECONDS);

        // Let other Havana components know we are in maintenance mode
        this.isMaintenanceShutdown = true;
        this.maintenanceAt = maintenanceAt;

        // Notify all users of shutdown timeout
        for (Player p : this.players) {
            if (p.getNetwork().isFlashConnection()) {
                p.send(new ALERT("The hotel is currently closing in " + Math.toIntExact(maintenanceAt.toMinutes()) + " minutes!"));
            } else {
                p.send(new INFO_HOTEL_CLOSING(maintenanceAt));
            }
        }
    }

    /**
     * Cancel shutdown timeout
     */
    public void cancelMaintenance() {
        // Cancel current timeout
        this.shutdownTimeout.cancel(true);

        // Let other Havana components know we are no longer in maintenance mode
        this.isMaintenanceShutdown = false;

        // Notify all users maintenance has been cancelled
        for (Player p : this.players) {
            p.send(new ALERT(TextsManager.getInstance().getValue("maintenance_cancelled")));
        }
    }

    public void sendAll(MessageComposer composer) {
        for (Player p : this.players) {
            p.send(composer);
        }
    }

    /**
     * Close and dispose all users.
     */
    public void dispose() {
        for (Player p : new ArrayList<>(this.players)) {
            // Send fancy maintenance alert if we're shutting down
            p.send(new INFO_HOTEL_CLOSED(LocalTime.now(), false));

            // Now disconnect the player
            p.kickFromServer();
        }
    }

    /**
     * Get the collection of players on the server.
     *
     * @return the collection of players
     */
    public List<Player> getPlayers() {
        return this.players;
    }

    /**
     * Get the collection of active players on the server.
     *
     * @return the collection of active players
     */
    public Collection<Player> getActivePlayers() {
        List<Player> activePlayers = new ArrayList<>();
        for (Player player : PlayerManager.getInstance().getPlayers()) {
            if (player.getRoomUser().getRoom() == null) {
                continue;
            }

            if (player.getRoomUser().isSleeping()) {
                continue;
            }

            activePlayers.add(player);
        }

        return activePlayers;
    }

    /**
     * Create password hash
     *
     * @param password password to hash
     * @return hashed password
     */
    public String createPassword(String password)  {
        return Havana.getPasswordEncoder().encode(password);
    }

    /**
     * Get whether the hash matches the entered password.
     *
     * @return true, if success
     */
    public boolean passwordMatches(String databasePassword, String enteredPassword) {
        return Havana.getPasswordEncoder().matches(enteredPassword, databasePassword);
    }

    /**
     * Get daily player peak
     *
     * @return the daily player peak
     */
    public long getDailyPlayerPeak() {
        return this.dailyPlayerPeak;
    }

    /**
     * Get all time player peak.
     *
     * @return all time player peak
     */
    public long getAllTimePlayerPeak() {
        return allTimePlayerPeak;
    }

    /**
     * Get duration until shutdown
     *
     * @return duration until shutdown
     */
    public Duration getMaintenanceAt() {
        return this.maintenanceAt;
    }

    /**
     * Get maintenance shutdown status
     *
     * @return the maintenance shutdown status
     */
    public boolean isMaintenance() {
        return this.isMaintenanceShutdown;
    }

    /**
     * Gets the instance
     *
     * @return the instance
     */
    public static PlayerManager getInstance() {
        if (instance == null) {
            instance = new PlayerManager();
        }

        return instance;
    }

    public void showMutedAlert(Player player) {
        long uptime = (player.getStatisticManager().getLongValue(PlayerStatistic.MUTE_EXPIRES_AT) - DateUtil.getCurrentTimeSeconds()) * 1000;
        long days = (uptime / (1000 * 60 * 60 * 24));
        long hours = (uptime - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);

        player.send(new ALERT("You are currently muted. Your mute will expire in " + days + " day(s) and " + hours + " hours(s)"));
    }
}
