package org.alexdev.http.controller.site;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.scheduler.WatchdogScheduler;
import org.alexdev.http.util.HomeUtil;
import org.alexdev.http.util.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomepageController {

    @GetMapping({"/", "/index", "/home"})
    public String homepage(
            @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberMe,
            @RequestParam(value = "username", defaultValue = "") String username,
            HttpSession session,
            Model model) {

        // If already authenticated, redirect to /me
        if (SessionHelper.isAuthenticated(session)) {
            return "redirect:/me";
        }

        model.addAttribute("rememberMe", rememberMe);
        model.addAttribute("username", username);
        model.addAttribute("tagCloud", WatchdogScheduler.TAG_CLOUD_20);

        // Valentine's month check
        boolean isValentinesMonth = Integer.parseInt(DateUtil.getCurrentDate("M")) == 2
                && Integer.parseInt(DateUtil.getCurrentDate("DD")) <= 16;
        model.addAttribute("isValentinesMonth", isValentinesMonth);
        model.addAttribute("randomValentinesImage", HomeUtil.getRandomValentinesImage());

        // Get homepage template from config
        String templateFile = GameConfiguration.getInstance().getString("homepage.template.file");
        return templateFile;
    }

    @GetMapping("/maintenance")
    public String maintenance() {
        return "maintenance";
    }
}
