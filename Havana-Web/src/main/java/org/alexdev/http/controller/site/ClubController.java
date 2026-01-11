package org.alexdev.http.controller.site;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.PlayerStatisticsDao;
import org.alexdev.havana.game.catalogue.CatalogueManager;
import org.alexdev.havana.game.club.ClubSubscription;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;
import org.alexdev.havana.game.player.statistics.PlayerStatisticManager;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.http.util.SessionHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class ClubController {

    @GetMapping("/credits/club")
    public String club(HttpSession session, Model model) {
        session.setAttribute("page", "credits");
        renderClub(session, model);
        return "club";
    }

    @GetMapping("/club")
    public String clubAlt(HttpSession session, Model model) {
        session.setAttribute("page", "credits");
        renderClub(session, model);
        return "club";
    }

    @GetMapping("/club/tryout")
    public String clubTryout(HttpSession session, Model model) {
        session.setAttribute("page", "credits");

        for (int i = 0; i < 3; i++) {
            var choiceData = ClubSubscription.getChoiceData(i + 1);
            model.addAttribute("clubChoiceCredits" + (i + 1), choiceData.getKey());
            model.addAttribute("clubChoiceDays" + (i + 1), choiceData.getValue());
        }

        if (SessionHelper.isAuthenticated(session)) {
            PlayerDetails playerDetails = SessionHelper.getPlayer(session);
            PlayerStatisticManager statisticManager = new PlayerStatisticManager(
                    playerDetails.getId(), PlayerStatisticsDao.getStatistics(playerDetails.getId()));

            if (playerDetails.hasClubSubscription()) {
                model.addAttribute("hcDays",
                        TimeUnit.SECONDS.toDays(playerDetails.getClubExpiration() - DateUtil.getCurrentTimeSeconds()));

                int days = (int) TimeUnit.SECONDS.toDays(statisticManager.getLongValue(PlayerStatistic.CLUB_MEMBER_TIME));
                int sinceMonths = days > 0 ? days / 31 : 0;
                model.addAttribute("hcSinceMonths", sinceMonths);
            }

            model.addAttribute("figure", playerDetails.getFigure());
            model.addAttribute("sex", playerDetails.getSex());
        }

        return "club_tryout";
    }

    @PostMapping("/habblet/habboclub/gift")
    @ResponseBody
    public String habboClubGift(
            @RequestParam(value = "month", required = false) String monthStr,
            @RequestParam(value = "catalogpage", required = false) String catalogpageStr,
            HttpSession session,
            Model model) {

        if (monthStr == null || catalogpageStr == null) {
            return "";
        }

        if (!StringUtils.isNumeric(monthStr) || !StringUtils.isNumeric(catalogpageStr)) {
            return "";
        }

        int month = Integer.parseInt(monthStr);
        int catalogpage = Integer.parseInt(catalogpageStr);

        appendGiftData(session, model, month, catalogpage);

        // This would need template rendering - for now return empty
        // In full implementation, would render the habblet/habboclubgift template
        return "";
    }

    public void renderClub(HttpSession session, Model model) {
        if (SessionHelper.isAuthenticated(session)) {
            PlayerDetails playerDetails = SessionHelper.getPlayer(session);
            var statistics = new PlayerStatisticManager(
                    playerDetails.getId(), PlayerStatisticsDao.getStatistics(playerDetails.getId()));
            ClubSubscription.countMemberDays(playerDetails, statistics);
        }

        for (int i = 0; i < 3; i++) {
            var choiceData = ClubSubscription.getChoiceData(i + 1);
            model.addAttribute("clubChoiceCredits" + (i + 1), choiceData.getKey());
            model.addAttribute("clubChoiceDays" + (i + 1), choiceData.getValue());
        }

        if (SessionHelper.isAuthenticated(session)) {
            PlayerDetails playerDetails = SessionHelper.getPlayer(session);
            PlayerStatisticManager statisticManager = new PlayerStatisticManager(
                    playerDetails.getId(), PlayerStatisticsDao.getStatistics(playerDetails.getId()));

            if (playerDetails.hasClubSubscription()) {
                model.addAttribute("hcDays",
                        TimeUnit.SECONDS.toDays(playerDetails.getClubExpiration() - DateUtil.getCurrentTimeSeconds()));

                int days = (int) TimeUnit.SECONDS.toDays(statisticManager.getLongValue(PlayerStatistic.CLUB_MEMBER_TIME));
                int sinceMonths = days > 0 ? days / 31 : 0;
                model.addAttribute("hcSinceMonths", sinceMonths);
            }
        }

        Integer lastClubGiftMonth = (Integer) session.getAttribute("lastClubGiftMonth");
        appendGiftData(session, model, lastClubGiftMonth != null ? lastClubGiftMonth : 1, 0);
    }

    private void appendGiftData(HttpSession session, Model model, int month, int catalogpage) {
        var giftOrder = new ArrayList<>(Arrays.asList(ClubSubscription.getGiftOrder()));
        giftOrder.add(0, "club_sofa");

        int position = month - 1;

        if (position >= giftOrder.size()) {
            position = 0;
        }

        var nextSpriteGift = giftOrder.get(0);

        try {
            nextSpriteGift = giftOrder.get(position);
        } catch (Exception ex) {
        }

        List<Integer> pages = new ArrayList<>();

        catalogpage = 0;

        if (month >= 5 && month <= 8) catalogpage = 1;
        if (month >= 9 && month <= 12) catalogpage = 2;
        if (month >= 13 && month <= 16) catalogpage = 3;
        if (month >= 17 && month <= 20) catalogpage = 4;
        if (month >= 21 && month <= 23) catalogpage = 5;

        switch (catalogpage) {
            case 0 -> { pages.add(1); pages.add(2); pages.add(3); pages.add(4); pages.add(5); }
            case 1 -> { pages.add(5); pages.add(6); pages.add(7); pages.add(8); pages.add(9); }
            case 2 -> { pages.add(9); pages.add(10); pages.add(11); pages.add(12); pages.add(13); }
            case 3 -> { pages.add(13); pages.add(14); pages.add(15); pages.add(16); pages.add(17); }
            case 4 -> { pages.add(17); pages.add(18); pages.add(19); pages.add(20); pages.add(21); }
            case 5 -> { pages.add(19); pages.add(20); pages.add(21); pages.add(22); pages.add(23); }
        }

        var definition = CatalogueManager.getInstance().getCatalogueItem(nextSpriteGift);

        model.addAttribute("pages", pages);
        model.addAttribute("currentPage", month);
        model.addAttribute("lastPage", giftOrder.size());

        if (definition.getDefinition() == null) {
            model.addAttribute("item", definition.getPackages().get(0).getDefinition());
        } else {
            model.addAttribute("item", definition.getDefinition());
        }

        session.setAttribute("lastClubGiftMonth", month);
    }
}
