package org.alexdev.http.controller.habblet;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.dao.mysql.PlayerStatisticsDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;
import org.alexdev.http.util.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoomSelectionController {

    @PostMapping("/myhabbo/roomselection/confirm")
    public String confirm(HttpSession session) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        return "habblet/roomselectionConfirm";
    }

    @PostMapping("/myhabbo/roomselection/create")
    @ResponseBody
    public String create(
            @RequestParam(value = "roomType", defaultValue = "-1") int roomType,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        PlayerDetails playerDetails = PlayerDao.getDetails(SessionHelper.getUserId(session));

        if (!playerDetails.canSelectRoom()) {
            return "";
        }

        if (roomType < 0 || roomType > 5) {
            return "";
        }

        return "";
    }

    @PostMapping("/myhabbo/roomselection/hide")
    @ResponseBody
    public String hide(HttpSession session) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        PlayerDetails playerDetails = PlayerDao.getDetails(SessionHelper.getUserId(session));

        if (!playerDetails.canSelectRoom()) {
            return "";
        }

        playerDetails.setSelectedRoomId(-1);

        PlayerDao.saveSelectedRoom(playerDetails.getId(), -1);
        PlayerStatisticsDao.updateStatistic(playerDetails.getId(), PlayerStatistic.NEWBIE_ROOM_LAYOUT, -1);

        return "";
    }
}
