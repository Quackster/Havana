package org.alexdev.http.controller.site;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.dao.mysql.PlayerStatisticsDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;
import org.alexdev.havana.game.room.handlers.RoomSelectionHandler;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.scheduler.WatchdogScheduler;
import org.alexdev.http.util.SessionHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class ClientController {

    @GetMapping("/client")
    public String client(HttpServletRequest request, HttpSession session) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/login_popup";
        }

        String queryString = request.getQueryString();
        String getRequests = queryString != null ? "?" + queryString : "";

        return "redirect:/shockwave_client" + getRequests;
    }

    @GetMapping("/shockwave_client")
    public String shockwaveClient(
            @RequestParam(value = "createRoom", required = false) String createRoom,
            @RequestParam(value = "forwardId", required = false) Integer forwardId,
            @RequestParam(value = "roomId", required = false) Integer roomId,
            @RequestParam(value = "shortcut", required = false) String shortcut,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            Model model) throws SQLException {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/login_popup";
        }

        session.setAttribute("clientRequest", request.getRequestURI() +
                (request.getQueryString() != null ? "?" + request.getQueryString() : ""));

        if (SessionHelper.getBoolean(session, "clientAuthenticate")) {
            return "redirect:/account/reauthenticate";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        if (playerDetails == null) {
            SessionHelper.logout(request, response);
            return "redirect:/";
        }

        var pair = playerDetails.isBanned();
        if (pair != null) {
            return "redirect:/account/banned";
        }

        boolean forwardRoom = false;
        int forwardType = -1;
        int forwardIdVal = -1;

        // Handle room creation
        if (createRoom != null && StringUtils.isNumeric(createRoom)) {
            int roomType = Integer.parseInt(createRoom);
            boolean setGift = false;

            if (!playerDetails.canSelectRoom()) {
                int roomLayout = (int) PlayerStatisticsDao.getStatisticLong(playerDetails.getId(), PlayerStatistic.NEWBIE_ROOM_LAYOUT);

                if (roomLayout == 0) {
                    if (!(roomType < 0 || roomType > 5)) {
                        setGift = true;
                    }
                }
            } else {
                setGift = RoomSelectionHandler.selectRoom(playerDetails.getId(), roomType);
            }

            if (setGift) {
                PlayerStatisticsDao.updateStatistic(playerDetails.getId(), PlayerStatistic.NEWBIE_ROOM_LAYOUT, roomType + 1);
                PlayerStatisticsDao.updateStatistic(playerDetails.getId(), PlayerStatistic.NEWBIE_GIFT, 1);
                PlayerStatisticsDao.updateStatistic(playerDetails.getId(), PlayerStatistic.NEWBIE_GIFT_TIME,
                        DateUtil.getCurrentTimeSeconds() + TimeUnit.DAYS.toSeconds(1));
            }

            playerDetails = PlayerDao.getDetails(SessionHelper.getUserId(session));
            forwardRoom = true;
            forwardType = 2; // Private room
            forwardIdVal = playerDetails.getSelectedRoomId();
        }

        // Handle forward
        if (forwardId != null) {
            forwardRoom = true;
            forwardIdVal = roomId != null ? roomId : -1;
            forwardType = forwardId;
        }

        // Handle shortcut
        if (shortcut != null) {
            int redirectionId = 0;
            if ("roomomatic".equals(shortcut)) {
                redirectionId = 1;
            }
            if (redirectionId > 0) {
                model.addAttribute("shortcut", "shortcut.id=" + redirectionId + ";");
            }
        }

        // SSO ticket
        String ssoTicket = playerDetails.getSsoTicket();
        if (GameConfiguration.getInstance().getBoolean("reset.sso.after.login") || ssoTicket == null || ssoTicket.isBlank()) {
            ssoTicket = UUID.randomUUID().toString();
            PlayerDao.setTicket(SessionHelper.getUserId(session), ssoTicket);
        }

        model.addAttribute("ssoTicket", ssoTicket);
        model.addAttribute("forwardRoom", forwardRoom);

        if (forwardRoom) {
            model.addAttribute("forward", "<param name=\"sw9\" value=\"forward.type=" + forwardType +
                    ";forward.id=" + forwardIdVal + ";processlog.url=\">");
            model.addAttribute("forwardSub", "sw9=\"forward.type=" + forwardType +
                    ";forward.id=" + forwardIdVal + ";processlog.url=\"");
            model.addAttribute("forwardScript", "<param name=\\\"sw9\\\" value=\\\"forward.type=" + forwardType +
                    ";forward.id=" + forwardIdVal + ";processlog.url=\\\">");
            model.addAttribute("forwardSubScript", "sw9=\\\"forward.type=" + forwardType +
                    ";forward.id=" + forwardIdVal + ";processlog.url=\\\"");
        }

        return "client";
    }

    @GetMapping("/flash_client")
    public String flashClient(
            @RequestParam(value = "createRoom", required = false) String createRoom,
            @RequestParam(value = "forwardId", required = false) Integer forwardId,
            @RequestParam(value = "roomId", required = false) Integer roomId,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            Model model) throws SQLException {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/login_popup";
        }

        session.setAttribute("clientRequest", request.getRequestURI() +
                (request.getQueryString() != null ? "?" + request.getQueryString() : ""));

        if (SessionHelper.getBoolean(session, "clientAuthenticate")) {
            return "redirect:/account/reauthenticate";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        if (playerDetails == null) {
            SessionHelper.logout(request, response);
            return "redirect:/";
        }

        var pair = playerDetails.isBanned();
        if (pair != null) {
            return "redirect:/account/banned";
        }

        boolean forwardRoom = false;
        int forwardType = -1;
        int forwardIdVal = -1;

        // Handle room creation
        if (createRoom != null && StringUtils.isNumeric(createRoom)) {
            int roomType = Integer.parseInt(createRoom);
            boolean setGift = false;

            if (!playerDetails.canSelectRoom()) {
                int roomLayout = (int) PlayerStatisticsDao.getStatisticLong(playerDetails.getId(), PlayerStatistic.NEWBIE_ROOM_LAYOUT);

                if (roomLayout == 0) {
                    if (!(roomType < 0 || roomType > 5)) {
                        setGift = true;
                    }
                }
            } else {
                setGift = RoomSelectionHandler.selectRoom(playerDetails.getId(), roomType);
            }

            if (setGift) {
                PlayerStatisticsDao.updateStatistic(playerDetails.getId(), PlayerStatistic.NEWBIE_ROOM_LAYOUT, roomType + 1);
                PlayerStatisticsDao.updateStatistic(playerDetails.getId(), PlayerStatistic.NEWBIE_GIFT, 1);
                PlayerStatisticsDao.updateStatistic(playerDetails.getId(), PlayerStatistic.NEWBIE_GIFT_TIME,
                        DateUtil.getCurrentTimeSeconds() + TimeUnit.DAYS.toSeconds(1));
            }

            playerDetails = PlayerDao.getDetails(SessionHelper.getUserId(session));
            forwardRoom = true;
            forwardType = 2;
            forwardIdVal = playerDetails.getSelectedRoomId();
        }

        // Handle forward
        if (forwardId != null) {
            forwardRoom = true;
            forwardIdVal = roomId != null ? roomId : -1;
            forwardType = forwardId;
        }

        // SSO ticket
        String ssoTicket = playerDetails.getSsoTicket();
        if (GameConfiguration.getInstance().getBoolean("reset.sso.after.login") || ssoTicket == null || ssoTicket.isBlank()) {
            ssoTicket = UUID.randomUUID().toString();
            PlayerDao.setTicket(SessionHelper.getUserId(session), ssoTicket);
        }

        model.addAttribute("ssoTicket", ssoTicket);
        model.addAttribute("forwardRoom", forwardRoom);
        model.addAttribute("forwardId", forwardIdVal);
        model.addAttribute("forwardType", forwardType);

        return "client_flash";
    }

    @GetMapping("/client_install_shockwave")
    public String clientInstallShockwave(HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/login_popup";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        var pair = playerDetails.isBanned();
        if (pair != null) {
            return "redirect:/account/banned";
        }

        return "client_install_shockwave";
    }

    @GetMapping("/update_habbo_count")
    public ResponseEntity<String> updateHabboCount() {
        String json = "{\"habboCountText\":\"" +
                NumberFormat.getNumberInstance(Locale.US).format(WatchdogScheduler.USERS_ONLINE) +
                " members online\"}";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header("X-JSON", json)
                .body("");
    }

    @GetMapping("/cacheCheck")
    public ResponseEntity<String> cacheCheck() {
        return ResponseEntity.ok("");
    }

    @GetMapping("/client_error")
    public String clientError(
            @RequestParam(value = "error_id", required = false) String errorId,
            HttpSession session,
            Model model) {

        if (SessionHelper.isAuthenticated(session)) {
            PlayerDetails playerDetails = SessionHelper.getPlayer(session);
            var pair = playerDetails.isBanned();
            if (pair != null) {
                return "redirect:/account/banned";
            }
        }

        if (errorId != null) {
            model.addAttribute("errorId", errorId);
        }

        return "client_error";
    }

    @GetMapping("/client_connection_failed")
    public String clientConnectionFailed(
            @RequestParam(value = "error_id", required = false) String errorId,
            HttpSession session,
            Model model) {

        if (SessionHelper.isAuthenticated(session)) {
            PlayerDetails playerDetails = SessionHelper.getPlayer(session);
            var pair = playerDetails.isBanned();
            if (pair != null) {
                return "redirect:/account/banned";
            }
        }

        if (errorId != null) {
            model.addAttribute("errorId", errorId);
        }

        return "client_connection_failed";
    }
}
