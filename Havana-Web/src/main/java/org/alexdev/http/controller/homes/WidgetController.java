package org.alexdev.http.controller.homes;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.GroupDao;
import org.alexdev.havana.game.groups.Group;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.http.dao.GroupEditDao;
import org.alexdev.http.dao.WidgetDao;
import org.alexdev.http.game.homes.Widget;
import org.alexdev.http.util.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WidgetController {

    @PostMapping("/myhabbo/widget/edit")
    public String editWidget(
            @RequestParam(value = "widgetId", defaultValue = "0") int widgetId,
            @RequestParam(value = "skinId", defaultValue = "0") int skinId,
            HttpSession session,
            HttpServletResponse response,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);
        boolean isGroupEdit = session.getAttribute("groupEditSession") != null;

        if (isGroupEdit) {
            int groupId = (Integer) session.getAttribute("groupEditSession");
            Group group = GroupDao.getGroup(groupId);

            if (group == null) {
                return "";
            }

            if (!GroupEditDao.hasSession(userId, group.getId())) {
                return "";
            }
        } else {
            if (session.getAttribute("homeEditSession") == null) {
                return "";
            }
        }

        Widget widget;

        if (isGroupEdit) {
            widget = WidgetDao.getGroupWidget(widgetId, (Integer) session.getAttribute("groupEditSession"));
        } else {
            widget = WidgetDao.getHomeWidget(userId, widgetId);
        }

        if (widget == null) {
            return "";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        if ((skinId == 7 || skinId == 8) && !playerDetails.hasClubSubscription()) {
            skinId = 1;
        }

        if (skinId == 9 && playerDetails.getRank().getRankId() < 5) {
            skinId = 1;
        }

        widget.setSkinId(skinId);
        widget.save();

        if (widget.getProduct().isGroupWidget() || widget.getProduct().isHomeWidget()) {
            response.setHeader("X-JSON", "{\"id\":\"" + widget.getId() + "\",\"cssClass\":\"w_skin_" + widget.getSkin() + "\",\"type\":\"widget\"}");
        }

        model.addAttribute("playerDetails", playerDetails);
        model.addAttribute("sticker", widget);
        return widget.getTemplateName();
    }

    @PostMapping("/myhabbo/sticker/place")
    public String placeSticker(
            @RequestParam(value = "selectedStickerId", defaultValue = "0") int widgetId,
            @RequestParam(value = "zindex", defaultValue = "0") int zindex,
            HttpSession session,
            HttpServletResponse response,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);
        boolean isGroupEdit = session.getAttribute("groupEditSession") != null;

        if (isGroupEdit) {
            int groupId = (Integer) session.getAttribute("groupEditSession");
            Group group = GroupDao.getGroup(groupId);

            if (group == null) {
                return "";
            }

            if (!GroupEditDao.hasSession(userId, group.getId())) {
                return "";
            }
        } else {
            if (session.getAttribute("homeEditSession") == null) {
                return "";
            }
        }

        if (zindex < 0 || zindex > 100) {
            zindex = 0;
        }

        Widget widget = WidgetDao.getInventoryWidget(userId, widgetId);
        widget.setX(20);
        widget.setY(30);
        widget.setZ(zindex);

        if (isGroupEdit) {
            widget.setGroupId((Integer) session.getAttribute("groupEditSession"));
        }

        widget.setPlaced(true);
        widget.save();

        response.setHeader("X-JSON", "[\"" + widget.getId() + "\"]");

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);
        model.addAttribute("playerDetails", playerDetails);
        model.addAttribute("sticker", widget);
        return widget.getTemplateName();
    }

    @PostMapping("/myhabbo/widget/place")
    public String placeWidget(
            @RequestParam(value = "widgetId", defaultValue = "0") int widgetId,
            @RequestParam(value = "zindex", defaultValue = "0") int zindex,
            HttpSession session,
            HttpServletResponse response,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);
        boolean isGroupEdit = session.getAttribute("groupEditSession") != null;

        if (isGroupEdit) {
            int groupId = (Integer) session.getAttribute("groupEditSession");
            Group group = GroupDao.getGroup(groupId);

            if (group == null) {
                return "";
            }

            if (!GroupEditDao.hasSession(userId, group.getId())) {
                return "";
            }
        } else {
            if (session.getAttribute("homeEditSession") == null) {
                return "";
            }
        }

        if (zindex < 0 || zindex > 100) {
            zindex = 0;
        }

        Widget widget;

        if (isGroupEdit) {
            widget = WidgetDao.getGroupWidget(widgetId, (Integer) session.getAttribute("groupEditSession"));
        } else {
            widget = WidgetDao.getHomeWidget(userId, widgetId);
        }

        widget.setX(10);
        widget.setY(10);
        widget.setZ(zindex);

        if (isGroupEdit) {
            widget.setGroupId((Integer) session.getAttribute("groupEditSession"));
        }

        widget.setPlaced(true);
        widget.save();

        response.setHeader("X-JSON", "[\"" + widget.getId() + "\"]");

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);
        model.addAttribute("playerDetails", playerDetails);
        model.addAttribute("sticker", widget);

        if (isGroupEdit) {
            model.addAttribute("group", GroupDao.getGroup(widget.getGroupId()));
        }

        return widget.getTemplateName();
    }

    @PostMapping("/myhabbo/sticker/remove")
    @ResponseBody
    public String removeSticker(
            @RequestParam(value = "stickerId", defaultValue = "0") int widgetId,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);
        boolean isGroupEdit = session.getAttribute("groupEditSession") != null;

        if (isGroupEdit) {
            int groupId = (Integer) session.getAttribute("groupEditSession");
            Group group = GroupDao.getGroup(groupId);

            if (group == null) {
                return "";
            }

            if (!GroupEditDao.hasSession(userId, group.getId())) {
                return "";
            }
        } else {
            if (session.getAttribute("homeEditSession") == null) {
                return "";
            }
        }

        Widget widget;

        if (isGroupEdit) {
            widget = WidgetDao.getGroupWidget(widgetId, (Integer) session.getAttribute("groupEditSession"));
        } else {
            widget = WidgetDao.getHomeWidget(userId, widgetId);
        }

        if (widget == null) {
            return "";
        }

        widget.setX(0);
        widget.setY(0);
        widget.setZ(0);
        widget.setGroupId(0);
        widget.setPlaced(false);
        widget.save();

        return "SUCCESS";
    }

    @PostMapping("/myhabbo/widget/remove")
    @ResponseBody
    public String removeWidget(
            @RequestParam(value = "widgetId", defaultValue = "0") int widgetId,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);
        boolean isGroupEdit = session.getAttribute("groupEditSession") != null;

        if (isGroupEdit) {
            int groupId = (Integer) session.getAttribute("groupEditSession");
            Group group = GroupDao.getGroup(groupId);

            if (group == null) {
                return "";
            }

            if (!GroupEditDao.hasSession(userId, group.getId())) {
                return "";
            }
        } else {
            if (session.getAttribute("homeEditSession") == null) {
                return "";
            }
        }

        Widget widget;

        if (isGroupEdit) {
            widget = WidgetDao.getGroupWidget(widgetId, (Integer) session.getAttribute("groupEditSession"));
        } else {
            widget = WidgetDao.getHomeWidget(userId, widgetId);
        }

        if (widget == null) {
            return "";
        }

        if (widget.getProduct().getData().equalsIgnoreCase("groupinfowidget") || widget.getProduct().getData().equalsIgnoreCase("profilewidget")) {
            return "";
        }

        widget.setX(0);
        widget.setY(0);
        widget.setZ(0);

        if (isGroupEdit) {
            widget.setGroupId((Integer) session.getAttribute("groupEditSession"));
        }

        widget.setPlaced(false);
        widget.save();

        return "SUCCESS";
    }
}
