package org.alexdev.http.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.http.dao.SessionDao;
import org.alexdev.http.interceptor.AuthInterceptor;

import java.util.UUID;

/**
 * Helper class for session management in Spring MVC controllers.
 * Provides type-safe access to session attributes and common operations.
 */
public class SessionHelper {

    /**
     * Check if the user is authenticated
     */
    public static boolean isAuthenticated(HttpSession session) {
        Boolean authenticated = (Boolean) session.getAttribute(AuthInterceptor.LOGGED_IN);
        return authenticated != null && authenticated;
    }

    /**
     * Get the current user ID from session
     */
    public static int getUserId(HttpSession session) {
        Object userId = session.getAttribute(AuthInterceptor.USER_ID);
        if (userId == null) {
            return 0;
        }
        if (userId instanceof Integer) {
            return (Integer) userId;
        }
        try {
            return Integer.parseInt(userId.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Get the current player details from session or load from database
     */
    public static PlayerDetails getPlayer(HttpSession session) {
        PlayerDetails player = (PlayerDetails) session.getAttribute(AuthInterceptor.PLAYER);
        if (player == null) {
            int userId = getUserId(session);
            if (userId > 0) {
                player = PlayerDao.getDetails(userId);
                if (player != null) {
                    session.setAttribute(AuthInterceptor.PLAYER, player);
                }
            }
        }
        return player;
    }

    /**
     * Perform login with username and password
     */
    public static boolean login(HttpServletRequest request, HttpServletResponse response,
                                String username, String password, boolean rememberMe) {
        HttpSession session = request.getSession();
        PlayerDetails details = new PlayerDetails();

        if (username.isBlank() || password.isBlank()) {
            session.setAttribute("alertMessage", "Incorrect username or password\n");
            return false;
        }

        boolean success = PlayerDao.login(details, username, password);

        if (!success) {
            session.setAttribute("alertMessage", "Incorrect username or password\n");
            session.removeAttribute(AuthInterceptor.USER_ID);
            session.removeAttribute(AuthInterceptor.LOGGED_IN);
            return false;
        }

        // Set session attributes
        session.setAttribute(AuthInterceptor.LOGGED_IN, true);
        session.setAttribute("captcha.invalid", false);
        session.setAttribute(AuthInterceptor.USER_ID, details.getId());
        session.setAttribute("clientAuthenticate", false);
        session.setAttribute("lastRequest", String.valueOf(
                DateUtil.getCurrentTimeSeconds() + AuthInterceptor.REAUTHENTICATE_TIME));

        // Handle remember me cookie
        if (rememberMe) {
            String rememberMeToken = UUID.randomUUID().toString();
            Cookie cookie = new Cookie(AuthInterceptor.REMEMBER_TOKEN_NAME, rememberMeToken);
            cookie.setMaxAge(AuthInterceptor.REMEMBER_TOKEN_AGE_SECONDS);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            SessionDao.setRememberToken(details.getId(), rememberMeToken);
        } else {
            // Clear any existing remember cookie
            Cookie cookie = new Cookie(AuthInterceptor.REMEMBER_TOKEN_NAME, "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        // Clear vote stamp cookie
        Cookie voteStamp = new Cookie("vote_stamp", "");
        voteStamp.setMaxAge(0);
        voteStamp.setPath("/");
        response.addCookie(voteStamp);

        // Check if banned
        var banInfo = details.isBanned();
        if (banInfo != null) {
            // User is banned, but we still logged them in - redirect will happen in controller
            return true;
        }

        return true;
    }

    /**
     * Perform logout
     */
    public static void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        // Clear remember token from database
        int userId = getUserId(session);
        if (userId > 0) {
            SessionDao.clearRememberToken(userId);
        }

        // Clear remember cookie
        Cookie cookie = new Cookie(AuthInterceptor.REMEMBER_TOKEN_NAME, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        // Clear session attributes
        session.removeAttribute(AuthInterceptor.USER_ID);
        session.removeAttribute(AuthInterceptor.LOGGED_IN);
        session.removeAttribute(AuthInterceptor.PLAYER);
        session.removeAttribute("minimailLabel");
        session.removeAttribute("lastBrowsedPage");
    }

    /**
     * Get a session attribute as a boolean
     */
    public static boolean getBoolean(HttpSession session, String key) {
        Object value = session.getAttribute(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return false;
    }

    /**
     * Get a session attribute as a string
     */
    public static String getString(HttpSession session, String key) {
        Object value = session.getAttribute(key);
        return value != null ? value.toString() : null;
    }

    /**
     * Get a session attribute as an integer
     */
    public static int getInt(HttpSession session, String key) {
        Object value = session.getAttribute(key);
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value != null) {
            try {
                return Integer.parseInt(value.toString());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    /**
     * Check if client needs re-authentication
     */
    public static boolean needsReauthentication(HttpSession session) {
        return getBoolean(session, "clientAuthenticate");
    }

    /**
     * Get and clear alert message from session
     */
    public static String getAlertMessage(HttpSession session) {
        String message = getString(session, "alertMessage");
        if (message != null) {
            session.removeAttribute("alertMessage");
        }
        return message;
    }

    /**
     * Set an alert message in session
     */
    public static void setAlertMessage(HttpSession session, String message) {
        session.setAttribute("alertMessage", message);
    }
}
