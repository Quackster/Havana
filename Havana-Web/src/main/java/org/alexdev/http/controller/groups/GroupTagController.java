package org.alexdev.http.controller.groups;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.GroupDao;
import org.alexdev.havana.dao.mysql.TagDao;
import org.alexdev.havana.game.groups.Group;
import org.alexdev.havana.game.wordfilter.WordfilterManager;
import org.alexdev.havana.util.StringUtil;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.util.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class GroupTagController {

    @PostMapping("/groups/tags/add")
    @ResponseBody
    public String addGroupTag(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            @RequestParam(value = "tagName", defaultValue = "") String tagName,
            HttpSession session) {

        int userId = SessionHelper.getUserId(session);

        if (userId < 1) {
            return "";
        }

        Group group = GroupDao.getGroup(groupId);

        if (group == null) {
            return "";
        }

        if (group.getOwnerId() != userId) {
            return "";
        }

        var tagList = TagDao.getGroupTags(groupId);

        if (tagList.size() >= GameConfiguration.getInstance().getInteger("max.tags.groups")) {
            return "taglimit";
        }

        String tag = StringUtil.isValidTag(tagName, 0, 0, groupId);

        if (tag == null) {
            return "invalidtag";
        }

        if (WordfilterManager.filterSentence(tag).equals(tag)) {
            StringUtil.addTag(tag, 0, 0, groupId);
        }

        return "valid";
    }

    @PostMapping("/groups/tags/remove")
    public String removeGroupTag(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            @RequestParam(value = "tagName", defaultValue = "") String tagName,
            HttpSession session,
            Model model) {

        int userId = SessionHelper.getUserId(session);

        if (userId < 1) {
            return "";
        }

        Group group = GroupDao.getGroup(groupId);

        if (group == null) {
            return "";
        }

        if (group.getOwnerId() != userId) {
            return "";
        }

        TagDao.removeTag(0, 0, groupId, tagName);
        List<String> groupTags = TagDao.getGroupTags(groupId);

        model.addAttribute("tags", groupTags);
        model.addAttribute("group", group);
        return "groups/habblet/listgrouptags";
    }

    @PostMapping("/groups/tags/list")
    public String listGroupTags(
            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
            HttpSession session,
            Model model) {

        int userId = SessionHelper.getUserId(session);

        if (userId < 1) {
            return "";
        }

        Group group = GroupDao.getGroup(groupId);

        if (group == null) {
            return "";
        }

        List<String> groupTags = TagDao.getGroupTags(groupId);

        model.addAttribute("tags", groupTags);
        model.addAttribute("group", group);
        return "groups/habblet/listgrouptags";
    }
}
