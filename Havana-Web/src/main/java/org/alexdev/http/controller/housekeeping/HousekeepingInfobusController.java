package org.alexdev.http.controller.housekeeping;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.InfobusDao;
import org.alexdev.havana.game.infobus.InfobusPoll;
import org.alexdev.havana.game.infobus.InfobusPollData;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.http.Routes;
import org.alexdev.http.game.housekeeping.HousekeepingManager;
import org.alexdev.http.util.HtmlUtil;
import org.alexdev.http.util.RconUtil;
import org.alexdev.http.util.piechart.PieChart;
import org.alexdev.http.util.piechart.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/" + Routes.HOUSEKEEPING_PATH)
public class HousekeepingInfobusController {

    @GetMapping("/infobus_polls")
    public String polls(HttpSession session, Model model) {
        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "infobus")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());
        model.addAttribute("pageName", "View Infobus Polls");
        model.addAttribute("infobusPolls", InfobusDao.getInfobusPolls());

        session.removeAttribute("alertMessage");

        return "housekeeping/infobus_polls";
    }

    @GetMapping("/infobus_polls/create")
    public String createPollsGet(HttpSession session, Model model) {
        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "infobus")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());
        model.addAttribute("pageName", "Create Infobus Poll");
        model.addAttribute("oneHourLater", DateUtil.getDate(DateUtil.getCurrentTimeSeconds() + TimeUnit.HOURS.toSeconds(1), "yyyy-MM-dd'T'HH:mm"));

        session.removeAttribute("alertMessage");

        return "housekeeping/infobus_polls_create";
    }

    @PostMapping("/infobus_polls/create")
    public String createPollsPost(
            @RequestParam(value = "question", defaultValue = "") String question,
            @RequestParam(value = "answers[]", required = false) List<String> answers,
            HttpSession session,
            Model model) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "infobus")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        session.setAttribute("alertColour", "success");
        session.setAttribute("alertMessage", "Infobus poll has been created successfully");

        InfobusPollData infobusPollData = new InfobusPollData(question);
        if (answers != null) {
            infobusPollData.getAnswers().addAll(answers);
        }
        InfobusDao.createInfobusPoll(playerDetails.getId(), infobusPollData);

        return "redirect:/" + Routes.HOUSEKEEPING_PATH + "/infobus_polls";
    }

    @GetMapping("/infobus_polls/delete")
    public String deletePoll(
            @RequestParam(value = "id", defaultValue = "0") int pollId,
            HttpSession session) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        InfobusPoll poll = InfobusDao.get(pollId);

        if (poll == null) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "The infobus poll does not exist");
            return "redirect:/" + Routes.HOUSEKEEPING_PATH + "/infobus_polls";
        }

        if (poll.getInitiatedBy() != playerDetails.getId()) {
            if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "infobus/delete_any")) {
                session.setAttribute("alertColour", "danger");
                session.setAttribute("alertMessage", "No permission to delete other polls");
                return "redirect:/" + Routes.HOUSEKEEPING_PATH;
            }
        }

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "infobus/delete_own")) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "No permission to delete");
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        var answers = InfobusDao.getAnswers(poll.getId());
        int totalAnswers = answers.values().stream().mapToInt(Integer::intValue).sum();

        if (totalAnswers > 0) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "You can't delete a poll with answers");
            return "redirect:/" + Routes.HOUSEKEEPING_PATH + "/infobus_polls";
        }

        session.setAttribute("alertColour", "success");
        session.setAttribute("alertMessage", "Successfully deleted the infobus poll");

        InfobusDao.delete(pollId);
        InfobusDao.clearAnswers(pollId);

        return "redirect:/" + Routes.HOUSEKEEPING_PATH + "/infobus_polls";
    }

    @GetMapping("/infobus_polls/send")
    public String sendPoll(
            @RequestParam(value = "id", defaultValue = "0") int pollId,
            HttpSession session) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "infobus")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        InfobusPoll poll = InfobusDao.get(pollId);

        if (poll == null) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "The infobus poll does not exist");
            return "redirect:/" + Routes.HOUSEKEEPING_PATH + "/infobus_polls";
        }

        session.setAttribute("alertColour", "warning");
        session.setAttribute("alertMessage", "The infobus poll request has been sent");

        final int finalPollId = poll.getId();
        RconUtil.sendCommand(RconHeader.INFOBUS_POLL, new HashMap<>() {{
            put("pollId", String.valueOf(finalPollId));
        }});

        return "redirect:/" + Routes.HOUSEKEEPING_PATH + "/infobus_polls";
    }

    @GetMapping("/infobus_polls/edit")
    public String editPollGet(
            @RequestParam(value = "id", defaultValue = "0") int pollId,
            HttpSession session,
            Model model) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "infobus")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());

        InfobusPoll infobusPoll = InfobusDao.get(pollId);

        if (pollId == 0) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "There was no infobus poll selected to edit");
            return "redirect:/" + Routes.HOUSEKEEPING_PATH + "/infobus_polls";
        } else if (infobusPoll == null) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "The infobus poll does not exist");
            return "redirect:/" + Routes.HOUSEKEEPING_PATH + "/infobus_polls";
        }

        model.addAttribute("poll", infobusPoll);

        session.removeAttribute("alertMessage");

        return "housekeeping/infobus_polls_edit";
    }

    @PostMapping("/infobus_polls/edit")
    public String editPollPost(
            @RequestParam(value = "id", defaultValue = "0") int pollId,
            @RequestParam(value = "question", defaultValue = "") String question,
            @RequestParam(value = "answers[]", required = false) List<String> answers,
            HttpSession session) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "infobus")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        InfobusPoll infobusPoll = InfobusDao.get(pollId);

        if (infobusPoll == null) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "The infobus poll does not exist");
            return "redirect:/" + Routes.HOUSEKEEPING_PATH + "/infobus_polls";
        }

        var existingAnswers = InfobusDao.getAnswers(infobusPoll.getId());
        int totalAnswers = existingAnswers.values().stream().mapToInt(Integer::intValue).sum();

        if (totalAnswers > 0) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "You can't edit the poll if it has answers");
        } else {
            session.setAttribute("alertColour", "success");
            session.setAttribute("alertMessage", "The infobus poll was successfully saved");

            InfobusPollData infobusPollData = new InfobusPollData(question);
            if (answers != null) {
                infobusPollData.getAnswers().addAll(answers);
            }
            InfobusDao.saveInfobusPoll(pollId, infobusPollData);
        }

        return "redirect:/" + Routes.HOUSEKEEPING_PATH + "/infobus_polls";
    }

    @GetMapping("/infobus_polls/view")
    public String viewResults(
            @RequestParam(value = "id", defaultValue = "0") int pollId,
            HttpSession session,
            Model model) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "infobus")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());

        InfobusPoll infobusPoll = InfobusDao.get(pollId);

        if (pollId == 0) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "There was no infobus poll selected to view");
            return "redirect:/" + Routes.HOUSEKEEPING_PATH + "/infobus_polls";
        } else if (infobusPoll == null) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "The infobus poll does not exist");
            return "redirect:/" + Routes.HOUSEKEEPING_PATH + "/infobus_polls";
        }

        model.addAttribute("poll", infobusPoll);

        var image = new BufferedImage(500, 250, BufferedImage.TYPE_INT_ARGB);

        var answers = InfobusDao.getAnswers(infobusPoll.getId());
        int totalAnswers = answers.values().stream().mapToInt(Integer::intValue).sum();

        var slices = new ArrayList<Slice>();

        Color[] colors = {Color.BLUE, Color.RED, Color.YELLOW, Color.PINK, Color.ORANGE};
        int i = 0;

        if (totalAnswers > 0) {
            for (var answer : answers.entrySet()) {
                Color color = i < colors.length ? colors[i] : Color.GRAY;

                try {
                    slices.add(new Slice(infobusPoll.getPollData().getAnswers().get(answer.getKey()),
                            (double) (answer.getValue() > 0 ? totalAnswers / answer.getValue() : 0), color));
                } catch (Exception ex) {
                    session.setAttribute("alertColour", "danger");
                    session.setAttribute("alertMessage", "There was an answer to a question that doesn't exist, some answers may not be visible on this chart");
                }

                i++;
            }
        }

        new PieChart(image, slices);

        model.addAttribute("imageData", "data:image/png;base64," + HtmlUtil.encodeToString(image, "PNG"));
        model.addAttribute("noAnswers", totalAnswers == 0);

        session.removeAttribute("alertMessage");

        return "housekeeping/infobus_polls_view";
    }

    @GetMapping("/infobus_polls/clear")
    public String clearResults(
            @RequestParam(value = "id", defaultValue = "0") int pollId,
            HttpSession session) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "infobus")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        InfobusPoll infobusPoll = InfobusDao.get(pollId);

        if (pollId == 0 || infobusPoll == null) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "The infobus poll does not exist");
        } else {
            session.setAttribute("alertColour", "success");
            session.setAttribute("alertMessage", "The infobus poll has had all answers cleared");

            InfobusDao.clearAnswers(infobusPoll.getId());
        }

        return "redirect:/" + Routes.HOUSEKEEPING_PATH + "/infobus_polls";
    }

    @GetMapping("/infobus/close_event")
    public String closeEvent(HttpSession session) {
        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "infobus")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        session.setAttribute("alertColour", "success");
        session.setAttribute("alertMessage", "The infobus status has been sent");

        RconUtil.sendCommand(RconHeader.INFOBUS_END_EVENT, new HashMap<>());

        return "redirect:/" + Routes.HOUSEKEEPING_PATH + "/infobus_polls";
    }

    @GetMapping("/infobus/door_status")
    public String doorStatus(
            @RequestParam(value = "status", defaultValue = "0") int status,
            HttpSession session) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "infobus")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        RconUtil.sendCommand(RconHeader.INFOBUS_DOOR_STATUS, new HashMap<>() {{
            put("doorStatus", String.valueOf(status));
        }});

        session.setAttribute("alertColour", "success");
        session.setAttribute("alertMessage", "The infobus door status has been sent");

        return "redirect:/" + Routes.HOUSEKEEPING_PATH + "/infobus_polls";
    }
}
