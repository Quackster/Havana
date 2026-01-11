package org.alexdev.http.scheduler;

import org.alexdev.havana.dao.mysql.*;
import org.alexdev.havana.game.catalogue.CatalogueManager;
import org.alexdev.havana.game.events.Event;
import org.alexdev.havana.game.groups.Group;
import org.alexdev.havana.game.item.ItemManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.havana.util.config.ServerConfiguration;
import org.alexdev.http.dao.*;
import org.alexdev.http.game.groups.DiscussionTopic;
import org.alexdev.http.game.news.NewsArticle;
import org.alexdev.http.game.news.NewsDateKey;
import org.alexdev.http.util.config.WebSettingsConfigWriter;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Component
public class WatchdogScheduler {
    private static final Logger logger = LoggerFactory.getLogger(WatchdogScheduler.class);

    // Cached data
    public static List<Group> STAFF_PICK_GROUPS = new ArrayList<>();
    public static List<Group> RECOMMENDED_GROUPS = new ArrayList<>();
    public static List<Event> EVENTS = new ArrayList<>();
    public static List<Room> RECOMMENDED_ROOMS = new ArrayList<>();
    public static List<Room> HIDDEN_RECOMMENDED_ROOMS = new ArrayList<>();
    public static List<DiscussionTopic> RECENT_DISCUSSIONS = new ArrayList<>();
    public static List<DiscussionTopic> NEXT_RECENT_DISCUSSIONS = new ArrayList<>();
    public static List<Pair<String, Integer>> TAG_CLOUD_10 = new ArrayList<>();
    public static List<Pair<String, Integer>> TAG_CLOUD_20 = new ArrayList<>();
    public static List<NewsArticle> NEWS = new ArrayList<>();
    public static List<NewsArticle> NEWS_STAFF = new ArrayList<>();

    public static int USERS_ONLINE;
    public static boolean IS_SERVER_ONLINE;
    public static int LAST_VISITS;

    private boolean canResetUsersFlag = true;
    private boolean hasResetUsersFlag = false;

    /**
     * Publish future news articles every 5 minutes
     */
    @Scheduled(fixedRate = 300000) // 5 minutes
    public void publishFutureArticles() {
        try {
            NewsDao.publishFutureArticles();
        } catch (Exception ex) {
            logger.error("Error publishing future articles", ex);
        }
    }

    /**
     * Batch cleaning and email recovery code removal every hour
     */
    @Scheduled(fixedRate = 3600000) // 1 hour
    public void hourlyCleanup() {
        try {
            if (GameConfiguration.getInstance().getBoolean("email.smtp.enable")) {
                EmailDao.removeRecoveryCodeBatch();
            }
            batchClean();
        } catch (Exception ex) {
            logger.error("Error during hourly cleanup", ex);
        }
    }

    /**
     * Reset item and catalogue managers every hour
     */
    @Scheduled(fixedRate = 3600000) // 1 hour
    public void hourlyResetManagers() {
        try {
            ItemManager.reset();
            CatalogueManager.reset();
        } catch (Exception ex) {
            logger.error("Error resetting managers", ex);
        }
    }

    /**
     * Reload game configuration every 30 seconds
     */
    @Scheduled(fixedRate = 30000) // 30 seconds
    public void reloadConfiguration() {
        GameConfiguration.getInstance(new WebSettingsConfigWriter());
    }

    /**
     * Update cached data every 30 seconds
     */
    @Scheduled(fixedRate = 30000) // 30 seconds
    public void updateCachedData() {
        updateServerStatus();
        updateEvents();
        updateGroups();
        updateRooms();
        updateDiscussions();
        updateTags();
        updateNews();
    }

    private void updateServerStatus() {
        try {
            IS_SERVER_ONLINE = isServerOnline(
                    ServerConfiguration.getString("rcon.ip"),
                    ServerConfiguration.getInteger("rcon.port")
            );
            USERS_ONLINE = Integer.parseInt(SettingsDao.getSetting("players.online"));
            LAST_VISITS = SiteDao.getLastVisits();

            if (!IS_SERVER_ONLINE) {
                USERS_ONLINE = 0;

                if (canResetUsersFlag) {
                    canResetUsersFlag = false;
                    hasResetUsersFlag = true;
                }
            } else {
                canResetUsersFlag = true;
            }

            if (hasResetUsersFlag) {
                hasResetUsersFlag = false;
                PlayerDao.resetOnline();
            }
        } catch (Exception ex) {
            logger.error("Error updating server status", ex);
        }
    }

    private void updateEvents() {
        try {
            EVENTS = EventsDao.getEvents();
        } catch (Exception ex) {
            logger.error("Error updating events", ex);
        }
    }

    private void updateGroups() {
        try {
            RECOMMENDED_GROUPS = RecommendedDao.getRecommendedGroups(false);
        } catch (Exception ex) {
            logger.error("Error updating recommended groups", ex);
        }

        try {
            STAFF_PICK_GROUPS = RecommendedDao.getRecommendedGroups(true);
        } catch (Exception ex) {
            logger.error("Error updating staff pick groups", ex);
        }
    }

    private void updateRooms() {
        try {
            RECOMMENDED_ROOMS = RoomDao.getRecommendedRooms(5, 0);
        } catch (Exception ex) {
            logger.error("Error updating recommended rooms", ex);
        }

        try {
            HIDDEN_RECOMMENDED_ROOMS = RoomDao.getRecommendedRooms(5, 5);
        } catch (Exception ex) {
            logger.error("Error updating hidden recommended rooms", ex);
        }
    }

    private void updateDiscussions() {
        try {
            RECENT_DISCUSSIONS = CommunityDao.getRecentDiscussions(10, 0);
        } catch (Exception ex) {
            logger.error("Error updating recent discussions", ex);
        }

        try {
            NEXT_RECENT_DISCUSSIONS = CommunityDao.getRecentDiscussions(10, 10);
        } catch (Exception ex) {
            logger.error("Error updating next recent discussions", ex);
        }
    }

    private void updateTags() {
        try {
            TAG_CLOUD_10 = TagDao.getPopularTags(10);
        } catch (Exception ex) {
            logger.error("Error updating tag cloud 10", ex);
        }

        try {
            TAG_CLOUD_20 = TagDao.getPopularTags(20);
        } catch (Exception ex) {
            logger.error("Error updating tag cloud 20", ex);
        }
    }

    private void updateNews() {
        try {
            NEWS = NewsDao.getTop(NewsDateKey.ALL, 5, false, List.of(), 0);
        } catch (Exception ex) {
            logger.error("Error updating news", ex);
        }

        try {
            NEWS_STAFF = NewsDao.getTop(NewsDateKey.ALL, 5, true, List.of(), 0);
        } catch (Exception ex) {
            logger.error("Error updating staff news", ex);
        }
    }

    private void batchClean() {
        if (GameConfiguration.getInstance().getInteger("delete.chatlogs.after.x.age") > 0) {
            LogDao.deleteChatLogs(GameConfiguration.getInstance().getInteger("delete.chatlogs.after.x.age"));
        }

        if (GameConfiguration.getInstance().getInteger("delete.iplogs.after.x.age") > 0) {
            LogDao.deleteIpAddressLogs(GameConfiguration.getInstance().getInteger("delete.iplogs.after.x.age"));
        }

        if (GameConfiguration.getInstance().getInteger("delete.tradelogs.after.x.age") > 0) {
            LogDao.deleteTradeLogs(GameConfiguration.getInstance().getInteger("delete.tradelogs.after.x.age"));
        }
    }

    private boolean isServerOnline(String host, int port) {
        if (GameConfiguration.getInstance().getBoolean("hotel.check.online")) {
            return isHostOnline(host, port);
        } else {
            return true;
        }
    }

    private boolean isHostOnline(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
