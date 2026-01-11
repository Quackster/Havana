package org.alexdev.http.controller.housekeeping;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.http.Routes;
import org.alexdev.http.dao.HousekeepingDao;
import org.alexdev.http.dao.housekeeping.HousekeepingPlayerDao;
import org.alexdev.http.game.housekeeping.HousekeepingManager;
import org.alexdev.http.game.housekeeping.HousekeepingStats;
import org.alexdev.http.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/" + Routes.HOUSEKEEPING_PATH)
public class HousekeepingController {

    @GetMapping
    public String dashboard(
            @RequestParam(value = "page", defaultValue = "0") int currentPage,
            @RequestParam(value = "zerocoins", required = false) String zeroCoinsParam,
            @RequestParam(value = "sort", defaultValue = "created_at") String sortBy,
            HttpSession session,
            Model model) {

        Boolean loggedIn = (Boolean) session.getAttribute(SessionUtil.LOGGED_IN_HOUSKEEPING);
        if (loggedIn == null || !loggedIn) {
            return "housekeeping/login";
        }

        boolean zeroCoinsFlag = zeroCoinsParam != null;

        if (!sortBy.equals("last_online") && !sortBy.equals("created_at")) {
            sortBy = "created_at";
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());
        model.addAttribute("pageName", "Dashboard");
        model.addAttribute("players", HousekeepingPlayerDao.getPlayers(currentPage, zeroCoinsFlag, sortBy));
        model.addAttribute("nextPlayers", HousekeepingPlayerDao.getPlayers(currentPage + 1, zeroCoinsFlag, sortBy));
        model.addAttribute("previousPlayers", HousekeepingPlayerDao.getPlayers(currentPage - 1, zeroCoinsFlag, sortBy));
        model.addAttribute("page", currentPage);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("stats", new HousekeepingStats(
                HousekeepingDao.getUserCount(),
                HousekeepingDao.getInventoryItemsCount(),
                HousekeepingDao.getRoomItemCount(),
                HousekeepingDao.getGroupCount(),
                HousekeepingDao.getPetCount(),
                HousekeepingDao.getPhotoCount()));
        model.addAttribute("zeroCoinsFlag", zeroCoinsFlag);

        session.removeAttribute("alertMessage");

        return "housekeeping/dashboard";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam(value = "hkusername", defaultValue = "") String username,
            @RequestParam(value = "hkpassword", defaultValue = "") String password,
            HttpSession session) {

        if (username.isEmpty() || password.isEmpty()) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "You need to enter both your username and password");
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = new PlayerDetails();

        if (!PlayerDao.login(playerDetails, username, password)) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "You have entered invalid details");
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "root/login")) {
            session.setAttribute("alertColour", "warning");
            session.setAttribute("alertMessage", "You don't have permission");
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        session.setAttribute(SessionUtil.LOGGED_IN_HOUSKEEPING, true);
        session.setAttribute(SessionUtil.USER_ID, String.valueOf(playerDetails.getId()));

        return "redirect:/" + Routes.HOUSEKEEPING_PATH;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        Boolean loggedIn = (Boolean) session.getAttribute(SessionUtil.LOGGED_IN_HOUSKEEPING);
        if (loggedIn != null && loggedIn) {
            session.setAttribute("alertColour", "success");
            session.setAttribute("alertMessage", "Successfully logged out!");
            session.setAttribute(SessionUtil.LOGGED_IN_HOUSKEEPING, false);
        }

        return "redirect:/" + Routes.HOUSEKEEPING_PATH;
    }
}
