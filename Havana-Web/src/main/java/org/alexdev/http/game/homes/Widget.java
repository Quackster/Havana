package org.alexdev.http.game.homes;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;
import org.alexdev.havana.dao.mysql.*;
import org.alexdev.havana.game.badges.Badge;
import org.alexdev.havana.game.badges.BadgeManager;
import org.alexdev.havana.game.groups.Group;
import org.alexdev.havana.game.groups.GroupMember;
import org.alexdev.havana.game.groups.GroupMemberRank;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.ItemManager;
import org.alexdev.havana.game.messenger.MessengerUser;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.game.song.Song;
import org.alexdev.havana.game.wordfilter.WordfilterManager;
import org.alexdev.havana.util.StringUtil;
import org.alexdev.http.dao.FriendManagementDao;
import org.alexdev.http.dao.GuestbookDao;
import org.alexdev.http.dao.RatingDao;
import org.alexdev.http.dao.WidgetDao;
import org.alexdev.http.game.stickers.StickerManager;
import org.alexdev.http.game.stickers.StickerProduct;
import org.alexdev.http.game.stickers.StickerType;
import org.alexdev.http.util.BBCode;
import org.alexdev.http.util.HtmlUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Widget {
    private static final Integer FRIENDS_PAGING_AMOUNT = 32;
    private static final Integer MEMBER_PAGING_AMOUNT = 32;

    private final int id;
    private final int userId;
    private int x;
    private int y;
    private int z;
    private final int stickerId;
    private int skinId;
    private int groupId;
    private int amount;
    private String text;
    private boolean isPlaced;
    private String extraData;

    public Widget(int id, int userId, int x, int y, int z, int stickerId, int skinId, int groupId, String text, int amount, boolean isPlaced, String extraData) {
        this.id = id;
        this.userId = userId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.stickerId = stickerId;
        this.skinId = skinId;
        this.groupId = groupId;
        this.text = text;
        this.amount = amount;
        this.isPlaced = isPlaced;
        this.extraData = extraData;
    }

    public Template template(WebConnection webConnection) {
        Template tpl = null;

        if (this.getProduct().getData().equalsIgnoreCase("groupinfowidget")) {
            tpl = webConnection.template("homes/widget/group_info_widget");
        } else if (this.getProduct().getData().equalsIgnoreCase("guestbookwidget")) {
            tpl = webConnection.template("homes/widget/guestbook_widget");
        } else if (this.getProduct().getData().equalsIgnoreCase("stickienote")) {
            tpl = webConnection.template("homes/widget/note");
        } else if (this.getProduct().getData().equalsIgnoreCase("memberwidget")) {
            tpl = webConnection.template("homes/widget/member_widget");
        } else if (this.getProduct().getData().equalsIgnoreCase("traxplayerwidget")) {
            tpl = webConnection.template("homes/widget/trax_player_widget");
        } else if (this.getProduct().getData().equalsIgnoreCase("profilewidget")) {
            tpl = webConnection.template("homes/widget/profile_widget");
        } else if (this.getProduct().getData().equalsIgnoreCase("roomswidget")) {
            tpl = webConnection.template("homes/widget/rooms_widget");
        } else if (this.getProduct().getData().equalsIgnoreCase("highscoreswidget")) {
            tpl = webConnection.template("homes/widget/highscores_widget");
        } else if (this.getProduct().getData().equalsIgnoreCase("badgeswidget")) {
            tpl = webConnection.template("homes/widget/badges_widget");
        } else if (this.getProduct().getData().equalsIgnoreCase("friendswidget")) {
            tpl = webConnection.template("homes/widget/friends_widget");
        } else if (this.getProduct().getData().equalsIgnoreCase("groupswidget")) {
            tpl = webConnection.template("homes/widget/groups_widget");
        } else if (this.getProduct().getData().equalsIgnoreCase("ratingwidget")) {
            tpl = webConnection.template("homes/widget/rating_widget");
        } else {
            tpl = webConnection.template("homes/widget/sticker");
        }

        tpl.set("editMode", webConnection.session().contains("homeEditSession") || webConnection.session().contains("groupEditSession"));
        tpl.set("sticker", this);

        if (webConnection.session().contains("homeEditSession")) {
            PlayerDetails playerDetails = PlayerDao.getDetails(webConnection.session().getInt("user.id"));
            tpl.set("user", playerDetails);
            tpl.set("canAddFriend", false);

            if (this.getProduct().getData().equalsIgnoreCase("profilewidget")) {
                var badges = BadgeDao.getBadges(playerDetails.getId());
                var enabledBadges = new ArrayList<>(badges);
                badges.removeIf(badge -> !badge.isEquipped());
                badges.sort(Comparator.comparingInt(Badge::getSlotId));

                tpl.set("hasBadge", enabledBadges.size() > 0);

                if (enabledBadges.size() > 0) {
                    tpl.set("badgeCode", enabledBadges.get(0).getBadgeCode());
                }

                tpl.set("hasFavouriteGroup", false);

                if (playerDetails.getFavouriteGroupId() > 0) {
                    Group group = GroupDao.getGroup(playerDetails.getFavouriteGroupId());

                    if (group != null) {
                        tpl.set("hasFavouriteGroup", true);
                        tpl.set("group", group);
                    }
                }
            }
        }

        return tpl;
    }

    public List<Badge> getFirstBadges() {
        return StringUtil.paginate(new BadgeManager(this.userId).getBadges(), 16, true).get(0);
    }

    public Map<Integer, List<Badge>> getBadgeList() {
        return StringUtil.paginate(new BadgeManager(this.userId).getBadges(), 16);
    }

    public List<Room> getOwnerRooms() {
        var roomList = RoomDao.getRoomsByUserId(this.userId);
        RoomManager.getInstance().sortRooms(roomList);

        return roomList;
    }

    public List<MessengerUser> getFirstFriendsList() {
        return FriendManagementDao.getFriends(this.userId, 1, FRIENDS_PAGING_AMOUNT);
    }

    public int getFriendsPagesSearch(String search) {
        int friends = FriendManagementDao.getFriendsCount(this.userId, search);
        int pages = friends > 0 ? (int) Math.ceil((double)friends / (double)FRIENDS_PAGING_AMOUNT) : 0;
        return pages == 0 ? 1 : pages;
    }

    public int getFriendsPages() {
        int friends = FriendManagementDao.getFriendsCount(this.userId);
        int pages = friends > 0 ? (int) Math.ceil((double)friends / (double)FRIENDS_PAGING_AMOUNT) : 0;
        return pages == 0 ? 1 : pages;
    }

    public int getFriendsAmount() {
        return MessengerDao.getFriendsCount(this.userId);
    }

    public List<MessengerUser> getFriendsList(String query, int page) {
        if (!query.isBlank()) {
            return FriendManagementDao.getFriendsSearch(this.userId, query, page, FRIENDS_PAGING_AMOUNT);
        } else {
            return FriendManagementDao.getFriends(this.userId, page, FRIENDS_PAGING_AMOUNT);
        }
    }

    public String getGuestbookState() {
        if (this.extraData.equalsIgnoreCase("public")) {
            return "public";
        }

        if (this.extraData.equalsIgnoreCase("private")) {
            return "private";
        }

        return "public";
    }

    public boolean isPostingAllowed(int userId) {
        if (this.getGuestbookState().equalsIgnoreCase("public")) {
            return true;
        }

        if (this.getProduct().getType() == StickerType.GROUP_WIDGET) {
            Group group = GroupDao.getGroup(this.groupId);
            return group.isMember(userId);
        }

        if (this.getProduct().getType() == StickerType.HOME_WIDGET) {
            return userId == this.getUserId() || MessengerDao.friendExists(userId, this.getUserId());
        }

        return false;
    }

    public List<GuestbookEntry> getGuestbookEntries() {
        List<GuestbookEntry> entries = null;

        int id = 0;

        if (this.getProduct().getType() == StickerType.GROUP_WIDGET) {
            entries = GuestbookDao.getEntriesByGroup(this.getGroupId());
        } else if (this.getProduct().getType() == StickerType.HOME_WIDGET) {
            entries = GuestbookDao.getEntriesByHome(this.getUserId());
        }

        return entries;
    }

    public boolean canDeleteEntries(int userId) {
        boolean canDelete = false;

        if (this.getProduct().getType() == StickerType.GROUP_WIDGET) {
            canDelete = (GroupDao.getGroupOwner(this.groupId) == userId);
        } else if (this.getProduct().getType() == StickerType.HOME_WIDGET) {
            canDelete = (userId == this.getUserId());
        }

        return canDelete;
    }

    public List<Song> getSongs() {
        List<Song> songList = null;
        int userId = 0;

        if (this.getProduct().getType() == StickerType.GROUP_WIDGET) {
            userId = GroupDao.getGroupOwner(this.groupId);
            songList = SongMachineDao.getSongUserList(userId);
        } else if (this.getProduct().getType() == StickerType.HOME_WIDGET) {
            userId = this.getUserId();
            songList = SongMachineDao.getSongUserList(userId);
        }

        for (Item item : ItemDao.getUserItemsByDefinition(userId, ItemManager.getInstance().getDefinitionBySprite("song_disk"))) {
            int songId = JukeboxDao.getSongIdByItem(item.getDatabaseId());
            Song song = SongMachineDao.getSong(songId);

            if (song == null) {
                continue;
            }

            if (songList != null) {
                if (songList.stream().noneMatch(s -> s.getId() == song.getId())) {
                    songList.add(song);
                }
            }
        }

        return songList;
    }

    public List<GroupMember> getFirstMembersList() {
        var members = GroupMemberDao.getMembers(this.groupId, false,  "",1, FRIENDS_PAGING_AMOUNT);
        members.add(new GroupMember(GroupDao.getGroupOwner(this.groupId), this.groupId, false, GroupMemberRank.OWNER.getRankId()));
        return members.stream()
                .sorted(Comparator.comparingLong((GroupMember gp) -> gp.getUser().getLastOnline()).reversed())
                .collect(Collectors.toList());
    }

    public List<GroupMember> getMembersList(String query, int page) {
        var members = GroupMemberDao.getMembers(this.groupId, false, query, page, FRIENDS_PAGING_AMOUNT);
        return members.stream()
                .sorted(Comparator.comparingLong((GroupMember gp) -> gp.getUser().getLastOnline()).reversed())
                .collect(Collectors.toList());
    }

    public int getMembersAmount() {
        return GroupMemberDao.countMembers(this.groupId, false) + 1;
    }

    public int getMembersPages() {
        int members = GroupMemberDao.countMembers(this.groupId, false) + 1;
        int pages = members > 0 ? (int) Math.ceil((double)members / (double)FRIENDS_PAGING_AMOUNT) : 0;
        return pages == 0 ? 1 : pages;
    }


    public boolean hasSong() {
        Song song = null;

        if (StringUtils.isNumeric(this.extraData)) {
            int songId = Integer.parseInt(this.extraData);
            song = SongMachineDao.getSong(songId);
        }

        return song != null;
    }


    public Song getSong() {
        Song song = null;

        if (StringUtils.isNumeric(this.extraData)) {
            int songId = Integer.parseInt(this.extraData);
            song = SongMachineDao.getSong(songId);
        }

        return song;
    }


    public List<Group> getOwnerGroups() {
        return GroupDao.getJoinedGroups(this.userId);
    }

    public boolean hasRated(int userId) {
        return RatingDao.hasRated(userId, this.userId);
    }

    public int getAverageRating() {
        return (int)(RatingDao.getAverageRating(this.userId));
    }

    public int getRatingPixels() {
        double rating = getAverageRating();

        if (rating <= 0) {
            rating = 1;
        }

        return (int) Math.round(rating * 150 / 5);
    }

    public int getHighVoteCount() {
        return RatingDao.getHighVoteCount(this.userId);
    }

    public int getVoteCount() {
        return RatingDao.getVoteCount(this.userId);
    }

    public void save() {
        WidgetDao.save(this);
    }

    public StickerProduct getProduct() {
        return StickerManager.getInstance().getStickerProduct(this.stickerId);
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getStickerId() {
        return stickerId;
    }

    public String getSkin() {
        return StickerManager.getInstance().getSkin(this.skinId);
    }

    public void setSkinId(int skinId) {
        this.skinId = skinId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getSkinId() {
        return skinId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getText() {
        return WordfilterManager.filterSentence(text);
    }

    public String getFormattedText() {
        return BBCode.format(HtmlUtil.escape(BBCode.normalise(WordfilterManager.filterSentence(this.text))), false);
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public void setPlaced(boolean placed) {
        isPlaced = placed;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }
}
