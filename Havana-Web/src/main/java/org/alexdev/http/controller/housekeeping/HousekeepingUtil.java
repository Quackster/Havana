package org.alexdev.http.controller.housekeeping;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.http.util.SessionUtil;

public class HousekeepingUtil {

    public static PlayerDetails getPlayerDetails(HttpSession session) {
        Object userIdObj = session.getAttribute(SessionUtil.USER_ID);
        if (userIdObj == null) {
            return null;
        }

        int userId;
        if (userIdObj instanceof String) {
            userId = Integer.parseInt((String) userIdObj);
        } else {
            userId = (Integer) userIdObj;
        }

        return PlayerDao.getDetails(userId);
    }

    public static boolean isLoggedIn(HttpSession session) {
        Boolean loggedIn = (Boolean) session.getAttribute(SessionUtil.LOGGED_IN_HOUSKEEPING);
        return loggedIn != null && loggedIn;
    }
}
