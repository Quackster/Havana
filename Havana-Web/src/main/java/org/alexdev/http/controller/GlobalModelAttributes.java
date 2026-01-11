package org.alexdev.http.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.interceptor.AuthInterceptor;
import org.alexdev.http.scheduler.WatchdogScheduler;
import org.alexdev.http.util.Captcha;
import org.alexdev.http.util.SessionHelper;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Provides common model attributes for all templates.
 * Replaces the SiteBinder, SessionBinder, and AlertBinder from duckHTTPD.
 */
@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute("site")
    public SiteInfo siteInfo() {
        return new SiteInfo();
    }

    @ModelAttribute("gameConfig")
    public GameConfiguration gameConfig() {
        return GameConfiguration.getInstance();
    }

    @ModelAttribute("session")
    public SessionInfo sessionInfo(HttpSession session) {
        return new SessionInfo(session);
    }

    @ModelAttribute("alert")
    public AlertInfo alertInfo(HttpSession session) {
        String message = (String) session.getAttribute("alertMessage");
        String colour = (String) session.getAttribute("alertColour");
        // Clear after reading
        session.removeAttribute("alertMessage");
        session.removeAttribute("alertColour");
        return new AlertInfo(message, colour);
    }

    @ModelAttribute("playerDetails")
    public PlayerDetails playerDetails(HttpSession session) {
        if (SessionHelper.isAuthenticated(session)) {
            int userId = SessionHelper.getUserId(session);
            if (userId > 0) {
                return PlayerDao.getDetails(userId);
            }
        }
        return null;
    }

    /**
     * Site configuration object for templates
     */
    public static class SiteInfo {
        private final String siteName;
        private final String sitePath;
        private final String staticContentPath;
        private final String loaderGameIp;
        private final String loaderGamePort;
        private final String loaderMusIp;
        private final String loaderMusPort;
        private final String loaderDcr;
        private final String loaderVariables;
        private final String loaderTexts;
        private final String loaderFlashBase;
        private final String loaderFlashSwf;
        private final String loaderFlashTexts;
        private final String loaderFlashVariables;
        private final String loaderFlashBetaBase;
        private final String loaderFlashBetaSwf;
        private final String loaderFlashBetaTexts;
        private final String loaderFlashBetaVariables;
        private final String emailStaticPath;
        private final String emailHotelName;
        private final String furniImagerPath;
        private final String housekeepingPath;
        private final Captcha captcha;

        public SiteInfo() {
            GameConfiguration config = GameConfiguration.getInstance();
            this.siteName = config.getString("site.name");
            this.sitePath = config.getString("site.path");
            this.staticContentPath = config.getString("static.content.path");
            this.loaderGameIp = config.getString("loader.game.ip");
            this.loaderGamePort = config.getString("loader.game.port");
            this.loaderMusIp = config.getString("loader.mus.ip");
            this.loaderMusPort = config.getString("loader.mus.port");
            this.loaderDcr = config.getString("loader.dcr");
            this.loaderVariables = config.getString("loader.external.variables");
            this.loaderTexts = config.getString("loader.external.texts");
            this.loaderFlashBase = config.getString("loader.flash.base");
            this.loaderFlashSwf = config.getString("loader.flash.swf");
            this.loaderFlashTexts = config.getString("loader.flash.external.texts");
            this.loaderFlashVariables = config.getString("loader.flash.external.variables");
            this.loaderFlashBetaBase = config.getString("loader.flash.beta.base");
            this.loaderFlashBetaSwf = config.getString("loader.flash.beta.swf");
            this.loaderFlashBetaTexts = config.getString("loader.flash.beta.external.texts");
            this.loaderFlashBetaVariables = config.getString("loader.flash.beta.external.variables");
            this.emailStaticPath = config.getString("email.static.content.path");
            this.emailHotelName = config.getString("site.path")
                    .replace("https://", "").replace("http://", "").replace("/", "").toUpperCase();
            this.furniImagerPath = "https://classichabbo.com/imager/furni";
            this.housekeepingPath = AuthInterceptor.HOUSEKEEPING_PATH;
            this.captcha = new Captcha();
        }

        public String getSiteName() { return siteName; }
        public String getSitePath() { return sitePath; }
        public String getStaticContentPath() { return staticContentPath; }
        public String getLoaderGameIp() { return loaderGameIp; }
        public String getLoaderGamePort() { return loaderGamePort; }
        public String getLoaderMusIp() { return loaderMusIp; }
        public String getLoaderMusPort() { return loaderMusPort; }
        public String getLoaderDcr() { return loaderDcr; }
        public String getLoaderVariables() { return loaderVariables; }
        public String getLoaderTexts() { return loaderTexts; }
        public String getLoaderFlashBase() { return loaderFlashBase; }
        public String getLoaderFlashSwf() { return loaderFlashSwf; }
        public String getLoaderFlashTexts() { return loaderFlashTexts; }
        public String getLoaderFlashVariables() { return loaderFlashVariables; }
        public String getLoaderFlashBetaBase() { return loaderFlashBetaBase; }
        public String getLoaderFlashBetaSwf() { return loaderFlashBetaSwf; }
        public String getLoaderFlashBetaTexts() { return loaderFlashBetaTexts; }
        public String getLoaderFlashBetaVariables() { return loaderFlashBetaVariables; }
        public String getEmailStaticPath() { return emailStaticPath; }
        public String getEmailHotelName() { return emailHotelName; }
        public String getFurniImagerPath() { return furniImagerPath; }
        public String getHousekeepingPath() { return housekeepingPath; }
        public Captcha getCaptcha() { return captcha; }

        public boolean isServerOnline() { return WatchdogScheduler.IS_SERVER_ONLINE; }
        public int getUsersOnline() { return WatchdogScheduler.USERS_ONLINE; }
        public String getFormattedUsersOnline() {
            return NumberFormat.getNumberInstance(Locale.US).format(WatchdogScheduler.USERS_ONLINE);
        }
        public int getVisits() { return WatchdogScheduler.LAST_VISITS; }
    }

    /**
     * Session info object for templates
     */
    public static class SessionInfo {
        private final boolean loggedIn;
        private final String currentPage;

        public SessionInfo(HttpSession session) {
            Boolean authenticated = (Boolean) session.getAttribute(AuthInterceptor.LOGGED_IN);
            this.loggedIn = authenticated != null && authenticated;
            this.currentPage = (String) session.getAttribute("page");
        }

        public boolean isLoggedIn() { return loggedIn; }
        public String getCurrentPage() { return currentPage; }
    }

    /**
     * Alert info object for templates
     */
    public static class AlertInfo {
        private final String message;
        private final String colour;

        public AlertInfo(String message, String colour) {
            this.message = message;
            this.colour = colour;
        }

        public String getMessage() { return message; }
        public String getColour() { return colour; }
        public boolean hasMessage() { return message != null && !message.isBlank(); }
    }
}
