package org.alexdev.http.controller.site;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.dao.mysql.PlayerStatisticsDao;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.dao.EmailDao;
import org.alexdev.http.util.EmailUtil;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class RecoveryController {

    @GetMapping("/account/password/forgot")
    public String forgotGet(HttpSession session, Model model) {
        if (!GameConfiguration.getInstance().getBoolean("email.smtp.enable")) {
            return "redirect:/";
        }

        session.setAttribute("page", "recover");
        return "account/email/account_forgot";
    }

    @PostMapping("/account/password/forgot")
    public String forgotPost(
            @RequestParam(value = "actionList", required = false) String actionList,
            @RequestParam(value = "actionForgot", required = false) String actionForgot,
            @RequestParam(value = "ownerEmailAddress", required = false) String ownerEmailAddress,
            @RequestParam(value = "forgottenpw-username", required = false) String forgotUsername,
            @RequestParam(value = "forgottenpw-email", required = false) String forgotEmail,
            HttpServletRequest request,
            HttpSession session,
            Model model) {

        if (!GameConfiguration.getInstance().getBoolean("email.smtp.enable")) {
            return "redirect:/";
        }

        session.setAttribute("page", "recover");

        if (actionList != null) {
            if (ownerEmailAddress == null
                    || !EmailValidator.getInstance().isValid(ownerEmailAddress)
                    || !EmailDao.getDetailsByEmail(ownerEmailAddress)) {
                model.addAttribute("invalidForgetName", true);
                return "account/email/account_forgot";
            }

            return "account/email/sent";
        }

        if (actionForgot != null) {
            if (forgotUsername == null || forgotUsername.isBlank()) {
                model.addAttribute("invalidForgetPassword", true);
                return "account/email/account_forgot";
            }

            if (forgotEmail == null || forgotEmail.isBlank()) {
                model.addAttribute("invalidForgetPassword", true);
                return "account/email/account_forgot";
            }

            var details = EmailDao.getDetails(forgotUsername, forgotEmail);

            if (!EmailValidator.getInstance().isValid(forgotEmail) || details == null) {
                model.addAttribute("invalidForgetPassword", true);
                return "account/email/account_forgot";
            }

            var recoveryCode = UUID.randomUUID().toString();
            var userId = PlayerDao.getId(forgotUsername);

            PlayerStatisticsDao.updateStatistic(userId, PlayerStatistic.FORGOT_PASSWORD_CODE, recoveryCode);
            PlayerStatisticsDao.updateStatistic(userId, PlayerStatistic.FORGOT_RECOVERY_REQUESTED_TIME, DateUtil.getCurrentTimeSeconds());

            if (GameConfiguration.getInstance().getBoolean("email.smtp.enable")) {
                EmailUtil.send(request, forgotEmail, "Password recovery at Classic Habbo",
                        EmailUtil.renderPasswordRecovery(
                                details.getId(),
                                details.getName(),
                                recoveryCode
                        )
                );
            }

            return "account/email/sent";
        }

        return "account/email/account_forgot";
    }

    @GetMapping("/account/password/recovery")
    public String recoveryGet(
            @RequestParam(value = "id", defaultValue = "0") int userId,
            @RequestParam(value = "code", required = false) String recoveryCode,
            HttpSession session,
            Model model) {

        if (!GameConfiguration.getInstance().getBoolean("email.smtp.enable")) {
            return "redirect:/";
        }

        if ((userId == 0 || recoveryCode == null) || !EmailDao.recoveryExists(userId, recoveryCode)) {
            session.setAttribute("alertMessage", "The recovery code was invalid");
            session.setAttribute("alertColour", "red");
        } else {
            model.addAttribute("recoveryCode", recoveryCode);
            model.addAttribute("userId", userId);
        }

        String alertMessage = (String) session.getAttribute("alertMessage");
        String alertColour = (String) session.getAttribute("alertColour");

        if (alertMessage != null) {
            model.addAttribute("alertMessage", alertMessage);
            model.addAttribute("alertColour", alertColour);
            session.removeAttribute("alertMessage");
            session.removeAttribute("alertColour");
        }

        return "account/email/account_recovery";
    }

    @PostMapping("/account/password/recovery")
    public String recoveryPost(
            @RequestParam(value = "user_id", defaultValue = "0") int userId,
            @RequestParam(value = "recovery_code", required = false) String recoveryCode,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "confirmpassword", required = false) String confirmPassword,
            HttpSession session,
            Model model) {

        if (!GameConfiguration.getInstance().getBoolean("email.smtp.enable")) {
            return "redirect:/";
        }

        if ((userId == 0 || recoveryCode == null) || !EmailDao.recoveryExists(userId, recoveryCode)) {
            session.setAttribute("alertMessage", "The recovery code was invalid");
            session.setAttribute("alertColour", "red");
        } else {
            if (password != null && confirmPassword != null) {
                if (!confirmPassword.equals(password)) {
                    session.setAttribute("alertMessage", "The passwords don't match");
                    session.setAttribute("alertColour", "red");
                } else if (password.length() < 6) {
                    session.setAttribute("alertMessage", "Password is too short, 6 characters minimum");
                    session.setAttribute("alertColour", "red");
                } else {
                    session.setAttribute("alertMessage", "Your password has been changed successfully.");
                    session.setAttribute("alertColour", "green");

                    PlayerDao.setPassword(userId, PlayerManager.getInstance().createPassword(password));
                    EmailDao.removeRecoveryCode(userId);
                }
            }

            model.addAttribute("recoveryCode", recoveryCode);
            model.addAttribute("userId", userId);
        }

        String alertMessage = (String) session.getAttribute("alertMessage");
        String alertColour = (String) session.getAttribute("alertColour");

        if (alertMessage != null) {
            model.addAttribute("alertMessage", alertMessage);
            model.addAttribute("alertColour", alertColour);
            session.removeAttribute("alertMessage");
            session.removeAttribute("alertColour");
        }

        return "account/email/account_recovery";
    }

    @GetMapping("/account/activate")
    public String activate(
            @RequestParam(value = "id", defaultValue = "0") int userId,
            @RequestParam(value = "code", required = false) String activationCode,
            Model model) {

        if (!GameConfiguration.getInstance().getBoolean("email.smtp.enable")) {
            return "redirect:/";
        }

        model.addAttribute("verifySuccess", true);

        if (userId == 0 || activationCode == null) {
            model.addAttribute("verifySuccess", false);
        } else if (!EmailDao.exists(userId, activationCode)) {
            model.addAttribute("verifySuccess", false);
        } else {
            EmailDao.activate(userId, activationCode);
        }

        return "account/email/account_activated";
    }
}
