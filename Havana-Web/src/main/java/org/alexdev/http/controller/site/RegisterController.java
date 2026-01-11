package org.alexdev.http.controller.site;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.dao.mysql.PlayerStatisticsDao;
import org.alexdev.havana.dao.mysql.ReferredDao;
import org.alexdev.havana.game.misc.figure.FigureManager;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.util.FigureUtil;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.dao.RegisterDao;
import org.alexdev.http.interceptor.AuthInterceptor;
import org.alexdev.http.util.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class RegisterController {

    @GetMapping("/register")
    public String registerGet(
            @RequestParam(value = "referral", required = false) Integer referral,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "errorCode", required = false) String errorCode,
            HttpServletRequest request,
            HttpSession session,
            Model model) {

        if (SessionHelper.isAuthenticated(session)) {
            return "redirect:/me";
        }

        int maxConnectionsPerIp = GameConfiguration.getInstance().getInteger("max.connections.per.ip");
        String ipAddress = getClientIp(request);

        if (PlayerDao.countIpAddress(ipAddress) >= maxConnectionsPerIp) {
            session.setAttribute("alertMessage", "You already have enough accounts registered");
            return "redirect:/";
        }

        String machineId = getCookie(request, "SECURITY_KEY");
        if (machineId != null) {
            if (PlayerDao.countMachineId("#" + machineId) >= maxConnectionsPerIp) {
                session.setAttribute("alertMessage", "You already have enough accounts registered");
                return "redirect:/";
            }
        }

        if (GameConfiguration.getInstance().getBoolean("registration.disabled")) {
            return "register_disabled";
        }

        if (referral != null && referral > 0) {
            session.setAttribute("referral", referral);
        }

        // Handle captcha/email invalid flags
        if (SessionHelper.getBoolean(session, "captcha.invalid") || SessionHelper.getBoolean(session, "email.invalid")) {
            if (SessionHelper.getBoolean(session, "captcha.invalid")) {
                model.addAttribute("registerCaptchaInvalid", true);
            }

            if (SessionHelper.getBoolean(session, "email.invalid")) {
                model.addAttribute("registerEmailInvalid", true);
            }

            model.addAttribute("registerUsername", session.getAttribute("registerUsername"));
            String password = (String) session.getAttribute("registerPassword");
            if (password != null) {
                model.addAttribute("registerShowPassword", password.replaceAll("(?s).", "*"));
            }
            model.addAttribute("registerFigure", session.getAttribute("registerFigure"));
            model.addAttribute("registerGender", session.getAttribute("registerGender"));
            model.addAttribute("registerEmail", session.getAttribute("registerEmail"));
            model.addAttribute("registerDay", session.getAttribute("registerDay"));
            model.addAttribute("registerMonth", session.getAttribute("registerMonth"));
            model.addAttribute("registerYear", session.getAttribute("registerYear"));
        }

        model.addAttribute("randomNum", ThreadLocalRandom.current().nextInt(0, 10000));
        model.addAttribute("randomFemaleFigure1", FigureUtil.getRandomFigure("F", false));
        model.addAttribute("randomFemaleFigure2", FigureUtil.getRandomFigure("F", false));
        model.addAttribute("randomFemaleFigure3", FigureUtil.getRandomFigure("F", false));
        model.addAttribute("randomMaleFigure1", FigureUtil.getRandomFigure("M", false));
        model.addAttribute("randomMaleFigure2", FigureUtil.getRandomFigure("M", false));
        model.addAttribute("randomMaleFigure3", FigureUtil.getRandomFigure("M", false));

        Integer referralId = (Integer) session.getAttribute("referral");
        model.addAttribute("referral", referralId != null ? referralId : 0);

        return "register";
    }

    @PostMapping("/register")
    public String registerPost(
            @RequestParam(value = "bean.avatarName", required = false) String avatarName,
            @RequestParam(value = "bean.email", required = false) String email,
            @RequestParam(value = "bean.captchaResponse", required = false) String captchaResponse,
            @RequestParam(value = "retypedPassword", required = false) String password,
            @RequestParam(value = "bean.day", required = false) String day,
            @RequestParam(value = "bean.month", required = false) String month,
            @RequestParam(value = "bean.year", required = false) String year,
            @RequestParam(value = "bean.figure", required = false) String figure,
            @RequestParam(value = "bean.gender", required = false) String gender,
            @RequestParam(value = "randomFigure", required = false) String randomFigure,
            HttpServletRequest request,
            HttpSession session) {

        if (SessionHelper.isAuthenticated(session)) {
            return "redirect:/me";
        }

        // Check for blank fields
        if (avatarName != null && avatarName.isBlank()) {
            session.setAttribute("captcha.invalid", false);
            return "redirect:/register?errorCode=blank_fields";
        }

        String username = "";
        String emailAddr = "";

        if (avatarName != null) {
            username = HtmlUtil.removeHtmlTags(avatarName);
        } else {
            username = (String) session.getAttribute("registerUsername");
            if (username != null) {
                username = HtmlUtil.removeHtmlTags(username);
            } else {
                username = "";
            }
        }

        if (email != null) {
            emailAddr = HtmlUtil.removeHtmlTags(email);
        } else {
            emailAddr = (String) session.getAttribute("registerEmail");
            if (emailAddr != null) {
                emailAddr = HtmlUtil.removeHtmlTags(emailAddr);
            } else {
                emailAddr = "";
            }
        }

        // Handle figure data
        if (password != null) {
            password = HtmlUtil.removeHtmlTags(password);

            if (randomFigure != null && !randomFigure.isBlank()) {
                String temp = HtmlUtil.removeHtmlTags(randomFigure);
                figure = temp.substring(2);
                gender = temp.substring(0, 1);
            } else if (figure != null && gender != null) {
                figure = HtmlUtil.removeHtmlTags(figure);
                gender = HtmlUtil.removeHtmlTags(gender);
            }

            session.setAttribute("registerUsername", username);
            session.setAttribute("registerPassword", password);
            session.setAttribute("registerShowPassword", password.replaceAll("(?s).", "*"));
            session.setAttribute("registerFigure", figure);
            session.setAttribute("registerGender", gender);
            session.setAttribute("registerEmail", emailAddr);
            session.setAttribute("registerDay", day);
            session.setAttribute("registerMonth", month);
            session.setAttribute("registerYear", year);

            if (!FigureManager.getInstance().validateFigure(figure, gender, false)) {
                return "redirect:/register?error=bad_look";
            }

            if (!RegisterUtil.isValidName(username)) {
                return "redirect:/register?error=bad_username";
            }

            if (!RegisterUtil.isValidEmail(emailAddr)) {
                session.setAttribute("email.invalid", true);
                return "redirect:/register?error=bad_email";
            }
        }

        // Validate captcha
        captchaResponse = captchaResponse != null ? HtmlUtil.removeHtmlTags(captchaResponse) : "";
        String captchaText = (String) session.getAttribute("captcha-text");

        if (!captchaResponse.equals(captchaText)) {
            session.setAttribute("captcha.invalid", true);
            return "redirect:/register?error=bad_captcha";
        }

        // Final email validation
        String registerEmail = (String) session.getAttribute("registerEmail");
        if (!RegisterUtil.isValidEmail(registerEmail)) {
            session.setAttribute("email.invalid", true);
            return "redirect:/register?error=bad_email";
        }

        // Create user
        String hashedPassword = PlayerManager.getInstance().createPassword((String) session.getAttribute("registerPassword"));
        int userId = RegisterDao.newUser(
                (String) session.getAttribute("registerUsername"),
                hashedPassword,
                (String) session.getAttribute("registerFigure"),
                (String) session.getAttribute("registerGender"),
                (String) session.getAttribute("registerEmail"));

        String activationCode = UUID.randomUUID().toString();
        PlayerStatisticsDao.newStatistics(userId, activationCode);

        // Send email if enabled
        if (GameConfiguration.getInstance().getBoolean("email.smtp.enable")) {
            // EmailUtil.send would need to be adapted for Spring
        }

        String ipAddress = getClientIp(request);
        String latestIpAddress = PlayerDao.getLatestIp(userId);

        if (latestIpAddress == null || !latestIpAddress.equals(ipAddress)) {
            PlayerDao.logIpAddress(userId, ipAddress);
        }

        // Handle referral
        Integer referral = (Integer) session.getAttribute("referral");
        if (referral != null && referral > 0) {
            ReferredDao.addReferred(referral, userId);
        }

        // Clear session
        session.removeAttribute("referral");
        session.removeAttribute("captcha.invalid");

        // Log in the user
        session.setAttribute(AuthInterceptor.USER_ID, userId);
        session.setAttribute(AuthInterceptor.LOGGED_IN, true);

        return "redirect:/welcome";
    }

    @GetMapping("/register/cancel")
    public String registerCancelled(HttpSession session) {
        session.removeAttribute("referral");
        session.removeAttribute("captcha.invalid");
        return "redirect:/";
    }

    @GetMapping(value = "/captcha.jpg", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> captcha(HttpSession session) {
        String captchaText = Captcha.generateText(7);
        session.setAttribute("captcha-text", captchaText);

        byte[] imageData = Captcha.generateImage(captchaText);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imageData);
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isBlank()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    private String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
