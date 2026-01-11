package org.alexdev.http.controller.site;

import jakarta.servlet.http.HttpSession;
import org.alexdev.http.util.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SiteController {

    @GetMapping("/pixels")
    public String pixels(HttpSession session) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        session.setAttribute("page", "credits");
        return "pixels";
    }
}
