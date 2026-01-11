package org.alexdev.http.controller.habblet;

import jakarta.servlet.http.HttpServletResponse;
import org.alexdev.http.util.RegisterUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NameCheckController {

    @PostMapping("/register/name_check")
    @ResponseBody
    public String nameCheck(
            @RequestParam(value = "name", defaultValue = "") String username,
            HttpServletResponse response) {

        String errorMessage = "";
        int errorCode = RegisterUtil.getNameErrorCode(username);

        if (errorCode == 6) {
            errorMessage = "This name is unacceptable to hotel management.";
        } else if (errorCode == 5) {
            errorMessage = "Your username is invalid or contains invalid characters.";
        } else if (errorCode == 4) {
            errorMessage = "This name is not allowed.";
        } else if (errorCode == 3) {
            errorMessage = "The name you have chosen is too long.";
        } else if (errorCode == 2) {
            errorMessage = "Please enter a username.";
        } else if (errorCode == 1) {
            errorMessage = "A user with this name already exists.";
        }

        response.setHeader("X-JSON", "{\"registration_name\":\"" + errorMessage + "\"}");
        return "";
    }

    public static boolean hasAllowedCharacters(String str, String allowedChars) {
        if (str == null) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            if (allowedChars.contains(Character.valueOf(str.toCharArray()[i]).toString())) {
                continue;
            }

            return false;
        }

        return true;
    }
}
