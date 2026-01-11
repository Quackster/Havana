package org.alexdev.http.controller.groups.discussions;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.http.dao.GroupDiscussionDao;
import org.alexdev.http.game.groups.DiscussionTopic;
import org.alexdev.http.util.BBCode;
import org.alexdev.http.util.HtmlUtil;
import org.alexdev.http.util.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DiscussionPreviewController {

    @PostMapping("/discussions/preview/topic")
    public String previewTopic(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            @RequestParam(value = "topicName", defaultValue = "") String topicName,
            @RequestParam(value = "message", defaultValue = "") String topicMessage,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);
        var displayBadges = GroupDiscussionDao.getDisplayBadges(userId);

        model.addAttribute("topicName", topicName);
        model.addAttribute("topicMessage", BBCode.format(HtmlUtil.escape(BBCode.normalise(topicMessage)), false));
        model.addAttribute("previewDay", DateUtil.getDate(DateUtil.getCurrentTimeSeconds(), "MMM dd, yyyy").replace("am", "AM").replace("pm", "PM").replace(".", ""));
        model.addAttribute("previewTime", DateUtil.getDate(DateUtil.getCurrentTimeSeconds(), "h:mm a").replace("am", "AM").replace("pm", "PM").replace(".", ""));
        model.addAttribute("userReplies", GroupDiscussionDao.countUserReplies(userId));

        model.addAttribute("hasBadge", false);
        model.addAttribute("hasGroup", false);

        if (displayBadges[0] != null) {
            model.addAttribute("hasBadge", true);
            model.addAttribute("badge", displayBadges[0]);
        }

        if (displayBadges[1] != null) {
            PlayerDetails playerDetails = SessionHelper.getPlayer(session);
            model.addAttribute("hasGroup", true);
            model.addAttribute("groupId", playerDetails.getFavouriteGroupId());
            model.addAttribute("groupBadge", displayBadges[1]);
        }

        return "groups/discussions/previewtopic";
    }

    @PostMapping("/discussions/preview/post")
    public String previewPost(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            @RequestParam(value = "topicId", defaultValue = "0") int topicId,
            @RequestParam(value = "message", defaultValue = "") String topicMessage,
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

        var displayBadges = GroupDiscussionDao.getDisplayBadges(userId);

        model.addAttribute("postName", "RE: " + discussionTopic.getTopicTitle());
        model.addAttribute("postMessage", BBCode.format(HtmlUtil.escape(BBCode.normalise(topicMessage)), false));
        model.addAttribute("previewDay", DateUtil.getDate(DateUtil.getCurrentTimeSeconds(), "MMM dd, yyyy").replace("am", "AM").replace("pm", "PM").replace(".", ""));
        model.addAttribute("previewTime", DateUtil.getDate(DateUtil.getCurrentTimeSeconds(), "h:mm a").replace("am", "AM").replace("pm", "PM").replace(".", ""));
        model.addAttribute("userReplies", GroupDiscussionDao.countUserReplies(userId));

        model.addAttribute("hasBadge", false);
        model.addAttribute("hasGroup", false);

        if (displayBadges[0] != null) {
            model.addAttribute("hasBadge", true);
            model.addAttribute("badge", displayBadges[0]);
        }

        if (displayBadges[1] != null) {
            PlayerDetails playerDetails = SessionHelper.getPlayer(session);
            model.addAttribute("hasGroup", true);
            model.addAttribute("groupId", playerDetails.getFavouriteGroupId());
            model.addAttribute("groupBadge", displayBadges[1]);
        }

        return "groups/discussions/previewpost";
    }
}
