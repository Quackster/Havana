package org.alexdev.http.controller.homes.widgets;

import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.messenger.MessengerUser;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.http.dao.WidgetDao;
import org.alexdev.http.game.homes.Widget;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FriendsWidgetController {

    @PostMapping("/myhabbo/avatarlist/paging")
    public String friendSearchPaging(
            @RequestParam(value = "widgetId", defaultValue = "0") int widgetId,
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(value = "searchString", defaultValue = "") String searchString,
            Model model) {

        if (pageNumber <= 0) {
            pageNumber = 1;
        }

        Widget widget = WidgetDao.getWidget(widgetId);

        if (widget == null) {
            return "";
        }

        var pages = searchString.isBlank() ? widget.getFriendsPages() : widget.getFriendsPagesSearch(searchString);
        List<MessengerUser> friendsList = widget.getFriendsList(searchString, pageNumber);

        model.addAttribute("sticker", widget);
        model.addAttribute("pages", pages);
        model.addAttribute("friends", widget.getFriendsAmount());
        model.addAttribute("friendsList", friendsList);
        model.addAttribute("currentPage", pageNumber);
        return "homes/widget/habblet/friendsearchpaging";
    }

    @PostMapping("/myhabbo/avatarlist/info")
    public String avatarInfo(
            @RequestParam(value = "anAccountId", defaultValue = "0") int userId,
            Model model) {

        PlayerDetails playerDetails = PlayerDao.getDetails(userId);

        if (playerDetails == null) {
            return "";
        }

        model.addAttribute("avatar", playerDetails);
        return "homes/widget/habblet/avatarinfo";
    }
}
