package org.alexdev.havana.game.events;

import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomData;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.util.DateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Event {
    private int roomId;
    private int userId;
    private int categoryId;
    private String name;
    private String description;
    private long expire;
    private List<String> tags;

    /***
     * The event constructor.
     *  @param roomId the room id the event is hosted in
     * @param userId the user id hosting the event
     * @param categoryId the category of the event
     * @param name the name of the event
     * @param description the event description
     * @param started the unix timestamp of when the event started
     * @param tags the room event tags (can only be created in flash)
     */
    public Event(int roomId, int userId, int categoryId, String name, String description, long started, String tags) {
        this.roomId = roomId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.expire = started;
        this.tags = new ArrayList<>(Arrays.asList(tags.split(",")));
    }

    /**
     * Get whether the event has expired.
     *
     * @return true, if successful
     */
    public boolean isExpired() {
        return DateUtil.getCurrentTimeSeconds() > this.expire;
    }

    /**
     * Get the room id the event is being hosted in.
     *
     * @return the room id the event is hosted in
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Get the room instance this event is being hosted for.
     */
    public int getPlayersInEvent() {
        Room room = RoomManager.getInstance().getRoomById(this.roomId);

        if (room != null) {
            return room.getEntityManager().getPlayers().size();
        }

        return 0;
    }

    /**
     * Get room data.
     */
    public RoomData getRoomData() {
        return RoomManager.getInstance().getRoomById(this.roomId).getData();
    }

    /**
     * Get the id of the user hosting the event.
     *
     * @return the event hoster id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Get player details
     */
    public PlayerDetails getUserInfo() {
        return PlayerManager.getInstance().getPlayerData(this.userId);
    }


    /**
     * Get the category id for this event.
     *
     * @return the catgeory id
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Set the category id for this event.
     *
     * @param categoryId the category id
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Get the event hoster.
     *
     * @return the event hoster
     */
    public PlayerDetails getEventHoster() {
        return PlayerManager.getInstance().getPlayerData(this.userId);
    }

    /**
     * Get the event name.
     *
     * @return the event name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the new name for this event
     *
     * @param name the event name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the event description.
     *
     * @return the event description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the new description for this event.
     *
     * @param description the event description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the time the event started.
     *
     * @return the time the event started
     */
    public long getExpireTime() {
        return expire;
    }

    /**
     * Get the time the event started as a formatted date.
     *
     * @return the date formatted
     */
    public String getStartedDate() {
        return DateUtil.getDate(this.expire - EventsManager.getEventLifetime(), DateUtil.LONG_DATE);
    }

    /**
     * Get the time the event started as a formatted friendly date.
     *
     * @return the date formatted
     */
    public String getFriendlyDate() {
        return DateUtil.getFriendlyDate(this.expire - EventsManager.getEventLifetime());
    }

    /**
     * Get the event tags
     *
     * @return the event tags
     */
    public List<String> getTags() {
        return tags;
    }
}
