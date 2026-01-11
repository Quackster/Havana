package org.alexdev.http.controller.habblet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NavigationController {

    @PostMapping("/esi/component/navigation")
    @ResponseBody
    public String navigation() {
        return "";
    }
}
