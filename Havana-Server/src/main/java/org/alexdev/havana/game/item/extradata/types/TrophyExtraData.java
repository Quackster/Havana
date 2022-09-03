package org.alexdev.havana.game.item.extradata.types;

public class TrophyExtraData {
    private int userId;
    private String message;
    private long date;

    public TrophyExtraData(int userId, String message, long date) {
        this.userId = userId;
        this.message = message;
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public long getDate() {
        return date;
    }
}
