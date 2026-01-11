package org.alexdev.http.controller.homes.widgets;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.GroupDao;
import org.alexdev.havana.dao.mysql.PlayerStatisticsDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;
import org.alexdev.havana.game.wordfilter.WordfilterManager;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.http.dao.GuestbookDao;
import org.alexdev.http.dao.WidgetDao;
import org.alexdev.http.game.homes.GuestbookEntry;
import org.alexdev.http.game.homes.Widget;
import org.alexdev.http.game.stickers.StickerType;
import org.alexdev.http.util.BBCode;
import org.alexdev.http.util.HtmlUtil;
import org.alexdev.http.util.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ThreadLocalRandom;

@Controller
public class GuestbookController {

    @PostMapping("/myhabbo/guestbook/preview")
    public String preview(
            @RequestParam(value = "message", defaultValue = "") String message,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        String formattedMessage = BBCode.format(HtmlUtil.escape(BBCode.normalise(message)), false);

        if (formattedMessage.length() > 200) {
            formattedMessage = formattedMessage.substring(0, 200);
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        model.addAttribute("message", formattedMessage);
        model.addAttribute("author", playerDetails);
        model.addAttribute("formattedDate", DateUtil.getFriendlyDate(DateUtil.getCurrentTimeSeconds()));
        return "homes/widget/guestbook/preview";
    }

    @PostMapping("/myhabbo/guestbook/add")
    public String add(
            @RequestParam(value = "widgetId", defaultValue = "0") int widgetId,
            @RequestParam(value = "message", defaultValue = "") String message,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);

        Widget widget = WidgetDao.getWidget(widgetId);

        if (widget == null || !widget.getProduct().getData().toLowerCase().equals("guestbookwidget")) {
            return "";
        }

        if (message.length() > 200) {
            message = message.substring(0, 200);
        }

        if (!widget.isPlaced()) {
            return "";
        }

        if (!widget.isPostingAllowed(userId)) {
            return "";
        }

        int homeId = 0;
        int groupId = 0;

        if (widget.getProduct().getType() == StickerType.GROUP_WIDGET) {
            groupId = widget.getGroupId();
        } else if (widget.getProduct().getType() == StickerType.HOME_WIDGET) {
            homeId = widget.getUserId();

            if (homeId != userId) {
                PlayerStatisticsDao.incrementStatistic(homeId, PlayerStatistic.GUESTBOOK_UNREAD_MESSAGES, 1);
            }
        }

        GuestbookEntry guestbookEntry;

        if (WordfilterManager.filterSentence(message).equals(message)) {
            guestbookEntry = GuestbookDao.create(userId, homeId, groupId, message);
        } else {
            guestbookEntry = new GuestbookEntry(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE), userId, homeId, groupId, message, DateUtil.getCurrentTimeSeconds());
        }

        model.addAttribute("entry", guestbookEntry);
        model.addAttribute("sticker", widget);
        model.addAttribute("canDeleteEntries", widget.canDeleteEntries(userId));
        return "homes/widget/guestbook/add";
    }

    @PostMapping("/myhabbo/guestbook/remove")
    public String remove(
            @RequestParam(value = "entryId", defaultValue = "0") int entryId,
            @RequestParam(value = "widgetId", defaultValue = "0") int widgetId,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);

        Widget widget = WidgetDao.getWidget(widgetId);

        if (widget == null || !widget.getProduct().getData().toLowerCase().equals("guestbookwidget") || !widget.isPlaced()) {
            return "";
        }

        GuestbookEntry entry = GuestbookDao.getEntry(entryId);

        if (entry == null || !widget.canDeleteEntries(userId) && entry.getUserId() != userId) {
            return "";
        }

        int homeId = 0;
        int groupId = 0;

        if (widget.getProduct().getType() == StickerType.GROUP_WIDGET) {
            groupId = widget.getGroupId();
        } else if (widget.getProduct().getType() == StickerType.HOME_WIDGET) {
            homeId = widget.getUserId();
        }

        GuestbookDao.remove(entryId, homeId, groupId);

        model.addAttribute("editMode", session.getAttribute("homeEditSession") != null || session.getAttribute("groupEditSession") != null);
        model.addAttribute("sticker", widget);
        return "homes/widget/guestbook_widget";
    }

    @PostMapping("/myhabbo/guestbook/configure")
    @ResponseBody
    public String configure(
            @RequestParam(value = "widgetId", defaultValue = "0") int widgetId,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);

        Widget widget = WidgetDao.getWidget(widgetId);

        if (widget == null || !widget.getProduct().getData().toLowerCase().equals("guestbookwidget") || !widget.isPlaced()) {
            return "";
        }

        int ownerId = 0;

        if (widget.getProduct().getType() == StickerType.GROUP_WIDGET) {
            ownerId = GroupDao.getGroupOwner(widget.getGroupId());
        } else if (widget.getProduct().getType() == StickerType.HOME_WIDGET) {
            ownerId = widget.getUserId();
        }

        if (ownerId != userId) {
            return "";
        }

        if (widget.getGuestbookState().equalsIgnoreCase("private")) {
            widget.setExtraData("public");
        } else {
            widget.setExtraData("private");
        }

        widget.save();

        return "var el = $(\"guestbook-type\");\n" +
                "if (el) {\n" +
                "\tif (el.hasClassName(\"public\")) {\n" +
                "\t\tel.className = \"private\";\n" +
                "\t\tnew Effect.Pulsate(el,\n" +
                "\t\t\t{ duration: 1.0, afterFinish : function() { Element.setOpacity(el, 1); } }\n" +
                "\t\t);\t\t\t\t\t\t\n" +
                "\t} else {\t\t\t\t\t\t\n" +
                "\t\tnew Effect.Pulsate(el,\n" +
                "\t\t\t{ duration: 1.0, afterFinish : function() { Element.setOpacity(el, 0); el.className = \"public\"; } }\n" +
                "\t\t);\t\t\t\t\t\t\n" +
                "\t}\n" +
                "}";
    }
}
