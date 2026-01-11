package org.alexdev.http.controller.habblet;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.MessengerDao;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.messenger.Messenger;
import org.alexdev.havana.game.messenger.MessengerManager;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.havana.util.StringUtil;
import org.alexdev.http.util.RconUtil;
import org.alexdev.http.util.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Controller
public class InviteController {

    @PostMapping("/myhabbo/invite/link")
    public String inviteLink(HttpSession session) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        return "habblet/invite_referralLink";
    }

    @PostMapping("/myhabbo/invite/search")
    public String searchContent(
            @RequestParam(value = "searchString", defaultValue = "") String searchString,
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);
        Messenger messenger = new Messenger(playerDetails);

        int pageId = pageNumber - 1;
        int nextPageId = -1;
        int previousPageId = -1;

        List<PlayerDetails> searchedFriends = new ArrayList<>();

        for (int userId : MessengerDao.search(searchString)) {
            if (playerDetails.getId() == userId) {
                continue;
            }

            searchedFriends.add(PlayerDao.getDetails(userId));
        }

        searchedFriends.sort(Comparator.comparing(PlayerDetails::getName));

        var searchMap = StringUtil.paginate(searchedFriends, 5);
        List<PlayerDetails> searchResults;

        if (searchMap.containsKey(pageId)) {
            searchResults = searchMap.get(pageId);
        } else {
            searchResults = new ArrayList<>();
        }

        if (searchMap.containsKey(pageId - 1)) {
            previousPageId = pageId - 1;
        }

        if (searchMap.containsKey(pageId + 1)) {
            nextPageId = pageId + 1;
        }

        nextPageId = nextPageId > -1 ? nextPageId + 1 : -1;
        previousPageId = previousPageId > -1 ? previousPageId + 1 : -1;

        model.addAttribute("searchResults", searchResults);
        model.addAttribute("currentPage", pageId + 1);
        model.addAttribute("totalPages", searchMap.size());
        model.addAttribute("previousPageId", previousPageId);
        model.addAttribute("nextPageId", nextPageId);
        model.addAttribute("messenger", messenger);

        return "habblet/invite_searchContent";
    }

    @PostMapping("/myhabbo/invite/confirmAddFriend")
    public String confirmAddFriend(HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        if (playerDetails == null) {
            return "";
        }

        model.addAttribute("username", playerDetails.getName());

        return "habblet/invite_confirmAddFriend";
    }

    @PostMapping("/myhabbo/invite/addFriend")
    public String addFriend(
            @RequestParam(value = "accountId", defaultValue = "0") int accountId,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        model.addAttribute("message", createFriendRequestResponse(session, accountId));

        return "habblet/invite_addFriend";
    }

    @PostMapping("/myhabbo/invite/add")
    @ResponseBody
    public String add(
            @RequestParam(value = "accountId", defaultValue = "0") int accountId,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        return "Dialog.showInfoDialog(\"add-friend-messages\", \"" + createFriendRequestResponse(session, accountId) + "\", \"OK\");";
    }

    private String createFriendRequestResponse(HttpSession session, int accountId) {
        String response;
        int userId = SessionHelper.getUserId(session);

        Messenger target = MessengerManager.getInstance().getMessengerData(accountId);
        Messenger callee = MessengerManager.getInstance().getMessengerData(userId);

        if (target == null) {
            response = "There was an error finding the user for the friend request.";
        } else {
            if (target.getMessengerUser().getUsername().equalsIgnoreCase("Abigail.Ryan")) {
                target = null;
            }

            if (target == null) {
                response = "There was an error finding the user for the friend request.";
            } else if (callee.isFriendsLimitReached()) {
                response = "Your friends list is full.";
            } else if (target.hasFriend(userId)) {
                response = "This person is already your friend";
            } else if (target.hasRequest(userId)) {
                response = "There is already a friend request for this user.";
            } else if (target.isFriendsLimitReached()) {
                response = "This user's friend list is full.";
            } else if (!target.allowsFriendRequests()) {
                response = "This user does not accept friend requests at the moment.";
            } else if (accountId == userId) {
                response = "There was an error processing your request.";
            } else {
                response = "Friend request has been sent successfully.";
                target.addRequest(callee.getMessengerUser());

                RconUtil.sendCommand(RconHeader.FRIEND_REQUEST, new HashMap<>() {{
                    put("userId", userId);
                    put("friendId", accountId);
                }});
            }
        }

        return response;
    }
}
