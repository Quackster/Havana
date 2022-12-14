package org.alexdev.havana.game.tags;

import org.alexdev.havana.dao.mysql.GroupDao;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.dao.mysql.TagDao;
import org.alexdev.havana.game.groups.Group;
import org.alexdev.havana.game.player.PlayerDetails;

import java.util.ArrayList;
import java.util.List;

public class HabboTag {
    private final String tag;
    private final int roomId;
    private final int userId;
    private final int groupId;
    private Group groupData;
    private PlayerDetails userData;
    private List<String> tagList;

    public HabboTag(String tag, int roomId, int userId, int groupId) {
        this.tag = tag;
        this.roomId = roomId;
        this.userId = userId;
        this.groupId = groupId;
        this.tagList = new ArrayList<>();
    }

    public List<String> getTagList() {
        if (this.groupId > 0) {
            this.getGroupData();
        }

        if (this.userId > 0) {
            this.getUserData();
        }

        return tagList;
    }


    public String getTag() {
        return tag;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getUserId() {
        return userId;
    }

    public int getGroupId() {
        return groupId;
    }

    public Group getGroupData() {
        if (this.groupData == null) {
            if (this.groupId > 0) {
                this.groupData = GroupDao.getGroup(this.groupId);
                this.tagList = TagDao.getGroupTags(this.groupId);
            }
        }

        return groupData;
    }

    public PlayerDetails getUserData() {
        if (this.userData == null) {
            if (this.userId > 0) {
                this.userData = PlayerDao.getDetails(this.userId);
                this.tagList = TagDao.getUserTags(this.userId);
            }
        }

        return userData;
    }
}
