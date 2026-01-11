package org.alexdev.http.controller.habblet;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.groups.Group;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.controller.site.MinimailController;
import org.alexdev.http.dao.CommunityDao;
import org.alexdev.http.server.Watchdog;
import org.alexdev.http.util.RconUtil;
import org.alexdev.http.util.SessionHelper;
import org.alexdev.http.util.XSSUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.function.ToIntFunction;

@Controller
public class ProxyHabbletController {

    @GetMapping("/components/habbletProxy")
    @ResponseBody
    public String moreInfo(
            @RequestParam(value = "hid", defaultValue = "") String hid,
            HttpSession session,
            Model model) {

        if (hid.isEmpty()) {
            return "";
        }

        if (hid.equals("h21")) {
            return "\n" +
                    "<div id=\"staffpicks-rooms-habblet-list-container\" class=\"habblet-list-container groups-list\">\n" +
                    "    <ul class=\"habblet-list\">\n" +
                    "\n" +
                    "        <li class=\"even room-occupancy-2\" roomid=\"1\">\n" +
                    "            <div>\n" +
                    "                <span class=\"room-name\"><a href=\"http://localhost/client?forwardId=2&amp;roomId=1\" onclick=\"HabboClient.roomForward(this, '1', 'private'); return false;\" target=\"client\">Room name</a></span>\n" +
                    "                <span class=\"room-owner\"><a href=\"http://localhost/home/Alex\">Alex</a></span>                \n" +
                    "\t\t\t\t<p>test</p>\n" +
                    "            </div>\n" +
                    "        </li>\n" +
                    "    </ul>\n" +
                    "</div>\n" +
                    "\n";
        }

        if (hid.equals("h122")) {
            int limit = GameConfiguration.getInstance().getInteger("hot.groups.community.limit");

            var hotGroups = CommunityDao.getHotGroups(limit, 0);
            var hotSortedGroups = new ArrayList<>(hotGroups.keySet());
            hotSortedGroups.sort(Comparator.comparingInt((ToIntFunction<Group>) hotGroups::get).reversed());

            var hotHiddenGroups = CommunityDao.getHotGroups(limit, limit);
            var hotHiddenSortedGroups = new ArrayList<>(hotHiddenGroups.keySet());
            hotHiddenSortedGroups.sort(Comparator.comparingInt((ToIntFunction<Group>) hotHiddenGroups::get).reversed());

            model.addAttribute("hotGroups", hotSortedGroups);
            model.addAttribute("hotHiddenGroups", hotHiddenSortedGroups);
            return "habblet/community_hot_groups";
        }

        if (hid.equals("h120")) {
            model.addAttribute("highestRatedRooms", RoomDao.getHighestRatedRooms(5, 0));
            model.addAttribute("highestHiddenRatedRooms", RoomDao.getHighestRatedRooms(5, 5));
            return "habblet/showMoreRooms";
        }

        if (hid.equals("h24")) {
            model.addAttribute("tagCloud", Watchdog.TAG_CLOUD_20);
            return "habblet/tagList";
        }

        if (hid.equals("groups")) {
            var hotGroups = CommunityDao.getHotGroups(GameConfiguration.getInstance().getInteger("hot.groups.limit"), 0);
            var sortedGroups = new ArrayList<>(hotGroups.keySet());
            sortedGroups.sort(Comparator.comparingInt((ToIntFunction<Group>) hotGroups::get).reversed());

            model.addAttribute("groups", sortedGroups);
            return "habblet/hot_groups";
        }

        return "";
    }

    @GetMapping("/components/minimailHabblet")
    public String minimail(
            @RequestParam(value = "habbletKey", defaultValue = "") String habbletKey,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        if (habbletKey.equalsIgnoreCase("news")) {
            return "habblet/news_habblet";
        }

        session.setAttribute("minimailLabel", "inbox");
        MinimailController.appendMessages(session, model, true, false, false, false, false, false);
        model.addAttribute("minimailClient", true);

        return "habblet/minimail";
    }

    @PostMapping("/credits/clearhand")
    @ResponseBody
    public String clearHand(
            @RequestParam(value = "xss_key", defaultValue = "") String xssKey,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        if (!XSSUtil.verifyKey(session, "/credits", xssKey)) {
            return "Failed to securely verify request";
        }

        int userId = SessionHelper.getUserId(session);
        ItemDao.deleteHandItems(userId);

        RconUtil.sendCommand(RconHeader.REFRESH_HAND, new HashMap<>() {{
            put("userId", userId);
        }});

        return "";
    }

    @PostMapping("/security/token_generate")
    @ResponseBody
    public String tokenGenerate(HttpSession session) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        String uuid = "token-" + UUID.randomUUID();
        session.setAttribute("authenticationToken", uuid);

        return uuid;
    }
}
