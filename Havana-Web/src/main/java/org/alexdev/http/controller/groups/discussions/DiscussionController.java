package org.alexdev.http.controller.groups.discussions;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.GroupDao;
import org.alexdev.havana.game.groups.Group;
import org.alexdev.havana.game.groups.GroupForumType;
import org.alexdev.havana.game.groups.GroupMember;
import org.alexdev.havana.game.groups.GroupMemberRank;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.dao.GroupDiscussionDao;
import org.alexdev.http.dao.ReplyDao;
import org.alexdev.http.game.groups.DiscussionReply;
import org.alexdev.http.game.groups.DiscussionTopic;
import org.alexdev.http.util.SessionHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DiscussionController {

    @GetMapping("/groups/{groupIdentifier}/discussions/{discussionId}/id")
    public String viewDiscussion(
            @PathVariable String groupIdentifier,
            @PathVariable int discussionId,
            HttpSession session,
            Model model) {

        session.setAttribute("page", "community");

        Group group = null;

        if (StringUtils.isNumeric(groupIdentifier)) {
            group = GroupDao.getGroup(Integer.parseInt(groupIdentifier));

            if (group != null && group.getAlias() != null && !group.getAlias().isBlank()) {
                return "redirect:/groups/" + group.getAlias() + "/discussions/" + discussionId + "/id";
            }
        } else {
            group = GroupDao.getGroupByAlias(groupIdentifier);
        }

        if (group == null) {
            return "error/404";
        }

        int userId = SessionHelper.isAuthenticated(session) ? SessionHelper.getUserId(session) : 0;
        DiscussionTopic discussionTopic = GroupDiscussionDao.getDiscussion(group.getId(), discussionId, userId);

        if (discussionTopic == null) {
            return "error/404";
        }

        if (!session.getAttribute("hasViewedDiscussion" + discussionId).equals(Boolean.TRUE)) {
            session.setAttribute("hasViewedDiscussion" + discussionId, true);
            GroupDiscussionDao.incrementViews(discussionId);
        }

        appendPageData(session, model, group, discussionTopic, 1);
        return "groups/discussion";
    }

    @GetMapping("/groups/{groupIdentifier}/discussions/{discussionId}/id/page/{pageNumber}")
    public String viewDiscussionPage(
            @PathVariable String groupIdentifier,
            @PathVariable int discussionId,
            @PathVariable int pageNumber,
            HttpSession session,
            Model model) {

        session.setAttribute("page", "community");

        Group group = null;

        if (StringUtils.isNumeric(groupIdentifier)) {
            group = GroupDao.getGroup(Integer.parseInt(groupIdentifier));

            if (group != null && group.getAlias() != null && !group.getAlias().isBlank()) {
                return "redirect:/groups/" + group.getAlias() + "/discussions/" + discussionId + "/id/page/" + pageNumber;
            }
        } else {
            group = GroupDao.getGroupByAlias(groupIdentifier);
        }

        if (group == null) {
            return "error/404";
        }

        int userId = SessionHelper.isAuthenticated(session) ? SessionHelper.getUserId(session) : 0;
        DiscussionTopic discussionTopic = GroupDiscussionDao.getDiscussion(group.getId(), discussionId, userId);

        if (discussionTopic == null) {
            return "error/404";
        }

        if (session.getAttribute("hasViewedDiscussion" + discussionId) == null) {
            session.setAttribute("hasViewedDiscussion" + discussionId, true);
            GroupDiscussionDao.incrementViews(discussionId);
        }

        appendPageData(session, model, group, discussionTopic, pageNumber);
        return "groups/discussion";
    }

    public static void appendPageData(HttpSession session, Model model, Group group, DiscussionTopic discussionTopic, int pageNumber) {
        boolean loggedIn = SessionHelper.isAuthenticated(session);
        boolean hasTopicAdmin = false;

        if (loggedIn) {
            PlayerDetails playerDetails = SessionHelper.getPlayer(session);
            hasTopicAdmin = Group.hasTopicAdmin(playerDetails.getRank());
        }

        model.addAttribute("group", group);
        model.addAttribute("hasMember", false);
        model.addAttribute("hasMessage", false);
        model.addAttribute("canViewForum", group.getForumType() == GroupForumType.PUBLIC);
        model.addAttribute("canReplyForum", false);

        int userId = SessionHelper.isAuthenticated(session) ? SessionHelper.getUserId(session) : 0;

        if (loggedIn) {
            GroupMember groupMember = group.getMember(userId);

            model.addAttribute("canViewForum", group.canViewForum(groupMember));
            model.addAttribute("canReplyForum", group.canReplyForum(groupMember));

            if (groupMember != null) {
                model.addAttribute("hasMember", true);
                model.addAttribute("groupMember", groupMember);

                if (!hasTopicAdmin) {
                    hasTopicAdmin = (groupMember.getMemberRank() == GroupMemberRank.ADMINISTRATOR || groupMember.getMemberRank() == GroupMemberRank.OWNER);
                }
            }
        }

        if (!discussionTopic.isOpen()) {
            model.addAttribute("canReplyForum", false);
        }

        Boolean canViewForum = (Boolean) model.getAttribute("canViewForum");
        if (canViewForum == null || !canViewForum) {
            model.addAttribute("hasMessage", true);
            model.addAttribute("message", "View forums denied. Please check that you are logged in and have the appropriate rights to view the forums.");
            return;
        }

        int firstReply = GroupDiscussionDao.getFirstReply(discussionTopic.getId());
        model.addAttribute("firstReply", firstReply);

        int limit = GameConfiguration.getInstance().getInteger("discussions.replies.per.page");

        int replyCount = GroupDiscussionDao.countReplies(discussionTopic.getId());
        int pages = replyCount > 0 ? (int) Math.ceil((double) replyCount / (double) limit) : 1;
        List<DiscussionReply> replyList = GroupDiscussionDao.getReplies(discussionTopic.getId(), pageNumber, limit, userId);

        if (userId > 0) {
            var isNew = replyList.stream().filter(DiscussionReply::isNew).collect(Collectors.toList());

            if (isNew.size() > 0) {
                ReplyDao.read(userId, isNew);
            }
        }

        for (int i = 1; i <= 3; i++) {
            int newPage = pageNumber - i;
            model.addAttribute("previousPage" + i, newPage >= 1 ? newPage : -1);
        }

        for (int i = 1; i <= 3; i++) {
            int newPage = pageNumber + i;
            model.addAttribute("nextPage" + i, (newPage > 1 && newPage <= pages) ? newPage : -1);
        }

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("pages", pages);
        model.addAttribute("replyList", replyList);
        model.addAttribute("discussionId", discussionTopic.getId());
        model.addAttribute("discussionTopic", discussionTopic);
        model.addAttribute("hasTopicAdmin", hasTopicAdmin);
    }
}
