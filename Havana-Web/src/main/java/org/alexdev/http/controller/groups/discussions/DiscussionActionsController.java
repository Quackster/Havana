package org.alexdev.http.controller.groups.discussions;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.GroupDao;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.groups.Group;
import org.alexdev.havana.game.groups.GroupForumType;
import org.alexdev.havana.game.groups.GroupMemberRank;
import org.alexdev.havana.game.groups.GroupPermissionType;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.dao.GroupDiscussionDao;
import org.alexdev.http.game.groups.DiscussionReply;
import org.alexdev.http.game.groups.DiscussionTopic;
import org.alexdev.http.util.Captcha;
import org.alexdev.http.util.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DiscussionActionsController {

    @PostMapping("/discussions/actions/newtopic")
    public String newTopic(HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        return "groups/discussions/newpost";
    }

    @PostMapping("/discussions/actions/savetopic")
    @ResponseBody
    public String saveTopic(
            @RequestParam(value = "captcha", defaultValue = "") String captcha,
            @RequestParam(value = "message", defaultValue = "") String message,
            @RequestParam(value = "topicName", defaultValue = "") String topicName,
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            HttpSession session,
            HttpServletResponse response,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        if (topicName.isBlank() || message.isBlank()) {
            return "";
        }

        String captchaText = (String) session.getAttribute("captcha-text");
        if (captchaText == null || !captchaText.equals(captcha)) {
            session.removeAttribute("captcha-text");
            response.setHeader("X-JSON", "{\"captchaError\":\"true\"}");
            return "";
        }

        int userId = SessionHelper.getUserId(session);
        var latestMessage = GroupDiscussionDao.getLatestReply(userId);

        if (latestMessage != null && latestMessage.getMessage().startsWith(message)) {
            return "";
        }

        Group group = GroupDao.getGroup(groupId);

        if (group == null) {
            return "";
        }

        if (group.getForumType() == GroupForumType.PRIVATE ||
            group.getForumPermission() == GroupPermissionType.MEMBER_ONLY ||
            group.getForumPermission() == GroupPermissionType.ADMIN_ONLY) {
            var groupMember = group.getMember(userId);

            if (groupMember == null) {
                return "";
            }

            if (group.getForumPermission() == GroupPermissionType.ADMIN_ONLY) {
                if (groupMember.getMemberRank() != GroupMemberRank.ADMINISTRATOR && groupMember.getMemberRank() != GroupMemberRank.OWNER) {
                    return "";
                }
            }
        }

        if (topicName.length() > 32) {
            topicName = topicName.substring(0, 32);
        }

        int topicId = GroupDiscussionDao.createDiscussion(groupId, userId, topicName);
        GroupDiscussionDao.createReplies(topicId, userId, message);

        session.removeAttribute("captcha-text");
        return group.generateClickLink() + "/discussions/" + topicId + "/id";
    }

    @PostMapping("/discussions/actions/pingsession")
    @ResponseBody
    public String pingSession(HttpSession session, HttpServletResponse response) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        response.setHeader("X-JSON", "{\"privilegeLevel\":\"1\"}");
        return "";
    }

    @PostMapping("/discussions/actions/topicsettings")
    public String openTopicSettings(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            @RequestParam(value = "topicId", defaultValue = "0") int topicId,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);
        DiscussionTopic discussionTopic = GroupDiscussionDao.getDiscussion(groupId, topicId, userId);

        if (discussionTopic == null) {
            return "redirect:/";
        }

        model.addAttribute("topic", discussionTopic);
        return "groups/discussions/opentopicsettings";
    }

    @PostMapping("/discussions/actions/confirmdeletetopic")
    public String confirmDeleteTopic(HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        return "groups/discussions/confirm_delete_topic";
    }

    @PostMapping("/discussions/actions/deletetopic")
    @ResponseBody
    public String deleteTopic(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            @RequestParam(value = "topicId", defaultValue = "0") int topicId,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);
        DiscussionTopic discussionTopic = GroupDiscussionDao.getDiscussion(groupId, topicId, userId);

        if (discussionTopic == null) {
            return "";
        }

        Group group = GroupDao.getGroup(groupId);

        if (group == null) {
            return "";
        }

        if (discussionTopic.getCreatorId() != userId) {
            var playerDetails = PlayerDao.getDetails(userId);
            var groupMember = group.getMember(userId);

            if (!Group.hasTopicAdmin(playerDetails.getRank())) {
                if (groupMember == null || groupMember.getMemberRank() == GroupMemberRank.MEMBER) {
                    return "";
                }
            }
        }

        GroupDiscussionDao.deleteDiscussion(groupId, topicId);
        return "SUCCESS";
    }

    @PostMapping("/discussions/actions/savetopicsettings")
    public String saveTopicSettings(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            @RequestParam(value = "topicId", defaultValue = "0") int topicId,
            @RequestParam(value = "topicName", defaultValue = "") String topicTitle,
            @RequestParam(value = "topicClosed", defaultValue = "0") int topicClosed,
            @RequestParam(value = "topicSticky", defaultValue = "0") int topicSticky,
            @RequestParam(value = "page", defaultValue = "1") int pageNumber,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);
        DiscussionTopic discussionTopic = GroupDiscussionDao.getDiscussion(groupId, topicId, userId);

        if (discussionTopic == null) {
            return "redirect:/";
        }

        Group group = GroupDao.getGroup(groupId);

        if (group == null) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        if (discussionTopic.getCreatorId() != userId) {
            var groupMember = group.getMember(userId);

            if (!Group.hasTopicAdmin(playerDetails.getRank())) {
                if (groupMember == null || groupMember.getMemberRank() == GroupMemberRank.MEMBER) {
                    return "";
                }
            }
        }

        if (pageNumber <= 0) {
            pageNumber = 1;
        }

        if (topicTitle.length() > 32) {
            topicTitle = topicTitle.substring(0, 32);
        }

        discussionTopic.setOpen(topicClosed == 0);
        discussionTopic.setStickied(topicSticky == 1);
        discussionTopic.setTopicTitle(topicTitle);
        GroupDiscussionDao.saveDiscussion(discussionTopic);

        DiscussionController.appendPageData(session, model, group, discussionTopic, pageNumber);
        return "groups/discussion_replies";
    }

    @PostMapping("/discussions/actions/updatepost")
    public String updatePost(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            @RequestParam(value = "topicId", defaultValue = "0") int topicId,
            @RequestParam(value = "postId", defaultValue = "0") int postId,
            @RequestParam(value = "page", defaultValue = "1") int pageNumber,
            @RequestParam(value = "captcha", defaultValue = "") String captcha,
            @RequestParam(value = "message", defaultValue = "") String message,
            HttpSession session,
            HttpServletResponse response,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        if (pageNumber <= 0) {
            pageNumber = 1;
        }

        int userId = SessionHelper.getUserId(session);
        DiscussionTopic discussionTopic = GroupDiscussionDao.getDiscussion(groupId, topicId, userId);

        if (discussionTopic == null || !discussionTopic.isOpen()) {
            return "redirect:/";
        }

        DiscussionReply discussionReply = GroupDiscussionDao.getReply(discussionTopic.getId(), postId, userId);

        if (discussionReply == null) {
            return "redirect:/";
        }

        Group group = GroupDao.getGroup(groupId);

        if (group == null) {
            return "redirect:/";
        }

        if (Captcha.matches(session, captcha)) {
            session.removeAttribute("captcha-text");
            response.setHeader("X-JSON", "{\"captchaError\":\"true\"}");
            return "";
        }

        if (discussionReply.getUserId() != userId) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        discussionReply.setMessage(message);

        if (!Group.hasTopicAdmin(playerDetails.getRank())) {
            discussionReply.setEdited(true);
        }

        GroupDiscussionDao.saveReply(discussionReply);

        session.removeAttribute("captcha-text");

        DiscussionController.appendPageData(session, model, group, discussionTopic, pageNumber);
        return "groups/discussion_replies";
    }

    @PostMapping("/discussions/actions/deletepost")
    public String deletePost(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            @RequestParam(value = "topicId", defaultValue = "0") int topicId,
            @RequestParam(value = "postId", defaultValue = "0") int postId,
            @RequestParam(value = "page", defaultValue = "1") int pageNumber,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        if (pageNumber <= 0) {
            pageNumber = 1;
        }

        int userId = SessionHelper.getUserId(session);
        DiscussionTopic discussionTopic = GroupDiscussionDao.getDiscussion(groupId, topicId, userId);

        if (discussionTopic == null || !discussionTopic.isOpen()) {
            return "redirect:/";
        }

        DiscussionReply discussionReply = GroupDiscussionDao.getReply(discussionTopic.getId(), postId, userId);

        if (discussionReply == null) {
            return "redirect:/";
        }

        Group group = GroupDao.getGroup(groupId);

        if (group == null) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        if (discussionReply.getUserId() != userId) {
            var groupMember = group.getMember(userId);

            if (!Group.hasTopicAdmin(playerDetails.getRank())) {
                if (groupMember == null || groupMember.getMemberRank() == GroupMemberRank.MEMBER) {
                    return "";
                }
            }
        }

        if (discussionReply.getUserId() != userId) {
            GroupDiscussionDao.deleteReply(discussionReply);
        } else {
            if (!Group.hasTopicAdmin(playerDetails.getRank())) {
                discussionReply.setDeleted(true);
                GroupDiscussionDao.saveReply(discussionReply);
            } else {
                GroupDiscussionDao.deleteReply(discussionReply);
            }
        }

        DiscussionController.appendPageData(session, model, group, discussionTopic, pageNumber);
        return "groups/discussion_replies";
    }

    @PostMapping("/discussions/actions/savepost")
    public String savePost(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            @RequestParam(value = "topicId", defaultValue = "0") int topicId,
            @RequestParam(value = "captcha", defaultValue = "") String captcha,
            @RequestParam(value = "message", defaultValue = "") String message,
            HttpSession session,
            HttpServletResponse response,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        String captchaText = (String) session.getAttribute("captcha-text");
        if (captchaText == null || !captchaText.equals(captcha)) {
            session.removeAttribute("captcha-text");
            response.setHeader("X-JSON", "{\"captchaError\":\"true\"}");
            return "";
        }

        if (message.isBlank()) {
            model.addAttribute("hasMessage", true);
            model.addAttribute("message", "Please supply a valid message.");
            return "groups/discussion_replies";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);
        int userId = SessionHelper.getUserId(session);

        DiscussionTopic discussionTopic = GroupDiscussionDao.getDiscussion(groupId, topicId, userId);

        if (discussionTopic == null || (!discussionTopic.isOpen() && !Group.hasTopicAdmin(playerDetails.getRank()))) {
            return "redirect:/";
        }

        var latestMessage = GroupDiscussionDao.getLatestReply(userId);

        if (latestMessage != null && latestMessage.getMessage().startsWith(message)) {
            model.addAttribute("hasMessage", true);
            model.addAttribute("message", "Do not spam the forums");
            return "groups/discussion_replies";
        }

        Group group = GroupDao.getGroup(groupId);

        if (group == null) {
            return "redirect:/";
        }

        if (group.getForumType() == GroupForumType.PRIVATE) {
            var groupMember = group.getMember(userId);

            if (groupMember == null) {
                return "redirect:/";
            }
        }

        session.removeAttribute("captcha-text");

        GroupDiscussionDao.createReplies(topicId, userId, message);

        int limit = GameConfiguration.getInstance().getInteger("discussions.replies.per.page");
        int replyCount = GroupDiscussionDao.countReplies(discussionTopic.getId());
        int pages = replyCount > 0 ? (int) Math.ceil((double) replyCount / (double) limit) : 1;

        DiscussionController.appendPageData(session, model, group, discussionTopic, pages);
        return "groups/discussion_replies";
    }
}
