package org.alexdev.http.controller.site;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.GroupDao;
import org.alexdev.havana.dao.mysql.MessengerDao;
import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.messenger.MessengerUser;
import org.alexdev.http.util.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
public class QuickmenuController {

    @GetMapping("/quickmenu/groups")
    public String groups(HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);
        var groups = GroupDao.getJoinedGroups(userId);

        model.addAttribute("groups", groups);
        return "quickmenu/groups";
    }

    @GetMapping("/quickmenu/friends")
    public String friends(HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);
        var friends = MessengerDao.getFriends(userId);

        var friendsOnline = friends.values().stream()
                .filter(MessengerUser::isOnline)
                .sorted(Comparator.comparingLong(MessengerUser::getLastOnline).reversed())
                .limit(10)
                .collect(Collectors.toList());

        var friendsOffline = friends.values().stream()
                .filter(user -> !user.isOnline())
                .sorted(Comparator.comparingLong(MessengerUser::getLastOnline).reversed())
                .limit(10)
                .collect(Collectors.toList());

        model.addAttribute("onlineFriends", friendsOnline);
        model.addAttribute("offlineFriends", friendsOffline);
        return "quickmenu/friends_all";
    }

    @GetMapping("/quickmenu/rooms")
    public String rooms(HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);
        model.addAttribute("rooms", RoomDao.getRoomsByUserId(userId));
        return "quickmenu/rooms";
    }
}
