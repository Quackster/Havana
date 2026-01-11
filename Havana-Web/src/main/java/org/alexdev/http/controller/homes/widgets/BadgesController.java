package org.alexdev.http.controller.homes.widgets;

import org.alexdev.havana.game.badges.Badge;
import org.alexdev.http.dao.WidgetDao;
import org.alexdev.http.game.homes.Widget;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BadgesController {

    @PostMapping("/myhabbo/badgelist/paging")
    public String badgePaging(
            @RequestParam(value = "widgetId", defaultValue = "0") int widgetId,
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
            Model model) {

        Widget widget = WidgetDao.getWidget(widgetId);

        if (widget == null) {
            return "";
        }

        var pages = widget.getBadgeList();
        boolean showLast = true;

        List<Badge> badgeList = pages.get(0);

        if (pageNumber > pages.size()) {
            pageNumber = pages.size();
        }

        if (pageNumber >= pages.size()) {
            showLast = false;
        }

        if (pageNumber <= 0) {
            pageNumber = 1;
        }

        if (pages.containsKey(pageNumber - 1)) {
            badgeList = pages.get(pageNumber - 1);
        }

        model.addAttribute("sticker", widget);
        model.addAttribute("pages", pages.size());
        model.addAttribute("showLast", showLast);
        model.addAttribute("badgeList", badgeList);
        model.addAttribute("currentPage", pageNumber);
        return "homes/widget/habblet/badgepaging";
    }
}
