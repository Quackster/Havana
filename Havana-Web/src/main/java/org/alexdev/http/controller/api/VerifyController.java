package org.alexdev.http.controller.api;

import org.alexdev.http.dao.VerifyDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VerifyController {

    @GetMapping("/api/verify/{token}")
    @ResponseBody
    public String getVerification(@PathVariable String token) {
        if (token == null || token.isBlank()) {
            return "error: INVALID";
        }

        var username = VerifyDao.getName(token);

        if (username == null) {
            return "error: INVALID";
        }

        return username;
    }

    @GetMapping("/api/verify/clear/{token}")
    @ResponseBody
    public String clearVerification(@PathVariable String token) {
        if (token == null || token.isBlank()) {
            return "error: INVALID";
        }

        try {
            VerifyDao.clearName(token);
            return "SUCCESS";
        } catch (Exception ex) {
            return "error: INVALID";
        }
    }
}
