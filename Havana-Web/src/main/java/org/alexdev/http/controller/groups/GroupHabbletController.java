package org.alexdev.http.controller.groups;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.*;
import org.alexdev.havana.game.groups.Group;
import org.alexdev.havana.game.groups.GroupForumType;
import org.alexdev.havana.game.groups.GroupPermissionType;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.havana.util.StringUtil;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.dao.GroupEditDao;
import org.alexdev.http.dao.WidgetDao;
import org.alexdev.http.game.stickers.StickerManager;
import org.alexdev.http.game.stickers.StickerType;
import org.alexdev.http.util.GroupUtil;
import org.alexdev.http.util.HtmlUtil;
import org.alexdev.http.util.RconUtil;
import org.alexdev.http.util.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
public class GroupHabbletController {

    @PostMapping("/groups/habblet/createform")
    public String groupCreateForm(HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        if (playerDetails.getCredits() < GameConfiguration.getInstance().getInteger("group.purchase.cost")) {
            return "groups/habblet/purchase_result_error";
        }

        model.addAttribute("groupCost", GameConfiguration.getInstance().getInteger("group.purchase.cost"));
        return "groups/habblet/group_create_form";
    }

    @PostMapping("/groups/habblet/purchaseconfirmation")
    public String purchaseConfirmation(
            @RequestParam(value = "name", defaultValue = "") String name,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        String cleanName = HtmlUtil.removeHtmlTags(StringUtil.filterInput(name, true));

        model.addAttribute("groupName", cleanName);
        model.addAttribute("groupCost", GameConfiguration.getInstance().getInteger("group.purchase.cost"));
        return "groups/habblet/purchase_confirmation";
    }

    @PostMapping("/groups/habblet/purchase")
    public String purchaseAjax(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "description", defaultValue = "") String description,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        if (playerDetails.getCredits() < GameConfiguration.getInstance().getInteger("group.purchase.cost")) {
            return "";
        }

        CurrencyDao.decreaseCredits(playerDetails, GameConfiguration.getInstance().getInteger("group.purchase.cost"));

        RconUtil.sendCommand(RconHeader.REFRESH_CREDITS, new HashMap<>() {{
            put("userId", playerDetails.getId());
        }});

        String cleanName = HtmlUtil.removeHtmlTags(StringUtil.filterInput(name, true));
        String cleanDescription = HtmlUtil.removeHtmlTags(StringUtil.filterInput(description, true));

        int groupId = GroupDao.addGroup(cleanName, cleanDescription, playerDetails.getId());

        WidgetDao.purchaseWidget(0, 40, 34, 6, 1, StickerManager.getInstance().getStickerByData("guestbookwidget", StickerType.GROUP_WIDGET).getId(), "", groupId, true);
        WidgetDao.purchaseWidget(0, 433, 40, 3, 1, StickerManager.getInstance().getStickerByData("groupinfowidget", StickerType.GROUP_WIDGET).getId(), "", groupId, true);
        WidgetDao.purchaseWidget(0, 0, 0, 0, 1, StickerManager.getInstance().getStickerByData("memberwidget", StickerType.GROUP_WIDGET).getId(), "", groupId, false);
        WidgetDao.purchaseWidget(0, 0, 0, 0, 1, StickerManager.getInstance().getStickerByData("traxplayerwidget", StickerType.GROUP_WIDGET).getId(), "", groupId, false);

        model.addAttribute("groupName", cleanName);
        model.addAttribute("groupId", groupId);
        model.addAttribute("deductedCredits", playerDetails.getCredits());
        return "groups/habblet/purchase_ajax";
    }

    @PostMapping("/groups/habblet/settings")
    public String groupSettings(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        Group group = GroupDao.getGroup(groupId);

        if (group == null) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);

        if (group.getOwnerId() != userId) {
            return "";
        }

        model.addAttribute("group", group);
        model.addAttribute("selected" + group.getGroupType() + "GroupType", " checked=\"checked\"");
        model.addAttribute("selected" + group.getForumType().getId() + "ForumType", " checked=\"checked\"");
        model.addAttribute("selected" + group.getForumPermission().getId() + "ForumPermissionType", " checked=\"checked\"");
        model.addAttribute("charactersLeft", String.valueOf(255 - group.getDescription().length()));
        model.addAttribute("rooms", RoomDao.getRoomsByUserId(userId).stream()
                .filter(room -> room.getData().getGroupId() == 0 || room.getData().getGroupId() == group.getId())
                .collect(Collectors.toList()));
        return "groups/habblet/group_settings";
    }

    @PostMapping("/groups/habblet/checkurl")
    public String checkGroupUrl(
            @RequestParam(value = "url", defaultValue = "") String url,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        model.addAttribute("url", HtmlUtil.escape(url));
        return "groups/habblet/check_group_url";
    }

    @PostMapping("/groups/habblet/updatesettings")
    public String updateGroupSettings(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "description", defaultValue = "") String description,
            @RequestParam(value = "url", defaultValue = "") String url,
            @RequestParam(value = "type", defaultValue = "0") int groupType,
            @RequestParam(value = "forumType", defaultValue = "0") int forumType,
            @RequestParam(value = "newTopicPermission", defaultValue = "0") int forumTypePermission,
            @RequestParam(value = "roomId", required = false) String roomIdStr,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);
        Group group = GroupDao.getGroup(groupId);

        if (group == null || group.getOwnerId() != userId) {
            return "";
        }

        String cleanName = HtmlUtil.removeHtmlTags(name);
        String cleanDescription = HtmlUtil.removeHtmlTags(description);
        String cleanUrl = url.replaceAll("[^a-zA-Z0-9]", "");

        if (cleanUrl.length() > 30) {
            cleanUrl = cleanUrl.substring(0, 30);
        }

        if (cleanName.length() > 30) {
            cleanName = cleanName.substring(0, 30);
        }

        if (cleanDescription.length() > 255) {
            cleanDescription = cleanDescription.substring(0, 255);
        }

        int roomId = 0;
        try {
            if (roomIdStr != null && !roomIdStr.isEmpty()) {
                roomId = Integer.parseInt(roomIdStr);
            }
        } catch (Exception ex) {
            // ignore
        }

        if (groupType < 0 || groupType > 3) {
            groupType = 0;
        }

        if (roomId < 0) {
            roomId = 0;
        }

        if (forumType < 0 || forumType > 1) {
            forumType = 0;
        }

        if (forumTypePermission < 0 || forumTypePermission > 2) {
            forumTypePermission = 0;
        }

        group.setName(cleanName);
        group.setDescription(cleanDescription);

        if (group.getGroupType() != 3) {
            group.setGroupType(groupType);
        }

        group.setForumType(GroupForumType.getById(forumType));
        group.setForumPermission(GroupPermissionType.getById(forumTypePermission));

        if (group.getAlias() == null || group.getAlias().isBlank()) {
            group.setAlias(null);

            if (!cleanUrl.isBlank()) {
                boolean existing = GroupDao.hasGroupByAlias(cleanUrl);

                if (!existing) {
                    group.setAlias(cleanUrl);
                }
            }
        }

        RoomDao.saveGroupId(group.getRoomId(), 0);

        if (roomId > 0) {
            Room room = RoomDao.getRoomById(roomId);

            if (room == null || room.getData().getOwnerId() != userId) {
                roomId = 0;
            } else {
                RoomDao.saveGroupId(roomId, groupId);
            }
        }

        group.setRoomId(roomId);
        group.save();

        GroupUtil.refreshGroup(groupId);

        model.addAttribute("group", group);
        model.addAttribute("message", "Editing group settings successful");
        return "groups/habblet/update_group_settings";
    }

    @PostMapping("/groups/habblet/badgeeditor")
    public String showBadgeEditor(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);
        Group group = GroupDao.getGroup(groupId);

        if (group == null || group.getOwnerId() != userId) {
            return "";
        }

        model.addAttribute("group", group);
        return "groups/habblet/show_badge_editor";
    }

    @PostMapping("/groups/habblet/updatebadge")
    public String updateGroupBadge(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            @RequestParam(value = "code", defaultValue = "") String badge,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);
        Group group = GroupDao.getGroup(groupId);

        if (group == null || group.getOwnerId() != userId) {
            return "redirect:/";
        }

        group.setBadge(HtmlUtil.removeHtmlTags(badge).replaceAll("[^a-zA-Z0-9]", ""));
        group.saveBadge();

        GroupUtil.refreshGroup(groupId);
        return "redirect:" + group.generateClickLink();
    }

    @PostMapping("/groups/habblet/confirmdelete")
    public String confirmDeleteGroup(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);
        Group group = GroupDao.getGroup(groupId);

        if (group == null || group.getOwnerId() != userId) {
            return "";
        }

        model.addAttribute("group", group);
        return "groups/habblet/confirm_delete_group";
    }

    @PostMapping("/groups/habblet/delete")
    public String deleteGroup(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);
        Group group = GroupDao.getGroup(groupId);

        if (group == null || group.getOwnerId() != userId) {
            return "";
        }

        GroupEditDao.deleteGroupWidgets(groupId);
        GroupEditDao.pickupUserWidgets(groupId);

        GroupMemberDao.deleteMembers(groupId);
        GroupMemberDao.resetFavourites(groupId);
        GroupDao.delete(groupId);

        RconUtil.sendCommand(RconHeader.GROUP_DELETED, new HashMap<>() {{
            put("groupId", String.valueOf(groupId));
        }});

        return "groups/habblet/delete_group";
    }
}
