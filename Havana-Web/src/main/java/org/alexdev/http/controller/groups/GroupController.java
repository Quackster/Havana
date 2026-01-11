package org.alexdev.http.controller.groups;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.GroupDao;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.dao.mysql.TagDao;
import org.alexdev.havana.game.groups.Group;
import org.alexdev.havana.game.groups.GroupMember;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.dao.GroupEditDao;
import org.alexdev.http.dao.WidgetDao;
import org.alexdev.http.game.homes.Widget;
import org.alexdev.http.util.HomeUtil;
import org.alexdev.http.util.SessionHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Controller
public class GroupController {

    @GetMapping("/groups/{groupIdentifier}")
    public String viewGroup(
            @PathVariable String groupIdentifier,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        session.setAttribute("page", "community");

        Group group = null;

        if (StringUtils.isNumeric(groupIdentifier)) {
            return "redirect:/groups/" + groupIdentifier + "/id";
        } else {
            group = GroupDao.getGroupByAlias(groupIdentifier);
        }

        if (group == null) {
            return "error/404";
        }

        return renderGroup(session, model, group);
    }

    @GetMapping("/groups/{groupId}/id")
    public String viewGroupById(
            @PathVariable int groupId,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        session.setAttribute("page", "community");

        Group group = GroupDao.getGroup(groupId);

        if (group == null) {
            return "error/404";
        }

        if (group.getAlias() != null && !group.getAlias().isBlank()) {
            return "redirect:/groups/" + group.getAlias();
        }

        return renderGroup(session, model, group);
    }

    private String renderGroup(HttpSession session, Model model, Group group) {
        long sessionTime = -1;

        if (SessionHelper.isAuthenticated(session)) {
            int userId = SessionHelper.getUserId(session);
            sessionTime = GroupEditDao.getSession(userId, group.getId());
        }

        if (sessionTime != -1) {
            session.removeAttribute("homeEditSession");
            session.setAttribute("groupEditSession", group.getId());
        }

        if (group.getAlias() != null) {
            if (group.getAlias().equalsIgnoreCase("battleball_rebound") ||
                group.getAlias().equalsIgnoreCase("lido") ||
                group.getAlias().equalsIgnoreCase("snow_storm") ||
                group.getAlias().equalsIgnoreCase("wobble_squabble")) {
                session.setAttribute("page", "games");
            }
        }

        model.addAttribute("editMode", sessionTime != -1);
        model.addAttribute("group", group);
        model.addAttribute("stickers", WidgetDao.getGroupWidgets(group.getId(), true));
        model.addAttribute("tags", TagDao.getGroupTags(group.getId()));

        var guestbook = WidgetDao.getGroupWidgets(group.getId()).stream()
                .filter(w -> w.getProduct().getData().equalsIgnoreCase("guestbookwidget"))
                .findFirst()
                .orElse(null);

        model.addAttribute("guestbookSetting", guestbook != null ? guestbook.getGuestbookState() : "public");
        model.addAttribute("stickerLimit", HomeUtil.getStickerLimit(true));

        if (sessionTime != -1) {
            model.addAttribute("expireMinutes", TimeUnit.SECONDS.toMinutes(sessionTime - DateUtil.getCurrentTimeSeconds()));
        }

        if (group.getRoomId() > 0) {
            Room room = RoomDao.getRoomById(group.getRoomId());
            if (room != null) {
                model.addAttribute("room", room);
            }
        }

        model.addAttribute("hasMember", false);

        if (SessionHelper.isAuthenticated(session)) {
            int userId = SessionHelper.getUserId(session);
            GroupMember groupMember = group.getMember(userId);

            if (groupMember != null) {
                model.addAttribute("hasMember", true);
                model.addAttribute("groupMember", groupMember);
            }
        }

        return "groups";
    }

    @GetMapping("/groups/{groupId}/edit")
    public String startEditingSession(
            @PathVariable int groupId,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);
        Group group = GroupDao.getGroup(groupId);

        if (group == null) {
            return "error/404";
        }

        if (group.isMember(userId) && group.hasAdministrator(userId)) {
            if (!GroupEditDao.hasSession(userId, group.getId())) {
                GroupEditDao.delete(userId, group.getId());
                GroupEditDao.createSession(userId, group.getId());
                session.removeAttribute("homeEditSession");
                session.setAttribute("groupEditSession", group.getId());
            }
        }

        return "redirect:" + group.generateClickLink();
    }

    @GetMapping("/groups/{groupId}/cancel")
    public String cancelEditingSession(
            @PathVariable int groupId,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        Integer sessionGroupId = (Integer) session.getAttribute("groupEditSession");

        if (sessionGroupId == null) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);

        if (GroupEditDao.hasSession(userId, sessionGroupId)) {
            GroupEditDao.delete(userId, sessionGroupId);
            session.removeAttribute("homeEditSession");
            session.removeAttribute("groupEditSession");
        }

        Group group = GroupDao.getGroup(sessionGroupId);
        return "redirect:" + group.generateClickLink();
    }

    @PostMapping("/groups/save")
    @ResponseBody
    public String saveEditingSession(
            @RequestParam(value = "background", required = false) String background,
            @RequestParam(value = "stickers", required = false) String stickers,
            @RequestParam(value = "widgets", required = false) String widgets,
            @RequestParam(value = "stickienotes", required = false) String stickienotes,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        if (playerDetails == null) {
            SessionHelper.logout(session);
            return "";
        }

        Integer groupId = (Integer) session.getAttribute("groupEditSession");

        if (groupId == null) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);
        Group group = GroupDao.getGroup(groupId);

        if (group == null) {
            return "";
        }

        if (!GroupEditDao.hasSession(userId, group.getId())) {
            return "";
        }

        List<Widget> groupWidgets = WidgetDao.getGroupWidgets(groupId, true);

        try {
            if (background != null) {
                int backgroundId = Integer.parseInt(background.split(":")[0]);

                List<Widget> widgetList = WidgetDao.getInventoryWidgets(playerDetails.getId());
                Widget widget = widgetList.stream().filter(w -> w.getId() == backgroundId).findFirst().orElse(null);

                if (widget != null) {
                    group.setBackground(widget.getProduct().getData());
                    group.saveBackground();
                    group.save();
                }
            }

            if (stickers != null) {
                String[] stickerData = stickers.split(Pattern.quote("/"));

                if (stickerData.length >= HomeUtil.getStickerLimit(true)) {
                    return "";
                }

                for (String sticker : stickerData) {
                    int stickerId = Integer.parseInt(sticker.split(":")[0]);
                    String[] coordData = sticker.replace(stickerId + ":", "").split(",");

                    int x = Integer.parseInt(coordData[0]);
                    int y = Integer.parseInt(coordData[1]);
                    int z = Integer.parseInt(coordData[2]);

                    Widget widget = groupWidgets.stream().filter(w -> w.getId() == stickerId).findFirst().orElse(null);

                    if (widget != null) {
                        widget.setX(x);
                        widget.setY(y);
                        widget.setZ(z);
                        widget.save();
                    }
                }
            }

            if (widgets != null) {
                String[] stickerData = widgets.split(Pattern.quote("/"));

                for (String sticker : stickerData) {
                    int stickerId = Integer.parseInt(sticker.split(":")[0]);
                    String[] coordData = sticker.replace(stickerId + ":", "").split(",");

                    int x = Integer.parseInt(coordData[0]);
                    int y = Integer.parseInt(coordData[1]);
                    int z = Integer.parseInt(coordData[2]);

                    Widget widget = groupWidgets.stream().filter(w -> w.getId() == stickerId).findFirst().orElse(null);

                    if (widget != null) {
                        widget.setX(x);
                        widget.setY(y);
                        widget.setZ(z);
                        widget.save();
                    }
                }
            }

            if (stickienotes != null) {
                String[] stickerData = stickienotes.split(Pattern.quote("/"));

                for (String sticker : stickerData) {
                    int stickerId = Integer.parseInt(sticker.split(":")[0]);
                    String[] coordData = sticker.replace(stickerId + ":", "").split(",");

                    int x = Integer.parseInt(coordData[0]);
                    int y = Integer.parseInt(coordData[1]);
                    int z = Integer.parseInt(coordData[2]);

                    Widget widget = groupWidgets.stream().filter(w -> w.getId() == stickerId).findFirst().orElse(null);

                    if (widget != null) {
                        widget.setX(x);
                        widget.setY(y);
                        widget.setZ(z);
                        widget.save();
                    }
                }
            }
        } catch (Exception ex) {
            // ignore
        }

        GroupEditDao.delete(userId, groupId);

        session.removeAttribute("homeEditSession");
        session.removeAttribute("groupEditSession");

        return "<script language=\"JavaScript\" type=\"text/javascript\">\n" +
                "waitAndGo('" + group.generateClickLink() + "');\n" +
                "</script>";
    }

    @PostMapping("/groups/info")
    public String groupInfo(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            Model model) {

        Group group = GroupDao.getGroup(groupId);

        if (group == null) {
            return "";
        }

        model.addAttribute("group", group);
        return "homes/widget/habblet/groupinfo";
    }
}
