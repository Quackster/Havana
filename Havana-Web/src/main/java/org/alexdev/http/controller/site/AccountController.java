package org.alexdev.http.controller.site;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.*;
import org.alexdev.havana.game.alerts.AlertType;
import org.alexdev.havana.game.club.ClubSubscription;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;
import org.alexdev.havana.game.player.statistics.PlayerStatisticManager;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.dao.GroupDiscussionDao;
import org.alexdev.http.game.account.BeginnerGiftManager;
import org.alexdev.http.game.news.NewsArticle;
import org.alexdev.http.interceptor.AuthInterceptor;
import org.alexdev.http.scheduler.WatchdogScheduler;
import org.alexdev.http.util.HtmlUtil;
import org.alexdev.http.util.SessionHelper;
import org.alexdev.http.util.TagUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
public class AccountController {

    @PostMapping("/account/submit")
    public String submit(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password,
            @RequestParam(value = "_login_remember_me", defaultValue = "false") String rememberMe,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            Model model) {

        username = HtmlUtil.removeHtmlTags(username);
        password = HtmlUtil.removeHtmlTags(password);
        boolean rememberMeFlag = "true".equals(rememberMe);

        if (SessionHelper.login(request, response, username, password, rememberMeFlag)) {
            return "redirect:/security_check";
        } else {
            model.addAttribute("rememberMe", rememberMeFlag ? "true" : "false");
            model.addAttribute("username", username);
            return "account/submit";
        }
    }

    @GetMapping("/security_check")
    public String securityCheck(HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        String redirectPath = (String) session.getAttribute("lastBrowsedPage");
        model.addAttribute("redirectPath", redirectPath != null ? redirectPath : "/me");
        return "security_check";
    }

    @GetMapping("/me")
    public String me(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        if (playerDetails == null) {
            session.removeAttribute(AuthInterceptor.USER_ID);
            session.removeAttribute(AuthInterceptor.LOGGED_IN);
            return "redirect:/";
        }

        var pair = playerDetails.isBanned();
        if (pair != null) {
            return "redirect:/account/banned";
        }

        session.setAttribute("page", "me");
        session.removeAttribute("captcha.invalid");

        // HC days
        if (playerDetails.hasClubSubscription()) {
            model.addAttribute("hcDays", TimeUnit.SECONDS.toDays(playerDetails.getClubExpiration() - DateUtil.getCurrentTimeSeconds()));
        }

        // News articles
        NewsArticle[] articles = new NewsArticle[5];
        boolean includeUnpublished = playerDetails.getRank().getRankId() > 1;
        List<NewsArticle> articleList = includeUnpublished ? WatchdogScheduler.NEWS_STAFF : WatchdogScheduler.NEWS;

        if (articleList == null) {
            articleList = List.of();
        }

        int i = 0;
        for (var article : articleList) {
            if (i < 5) articles[i++] = article;
        }

        for (i = 0; i < 5; i++) {
            if (articles[i] == null) {
                articles[i] = new NewsArticle(0, "No news", 0, "", "", "", DateUtil.getCurrentTimeSeconds(), "attention_topstory.png", "", "", "0", true, 0, false);
            }
            model.addAttribute("article" + (i + 1), articles[i]);
        }

        // Statistics
        var alerts = AlertsDao.getAlerts(playerDetails.getId());
        var statisticsValues = PlayerStatisticsDao.getStatistics(playerDetails.getId());

        if (statisticsValues.isEmpty()) {
            PlayerStatisticsDao.newStatistics(playerDetails.getId(), UUID.randomUUID().toString());
            statisticsValues = PlayerStatisticsDao.getStatistics(playerDetails.getId());
        }

        var statistics = new PlayerStatisticManager(playerDetails.getId(), statisticsValues);

        model.addAttribute("newbieRoomLayout", statistics.getIntValue(PlayerStatistic.NEWBIE_ROOM_LAYOUT));
        model.addAttribute("newbieNextGift", statistics.getIntValue(PlayerStatistic.NEWBIE_GIFT));

        if (statistics.getIntValue(PlayerStatistic.NEWBIE_ROOM_LAYOUT) > 0 && statistics.getIntValue(PlayerStatistic.NEWBIE_GIFT) > 0) {
            int seconds = statistics.getIntValue(PlayerStatistic.NEWBIE_GIFT_TIME) - DateUtil.getCurrentTimeSeconds();

            if (BeginnerGiftManager.progress(playerDetails, statistics)) {
                seconds = statistics.getIntValue(PlayerStatistic.NEWBIE_GIFT_TIME) - DateUtil.getCurrentTimeSeconds();
            }

            if (seconds < 0) {
                seconds = 0;
            }

            model.addAttribute("newbieNextGift", statistics.getIntValue(PlayerStatistic.NEWBIE_GIFT));
            model.addAttribute("newbieGiftSeconds", seconds);
        }

        // Club subscription alerts
        if (playerDetails.hasClubSubscription()) {
            if (alerts.stream().anyMatch(alert -> alert.getAlertType() == AlertType.HC_EXPIRED)) {
                AlertsDao.deleteAlerts(playerDetails.getId(), AlertType.HC_EXPIRED);
            }
        } else if (alerts.stream().noneMatch(alert -> alert.getAlertType() == AlertType.HC_EXPIRED)) {
            if (playerDetails.getFirstClubSubscription() > 0) {
                AlertsDao.createAlert(playerDetails.getId(), AlertType.HC_EXPIRED, "");
            }
        }

        if (playerDetails.getSelectedRoomId() == -1 && statistics.getIntValue(PlayerStatistic.NEWBIE_ROOM_LAYOUT) != -1) {
            statistics.setLongValue(PlayerStatistic.NEWBIE_ROOM_LAYOUT, -1);
        }

        // Birthday check
        if (playerDetails.formatJoinDate("MM/dd").equalsIgnoreCase(DateUtil.getDate(DateUtil.getCurrentTimeSeconds(), "MM/dd")) &&
                !(playerDetails.formatJoinDate("MM/dd/yyyy").equalsIgnoreCase(DateUtil.getDate(DateUtil.getCurrentTimeSeconds(), "MM/dd/yyy")))) {
            LocalDateTime birthday = DateUtil.getDateTimeFromTimestamp(playerDetails.getJoinDate());
            LocalDateTime now = DateUtil.getDateTimeFromTimestamp(DateUtil.getCurrentTimeSeconds());

            Period period = Period.between(birthday.toLocalDate(), now.toLocalDate());

            model.addAttribute("hasBirthday", true);
            model.addAttribute("birthdayAge", period.getYears());

            String prefix = "th";
            String yearStr = String.valueOf(period.getYears());
            if (yearStr.endsWith("1") && !yearStr.endsWith("11")) prefix = "st";
            else if (yearStr.endsWith("2") && !yearStr.endsWith("12")) prefix = "nd";
            else if (yearStr.endsWith("3") && !yearStr.endsWith("13")) prefix = "rd";
            model.addAttribute("birthdayPrefix", prefix);
        } else {
            model.addAttribute("hasBirthday", false);
        }

        model.addAttribute("tags", TagDao.getUserTags(playerDetails.getId()));
        model.addAttribute("lastOnline", DateUtil.getFriendlyDate(playerDetails.getLastOnline()));
        model.addAttribute("tagRandomQuestion", TagUtil.getRandomQuestion());
        model.addAttribute("events", WatchdogScheduler.EVENTS);
        model.addAttribute("groups", GroupDao.getJoinedGroups(SessionHelper.getUserId(session)));
        model.addAttribute("alerts", AlertsDao.getAlerts(playerDetails.getId()).stream()
                .filter(alert -> !alert.isDisabled()).collect(Collectors.toList()));
        model.addAttribute("recommendedGroups", WatchdogScheduler.RECOMMENDED_GROUPS);
        model.addAttribute("staffPickGroups", WatchdogScheduler.STAFF_PICK_GROUPS);

        // Pending members
        var pendingDetails = GroupMemberDao.getPendingMembers(playerDetails.getId());
        model.addAttribute("pendingMembers", pendingDetails.getKey());
        model.addAttribute("pendingGroups", pendingDetails.getValue());

        // New group posts
        var newGroupPosts = GroupDiscussionDao.getNewGroupMessages(playerDetails.getId(), playerDetails.getLastOnline());
        model.addAttribute("newPostsAmount", newGroupPosts.getKey());
        model.addAttribute("newPosts", newGroupPosts.getValue());
        model.addAttribute("unreadGuestbookMessages", statistics.getIntValue(PlayerStatistic.GUESTBOOK_UNREAD_MESSAGES));

        ClubSubscription.countMemberDays(playerDetails, statistics);

        // Log IP
        String ipAddress = getClientIp(request);
        String latestIpAddress = PlayerDao.getLatestIp(playerDetails.getId());

        if (latestIpAddress == null || !latestIpAddress.equals(ipAddress)) {
            PlayerDao.logIpAddress(playerDetails.getId(), ipAddress);
        }

        // Machine ID cookie
        String machineId = getCookie(request, AuthInterceptor.REMEMBER_TOKEN_NAME);
        if (machineId == null || !machineId.equals(playerDetails.getMachineId())) {
            if (!playerDetails.getMachineId().isBlank()) {
                Cookie cookie = new Cookie("SECURITY_KEY", playerDetails.getMachineId().replace("#", ""));
                cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(2));
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }

        return "me";
    }

    @GetMapping("/welcome")
    public String welcome(HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        var pair = playerDetails.isBanned();
        if (pair != null) {
            return "redirect:/account/banned";
        }

        if (!playerDetails.canSelectRoom()) {
            return "redirect:/me";
        }

        session.setAttribute("page", "welcome");
        return "welcome";
    }

    @GetMapping("/account/reauthenticate")
    public String reauthenticateGet(HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        session.setAttribute("page", "reauthenticate");
        return "account/reauthenticate";
    }

    @PostMapping("/account/reauthenticate")
    public String reauthenticatePost(
            @RequestParam(value = "password", defaultValue = "") String password,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = PlayerDao.getDetails(SessionHelper.getUserId(session));
        String username = playerDetails.getName();

        if (SessionHelper.login(request, response, username, password, false)) {
            String clientRequest = (String) session.getAttribute("clientRequest");
            return "redirect:" + (clientRequest != null ? clientRequest : "/me");
        }

        session.setAttribute("page", "reauthenticate");
        return "account/reauthenticate";
    }

    @GetMapping("/login_popup")
    public String loginPopup(HttpSession session) {
        session.setAttribute("page", "login_popup");
        return "account/login";
    }

    @GetMapping("/account/banned")
    public String banned(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        session.removeAttribute("lastBrowsedPage");

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);
        var pair = playerDetails.isBanned();

        if (pair == null) {
            return "redirect:/me";
        }

        session.setAttribute("page", "banned");

        String bannedMessage = String.format(
                "You have been banned from %s. The reason for the ban is \"%s\". The ban will expire at %s.",
                GameConfiguration.getInstance().getString("site.name"),
                pair.getKey(),
                DateUtil.getDate(pair.getValue(), DateUtil.LONG_DATE));

        model.addAttribute("bannedMsg", bannedMessage);

        // Set machine ID cookie
        if (!playerDetails.getMachineId().isBlank()) {
            Cookie cookie = new Cookie("SECURITY_KEY", playerDetails.getMachineId().replace("#", ""));
            cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(2));
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        // Logout
        SessionHelper.logout(request, response);

        return "account/banned";
    }

    @GetMapping("/account/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        SessionHelper.logout(request, response);
        session.setAttribute("page", "logout");

        return "account/logout";
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
