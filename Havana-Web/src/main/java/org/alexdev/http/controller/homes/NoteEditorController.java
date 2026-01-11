package org.alexdev.http.controller.homes;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.GroupDao;
import org.alexdev.havana.dao.mysql.MessengerDao;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.groups.Group;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.room.Room;
import org.alexdev.http.dao.GroupEditDao;
import org.alexdev.http.dao.WidgetDao;
import org.alexdev.http.game.homes.Widget;
import org.alexdev.http.game.stickers.StickerManager;
import org.alexdev.http.game.stickers.StickerType;
import org.alexdev.http.util.BBCode;
import org.alexdev.http.util.HtmlUtil;
import org.alexdev.http.util.SessionHelper;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class NoteEditorController {

    @PostMapping("/myhabbo/noteeditor")
    public String noteEditor(
            @RequestParam(value = "noteText", defaultValue = "") String noteText,
            @RequestParam(value = "skin", defaultValue = "0") int skin,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        if (noteText.length() > 500) {
            noteText = noteText.substring(0, 500);
        }

        if (skin > 0 && skin < 9) {
            model.addAttribute("skin" + skin + "Selected", " selected");
        }

        model.addAttribute("noteText", noteText);
        return "homes/editor/noteeditor";
    }

    @PostMapping("/myhabbo/noteeditor/preview")
    public String notePreview(
            @RequestParam(value = "noteText", defaultValue = "") String noteText,
            @RequestParam(value = "skin", defaultValue = "0") int skin,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        String formattedText = BBCode.format(HtmlUtil.escape(BBCode.normalise(noteText)), false);

        if (formattedText.length() > 500) {
            formattedText = formattedText.substring(0, 500);
        }

        model.addAttribute("skin", StickerManager.getInstance().getSkin(skin));
        model.addAttribute("noteText", formattedText);
        return "homes/editor/preview";
    }

    @GetMapping("/myhabbo/noteeditor/search")
    public String search(
            @RequestParam(value = "query", defaultValue = "") String query,
            @RequestParam(value = "scope", defaultValue = "0") int scope,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        String type;
        List<Pair<String, String>> querySearch = new ArrayList<>();

        switch (scope) {
            case 1:
                type = "habbo";
                List<PlayerDetails> searchedFriends = new ArrayList<>();

                for (int playerId : MessengerDao.search(query)) {
                    searchedFriends.add(PlayerDao.getDetails(playerId));
                }

                searchedFriends.sort(Comparator.comparing(PlayerDetails::getName));

                for (PlayerDetails playerDetails : searchedFriends.stream().limit(10).collect(Collectors.toList())) {
                    querySearch.add(Pair.of(playerDetails.getName(), String.valueOf(playerDetails.getId())));
                }
                break;

            case 2:
                type = "room";
                var roomList = RoomDao.searchRooms(query, -1, 30);

                for (Room room : roomList.stream().limit(10).collect(Collectors.toList())) {
                    querySearch.add(Pair.of(room.getData().getName(), String.valueOf(room.getData().getId())));
                }
                break;

            default:
                type = "group";
                var groupList = GroupDao.querySearch(query);

                for (Group group : groupList.stream().limit(10).collect(Collectors.toList())) {
                    querySearch.add(Pair.of(group.getName(), String.valueOf(group.getId())));
                }
                break;
        }

        model.addAttribute("querySearch", querySearch);
        model.addAttribute("type", type);
        return "homes/editor/search";
    }

    @PostMapping("/myhabbo/noteeditor/place")
    public String place(
            @RequestParam(value = "skin", defaultValue = "0") int skin,
            @RequestParam(value = "noteText", defaultValue = "") String noteText,
            HttpSession session,
            HttpServletResponse response,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);

        if (noteText.length() > 500) {
            noteText = noteText.substring(0, 500);
        }

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

        Widget widget = WidgetDao.getInventoryWidgets(userId, StickerType.NOTE.getTypeId()).get(0);
        widget.setX(20);
        widget.setY(30);
        widget.setZ(1);

        if (isGroupEdit) {
            widget.setGroupId((Integer) session.getAttribute("groupEditSession"));
        }

        widget.setText(noteText);
        widget.setSkinId(skin);
        widget.setPlaced(true);
        widget.save();

        response.setHeader("X-JSON", "" + widget.getId() + "");

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);
        model.addAttribute("playerDetails", playerDetails);
        model.addAttribute("sticker", widget);

        return widget.getTemplateName();
    }

    @PostMapping("/myhabbo/stickieedit")
    public String stickieEdit(
            @RequestParam(value = "stickieId", defaultValue = "0") int widgetId,
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

        response.setHeader("X-JSON", "{\"id\":\"" + widget.getId() + "\",\"cssClass\":\"n_skin_" + widget.getSkin() + "\",\"type\":\"stickie\"}");

        model.addAttribute("playerDetails", playerDetails);
        model.addAttribute("sticker", widget);
        return widget.getTemplateName();
    }

    @PostMapping("/myhabbo/stickiedelete")
    @ResponseBody
    public String stickieDelete(
            @RequestParam(value = "stickieId", defaultValue = "0") int stickieId,
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

        if (isGroupEdit) {
            WidgetDao.delete(stickieId, (Integer) session.getAttribute("groupEditSession"));
        } else {
            WidgetDao.deleteHomeNote(stickieId, userId);
        }

        return "SUCCESS";
    }
}
