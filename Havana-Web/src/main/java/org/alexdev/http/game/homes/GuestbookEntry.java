package org.alexdev.http.game.homes;

import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.wordfilter.WordfilterManager;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.http.util.BBCode;
import org.alexdev.http.util.HtmlUtil;

public class GuestbookEntry {
    private final int id;
    private final int userId;
    private final int homeId;
    private final int groupId;
    private final long creationDate;
    private final String message;

    public GuestbookEntry(int id, int userId, int homeId, int groupId, String message, long creationDate) {
        this.id = id;
        this.userId = userId;
        this.homeId = homeId;
        this.groupId = groupId;
        this.message = message;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public PlayerDetails getUser() {
        return PlayerDao.getDetails(this.userId);
    }

    public int getHomeId() {
        return homeId;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getCreationDate() {
        return DateUtil.getFriendlyDate(this.creationDate);
    }

    public String getMessage() {
        return BBCode.format(HtmlUtil.escape(BBCode.normalise(WordfilterManager.filterSentence(this.message))), false);
    }
}
