package org.alexdev.http.controller.habblet;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.AlertsDao;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.dao.mysql.PlayerStatisticsDao;
import org.alexdev.havana.game.alerts.AlertType;
import org.alexdev.havana.game.club.ClubSubscription;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.util.RconUtil;
import org.alexdev.http.util.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Controller
public class HabboClubController {

    @PostMapping("/credits/habboclub/confirm")
    public String confirm(
            @RequestParam(value = "optionNumber", defaultValue = "1") int optionNumber,
            HttpSession session,
            Model model) {

        if (optionNumber < 0 || optionNumber > 4) {
            optionNumber = 1;
        }

        var choiceData = ClubSubscription.getChoiceData(optionNumber);

        model.addAttribute("clubCredits", choiceData.getKey());
        model.addAttribute("clubDays", choiceData.getValue());
        model.addAttribute("clubType", optionNumber);

        return "habblet/habboClubConfirm";
    }

    @PostMapping("/credits/habboclub/subscribe")
    public String subscribe(
            @RequestParam(value = "optionNumber", defaultValue = "1") int optionNumber,
            HttpSession session,
            Model model) throws SQLException {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        if (optionNumber < 0 || optionNumber > 4) {
            optionNumber = 1;
        }

        var choiceData = ClubSubscription.getChoiceData(optionNumber);

        int credits = choiceData.getKey();
        int days = choiceData.getValue();

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        if (playerDetails.getCredits() < credits) {
            model.addAttribute("subscribeMsg", "You don't have enough credits to complete the subscription purchase.");
        } else {
            model.addAttribute("subscribeMsg", "Congratulations! You have successfully subscribed to " + GameConfiguration.getInstance().getString("site.name") + " Club.");

            boolean firstTime = (playerDetails.getFirstClubSubscription() == 0);

            ClubSubscription.subscribeClub(playerDetails, optionNumber);
            PlayerStatisticsDao.updateStatistic(playerDetails.getId(), PlayerStatistic.CLUB_MEMBER_TIME_UPDATED, DateUtil.getCurrentTimeSeconds() + ClubSubscription.getClubGiftSeconds());

            if (playerDetails.isOnline()) {
                RconUtil.sendCommand(RconHeader.REFRESH_CLUB, new HashMap<>() {{
                    put("userId", playerDetails.getId());
                }});

                if (firstTime) {
                    RconUtil.sendCommand(RconHeader.REFRESH_HAND, new HashMap<>() {{
                        put("userId", playerDetails.getId());
                    }});
                }
            }
        }

        return "habblet/habboClubSubscribe";
    }

    @PostMapping("/credits/habboclub/enddate")
    public String endDate(HttpSession session, Model model) {
        if (SessionHelper.isAuthenticated(session)) {
            PlayerDetails playerDetails = SessionHelper.getPlayer(session);

            if (playerDetails.hasClubSubscription()) {
                model.addAttribute("hcDays", TimeUnit.SECONDS.toDays(playerDetails.getClubExpiration() - DateUtil.getCurrentTimeSeconds()));
            }
        }

        return "habblet/habboClubEnddate";
    }

    @PostMapping("/credits/habboclub/reminder/remove")
    @ResponseBody
    public String reminderRemove(HttpSession session) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        PlayerDetails playerDetails = PlayerDao.getDetails(SessionHelper.getUserId(session));
        AlertsDao.disableAlerts(playerDetails.getId(), AlertType.HC_EXPIRED);

        return "";
    }
}
