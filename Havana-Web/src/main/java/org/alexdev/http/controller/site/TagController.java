package org.alexdev.http.controller.site;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.MessengerDao;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.dao.mysql.TagDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.tags.HabboTag;
import org.alexdev.havana.game.wordfilter.WordfilterManager;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.havana.util.StringUtil;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.scheduler.WatchdogScheduler;
import org.alexdev.http.util.HtmlUtil;
import org.alexdev.http.util.RconUtil;
import org.alexdev.http.util.SessionHelper;
import org.alexdev.http.util.TagUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TagController {

    @GetMapping("/tag")
    public String tag(
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageId,
            HttpSession session,
            Model model) {

        session.setAttribute("page", "community");

        List<HabboTag> tags = new ArrayList<>();
        Map<Integer, List<HabboTag>> paginatedUsers = StringUtil.paginate(tags, 5);

        if (!paginatedUsers.containsKey(pageId - 1)) {
            pageId = 1;
        }

        if (paginatedUsers.get(pageId - 1) == null) {
            paginatedUsers.put(pageId - 1, new ArrayList<>());
        }

        model.addAttribute("tagList", paginatedUsers.get(pageId - 1));
        model.addAttribute("pageId", pageId);
        model.addAttribute("totalCount", tags.size());
        model.addAttribute("tagCloud", WatchdogScheduler.TAG_CLOUD_10);
        model.addAttribute("tagSearchAdd", "");
        model.addAttribute("showOlder", false);
        model.addAttribute("showOldest", false);
        model.addAttribute("showNewer", false);
        model.addAttribute("showNewest", false);
        model.addAttribute("showFirst", false);
        model.addAttribute("showLast", false);
        model.addAttribute("showFirstPage", 1);

        return "tag";
    }

    @GetMapping("/tag/{tagName}")
    public String searchByPath(
            @PathVariable String tagName,
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageId,
            HttpSession session,
            Model model) {

        return respondWithSearch(session, model, tagName, pageId, "tag");
    }

    @GetMapping("/tag/search")
    public String search(
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageId,
            HttpSession session,
            Model model) {

        if (tag == null) tag = "";
        return respondWithSearch(session, model, tag, pageId, "tag");
    }

    @PostMapping("/habblet/ajax/tagsearch")
    public String tagSearch(
            @RequestParam(value = "tag", defaultValue = "") String tag,
            HttpSession session,
            Model model) {

        return respondWithSearch(session, model, tag, 0, "base/tag_search");
    }

    @PostMapping("/habblet/ajax/tagadd")
    @ResponseBody
    public String add(@RequestParam(value = "tagName", defaultValue = "") String tagName, HttpSession session) {
        int userId = SessionHelper.getUserId(session);

        if (userId < 1) {
            return "";
        }

        var tagList = TagDao.getUserTags(userId);

        if (tagList.size() >= GameConfiguration.getInstance().getInteger("max.tags.users")) {
            return "taglimit";
        }

        String tag = StringUtil.isValidTag(tagName, userId, 0, 0);

        if (tag == null) {
            return "invalidtag";
        }

        if (WordfilterManager.filterSentence(tag).equals(tag)) {
            StringUtil.addTag(tag, userId, 0, 0);
        }

        RconUtil.sendCommand(RconHeader.REFRESH_TAGS, new HashMap<>() {{
            put("userId", userId);
        }});

        return "valid";
    }

    @PostMapping("/habblet/ajax/tagremove")
    public String remove(
            @RequestParam(value = "tagName", defaultValue = "") String tagName,
            HttpSession session,
            Model model) {

        int userId = SessionHelper.getUserId(session);

        if (userId < 1) {
            return "";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        if (playerDetails == null) {
            return "";
        }

        TagDao.removeTag(userId, 0, 0, tagName);

        List<String> tags = TagDao.getUserTags(userId);
        model.addAttribute("tags", tags);
        model.addAttribute("user", playerDetails);

        RconUtil.sendCommand(RconHeader.REFRESH_TAGS, new HashMap<>() {{
            put("userId", userId);
        }});

        return "homes/widget/habblet/taglist";
    }

    @GetMapping("/habblet/ajax/mytaglist")
    public String myTagList(HttpSession session, Model model) {
        int userId = SessionHelper.getUserId(session);

        if (userId < 1) {
            return "";
        }

        model.addAttribute("tags", TagDao.getUserTags(userId));
        model.addAttribute("tagRandomQuestion", TagUtil.getRandomQuestion());

        return "habblet/myTagList";
    }

    @PostMapping("/habblet/ajax/tagfight")
    public String tagFight(
            @RequestParam(value = "tag1", defaultValue = "") String firstTag,
            @RequestParam(value = "tag2", defaultValue = "") String secondTag,
            Model model) {

        firstTag = HtmlUtil.removeHtmlTags(firstTag);
        secondTag = HtmlUtil.removeHtmlTags(secondTag);

        int firstCount = TagDao.countTag(firstTag);
        int secondCount = TagDao.countTag(secondTag);
        int imageNumber = 0;

        String result = "Tie!";

        if (secondCount > firstCount) {
            imageNumber = 1;
            result = "The winner is:";
        }

        if (secondCount < firstCount) {
            imageNumber = 2;
            result = "The winner is:";
        }

        model.addAttribute("result", result);
        model.addAttribute("resultTag1", firstTag);
        model.addAttribute("resultTag2", secondTag);
        model.addAttribute("resultHits1", firstCount);
        model.addAttribute("resultHits2", secondCount);
        model.addAttribute("tagFightImage", imageNumber);

        return "habblet/tagFightResult";
    }

    @PostMapping("/habblet/ajax/tagmatch")
    public String tagMatch(
            @RequestParam(value = "friendName", defaultValue = "") String friendName,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        if (playerDetails == null) {
            return "redirect:/";
        }

        String errorMessage = "";

        if (!MessengerDao.friendExists(playerDetails.getId(), PlayerDao.getId(friendName))) {
            errorMessage = "Friend not found. Are you sure that they really exist?";
        }

        model.addAttribute("errorMsg", errorMessage);
        return "habblet/tagMatch";
    }

    @PostMapping("/habblet/ajax/removealltags")
    @ResponseBody
    public String removeAllTags(HttpSession session) {
        int userId = SessionHelper.getUserId(session);

        if (userId < 1) {
            return "Please login to remove all your tags.";
        }

        var myTagList = TagDao.getUserTags(userId);
        TagDao.removeTags(userId, 0, 0);
        return "All tags removed!<br><br>The tags removed: " + String.join(", ", myTagList);
    }

    private String respondWithSearch(HttpSession session, Model model, String tag, int pageId, String template) {
        session.setAttribute("page", "community");

        try {
            tag = URLDecoder.decode(tag, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            // Ignore
        }

        List<HabboTag> tags = tag.isBlank() ? new ArrayList<>() : TagDao.getTagInfoList(tag);
        Map<Integer, List<HabboTag>> paginatedUsers = StringUtil.paginate(tags, 5);

        if (!paginatedUsers.containsKey(pageId - 1)) {
            pageId = 1;
        }

        tag = StringUtils.normalizeSpace(HtmlUtil.removeHtmlTags(tag));

        model.addAttribute("tagSearchAdd", "");

        if (SessionHelper.isAuthenticated(session)) {
            int userId = SessionHelper.getUserId(session);
            var temporaryTag = StringUtil.isValidTag(tag, userId, 0, 0);
            boolean isValidTag = temporaryTag != null;

            if (isValidTag) {
                model.addAttribute("tagSearchAdd", tag);
            }
        }

        model.addAttribute("showOlder", false);
        model.addAttribute("showOldest", false);
        model.addAttribute("showNewer", false);
        model.addAttribute("showNewest", false);
        model.addAttribute("showFirst", false);
        model.addAttribute("showFirstPage", 1);
        model.addAttribute("showLast", false);

        int codePage = pageId - 1;

        if (paginatedUsers.containsKey(codePage - 1)) {
            model.addAttribute("showOlder", true);
        }

        if (paginatedUsers.containsKey(codePage - 2)) {
            model.addAttribute("showOldest", true);
        }

        if (paginatedUsers.containsKey(codePage + 1)) {
            model.addAttribute("showNewer", true);
        }

        if (paginatedUsers.containsKey(codePage + 2)) {
            model.addAttribute("showNewest", true);
        }

        if (paginatedUsers.containsKey(codePage + 3)) {
            model.addAttribute("showLast", true);
            model.addAttribute("showLastPage", paginatedUsers.size());
        }

        if (paginatedUsers.containsKey(codePage - 3)) {
            model.addAttribute("showFirst", true);
            model.addAttribute("showFirstPage", 1);
        }

        List<HabboTag> tagList = paginatedUsers.get(pageId - 1);
        if (tagList == null) {
            tagList = new ArrayList<>();
        }

        model.addAttribute("tagList", tagList);
        model.addAttribute("totalTagUsers", paginatedUsers);
        model.addAttribute("tag", tag);
        model.addAttribute("pageId", pageId);
        model.addAttribute("totalCount", tags.size());
        model.addAttribute("tagCloud", WatchdogScheduler.TAG_CLOUD_10);
        model.addAttribute("lastPage", tags.size());

        return template;
    }
}
