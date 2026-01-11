package org.alexdev.http.controller.housekeeping;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.http.Routes;
import org.alexdev.http.dao.housekeeping.HousekeepingPlayerDao;
import org.alexdev.http.game.housekeeping.HousekeepingManager;
import org.alexdev.http.util.SessionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/" + Routes.HOUSEKEEPING_PATH + "/users")
public class HousekeepingUsersController {

    @GetMapping("/imitate/{playerName}")
    public String imitate(
            @PathVariable String playerName,
            HttpSession session) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails adminDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(adminDetails.getRank(), "user/imitate")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails player = PlayerDao.getDetails(playerName);

        if (player == null) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        session.setAttribute("authenticated", true);
        session.setAttribute("captcha.invalid", false);
        session.setAttribute("user.id", player.getId());
        session.setAttribute("clientAuthenticate", false);
        session.setAttribute(SessionUtil.LOGGED_IN_HOUSKEEPING, false);
        session.setAttribute("lastRequest", String.valueOf(DateUtil.getCurrentTimeSeconds() + SessionUtil.REAUTHENTICATE_TIME));

        return "redirect:/me";
    }

    @GetMapping("/search")
    public String searchGet(HttpSession session, Model model) {
        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "user/search")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());
        model.addAttribute("pageName", "Search Users");

        session.removeAttribute("alertMessage");

        return "housekeeping/users_search";
    }

    @PostMapping("/search")
    public String searchPost(
            @RequestParam(value = "searchField", defaultValue = "") String field,
            @RequestParam(value = "searchQuery", defaultValue = "") String input,
            @RequestParam(value = "searchType", defaultValue = "") String type,
            HttpSession session,
            Model model) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "user/search")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());

        if (field.isEmpty() || input.isEmpty() || type.isEmpty()) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "You need to enter all fields");
        } else {
            List<String> whitelistColumns = List.of("username", "id", "credits", "pixels", "mission");
            List<PlayerDetails> players;

            if (whitelistColumns.contains(field)) {
                players = HousekeepingPlayerDao.search(type, field, input);
            } else {
                players = new ArrayList<>();
            }

            model.addAttribute("players", players);
        }

        model.addAttribute("pageName", "Search Users");

        session.removeAttribute("alertMessage");

        return "housekeeping/users_search";
    }

    @GetMapping("/create")
    public String createGet(HttpSession session, Model model) {
        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "user/create")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());
        model.addAttribute("pageName", "Create User");

        session.removeAttribute("alertMessage");

        return "housekeeping/users_create";
    }

    @GetMapping("/edit")
    public String editGet(
            @RequestParam(value = "id", defaultValue = "0") int userId,
            HttpSession session,
            Model model) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails adminDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(adminDetails.getRank(), "user/edit")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());

        if (userId == 0) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "You did not select a user to edit");
        }

        PlayerDetails player = PlayerDao.getDetails(userId);

        if (player == null) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "The user does not exist");
        } else {
            if (adminDetails.getRank().getRankId() <= player.getRank().getRankId()) {
                session.setAttribute("alertColour", "danger");
                session.setAttribute("alertMessage", "You cannot edit someone that has an equal or higher rank than you");
            } else {
                model.addAttribute("playerId", player.getId());
                model.addAttribute("playerUsername", player.getName());
                model.addAttribute("playerEmail", player.getEmail());
                model.addAttribute("playerMotto", player.getMotto());
                model.addAttribute("playerPixels", player.getPixels());
                model.addAttribute("playerCredits", player.getCredits());
                model.addAttribute("playerFigure", player.getFigure());
            }
        }

        model.addAttribute("pageName", "Edit User");

        session.removeAttribute("alertMessage");

        return "housekeeping/users_edit";
    }

    @PostMapping("/edit")
    public String editPost(
            @RequestParam(value = "id", defaultValue = "0") int userId,
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "figure", defaultValue = "") String figure,
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "motto", defaultValue = "") String motto,
            @RequestParam(value = "credits", defaultValue = "") String credits,
            @RequestParam(value = "pixels", defaultValue = "") String pixels,
            HttpSession session,
            Model model) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails adminDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(adminDetails.getRank(), "user/edit")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());

        PlayerDetails player = PlayerDao.getDetails(userId);

        if (player == null) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "The user does not exist");
        } else {
            if (adminDetails.getRank().getRankId() <= player.getRank().getRankId()) {
                session.setAttribute("alertColour", "danger");
                session.setAttribute("alertMessage", "You cannot edit someone that has an equal or higher rank than you");
            } else {
                if (!EmailValidator.getInstance().isValid(email)) {
                    session.setAttribute("alertColour", "warning");
                    session.setAttribute("alertMessage", "The email entered is not valid");
                } else if (!StringUtils.isNumeric(credits)) {
                    session.setAttribute("alertColour", "warning");
                    session.setAttribute("alertMessage", "The value supplied for credits is not a number");
                } else if (!StringUtils.isNumeric(pixels)) {
                    session.setAttribute("alertColour", "warning");
                    session.setAttribute("alertMessage", "The value supplied for pixels is not a number");
                } else {
                    player.setFigure(figure);
                    player.setMotto(motto);
                    player.setPixels(Integer.parseInt(pixels));
                    player.setCredits(Integer.parseInt(credits));
                    player.setEmail(email);

                    PlayerDao.saveDetails(player.getId(), player.getFigure(), player.getPoolFigure(), player.getSex());
                    PlayerDao.saveMotto(player.getId(), player.getMotto());
                    PlayerDao.saveCurrency(player.getId(), player.getCredits(), player.getPixels());
                    PlayerDao.saveEmail(player.getId(), player.getEmail());

                    session.setAttribute("alertColour", "success");
                    session.setAttribute("alertMessage", "The user has been successfully saved");
                }
            }

            model.addAttribute("playerId", player.getId());
            model.addAttribute("playerUsername", player.getName());
            model.addAttribute("playerEmail", player.getEmail());
            model.addAttribute("playerMotto", player.getMotto());
            model.addAttribute("playerPixels", player.getPixels());
            model.addAttribute("playerCredits", player.getCredits());
            model.addAttribute("playerFigure", player.getFigure());
        }

        model.addAttribute("pageName", "Edit User");

        session.removeAttribute("alertMessage");

        return "housekeeping/users_edit";
    }
}
