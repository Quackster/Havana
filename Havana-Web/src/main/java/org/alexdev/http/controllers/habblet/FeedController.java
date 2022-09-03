package org.alexdev.http.controllers.habblet;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.havana.dao.mysql.AlertsDao;
import org.alexdev.havana.dao.mysql.PlayerStatisticsDao;
import org.alexdev.havana.game.alerts.AccountAlert;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;
import org.alexdev.havana.game.player.statistics.PlayerStatisticManager;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.http.game.account.BeginnerGiftManager;

import java.util.List;

public class FeedController {
    public static void removeFeedItem(WebConnection connection) {
        if (!connection.session().getBoolean("authenticated")) {
            connection.redirect("/");
            return;
        }

        int feedItemIndex = -1;

        try {
            feedItemIndex = connection.post().getInt("feedItemIndex");
        } catch (Exception ex) {

        }

        if (feedItemIndex != -1) {
            int userId = connection.session().getInt("user.id");
            List<AccountAlert> accountAlerts = AlertsDao.getAlerts(userId);

            try {
                AccountAlert alert = accountAlerts.get(feedItemIndex);
                AlertsDao.deleteAlert(userId, alert.getId());
            } catch (Exception ex) {

            }

            connection.send("");
        }
    }

    public static void nextgift(WebConnection connection) {
        if (!connection.session().getBoolean("authenticated")) {
            connection.send("");
            return;
        }

        var template = connection.template("habblet/nextgift");

        PlayerDetails playerDetails = (PlayerDetails) template.get("playerDetails");
        var statistics = new PlayerStatisticManager(playerDetails.getId(), PlayerStatisticsDao.getStatistics(playerDetails.getId()));

        template.set("newbieRoomLayout", statistics.getIntValue(PlayerStatistic.NEWBIE_ROOM_LAYOUT));
        template.set("newbieNextGift", statistics.getIntValue(PlayerStatistic.NEWBIE_GIFT));

        if (statistics.getIntValue(PlayerStatistic.NEWBIE_ROOM_LAYOUT) > 0 && statistics.getIntValue(PlayerStatistic.NEWBIE_GIFT) > 0) {
            int seconds = statistics.getIntValue(PlayerStatistic.NEWBIE_GIFT_TIME) - DateUtil.getCurrentTimeSeconds();

            if (BeginnerGiftManager.progress(playerDetails, statistics)) {
                seconds = statistics.getIntValue(PlayerStatistic.NEWBIE_GIFT_TIME) - DateUtil.getCurrentTimeSeconds();
                template.set("newbieNextGift", statistics.getIntValue(PlayerStatistic.NEWBIE_GIFT));
            }

            if (seconds < 0) {
                seconds = 0;
            }

            template.set("newbieGiftSeconds", seconds);
        }

        template.render();
    }

    public static void giftqueueHide(WebConnection connection) {
        if (!connection.session().getBoolean("authenticated")) {
            connection.send("");
            return;
        }

        int userId = connection.session().getInt("user.id");

        int nextGift = (int) PlayerStatisticsDao.getStatisticLong(userId, PlayerStatistic.NEWBIE_GIFT);

        if (nextGift == 3) {
            PlayerStatisticsDao.updateStatistic(userId, PlayerStatistic.NEWBIE_GIFT, 4);
        }

        connection.send("");
    }
}
