package org.alexdev.http.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.dao.SessionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.TimeUnit;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    public static final String HOUSEKEEPING_PATH = "allseeingeye/hk";
    public static final String PLAYER = "player";
    public static final String USER_ID = "user.id";
    public static final String LOGGED_IN = "authenticated";
    public static final String LOGGED_IN_HOUSEKEEPING = "authenticatedHousekeeping";

    public static final String REMEMBER_TOKEN_NAME = "remember_token";
    public static final int REMEMBER_TOKEN_AGE_SECONDS = (int) TimeUnit.DAYS.toSeconds(31);
    public static final int REAUTHENTICATE_TIME = (int) TimeUnit.MINUTES.toSeconds(30);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        HttpSession session = request.getSession();

        // Maintenance mode check
        if (GameConfiguration.getInstance().getBoolean("maintenance") && !requestUri.startsWith("/api")) {
            if (!requestUri.startsWith("/maintenance") && !requestUri.startsWith("/" + HOUSEKEEPING_PATH)) {
                response.sendRedirect("/maintenance");
                return false;
            }
        }

        // HTTPS redirect for proxy
        if (!requestUri.startsWith("/api")) {
            String forwardedProto = request.getHeader("X-Forwarded-Proto");
            String host = request.getHeader("Host");

            if (forwardedProto != null && forwardedProto.equalsIgnoreCase("http") && host != null) {
                String targetUrl = "https://" + host;
                String uri = request.getRequestURI();

                if (!uri.startsWith("/")) {
                    targetUrl += "/";
                }
                targetUrl += uri;

                if (request.getQueryString() != null) {
                    targetUrl += "?" + request.getQueryString();
                }

                response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
                response.setHeader("Location", targetUrl);
                return false;
            }
        }

        // Authentication checks
        Boolean authenticated = (Boolean) session.getAttribute(LOGGED_IN);

        if (authenticated != null && authenticated) {
            handleAuthenticatedRequest(request, session);
        } else {
            checkRememberMeCookie(request, response, session);
        }

        return true;
    }

    private void handleAuthenticatedRequest(HttpServletRequest request, HttpSession session) {
        String requestUri = request.getRequestURI();

        // Update last request time when accessing client
        if (requestUri.equals("/client")) {
            session.setAttribute("lastRequest", String.valueOf(DateUtil.getCurrentTimeSeconds() + REAUTHENTICATE_TIME));
        }

        // Check for re-authentication requirement
        Object lastRequestObj = session.getAttribute("lastRequest");
        if (lastRequestObj != null) {
            long lastRequest = Long.parseLong(lastRequestObj.toString());

            if (DateUtil.getCurrentTimeSeconds() > lastRequest) {
                session.setAttribute("clientAuthenticate", true);
            }
        } else {
            session.setAttribute("clientAuthenticate", false);
        }
    }

    private void checkRememberMeCookie(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return;
        }

        String token = null;
        for (Cookie cookie : cookies) {
            if (REMEMBER_TOKEN_NAME.equals(cookie.getName())) {
                token = cookie.getValue();
                break;
            }
        }

        if (token == null || token.isBlank()) {
            return;
        }

        int userId = SessionDao.getRememberToken(token);

        if (userId > 0) {
            session.setAttribute(LOGGED_IN, true);
            session.setAttribute("captcha.invalid", false);
            session.setAttribute(USER_ID, userId);

            String uri = request.getRequestURI();
            if (uri.equals("/home") || uri.equals("/index") || uri.equals("/")) {
                try {
                    response.sendRedirect("/me");
                } catch (Exception e) {
                    logger.error("Failed to redirect after remember-me login", e);
                }
            }
        } else {
            session.removeAttribute(USER_ID);
            session.removeAttribute(LOGGED_IN);

            // Clear the invalid cookie
            Cookie clearCookie = new Cookie(REMEMBER_TOKEN_NAME, "");
            clearCookie.setMaxAge(0);
            clearCookie.setPath("/");
            response.addCookie(clearCookie);
        }
    }
}
