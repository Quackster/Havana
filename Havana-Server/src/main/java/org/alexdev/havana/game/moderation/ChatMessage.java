package org.alexdev.havana.game.moderation;

import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE;

import java.util.Calendar;

public class ChatMessage {
    private final int playerId;
    private final String message;
    private final CHAT_MESSAGE.ChatMessageType chatMessageType;
    private final int roomId;
    private final long sentTime;
    private final String playerName;

    public ChatMessage(int playerId, String playerName, String message, CHAT_MESSAGE.ChatMessageType chatMessageType, int roomId, long sentTime) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.roomId =  roomId;
        this.message = message;
        this.chatMessageType = chatMessageType;
        this.sentTime = sentTime;
    }

    public Calendar getCalendar() {
        var calendar = Calendar.getInstance();
        calendar.setTimeInMillis(this.sentTime * 1000L);
        return calendar;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getMessage() {
        return message;
    }

    public CHAT_MESSAGE.ChatMessageType getChatMessageType() {
        return chatMessageType;
    }

    public long getSentTime() {
        return sentTime;
    }

    public String getPlayerName() {
        return playerName;
    }
}
