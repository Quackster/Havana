package org.alexdev.http.controller.site;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.MessengerDao;
import org.alexdev.havana.game.messenger.MessengerCategory;
import org.alexdev.havana.game.messenger.MessengerUser;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.http.dao.FriendManagementDao;
import org.alexdev.http.util.RconUtil;
import org.alexdev.http.util.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class FriendManagementController {

    @PostMapping("/friendmanagement/editcategory")
    public String editCategory(
            @RequestParam(value = "name", defaultValue = "") String newName,
            @RequestParam(value = "categoryId", defaultValue = "0") int categoryId,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        if (!newName.isBlank()) {
            MessengerDao.updateCategory(newName, categoryId, playerDetails.getId());
        }

        RconUtil.sendCommand(RconHeader.REFRESH_MESSENGER_CATEGORIES, new HashMap<>() {{
            put("userId", playerDetails.getId());
        }});

        var categories = MessengerDao.getCategories(playerDetails.getId());
        categories.sort(Comparator.comparingInt(MessengerCategory::getId));

        model.addAttribute("categories", categories);
        return "profile/profile_widgets/friend_category_widget";
    }

    @PostMapping("/friendmanagement/createcategory")
    public String createCategory(
            @RequestParam(value = "name", defaultValue = "") String newName,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        if (!newName.isBlank()) {
            if (newName.length() > 50) {
                newName = newName.substring(0, 50);
            }

            MessengerDao.addCategory(newName, playerDetails.getId());
        }

        RconUtil.sendCommand(RconHeader.REFRESH_MESSENGER_CATEGORIES, new HashMap<>() {{
            put("userId", playerDetails.getId());
        }});

        var categories = MessengerDao.getCategories(playerDetails.getId());
        categories.sort(Comparator.comparingInt(MessengerCategory::getId));

        model.addAttribute("categories", categories);
        return "profile/profile_widgets/friend_category_widget";
    }

    @PostMapping("/friendmanagement/deletecategory")
    public String deleteCategory(
            @RequestParam(value = "categoryId", defaultValue = "0") int categoryId,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        MessengerDao.deleteCategory(categoryId, playerDetails.getId());

        RconUtil.sendCommand(RconHeader.REFRESH_MESSENGER_CATEGORIES, new HashMap<>() {{
            put("userId", playerDetails.getId());
        }});

        var categories = MessengerDao.getCategories(playerDetails.getId());
        categories.sort(Comparator.comparingInt(MessengerCategory::getId));

        MessengerDao.resetFriendCategories(playerDetails.getId(), categoryId);

        model.addAttribute("categories", categories);
        return "profile/profile_widgets/friend_category_widget";
    }

    @GetMapping("/friendmanagement/viewcategory")
    public String viewCategoryGet(
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "30") int pageSize,
            @RequestParam(value = "categoryId", defaultValue = "-1") int categoryId,
            @RequestParam(value = "searchString", required = false) String searchString,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        if (pageSize > 100 || pageSize <= 0) {
            pageSize = 30;
        }

        if (pageNumber <= 0) {
            pageNumber = 1;
        }

        appendFriendManagement(session, model, pageSize, pageNumber, categoryId, searchString);
        return "profile/profile_widgets/friend_view_category";
    }

    @PostMapping("/friendmanagement/viewcategory")
    public String viewCategoryPost(
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "30") int pageSize,
            @RequestParam(value = "categoryId", defaultValue = "-1") int categoryId,
            @RequestParam(value = "searchString", required = false) String searchString,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        if (pageSize > 100 || pageSize <= 0) {
            pageSize = 30;
        }

        if (pageNumber <= 0) {
            pageNumber = 1;
        }

        appendFriendManagement(session, model, pageSize, pageNumber, categoryId, searchString);
        return "profile/profile_widgets/friend_view_category";
    }

    @PostMapping("/friendmanagement/updatecategoryoptions")
    public String updateCategoryOptions(HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);
        model.addAttribute("categories", MessengerDao.getCategories(playerDetails.getId()));
        return "profile/profile_widgets/friend_category_options";
    }

    @PostMapping("/friendmanagement/movefriends")
    public String moveFriends(
            @RequestParam(value = "pageSize", defaultValue = "30") int pageSize,
            @RequestParam(value = "moveCategoryId", defaultValue = "-1") int categoryId,
            @RequestParam(value = "friendList[]", required = false) List<String> friendList,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        if (pageSize > 100 || pageSize <= 0) {
            pageSize = 30;
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        if (friendList != null) {
            for (String value : friendList) {
                try {
                    int userId = Integer.parseInt(value);
                    MessengerDao.updateFriendCategory(playerDetails.getId(), userId, categoryId);
                } catch (Exception ex) {
                    // ignore
                }
            }
        }

        RconUtil.sendCommand(RconHeader.REFRESH_MESSENGER_CATEGORIES, new HashMap<>() {{
            put("userId", playerDetails.getId());
        }});

        appendFriendManagement(session, model, pageSize, 1, categoryId, null);
        return "profile/profile_widgets/friend_view_category";
    }

    @PostMapping("/friendmanagement/deletefriends")
    public String deleteFriends(
            @RequestParam(value = "friendList[]", required = false) List<String> friendList,
            @RequestParam(value = "friendId", defaultValue = "0") int friendId,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        if (friendList != null) {
            for (String value : friendList) {
                int userId = Integer.parseInt(value);
                MessengerDao.removeFriend(playerDetails.getId(), userId);
                MessengerDao.removeFriend(userId, playerDetails.getId());
            }
        }

        if (friendId > 0) {
            MessengerDao.removeFriend(playerDetails.getId(), friendId);
            MessengerDao.removeFriend(friendId, playerDetails.getId());
        }

        RconUtil.sendCommand(RconHeader.REFRESH_MESSENGER_CATEGORIES, new HashMap<>() {{
            put("userId", playerDetails.getId());
        }});

        appendFriendManagement(session, model, 30, 1, -1, null);
        return "profile/profile_widgets/friend_view_category";
    }

    private void appendFriendManagement(HttpSession session, Model model, int limit, int currentPage, int categoryId, String searchString) {
        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        var friendsCount = 0;
        List<MessengerUser> friends = new ArrayList<>();

        if (searchString != null) {
            friends = FriendManagementDao.getFriendsSearch(playerDetails.getId(), searchString, currentPage, limit).stream()
                    .sorted(Comparator.comparingLong(MessengerUser::getLastOnline).reversed())
                    .collect(Collectors.toList());
            friendsCount = FriendManagementDao.getFriendsCount(playerDetails.getId(), searchString);
        } else {
            friends = FriendManagementDao.getFriends(playerDetails.getId(), currentPage, limit).stream()
                    .sorted(Comparator.comparingLong(MessengerUser::getLastOnline).reversed())
                    .collect(Collectors.toList());
            friendsCount = FriendManagementDao.getFriendsCount(playerDetails.getId());
        }

        int pages = friendsCount > 0 ? (int) Math.ceil((double) friendsCount / (double) limit) : 0;

        if (pages == 0) {
            pages = 1;
        }

        var categories = MessengerDao.getCategories(playerDetails.getId());
        categories.sort(Comparator.comparingInt(MessengerCategory::getId));

        for (MessengerUser friend : friends) {
            if (categories.stream().noneMatch(category -> friend.getCategoryId() == category.getId())) {
                friend.setCategoryId(0);
                MessengerDao.updateFriendCategory(playerDetails.getId(), friend.getUserId(), 0);
            }
        }

        if (categoryId > -1) {
            friends = friends.stream().filter(friend -> friend.getCategoryId() == categoryId).collect(Collectors.toList());
        }

        model.addAttribute("friends", friends);
        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageLimit", limit);

        if (currentPage >= 2) {
            model.addAttribute("firstPage", 1);
        } else {
            model.addAttribute("firstPage", -1);
        }

        if (currentPage > 1) {
            model.addAttribute("previousPage", currentPage - 1);
        } else {
            model.addAttribute("previousPage", -1);
        }

        if (pages >= (currentPage + 1)) {
            model.addAttribute("nextPage", currentPage + 1);
        } else {
            model.addAttribute("nextPage", -1);
        }

        if (pages >= (currentPage + 2)) {
            model.addAttribute("lastPage", pages);
        } else {
            model.addAttribute("lastPage", -1);
        }
    }
}
