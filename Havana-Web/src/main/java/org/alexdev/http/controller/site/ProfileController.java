package org.alexdev.http.controller.site;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.dao.mysql.PlayerStatisticsDao;
import org.alexdev.havana.dao.mysql.WardrobeDao;
import org.alexdev.havana.game.misc.figure.FigureManager;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.player.Wardrobe;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;
import org.alexdev.havana.game.player.statistics.PlayerStatisticManager;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.havana.util.StringUtil;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.dao.SessionDao;
import org.alexdev.http.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.alexdev.havana.game.achievements.progressions.AchievementTraderPass.isActivated;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String profile(
            @RequestParam(value = "tab", defaultValue = "0") String tabStr,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);
        var statistics = new PlayerStatisticManager(userId, PlayerStatisticsDao.getStatistics(userId));

        session.setAttribute("page", "me");
        int tab = 0;

        if (StringUtils.isNumeric(tabStr)) {
            tab = Integer.parseInt(tabStr);
        }

        String template;

        switch (tab) {
            case 1 -> {
                template = "profile/change_looks";
                profileFlash(session, model);
            }
            case 2 -> {
                template = "profile/change_preferences";
                preferences(session, model);
            }
            case 3 -> template = "profile/change_email";
            case 4 -> template = "profile/change_password";
            case 5 -> {
                template = "profile/friend_management";
                // FriendManagementController logic would go here
            }
            case 6 -> {
                template = "profile/change_trade_settings";
                tradeSettings(session, model);
            }
            default -> {
                template = "profile/change_looks";
                profileFlash(session, model);
            }
        }

        model.addAttribute("settingsSavedAlert", session.getAttribute("settings.saved.successfully") != null);
        model.addAttribute("accountActivated", isActivated(statistics.getValue(PlayerStatistic.ACTIVATION_CODE)));
        model.addAttribute("randomNumber", ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE));

        session.removeAttribute("settings.saved.successfully");
        session.removeAttribute("alertMessage");
        session.removeAttribute("alertColour");

        return template;
    }

    @PostMapping("/profile/password")
    public String passwordUpdate(
            @RequestParam(value = "currentpassword", defaultValue = "") String currentPassword,
            @RequestParam(value = "newpassword", defaultValue = "") String newPassword,
            @RequestParam(value = "newpasswordconfirm", defaultValue = "") String newPasswordConfirm,
            @RequestParam(value = "captcha", defaultValue = "") String captcha,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);
        boolean logout = false;

        if (currentPassword.isBlank() || newPassword.isBlank() || newPasswordConfirm.isBlank() || captcha.isBlank()) {
            session.setAttribute("alertMessage", "Please enter all fields");
            session.setAttribute("alertColour", "red");
        } else {
            String captchaText = (String) session.getAttribute("captcha-text");

            if (!PlayerDao.login(playerDetails, playerDetails.getName(), currentPassword)) {
                session.setAttribute("alertMessage", "Your current password is invalid");
                session.setAttribute("alertColour", "red");
            } else if (newPassword.length() < 6) {
                session.setAttribute("alertMessage", "Password is too short, 6 characters minimum");
                session.setAttribute("alertColour", "red");
            } else if (!newPassword.equals(newPasswordConfirm)) {
                session.setAttribute("alertMessage", "The passwords don't match");
                session.setAttribute("alertColour", "red");
            } else if (captchaText == null || !captchaText.equals(captcha)) {
                session.setAttribute("alertMessage", "The security code was invalid, please try again.");
                session.setAttribute("alertColour", "red");
            } else {
                session.setAttribute("alertMessage", "Your password has been changed successfully. You will need to login again.");
                session.setAttribute("alertColour", "green");

                PlayerDao.setPassword(playerDetails.getId(), PlayerManager.getInstance().createPassword(newPassword));
                logout = true;
            }
        }

        model.addAttribute("randomNumber", ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE));

        if (logout) {
            SessionHelper.logout(request, response);
        }

        session.removeAttribute("captcha-text");

        return "profile/change_password";
    }

    @PostMapping("/profile/email")
    public String emailUpdate(
            @RequestParam(value = "password", defaultValue = "") String password,
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "captcha", defaultValue = "") String captcha,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = PlayerDao.getDetails(SessionHelper.getUserId(session));
        String captchaText = (String) session.getAttribute("captcha-text");

        if (password.isBlank() || captcha.isBlank()) {
            session.setAttribute("alertMessage", "Please enter all fields");
            session.setAttribute("alertColour", "red");
        } else if (!PlayerDao.login(playerDetails, playerDetails.getName(), password)) {
            session.setAttribute("alertMessage", "Your current password is invalid");
            session.setAttribute("alertColour", "red");
        } else if (!EmailValidator.getInstance().isValid(email)) {
            session.setAttribute("alertMessage", "The email you entered is invalid");
            session.setAttribute("alertColour", "red");
        } else if (captchaText == null || !captchaText.equals(captcha)) {
            session.setAttribute("alertMessage", "The security code was invalid, please try again.");
            session.setAttribute("alertColour", "red");
        } else {
            session.setAttribute("alertMessage", "Your email has been changed successfully.");
            session.setAttribute("alertColour", "green");

            if (!playerDetails.getEmail().equals(email)) {
                var activationCode = UUID.randomUUID().toString();

                if (GameConfiguration.getInstance().getBoolean("email.smtp.enable")) {
                    // Email sending would go here
                    PlayerStatisticsDao.updateStatistic(playerDetails.getId(), PlayerStatistic.ACTIVATION_CODE, activationCode);

                    if (GameConfiguration.getInstance().getBoolean("trade.email.verification")) {
                        if (playerDetails.isTradeEnabled()) {
                            SessionDao.saveTrade(playerDetails.getId(), false);
                            RconUtil.sendCommand(RconHeader.REFRESH_TRADE_SETTING, new HashMap<>() {{
                                put("userId", playerDetails.getId());
                                put("tradeEnabled", "0");
                            }});
                        }
                    }

                    PlayerDao.setEmail(playerDetails.getId(), email);
                }
            }
        }

        session.removeAttribute("captcha-text");
        return "redirect:/profile?tab=3";
    }

    @PostMapping("/profile/character")
    public String characterUpdate(
            @RequestParam(value = "figureData", required = false) String newFigure,
            @RequestParam(value = "newGender", required = false) String newGender,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        if (newFigure == null || newGender == null || newFigure.isEmpty() || newGender.isEmpty()) {
            return "redirect:/profile";
        }

        PlayerDetails playerDetails = PlayerDao.getDetails(SessionHelper.getUserId(session));
        int validateFigureCode = FigureManager.getInstance().validateFigureCode(newFigure, newGender, playerDetails.hasClubSubscription());

        if (validateFigureCode > 0) {
            return "redirect:/profile";
        }

        if (newGender.charAt(0) != 'M' && newGender.charAt(0) != 'F') {
            return "redirect:/profile";
        }

        PlayerDao.saveDetails(playerDetails.getId(), newFigure, playerDetails.getPoolFigure(), newGender);

        if (playerDetails.isOnline()) {
            RconUtil.sendCommand(RconHeader.REFRESH_LOOKS, new HashMap<>() {{
                put("userId", playerDetails.getId());
            }});
        }

        session.setAttribute("settings.saved.successfully", "");
        return "redirect:/profile";
    }

    @PostMapping("/profile/preferences")
    public String profileUpdate(
            @RequestParam(value = "motto", defaultValue = "") String motto,
            @RequestParam(value = "visibility", defaultValue = "") String visibility,
            @RequestParam(value = "showOnlineStatus", defaultValue = "") String showOnlineStatus,
            @RequestParam(value = "wordFilterSetting", defaultValue = "") String wordFilterSetting,
            @RequestParam(value = "allowFriendRequests", defaultValue = "") String allowFriendRequests,
            @RequestParam(value = "followFriendSetting", defaultValue = "") String followFriendSetting,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);

        boolean profileVisibility = visibility.equals("EVERYONE");
        boolean onlineStatusVisibility = showOnlineStatus.equals("true");
        boolean wordFilterEnabled = !wordFilterSetting.equals("false");
        boolean allowFriendRequestsBool = allowFriendRequests.equals("true");
        boolean allowFriendStalking = followFriendSetting.equals("true");

        if (motto.length() > 32) {
            motto = motto.substring(0, 32);
        }

        SessionDao.savePreferences(motto, profileVisibility, onlineStatusVisibility, wordFilterEnabled,
                allowFriendRequestsBool, allowFriendStalking, userId);

        session.setAttribute("settings.saved.successfully", "true");
        return "redirect:/profile?tab=2";
    }

    @PostMapping("/profile/security")
    public String securitySettingUpdate(
            @RequestParam(value = "password", defaultValue = "") String password,
            @RequestParam(value = "tradingsetting", defaultValue = "") String tradingSetting,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = PlayerDao.getDetails(SessionHelper.getUserId(session));

        if (password.isEmpty()) {
            session.setAttribute("alertMessage", "You did not enter a password");
            session.setAttribute("alertColour", "red");
        } else if (!PlayerDao.login(playerDetails, playerDetails.getName(), password)) {
            session.setAttribute("alertMessage", "Your current password is invalid");
            session.setAttribute("alertColour", "red");
        } else if (GameConfiguration.getInstance().getBoolean("trade.email.verification") &&
                !isActivated(PlayerStatisticsDao.getStatisticString(playerDetails.getId(), PlayerStatistic.ACTIVATION_CODE))) {
            session.setAttribute("alertMessage", "You must verify your email before enabling trade.");
            session.setAttribute("alertColour", "red");
        } else if (EmailUtil.isAlreadyTradePass(playerDetails.getId(), playerDetails.getEmail())) {
            session.setAttribute("alertMessage", "This email is already used for a trade pass.");
            session.setAttribute("alertColour", "red");
        } else {
            session.setAttribute("alertMessage", "Security settings updated successfully");
            session.setAttribute("alertColour", "green");

            boolean tradeSettingBool = tradingSetting.equals("true");
            SessionDao.saveTrade(playerDetails.getId(), tradeSettingBool);

            RconUtil.sendCommand(RconHeader.REFRESH_TRADE_SETTING, new HashMap<>() {{
                put("userId", playerDetails.getId());
                put("tradeEnabled", tradeSettingBool ? "1" : "0");
            }});
        }

        return "redirect:/profile?tab=6";
    }

    @PostMapping(value = "/profile/wardrobe/store", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> wardrobeStore(
            @RequestParam(value = "slot", defaultValue = "") String slotStr,
            @RequestParam(value = "figure", defaultValue = "") String figure,
            @RequestParam(value = "gender", defaultValue = "") String sex,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return ResponseEntity.ok("");
        }

        if (!StringUtils.isNumeric(slotStr)) {
            return ResponseEntity.ok("");
        }

        int userId = SessionHelper.getUserId(session);
        int slotId = Integer.parseInt(slotStr);

        figure = StringUtil.filterInput(figure, true);
        sex = StringUtil.filterInput(sex, true);

        if (sex.isBlank()) {
            sex = "M";
        }

        PlayerDetails playerDetails = PlayerDao.getDetails(userId);

        if (!FigureManager.getInstance().validateFigure(figure, sex, playerDetails.hasClubSubscription())) {
            return ResponseEntity.ok("");
        }

        if (slotId < 1 || slotId > 5) {
            return ResponseEntity.ok("");
        }

        List<Wardrobe> wardrobeList = WardrobeDao.getWardrobe(userId);
        Wardrobe wardrobeData = wardrobeList.stream().filter(wardrobe -> wardrobe.getSlotId() == slotId).findFirst().orElse(null);

        if (wardrobeData == null) {
            WardrobeDao.addWardrobe(userId, slotId, figure, sex.toUpperCase());
        } else {
            WardrobeDao.updateWardrobe(userId, slotId, figure, sex.toUpperCase());
        }

        String json = "{\"slot\":\"" + slotId + "\",\"u\":\"" + HtmlUtil.createFigureLink(figure, sex) + "\",\"f\":\"" + figure + "\",\"g\":77}";
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(json);
    }

    @GetMapping("/profile/verify")
    public String verify(HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);
        var statistics = new PlayerStatisticManager(userId, PlayerStatisticsDao.getStatistics(userId));

        session.setAttribute("page", "me");
        model.addAttribute("accountActivated", isActivated(statistics.getValue(PlayerStatistic.ACTIVATION_CODE)));

        return "profile/verify_email";
    }

    @PostMapping("/profile/verify/send")
    public String sendEmail(HttpSession session) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);
        var playerDetails = PlayerDao.getDetails(userId);
        var statistics = new PlayerStatisticManager(userId, PlayerStatisticsDao.getStatistics(userId));

        if (isActivated(statistics.getValue(PlayerStatistic.ACTIVATION_CODE))) {
            session.setAttribute("alertMessage", "Your email is already activated");
            session.setAttribute("alertColour", "red");
            return "redirect:/profile/verify";
        }

        statistics.setValue(PlayerStatistic.ACTIVATION_CODE, UUID.randomUUID().toString());

        if (GameConfiguration.getInstance().getBoolean("email.smtp.enable")) {
            session.setAttribute("alertMessage", "A verification email has been sent to your email address");
            session.setAttribute("alertColour", "green");
            // Email sending would go here
        }

        return "redirect:/profile/verify";
    }

    private void profileFlash(HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return;
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);
        changeLooks(playerDetails, model);
    }

    private void changeLooks(PlayerDetails playerDetails, Model model) {
        if (!playerDetails.hasClubSubscription()) {
            return;
        }

        List<Wardrobe> wardrobeList = WardrobeDao.getWardrobe(playerDetails.getId());

        for (int i = 1; i <= 5; i++) {
            int slotId = i;
            Wardrobe wardrobeSlot = wardrobeList.stream().filter(w -> w.getSlotId() == slotId).findFirst().orElse(null);

            model.addAttribute("wardrobe" + i, wardrobeSlot != null);
            if (wardrobeSlot != null) {
                model.addAttribute("wardrobeUrl" + i, HtmlUtil.createFigureLink(wardrobeSlot.getFigure(), wardrobeSlot.getSex()));
                model.addAttribute("wardrobeFigure" + i, wardrobeSlot.getFigure());
                model.addAttribute("wardrobeSex" + i, wardrobeSlot.getSex());
            }
        }

        if (!playerDetails.hasClubSubscription()) {
            int validateFigureCode = FigureManager.getInstance().validateFigureCode(playerDetails.getFigure(), playerDetails.getSex(), false);
            model.addAttribute("figureHasClub", validateFigureCode == 6);
        } else {
            model.addAttribute("figureHasClub", false);
        }
    }

    private void preferences(HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return;
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        model.addAttribute("onlineStatusEnabled", playerDetails.isOnlineStatusVisible() ? "checked=\"checked\"" : "");
        model.addAttribute("onlineStatusDisabled", !playerDetails.isOnlineStatusVisible() ? "checked=\"checked\"" : "");
        model.addAttribute("followFriendEnabled", playerDetails.doesAllowStalking() ? "checked=\"checked\"" : "");
        model.addAttribute("followFriendDisabled", !playerDetails.doesAllowStalking() ? "checked=\"checked\"" : "");
        model.addAttribute("profileVisibleEnabled", playerDetails.isProfileVisible() ? "checked=\"checked\"" : "");
        model.addAttribute("profileVisibleDisabled", !playerDetails.isProfileVisible() ? "checked=\"checked\"" : "");
        model.addAttribute("allowFriendRequests", playerDetails.isAllowFriendRequests() ? "checked=\"true\"" : "");
        model.addAttribute("wordFilterSetting", !playerDetails.isWordFilterEnabled() ? "checked=\"true\"" : "");
    }

    private void tradeSettings(HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return;
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);
        boolean canUseTrade = !GameConfiguration.getInstance().getBoolean("trade.email.verification") ||
                isActivated(PlayerStatisticsDao.getStatisticString(playerDetails.getId(), PlayerStatistic.ACTIVATION_CODE));
        model.addAttribute("canUseTrade", canUseTrade);
    }
}
