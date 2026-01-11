package org.alexdev.http.controller.housekeeping;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.Routes;
import org.alexdev.http.dao.NewsDao;
import org.alexdev.http.game.housekeeping.HousekeepingManager;
import org.alexdev.http.util.RconUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/" + Routes.HOUSEKEEPING_PATH)
public class HousekeepingCatalogueFrontpageController {

    @GetMapping("/catalogue/frontpage")
    public String editGet(HttpSession session, Model model) {
        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "catalogue/edit_frontpage")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        List<String> images = NewsDao.getTopStoryImages();

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());
        model.addAttribute("pageName", "Edit Catalogue Frontpage");
        model.addAttribute("images", images);
        model.addAttribute("frontpageText1", GameConfiguration.getInstance().getString("catalogue.frontpage.input.1"));
        model.addAttribute("frontpageText2", GameConfiguration.getInstance().getString("catalogue.frontpage.input.2"));
        model.addAttribute("frontpageText3", GameConfiguration.getInstance().getString("catalogue.frontpage.input.3"));
        model.addAttribute("frontpageText4", GameConfiguration.getInstance().getString("catalogue.frontpage.input.4"));

        session.removeAttribute("alertMessage");

        return "housekeeping/catalogue_frontpage";
    }

    @PostMapping("/catalogue/frontpage")
    public String editPost(
            @RequestParam(value = "image", defaultValue = "") String image,
            @RequestParam(value = "header", defaultValue = "") String header,
            @RequestParam(value = "subtext", defaultValue = "") String subtext,
            @RequestParam(value = "link", defaultValue = "") String link,
            HttpSession session,
            Model model) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "catalogue/edit_frontpage")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        if (header.isBlank()) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "Header cannot be blank");
        } else if (subtext.isBlank()) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "The subtext cannot be blank");
        } else {
            session.setAttribute("alertColour", "success");
            session.setAttribute("alertMessage", "The frontpage has been successfully saved");
        }

        GameConfiguration.getInstance().updateSetting("catalogue.frontpage.input.1", image);
        GameConfiguration.getInstance().updateSetting("catalogue.frontpage.input.2", header);
        GameConfiguration.getInstance().updateSetting("catalogue.frontpage.input.3", subtext);
        GameConfiguration.getInstance().updateSetting("catalogue.frontpage.input.4", link);

        RconUtil.sendCommand(RconHeader.REFRESH_CATALOGUE_FRONTPAGE, new HashMap<>());

        List<String> images = NewsDao.getTopStoryImages();

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());
        model.addAttribute("pageName", "Edit Catalogue Frontpage");
        model.addAttribute("images", images);
        model.addAttribute("frontpageText1", GameConfiguration.getInstance().getString("catalogue.frontpage.input.1"));
        model.addAttribute("frontpageText2", GameConfiguration.getInstance().getString("catalogue.frontpage.input.2"));
        model.addAttribute("frontpageText3", GameConfiguration.getInstance().getString("catalogue.frontpage.input.3"));
        model.addAttribute("frontpageText4", GameConfiguration.getInstance().getString("catalogue.frontpage.input.4"));

        session.removeAttribute("alertMessage");

        return "housekeeping/catalogue_frontpage";
    }
}
