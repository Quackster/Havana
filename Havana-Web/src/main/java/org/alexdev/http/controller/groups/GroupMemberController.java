package org.alexdev.http.controller.groups;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.GroupDao;
import org.alexdev.havana.dao.mysql.GroupMemberDao;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.groups.Group;
import org.alexdev.havana.game.groups.GroupMember;
import org.alexdev.havana.game.groups.GroupMemberRank;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.http.util.RconUtil;
import org.alexdev.http.util.SessionHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
public class GroupMemberController {

    @PostMapping("/groups/actions/join")
    public String join(
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
        var playerDetails = PlayerDao.getDetails(userId);

        if (playerDetails == null || group.isMember(userId) || group.isPendingMember(userId) || group.getGroupType() == 2) {
            return "";
        }

        if (group.getGroupType() == 0 || group.getGroupType() == 3 || playerDetails.getRank().getRankId() >= PlayerRank.MODERATOR.getRankId()) {
            if (playerDetails.getRank().getRankId() >= PlayerRank.MODERATOR.getRankId()) {
                GroupMemberDao.addMember(userId, groupId, false);
            } else {
                GroupMemberDao.addMember(userId, groupId, group.getGroupType() == 1);
            }
            return "groups/member/member_added";
        }

        if (group.getGroupType() == 1) {
            GroupMemberDao.addMember(userId, groupId, true);
            return "groups/member/member_added_request";
        }

        return "";
    }

    @PostMapping("/groups/actions/confirmleave")
    public String confirmLeave(Model model) {
        return "groups/member/confirm_leave";
    }

    @PostMapping("/groups/actions/leave")
    public String leave(
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

        if (group.isMember(userId)) {
            GroupMember groupMember = group.getMember(userId);

            GroupMemberDao.deleteMember(userId, groupId);

            if (groupMember.getUser().getFavouriteGroupId() == group.getId()) {
                PlayerDao.saveFavouriteGroup(userId, 0);

                RconUtil.sendCommand(RconHeader.REFRESH_GROUP_PERMS, new HashMap<>() {{
                    put("userId", String.valueOf(userId));
                }});
            }
        }

        model.addAttribute("groupId", groupId);
        return "groups/member/leave";
    }

    @PostMapping("/groups/actions/memberlist")
    public String memberList(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(value = "pending", defaultValue = "false") boolean isPending,
            HttpSession session,
            HttpServletResponse response,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);
        Group group = GroupDao.getGroup(groupId);

        if (group == null || !group.hasAdministrator(userId)) {
            return "";
        }

        if (pageNumber <= 0) {
            pageNumber = 1;
        }

        int pendingMembers = GroupMemberDao.countMembers(group.getId(), true);
        int groupMembers = GroupMemberDao.countMembers(group.getId(), false);

        int limit = 12;
        List<GroupMember> groupMemberList = GroupMemberDao.getMembers(groupId, isPending, "", pageNumber, limit);

        int memberCount = isPending ? pendingMembers : groupMembers;
        int pages = memberCount > 0 ? (int) Math.ceil((double) memberCount / (double) limit) : 1;

        GroupMember selfMember = group.getMember(userId);
        response.setHeader("X-JSON", "{\"pending\":\"Pending members (" + pendingMembers + ")\",\"members\":\"Members (" + groupMembers + ")\"}");

        model.addAttribute("pages", pages);
        model.addAttribute("memberList", groupMemberList);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("selfMember", selfMember);
        return "groups/memberlist";
    }

    @PostMapping("/groups/actions/confirmrevokerights")
    public String confirmRevokeRights(
            @RequestParam(value = "targetIds[]", required = false) List<String> targetIds,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        model.addAttribute("targetIds", targetIds != null ? targetIds.size() : 0);
        return "groups/member/confirm_revoke_rights";
    }

    @PostMapping("/groups/actions/revokerights")
    @ResponseBody
    public String revokeRights(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            @RequestParam(value = "targetIds[]", required = false) List<String> targetIds,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);
        Group group = GroupDao.getGroup(groupId);

        if (group == null || group.getMember(userId).getMemberRank() != GroupMemberRank.OWNER) {
            return "";
        }

        if (targetIds != null) {
            for (String user : targetIds) {
                if (!StringUtils.isNumeric(user)) {
                    continue;
                }

                int memberId = Integer.parseInt(user);
                GroupMember groupMember = group.getMember(memberId);

                if (groupMember == null || groupMember.getMemberRank() == GroupMemberRank.OWNER) {
                    continue;
                }

                GroupMemberDao.updateMember(groupMember.getUserId(), groupMember.getGroupId(), GroupMemberRank.MEMBER, false);
            }
        }

        return "OK";
    }

    @PostMapping("/groups/actions/confirmgiverights")
    public String confirmGiveRights(
            @RequestParam(value = "targetIds[]", required = false) List<String> targetIds,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        model.addAttribute("targetIds", targetIds != null ? targetIds.size() : 0);
        return "groups/member/confirm_give_rights";
    }

    @PostMapping("/groups/actions/giverights")
    @ResponseBody
    public String giveRights(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            @RequestParam(value = "targetIds[]", required = false) List<String> targetIds,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);
        Group group = GroupDao.getGroup(groupId);

        if (group == null || group.getMember(userId).getMemberRank() != GroupMemberRank.OWNER) {
            return "";
        }

        if (targetIds != null) {
            for (String user : targetIds) {
                if (!StringUtils.isNumeric(user)) {
                    continue;
                }

                int memberId = Integer.parseInt(user);
                GroupMember groupMember = group.getMember(memberId);

                if (groupMember == null || groupMember.getMemberRank() == GroupMemberRank.OWNER) {
                    continue;
                }

                GroupMemberDao.updateMember(groupMember.getUserId(), groupMember.getGroupId(), GroupMemberRank.ADMINISTRATOR, false);
            }
        }

        return "OK";
    }

    @PostMapping("/groups/actions/confirmremove")
    public String confirmRemove(
            @RequestParam(value = "targetIds[]", required = false) List<String> targetIds,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        model.addAttribute("targetIds", targetIds != null ? targetIds.size() : 0);
        return "groups/member/confirm_remove";
    }

    @PostMapping("/groups/actions/remove")
    @ResponseBody
    public String remove(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            @RequestParam(value = "targetIds[]", required = false) List<String> targetIds,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);
        Group group = GroupDao.getGroup(groupId);

        if (group == null) {
            return "";
        }

        GroupMember selfMember = group.getMember(userId);
        if (selfMember == null || (selfMember.getMemberRank() != GroupMemberRank.OWNER && selfMember.getMemberRank() != GroupMemberRank.ADMINISTRATOR)) {
            return "";
        }

        if (targetIds != null) {
            for (String user : targetIds) {
                if (!StringUtils.isNumeric(user)) {
                    continue;
                }

                int memberId = Integer.parseInt(user);
                GroupMember groupMember = group.getMember(memberId);

                if (groupMember == null || groupMember.getMemberRank() == GroupMemberRank.OWNER || groupMember.getMemberRank() == GroupMemberRank.ADMINISTRATOR) {
                    continue;
                }

                GroupMemberDao.deleteMember(groupMember.getUserId(), groupMember.getGroupId());

                if (groupMember.getUser().getFavouriteGroupId() == group.getId()) {
                    PlayerDao.saveFavouriteGroup(groupMember.getUserId(), 0);

                    if (groupMember.getUser().isOnline()) {
                        RconUtil.sendCommand(RconHeader.REFRESH_GROUP_PERMS, new HashMap<>() {{
                            put("userId", String.valueOf(memberId));
                        }});
                    }
                }
            }
        }

        return "OK";
    }

    @PostMapping("/groups/actions/confirmaccept")
    public String confirmAccept(
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
        return "groups/member/confirm_accept";
    }

    @PostMapping("/groups/actions/accept")
    @ResponseBody
    public String accept(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            @RequestParam(value = "targetIds[]", required = false) List<String> targetIds,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);
        Group group = GroupDao.getGroup(groupId);

        if (group == null) {
            return "";
        }

        GroupMember selfMember = group.getMember(userId);
        if (selfMember == null || (selfMember.getMemberRank() != GroupMemberRank.OWNER && selfMember.getMemberRank() != GroupMemberRank.ADMINISTRATOR)) {
            return "";
        }

        if (targetIds != null) {
            for (String user : targetIds) {
                if (!StringUtils.isNumeric(user)) {
                    continue;
                }

                int memberId = Integer.parseInt(user);
                GroupMember groupMember = group.getPendingMember(memberId);

                if (groupMember == null || groupMember.getMemberRank() == GroupMemberRank.OWNER || groupMember.getMemberRank() == GroupMemberRank.ADMINISTRATOR) {
                    continue;
                }

                GroupMemberDao.updateMember(groupMember.getUserId(), groupMember.getGroupId(), GroupMemberRank.MEMBER, false);
            }
        }

        return "OK";
    }

    @PostMapping("/groups/actions/confirmdecline")
    public String confirmDecline(
            @RequestParam(value = "targetIds", required = false) String targetIdsStr,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int count = targetIdsStr != null ? targetIdsStr.split(",").length : 0;
        model.addAttribute("targetIds", count);
        return "groups/member/confirm_decline";
    }

    @PostMapping("/groups/actions/decline")
    @ResponseBody
    public String decline(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            @RequestParam(value = "targetIds[]", required = false) List<String> targetIds,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);
        Group group = GroupDao.getGroup(groupId);

        if (group == null) {
            return "";
        }

        GroupMember selfMember = group.getMember(userId);
        if (selfMember == null || (selfMember.getMemberRank() != GroupMemberRank.OWNER && selfMember.getMemberRank() != GroupMemberRank.ADMINISTRATOR)) {
            return "";
        }

        if (targetIds != null) {
            for (String user : targetIds) {
                if (!StringUtils.isNumeric(user)) {
                    continue;
                }

                int memberId = Integer.parseInt(user);
                GroupMember groupMember = group.getPendingMember(memberId);

                if (groupMember == null || groupMember.getMemberRank() == GroupMemberRank.OWNER || groupMember.getMemberRank() == GroupMemberRank.ADMINISTRATOR) {
                    continue;
                }

                GroupMemberDao.deleteMember(groupMember.getUserId(), groupMember.getGroupId());
            }
        }

        return "OK";
    }
}
