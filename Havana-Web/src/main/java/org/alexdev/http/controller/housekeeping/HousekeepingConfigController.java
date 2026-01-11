package org.alexdev.http.controller.housekeeping;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.SettingsDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.http.Routes;
import org.alexdev.http.game.housekeeping.HousekeepingManager;
import org.alexdev.http.util.ConfigEntry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

@Controller
@RequestMapping("/" + Routes.HOUSEKEEPING_PATH)
public class HousekeepingConfigController {

    @GetMapping("/configurations")
    public String configurationsGet(HttpSession session, Model model) {
        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "configuration")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());

        var settings = new ArrayList<ConfigEntry>();

        for (var setting : SettingsDao.getAllSettings().entrySet()) {
            settings.add(new ConfigEntry(setting.getKey(), setting.getValue()));
        }

        settings.sort(Comparator.comparing(ConfigEntry::getKey));

        model.addAttribute("pageName", "Configurations");
        model.addAttribute("configs", settings);

        session.removeAttribute("alertMessage");

        return "housekeeping/configurations";
    }

    @PostMapping("/configurations")
    public String configurationsPost(
            @RequestParam Map<String, String> allParams,
            HttpSession session,
            Model model) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "configuration")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());

        SettingsDao.updateSettings(allParams.entrySet());

        session.setAttribute("alertColour", "success");
        session.setAttribute("alertMessage", "All configuration values have been saved successfully! It will take effect within 30 seconds.");

        var settings = new ArrayList<ConfigEntry>();

        for (var setting : SettingsDao.getAllSettings().entrySet()) {
            settings.add(new ConfigEntry(setting.getKey(), setting.getValue()));
        }

        settings.sort(Comparator.comparing(ConfigEntry::getKey));

        model.addAttribute("pageName", "Configurations");
        model.addAttribute("configs", settings);

        session.removeAttribute("alertMessage");

        return "housekeeping/configurations";
    }
}
