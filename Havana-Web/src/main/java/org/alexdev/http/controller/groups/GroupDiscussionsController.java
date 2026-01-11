package org.alexdev.http.controller.groups;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.GroupDao;
import org.alexdev.havana.game.groups.Group;
import org.alexdev.havana.game.groups.GroupForumType;
import org.alexdev.havana.game.groups.GroupMember;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.dao.GroupDiscussionDao;
import org.alexdev.http.game.groups.DiscussionTopic;
import org.alexdev.http.util.SessionHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GroupDiscussionsController {

    @GetMapping("/groups/{groupIdentifier}/discussions")
    public String viewDiscussions(
            @PathVariable String groupIdentifier,
            HttpSession session,
            Model model) {

        session.setAttribute("page", "community");

        Group group = null;

        if (StringUtils.isNumeric(groupIdentifier)) {
            return "redirect:/groups/" + groupIdentifier + "/id/discussions";
        } else {
            group = GroupDao.getGroupByAlias(groupIdentifier);
        }

        if (group == null) {
            return "error/404";
        }

        setGamePage(session, group);

        model.addAttribute("group", group);
        render(session, model, group, 1);
        return "groups/view_discussions";
    }

    @GetMapping("/groups/{groupId}/id/discussions")
    public String viewDiscussionsById(
            @PathVariable int groupId,
            HttpSession session,
            Model model) {

        session.setAttribute("page", "community");

        Group group = GroupDao.getGroup(groupId);

        if (group == null) {
            return "error/404";
        }

        if (group.getAlias() != null && !group.getAlias().isBlank()) {
            return "redirect:/groups/" + group.getAlias() + "/discussions";
        }

        setGamePage(session, group);

        model.addAttribute("group", group);
        render(session, model, group, 1);
        return "groups/view_discussions";
    }

    @GetMapping("/groups/{groupIdentifier}/discussions/page/{pageNumber}")
    public String viewDiscussionsPage(
            @PathVariable String groupIdentifier,
            @PathVariable int pageNumber,
            HttpSession session,
            Model model) {

        session.setAttribute("page", "community");

        Group group = null;

        if (StringUtils.isNumeric(groupIdentifier)) {
            group = GroupDao.getGroup(Integer.parseInt(groupIdentifier));

            if (group != null && group.getAlias() != null && !group.getAlias().isBlank()) {
                return "redirect:/groups/" + group.getAlias() + "/discussions/page/" + pageNumber;
            }
        } else {
            group = GroupDao.getGroupByAlias(groupIdentifier);
        }

        if (group == null) {
            return "error/404";
        }

        setGamePage(session, group);

        model.addAttribute("group", group);
        render(session, model, group, pageNumber);
        return "groups/view_discussions";
    }

    private void setGamePage(HttpSession session, Group group) {
        if (group.getAlias() != null) {
            if (group.getAlias().equalsIgnoreCase("battleball_rebound") ||
                group.getAlias().equalsIgnoreCase("lido") ||
                group.getAlias().equalsIgnoreCase("snow_storm") ||
                group.getAlias().equalsIgnoreCase("wobble_squabble")) {
                session.setAttribute("page", "games");
            }
        }
    }

    private void render(HttpSession session, Model model, Group group, int pageNumber) {
        boolean loggedIn = SessionHelper.isAuthenticated(session);
        model.addAttribute("hasMember", false);
        model.addAttribute("canViewForum", group.getForumType() == GroupForumType.PUBLIC);
        model.addAttribute("canPostForum", false);

        if (loggedIn) {
            int userId = SessionHelper.getUserId(session);
            GroupMember groupMember = group.getMember(userId);

            model.addAttribute("canPostForum", group.canForumPost(groupMember));

            if (groupMember != null) {
                model.addAttribute("hasMember", true);
                model.addAttribute("groupMember", groupMember);
                model.addAttribute("canViewForum", group.canViewForum(groupMember));
            }
        }

        if (pageNumber <= 0) {
            pageNumber = 1;
        }

        List<DiscussionTopic> discussionTopics = new ArrayList<>();

        int limit = GameConfiguration.getInstance().getInteger("discussions.per.page");
        int discussionCount = 0;
        int pages = 1;

        Boolean canViewForum = (Boolean) model.getAttribute("canViewForum");
        if (canViewForum != null && canViewForum) {
            int userId = loggedIn ? SessionHelper.getUserId(session) : 0;
            discussionCount = GroupDiscussionDao.countDiscussions(group.getId());
            pages = discussionCount > 0 ? (int) Math.ceil((double) discussionCount / (double) limit) : 1;
            discussionTopics = GroupDiscussionDao.getDiscussions(group.getId(), pageNumber, limit, userId);
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
        model.addAttribute("discussionTopics", discussionTopics);
    }
}
