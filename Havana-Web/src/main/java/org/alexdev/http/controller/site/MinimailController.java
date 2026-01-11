package org.alexdev.http.controller.site;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.dao.mysql.PlayerStatisticsDao;
import org.alexdev.havana.game.messenger.Messenger;
import org.alexdev.havana.game.messenger.MessengerUser;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;
import org.alexdev.havana.game.wordfilter.WordfilterManager;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.StringUtil;
import org.alexdev.http.dao.MinimailDao;
import org.alexdev.http.game.minimail.MinimailMessage;
import org.alexdev.http.util.BBCode;
import org.alexdev.http.util.HtmlUtil;
import org.alexdev.http.util.SessionHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class MinimailController {

    @PostMapping("/habblet/ajax/minimail/loadmessages")
    public String loadMessages(
            @RequestParam(value = "label", defaultValue = "") String label,
            @RequestParam(value = "start", defaultValue = "0") int startNumber,
            @RequestParam(value = "unreadOnly", defaultValue = "false") boolean unreadOnly,
            @RequestParam(value = "conversationId", defaultValue = "0") int conversationId,
            HttpSession session,
            HttpServletResponse response,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        appendMessages(session, response, model, label, startNumber, unreadOnly, conversationId, false, false, false, false, false, false);
        return "habblet/minimail/minimail_messages";
    }

    @PostMapping("/habblet/ajax/minimail/recipients")
    @ResponseBody
    public String recipients(HttpSession session) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        PlayerDetails playerDetails = PlayerDao.getDetails(SessionHelper.getUserId(session));
        Messenger messenger = new Messenger(playerDetails);

        StringBuilder recipients = new StringBuilder();

        int i = 0;
        for (MessengerUser messengerUser : new ArrayList<>(messenger.getFriends().values())) {
            i++;
            recipients.append("{\"id\":").append(messengerUser.getUserId()).append(",\"name\":\"").append(messengerUser.getUsername()).append("\"}");
            if (messenger.getFriends().size() > i) {
                recipients.append(",");
            }
        }

        return "/*-secure-\n[" + recipients.toString() + "]\n */";
    }

    @PostMapping("/habblet/ajax/minimail/preview")
    @ResponseBody
    public String preview(
            @RequestParam(value = "body", defaultValue = "") String body,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        return BBCode.format(HtmlUtil.escape(body), false);
    }

    @PostMapping("/habblet/ajax/minimail/sendmessage")
    public String sendMessage(
            @RequestParam(value = "label", defaultValue = "") String label,
            @RequestParam(value = "start", defaultValue = "0") int startNumber,
            @RequestParam(value = "unreadOnly", defaultValue = "false") boolean unreadOnly,
            @RequestParam(value = "conversationId", defaultValue = "0") int conversationId,
            @RequestParam(value = "body", defaultValue = "") String message,
            @RequestParam(value = "recipientIds", required = false) String recipientIds,
            @RequestParam(value = "messageId", defaultValue = "0") int messageId,
            @RequestParam(value = "subject", defaultValue = "") String subject,
            HttpSession session,
            HttpServletResponse response,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);
        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        List<MinimailMessage> minimailMessageList = new ArrayList<>();
        var muteExpireTime = PlayerStatisticsDao.getStatisticLong(playerDetails.getId(), PlayerStatistic.MUTE_EXPIRES_AT);
        var isMuted = muteExpireTime > 0 && muteExpireTime > DateUtil.getCurrentTimeSeconds();

        if (!isMuted) {
            if (recipientIds != null && !recipientIds.isEmpty()) {
                String[] recipients = recipientIds.split(",");
                Messenger messenger = new Messenger(playerDetails);

                for (String data : recipients) {
                    if (!StringUtils.isNumeric(data)) {
                        continue;
                    }

                    int toId = Integer.parseInt(data);

                    if (!messenger.hasFriend(toId)) {
                        continue;
                    }

                    if (WordfilterManager.filterSentence(message).equals(message)) {
                        minimailMessageList.add(new MinimailMessage(-1, userId, toId, userId, false, subject, message, 0, 0, false));
                        minimailMessageList.add(new MinimailMessage(-1, toId, toId, userId, false, subject, message, 0, 0, false));
                    }
                }
            } else if (messageId > 0) {
                MinimailMessage minimailMessage = MinimailDao.getMessage(messageId, userId);

                if (minimailMessage != null) {
                    minimailMessage.setConversationId(messageId);
                    MinimailDao.updateMessage(minimailMessage);

                    if (WordfilterManager.filterSentence(message).equals(message)) {
                        minimailMessageList.add(new MinimailMessage(-1, userId, minimailMessage.getSenderId(), userId, false, "Re: " + minimailMessage.getSubject(), message, 0, minimailMessage.getConversationId(), false));
                        minimailMessageList.add(new MinimailMessage(-1, minimailMessage.getSenderId(), minimailMessage.getSenderId(), userId, false, "Re: " + minimailMessage.getSubject(), message, 0, minimailMessage.getConversationId(), false));
                    }
                }
            }

            MinimailDao.createMessages(minimailMessageList);
        }

        appendMessages(session, response, model, label, startNumber, unreadOnly, conversationId, false, true, false, false, false, isMuted);
        return "habblet/minimail/minimail_messages";
    }

    @GetMapping("/habblet/ajax/minimail/loadmessage")
    public String loadMessage(
            @RequestParam(value = "messageId", defaultValue = "0") int messageId,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);

        if (messageId <= 0) {
            return "";
        }

        MinimailMessage minimailMessage = MinimailDao.getMessage(messageId, userId);

        if (minimailMessage == null) {
            return "";
        }

        String minimailLabel = (String) session.getAttribute("minimailLabel");
        if (minimailLabel == null) {
            minimailLabel = "inbox";
        }

        boolean canSetUnread = false;

        if (minimailLabel.equalsIgnoreCase("conversation")) {
            if (userId != minimailMessage.getTargetId() && userId != minimailMessage.getSenderId()) {
                return "";
            }
        } else if (minimailLabel.equalsIgnoreCase("sent")) {
            if (userId != minimailMessage.getTargetId() && userId != minimailMessage.getSenderId()) {
                return "";
            }
        }

        canSetUnread = true;

        minimailMessage.setTarget(PlayerDao.getDetails(minimailMessage.getToId()));
        minimailMessage.setAuthor(PlayerDao.getDetails(minimailMessage.getSenderId()));

        if (canSetUnread && !minimailMessage.isRead()) {
            minimailMessage.setRead(true);
            MinimailDao.updateMessage(minimailMessage);
        }

        model.addAttribute("minimailLabel", minimailLabel);
        model.addAttribute("minimailMessage", minimailMessage);
        return "habblet/minimail/minimail_load_message";
    }

    @PostMapping("/habblet/ajax/minimail/deletemessage")
    public String deleteMessage(
            @RequestParam(value = "label", defaultValue = "") String label,
            @RequestParam(value = "start", defaultValue = "0") int startNumber,
            @RequestParam(value = "unreadOnly", defaultValue = "false") boolean unreadOnly,
            @RequestParam(value = "conversationId", defaultValue = "0") int conversationId,
            @RequestParam(value = "messageId", defaultValue = "0") int messageId,
            HttpSession session,
            HttpServletResponse response,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);

        if (messageId <= 0) {
            return "";
        }

        MinimailMessage minimailMessage = MinimailDao.getMessage(messageId, userId);

        if (minimailMessage == null) {
            return "";
        }

        if (!minimailMessage.isTrash()) {
            minimailMessage.setTrash(true);
            MinimailDao.updateMessage(minimailMessage);
        } else {
            MinimailDao.deleteMessage(minimailMessage);
        }

        appendMessages(session, response, model, label, startNumber, unreadOnly, conversationId, false, false, true, false, false, false);
        return "habblet/minimail/minimail_messages";
    }

    @PostMapping("/habblet/ajax/minimail/undeletemessage")
    public String undeleteMessage(
            @RequestParam(value = "label", defaultValue = "") String label,
            @RequestParam(value = "start", defaultValue = "0") int startNumber,
            @RequestParam(value = "unreadOnly", defaultValue = "false") boolean unreadOnly,
            @RequestParam(value = "conversationId", defaultValue = "0") int conversationId,
            @RequestParam(value = "messageId", defaultValue = "0") int messageId,
            HttpSession session,
            HttpServletResponse response,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);

        if (messageId <= 0) {
            return "";
        }

        MinimailMessage minimailMessage = MinimailDao.getMessage(messageId, userId);

        if (minimailMessage == null) {
            return "";
        }

        minimailMessage.setTrash(false);
        MinimailDao.updateMessage(minimailMessage);

        appendMessages(session, response, model, label, startNumber, unreadOnly, conversationId, false, false, false, true, false, false);
        return "habblet/minimail/minimail_messages";
    }

    @PostMapping("/habblet/ajax/minimail/emptytrash")
    public String emptyTrash(
            @RequestParam(value = "label", defaultValue = "") String label,
            @RequestParam(value = "start", defaultValue = "0") int startNumber,
            @RequestParam(value = "unreadOnly", defaultValue = "false") boolean unreadOnly,
            @RequestParam(value = "conversationId", defaultValue = "0") int conversationId,
            HttpSession session,
            HttpServletResponse response,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);
        MinimailDao.emptyTrash(userId);

        appendMessages(session, response, model, label, startNumber, unreadOnly, conversationId, false, false, false, false, true, false);
        return "habblet/minimail/minimail_messages";
    }

    private void appendMessages(HttpSession session, HttpServletResponse response, Model model,
                                String label, int startNumber, boolean unreadOnly, int conversationId,
                                boolean isPageLoad, boolean messageSent, boolean messageDeleted,
                                boolean messageUndeleted, boolean trashEmptied, boolean isMuted) {

        if (label.isBlank()) {
            String sessionLabel = (String) session.getAttribute("minimailLabel");
            if (sessionLabel == null) {
                label = "inbox";
            } else {
                label = sessionLabel;
                if (label.equals("conversation") && conversationId <= 0) {
                    label = "inbox";
                }
            }
        }

        int userId = SessionHelper.getUserId(session);

        int pageNumber = 0;
        if (startNumber > 0) {
            pageNumber = startNumber / 10;
        }

        session.setAttribute("minimailLabel", label);
        model.addAttribute("minimailLabel", label);

        List<MinimailMessage> entireMessageList = new ArrayList<>();

        if (label.equalsIgnoreCase("inbox")) {
            entireMessageList = MinimailDao.getMessages(userId);
        }

        if (label.equalsIgnoreCase("sent")) {
            entireMessageList = MinimailDao.getMessagesSent(userId);
        }

        if (label.equalsIgnoreCase("trash")) {
            entireMessageList = MinimailDao.getMessageTrash(userId);
        }

        if (label.equalsIgnoreCase("conversation")) {
            entireMessageList = MinimailDao.getMessagesConversation(userId, conversationId);
        }

        if (unreadOnly) {
            entireMessageList.removeIf(MinimailMessage::isRead);
        }

        model.addAttribute("unreadOnly", unreadOnly);

        entireMessageList.sort(Comparator.comparingLong(MinimailMessage::getDateSent).reversed());
        var paginatedMessages = StringUtil.paginate(entireMessageList, 10, true);
        var minimailMessages = paginatedMessages.get(pageNumber);

        model.addAttribute("showOlder", false);
        model.addAttribute("showOldest", false);

        if (paginatedMessages.containsKey(pageNumber + 1)) {
            model.addAttribute("showOlder", true);
        }

        if (paginatedMessages.containsKey(pageNumber + 2)) {
            model.addAttribute("showOldest", true);
        }

        if (paginatedMessages.containsKey(pageNumber - 1)) {
            model.addAttribute("showNewer", true);
        }

        if (paginatedMessages.containsKey(pageNumber - 2)) {
            model.addAttribute("showNewest", true);
        }

        model.addAttribute("minimailMessages", minimailMessages);
        model.addAttribute("totalMessages", entireMessageList.size());
        model.addAttribute("minimailClient", false);

        var endPage = 10;
        var startPage = startNumber;

        if (startNumber != 0) {
            startPage++;
            endPage = startNumber + 10;
        } else {
            startPage = 1;
        }

        if (endPage > entireMessageList.size()) {
            endPage = entireMessageList.size();
        }

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        Map<Integer, PlayerDetails> playerDetailsMap = new HashMap<>();

        for (MinimailMessage minimailMessage : minimailMessages) {
            if (!playerDetailsMap.containsKey(minimailMessage.getToId())) {
                playerDetailsMap.put(minimailMessage.getToId(), PlayerDao.getDetails(minimailMessage.getToId()));
            }

            if (!playerDetailsMap.containsKey(minimailMessage.getSenderId())) {
                playerDetailsMap.put(minimailMessage.getSenderId(), PlayerDao.getDetails(minimailMessage.getSenderId()));
            }

            minimailMessage.setAuthor(playerDetailsMap.get(minimailMessage.getSenderId()));
            minimailMessage.setTarget(playerDetailsMap.get(minimailMessage.getToId()));
        }

        if (!isPageLoad) {
            if (messageSent) {
                if (isMuted) {
                    response.setHeader("X-JSON", "{\"message\":\"You are muted and cannot send messages.\",\"totalMessages\":" + entireMessageList.size() + "}");
                } else {
                    response.setHeader("X-JSON", "{\"message\":\"Message sent successfully.\",\"totalMessages\":" + entireMessageList.size() + "}");
                }
            } else if (messageDeleted) {
                response.setHeader("X-JSON", "{\"message\":\"The message has been moved to the trash. You can undelete it, if you wish\",\"totalMessages\":" + entireMessageList.size() + "}");
            } else if (messageUndeleted) {
                response.setHeader("X-JSON", "{\"message\":\"Message undeleted\",\"totalMessages\":" + entireMessageList.size() + "}");
            } else if (trashEmptied) {
                response.setHeader("X-JSON", "{\"message\":\"The trash has been emptied. Good Job!\",\"totalMessages\":" + entireMessageList.size() + "}");
            } else {
                response.setHeader("X-JSON", "{\"totalMessages\":" + entireMessageList.size() + "}");
            }
        }
    }
}
