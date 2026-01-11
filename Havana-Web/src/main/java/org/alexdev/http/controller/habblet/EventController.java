package org.alexdev.http.controller.habblet;

import jakarta.servlet.http.HttpSession;
import org.alexdev.http.server.Watchdog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@Controller
public class EventController {

    @PostMapping("/community/loadEvents")
    public String loadEvents(
            @RequestParam(value = "eventTypeId", defaultValue = "-1") int filterId,
            HttpSession session,
            Model model) {

        if (filterId == -1) {
            return "";
        }

        model.addAttribute("events", Watchdog.EVENTS.stream()
                .filter(event -> event.getCategoryId() == filterId)
                .collect(Collectors.toList()));

        return "habblet/load_events";
    }
}
