package org.alexdev.http.controller.housekeeping;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.BanDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.http.Routes;
import org.alexdev.http.game.housekeeping.HousekeepingManager;
import org.alexdev.http.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/" + Routes.HOUSEKEEPING_PATH)
public class HousekeepingBansController {

    @GetMapping("/bans")
    public String bans(
            @RequestParam(value = "page", defaultValue = "0") int currentPage,
            @RequestParam(value = "sort", defaultValue = "banned_at") String sortBy,
            HttpSession session,
            Model model) {

        Boolean loggedIn = (Boolean) session.getAttribute(SessionUtil.LOGGED_IN_HOUSKEEPING);
        if (loggedIn == null || !loggedIn) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "bans")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        if (!sortBy.equals("banned_at") && !sortBy.equals("banned_until")) {
            sortBy = "banned_at";
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());
        model.addAttribute("pageName", "Bans");
        model.addAttribute("bans", BanDao.getActiveBans(currentPage, sortBy));
        model.addAttribute("nextBans", BanDao.getActiveBans(currentPage + 1, sortBy));
        model.addAttribute("previousBans", BanDao.getActiveBans(currentPage - 1, sortBy));
        model.addAttribute("page", currentPage);
        model.addAttribute("sortBy", sortBy);

        session.removeAttribute("alertMessage");

        return "housekeeping/users_bans";
    }
}
