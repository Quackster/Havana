package org.alexdev.http.controller.habblet;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.AlertsDao;
import org.alexdev.havana.dao.mysql.PlayerStatisticsDao;
import org.alexdev.havana.game.alerts.AccountAlert;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;
import org.alexdev.havana.game.player.statistics.PlayerStatisticManager;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.http.game.account.BeginnerGiftManager;
import org.alexdev.http.util.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class FeedController {

    @PostMapping("/myhabbo/removeFeedItem")
    @ResponseBody
    public String removeFeedItem(
            @RequestParam(value = "feedItemIndex", defaultValue = "-1") int feedItemIndex,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        if (feedItemIndex != -1) {
            int userId = SessionHelper.getUserId(session);
            List<AccountAlert> accountAlerts = AlertsDao.getAlerts(userId);

            try {
                AccountAlert alert = accountAlerts.get(feedItemIndex);
                AlertsDao.deleteAlert(userId, alert.getId());
            } catch (Exception ex) {
                // Ignore
            }
        }

        return "";
    }

    @PostMapping("/myhabbo/nextgift")
    public String nextGift(HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);
        var statistics = new PlayerStatisticManager(playerDetails.getId(), PlayerStatisticsDao.getStatistics(playerDetails.getId()));

        model.addAttribute("newbieRoomLayout", statistics.getIntValue(PlayerStatistic.NEWBIE_ROOM_LAYOUT));
        model.addAttribute("newbieNextGift", statistics.getIntValue(PlayerStatistic.NEWBIE_GIFT));

        if (statistics.getIntValue(PlayerStatistic.NEWBIE_ROOM_LAYOUT) > 0 && statistics.getIntValue(PlayerStatistic.NEWBIE_GIFT) > 0) {
            int seconds = statistics.getIntValue(PlayerStatistic.NEWBIE_GIFT_TIME) - DateUtil.getCurrentTimeSeconds();

            if (BeginnerGiftManager.progress(playerDetails, statistics)) {
                seconds = statistics.getIntValue(PlayerStatistic.NEWBIE_GIFT_TIME) - DateUtil.getCurrentTimeSeconds();
                model.addAttribute("newbieNextGift", statistics.getIntValue(PlayerStatistic.NEWBIE_GIFT));
            }

            if (seconds < 0) {
                seconds = 0;
            }

            model.addAttribute("newbieGiftSeconds", seconds);
        }

        return "habblet/nextgift";
    }

    @PostMapping("/myhabbo/giftqueue/hide")
    @ResponseBody
    public String giftQueueHide(HttpSession session) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);
        int nextGift = (int) PlayerStatisticsDao.getStatisticLong(userId, PlayerStatistic.NEWBIE_GIFT);

        if (nextGift == 3) {
            PlayerStatisticsDao.updateStatistic(userId, PlayerStatistic.NEWBIE_GIFT, 4);
        }

        return "";
    }
}
