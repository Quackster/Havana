package org.alexdev.http.controller.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaqController {

    @GetMapping("/help")
    public String faq() {
        return "faq";
    }

    @GetMapping("/help/faq")
    public String faqAlt() {
        return "faq";
    }
}
