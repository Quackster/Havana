package org.alexdev.http.controller.groups;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.GroupDao;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.groups.Group;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.http.util.RconUtil;
import org.alexdev.http.util.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class GroupFavouriteController {

    @PostMapping("/groups/favourite/confirm")
    public String confirmSelectFavourite(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        String groupName = GroupDao.getGroupName(groupId);

        if (groupName == null) {
            return "";
        }

        model.addAttribute("groupName", groupName);
        return "groups/favourite/confirm_select_favourite";
    }

    @PostMapping("/groups/favourite/select")
    @ResponseBody
    public String selectFavourite(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);
        Group group = GroupDao.getGroup(groupId);

        if (group == null || !group.isMember(userId)) {
            return "";
        }

        PlayerDao.saveFavouriteGroup(userId, groupId);
        RconUtil.sendCommand(RconHeader.REFRESH_GROUP_PERMS, new HashMap<>() {{
            put("userId", String.valueOf(userId));
        }});

        return "OK";
    }

    @PostMapping("/groups/favourite/confirmdeselect")
    public String confirmDeselectFavourite(HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        return "groups/favourite/confirm_deselect_favourite";
    }

    @PostMapping("/groups/favourite/deselect")
    @ResponseBody
    public String deselectFavourite(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);
        Group group = GroupDao.getGroup(groupId);

        if (group == null || !group.isMember(userId)) {
            return "";
        }

        PlayerDao.saveFavouriteGroup(userId, 0);

        RconUtil.sendCommand(RconHeader.REFRESH_GROUP_PERMS, new HashMap<>() {{
            put("userId", String.valueOf(userId));
        }});

        return "OK";
    }
}
