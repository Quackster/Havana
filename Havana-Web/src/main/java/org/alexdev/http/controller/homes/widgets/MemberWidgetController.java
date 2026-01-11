package org.alexdev.http.controller.homes.widgets;

import org.alexdev.havana.dao.mysql.GroupDao;
import org.alexdev.havana.game.groups.GroupMember;
import org.alexdev.http.dao.WidgetDao;
import org.alexdev.http.game.homes.Widget;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MemberWidgetController {

    @PostMapping("/myhabbo/memberlist/paging")
    public String memberSearchPaging(
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

        var pages = widget.getMembersPages();
        List<GroupMember> memberList = widget.getMembersList(searchString, pageNumber);

        model.addAttribute("sticker", widget);
        model.addAttribute("pages", pages);
        model.addAttribute("members", widget.getMembersAmount());
        model.addAttribute("membersList", memberList);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("group", GroupDao.getGroup(widget.getGroupId()));
        return "homes/widget/habblet/membersearchpaging";
    }
}
