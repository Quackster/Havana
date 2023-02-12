package org.alexdev.http.server;

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

import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Watchdog implements Runnable {
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

    public static int USERS_ONLNE;
    public static boolean IS_IMAGER_ONLINE;
    public static boolean IS_SERVER_ONLINE;
    public static int LAST_VISITS;

    private boolean canResetUsersFlag = true;
    private boolean hasResetUsersFlag = false;

    private AtomicInteger counter;

    public Watchdog() {
        this.counter = new AtomicInteger(0);
    }

    @Override
    public void run() {
        if (this.counter.get() % TimeUnit.MINUTES.toSeconds(5) == 0) {
            try {
            NewsDao.publishFutureArticles();
        } catch (Exception ex) {

        }
        }

        if (this.counter.get() % TimeUnit.HOURS.toSeconds(1) == 0) {
            try {
            if (GameConfiguration.getInstance().getBoolean("email.smtp.enable")) {
                EmailDao.removeRecoveryCodeBatch();
            }

            batchClean();

        } catch (Exception ex) {

        }
        }


        if (this.counter.get() % 30 == 0) {
            try {

                String imagerPath =  GameConfiguration.getInstance().getString("site.imaging.endpoint");

                if (!imagerPath.isBlank()) {
                    try {
                        URL url = new URL(imagerPath);
                        String hostname = url.getHost();
                        int port = url.getPort() == -1 ? 80 : url.getPort();

                        IS_IMAGER_ONLINE = isServerOnline(hostname, port);
                    } catch (MalformedURLException e) { }

                }

                IS_SERVER_ONLINE = isServerOnline(ServerConfiguration.getString("rcon.ip"), ServerConfiguration.getInteger("rcon.port"));
                USERS_ONLNE = Integer.parseInt(SettingsDao.getSetting("players.online"));
                LAST_VISITS = SiteDao.getLastVisits();

                // Checks to make sure the user count is set as 0 once while offline.
                if (!IS_SERVER_ONLINE) {
                    USERS_ONLNE = 0;

                    if (this.canResetUsersFlag) {
                        this.canResetUsersFlag = false;
                        this.hasResetUsersFlag = true;
                    }
                } else {
                    this.canResetUsersFlag = true;
                }

                if (this.hasResetUsersFlag) {
                    this.hasResetUsersFlag = false;
                    PlayerDao.resetOnline();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                EVENTS = EventsDao.getEvents();
            } catch (Exception ex) {

            }

            try {
                RECOMMENDED_GROUPS = RecommendedDao.getRecommendedGroups(false);
            } catch (Exception ex) {

            }

            try {
                STAFF_PICK_GROUPS = RecommendedDao.getRecommendedGroups(true);
            } catch (Exception ex) {

            }

            try {
                RECOMMENDED_ROOMS = RoomDao.getRecommendedRooms(5, 0);
            } catch (Exception ex) {

            }

            try {
                HIDDEN_RECOMMENDED_ROOMS = RoomDao.getRecommendedRooms(5, 5);
            } catch (Exception ex) {

            }

            try {
                RECENT_DISCUSSIONS = CommunityDao.getRecentDiscussions(10, 0);
            } catch (Exception ex) {

            }

            try {
                NEXT_RECENT_DISCUSSIONS = CommunityDao.getRecentDiscussions(10, 10);
            } catch (Exception ex) {

            }

            try {
                TAG_CLOUD_10 = TagDao.getPopularTags(10);
            } catch (Exception ex) {

            }

            try {
                TAG_CLOUD_20 = TagDao.getPopularTags(20);
            } catch (Exception ex) {

            }

            try {
                NEWS = NewsDao.getTop(NewsDateKey.ALL, 5, false, List.of(), 0);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                NEWS_STAFF = NewsDao.getTop(NewsDateKey.ALL, 5, true, List.of(), 0);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (this.counter.get() % TimeUnit.HOURS.toSeconds(1) == 0) {
            try {
            ItemManager.reset();
            CatalogueManager.reset();
            } catch (Exception ex) {

            }
        }

        // Reload config every 30 seconds
        if (this.counter.get() % TimeUnit.SECONDS.toSeconds(30) == 0) {
            GameConfiguration.getInstance(new WebSettingsConfigWriter());
        }

        this.resetCounter();
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

    private void resetCounter() {
        try {
            this.counter.incrementAndGet();
        } catch (Exception ex) {
            this.counter.set(0);
        }
    }

    public boolean isServerOnline(String host, int port) {
        if (GameConfiguration.getInstance().getBoolean("hotel.check.online")) {
            return this.isHostOnline(host, port);
        } else {
            return true;
        }
    }

    public boolean isHostOnline(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
