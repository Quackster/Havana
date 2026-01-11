package org.alexdev.http.controller.housekeeping;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.BadgeDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.http.Routes;
import org.alexdev.http.game.housekeeping.HousekeepingManager;
import org.alexdev.http.util.RconUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/" + Routes.HOUSEKEEPING_PATH)
public class HousekeepingRoomBadgesController {

    @GetMapping("/room_badges")
    public String badgesGet(HttpSession session, Model model) {
        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "room_badges")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());
        model.addAttribute("roomBadges", RoomManager.getInstance().getRoomEntryBadges());
        model.addAttribute("util", new org.alexdev.http.util.HousekeepingUtil());

        session.removeAttribute("alertMessage");

        return "housekeeping/room_badges";
    }

    @PostMapping("/room_badges")
    public String badgesPost(
            @RequestParam Map<String, String> allParams,
            HttpSession session,
            Model model) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "room_badges")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());

        try {
            Map<Integer, List<String>> badges = new HashMap<>();

            for (var entry : allParams.entrySet()) {
                String key = entry.getKey();

                if (!key.startsWith("roombadge-id-")) {
                    continue;
                }

                String values = key.replace("roombadge-id-", "");

                int roomId = Integer.parseInt(allParams.getOrDefault("roomad-" + values + "-roomid", "0"));
                String badgeCode = allParams.getOrDefault("roomad-" + values + "-badge", "");

                if (!badges.containsKey(roomId)) {
                    badges.put(roomId, new ArrayList<>());
                }

                badges.get(roomId).add(badgeCode);
            }

            BadgeDao.updateBadges(badges);
            sendRoomBadgeUpdate();

            session.setAttribute("alertColour", "success");
            session.setAttribute("alertMessage", "All badge rooms have been saved successfully!");
        } catch (Exception ex) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "Error occurred, make sure the room ID is a valid number");
        }

        model.addAttribute("roomBadges", RoomManager.getInstance().getRoomEntryBadges());
        model.addAttribute("util", new org.alexdev.http.util.HousekeepingUtil());

        session.removeAttribute("alertMessage");

        return "housekeeping/room_badges";
    }

    @GetMapping("/room_badges/delete")
    public String deleteBadge(
            @RequestParam(value = "id", defaultValue = "") String id,
            HttpSession session,
            Model model) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "room_badges")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());

        if (id.isEmpty()) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "There was no badge selected to delete");
        } else {
            session.setAttribute("alertColour", "success");
            session.setAttribute("alertMessage", "Successfully deleted the badge");

            String[] data = id.split("_");
            BadgeDao.deleteRoomBadge(data[0], data[1]);
        }

        sendRoomBadgeUpdate();

        model.addAttribute("roomBadges", RoomManager.getInstance().getRoomEntryBadges());
        model.addAttribute("util", new org.alexdev.http.util.HousekeepingUtil());

        session.removeAttribute("alertMessage");

        return "housekeeping/room_badges";
    }

    @GetMapping("/room_badges/create")
    public String createBadgeGet(HttpSession session, Model model) {
        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "room_badges")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());

        session.removeAttribute("alertMessage");

        return "housekeeping/room_badges_create";
    }

    @PostMapping("/room_badges/create")
    public String createBadgePost(
            @RequestParam(value = "roomid", defaultValue = "0") int roomId,
            @RequestParam(value = "badgecode", defaultValue = "") String badgeCode,
            HttpSession session,
            Model model) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "room_badges")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());

        try {
            BadgeDao.createEntryBadge(roomId, badgeCode);

            session.setAttribute("alertColour", "success");
            session.setAttribute("alertMessage", "Successfully created the room entry badge");

            sendRoomBadgeUpdate();

            return "redirect:/" + Routes.HOUSEKEEPING_PATH + "/room_badges";
        } catch (Exception ex) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "Error occurred, make sure the room ID is a valid number");
        }

        session.removeAttribute("alertMessage");

        return "housekeeping/room_badges_create";
    }

    private void sendRoomBadgeUpdate() {
        RoomManager.getInstance().reloadBadges();
        RconUtil.sendCommand(RconHeader.REFRESH_ROOM_BADGES, new HashMap<>());
    }
}
