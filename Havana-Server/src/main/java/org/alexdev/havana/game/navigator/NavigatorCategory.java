package org.alexdev.havana.game.navigator;

import org.alexdev.havana.dao.mysql.NavigatorDao;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;

import java.util.ArrayList;
import java.util.List;

public class NavigatorCategory {
    private int id;
    private int parentId;
    private int orderId;
    private String name;
    private boolean publicSpaces;
    private boolean allowTrading;
    private PlayerRank minimumRoleAccess;
    private PlayerRank minimumRoleSetFlat;
    private boolean isNode;
    private boolean isClubOnly;

    public NavigatorCategory(int id, int parentId, int orderId, String name, boolean publicSpaces, boolean allowTrading, PlayerRank minimumRoleAccess, PlayerRank minimumRoleSetFlat, boolean isNode, boolean isClubOnly) {
        this.id = id;
        this.parentId = parentId;
        this.orderId = orderId;
        this.name = name;
        this.publicSpaces = publicSpaces;
        this.allowTrading = allowTrading;
        this.minimumRoleAccess = minimumRoleAccess;
        this.minimumRoleSetFlat = minimumRoleSetFlat;
        this.isNode = isNode;
        this.isClubOnly = isClubOnly;
    }

    /**
     * Count the active rooms under this category.
     *
     * @return the active room count
     */
    public int getRoomCount() {
        if (this.publicSpaces) {
            return NavigatorDao.getRoomCountByCategory(this.id);
        } else {
            List<Room> rooms = new ArrayList<>();

            for (Room room : RoomManager.getInstance().getRooms()) {
                if (room.getData().getCategoryId() == this.id) {
                    rooms.add(room);
                }
            }

            return rooms.size();//NavigatorDao.getRoomCountByCategory(this.id);
        }
    }

    /**
     * Get the current visitors within this category.
     *
     * @return the current visitors
     */
    public int getCurrentVisitors() {
        int currentVisitors = 0;

        for (Room room : this.isPublicSpaces() ? RoomManager.getInstance().replaceQueryRooms(NavigatorDao.getRoomsByCategory(this.id)) : RoomManager.getInstance().getRooms()) {
            if (room.getData().getCategoryId() == this.id) {
                currentVisitors += room.getData().getVisitorsNow();
            }

        }

        return currentVisitors;
    }

    /**
     * Get the max visitors within this category.
     *
     * @return the max visitors
     */
    public int getMaxVisitors() {
        int maxVisitors = 0;

        for (Room room : this.isPublicSpaces() ? RoomManager.getInstance().replaceQueryRooms(NavigatorDao.getRoomsByCategory(this.id)) : RoomManager.getInstance().getRooms()) {
            if (room.getData().getCategoryId() == this.id) {
                maxVisitors += room.getData().getVisitorsMax();
            }
        }

        return maxVisitors;
    }

    public int getId() {
        return id;
    }

    public int getParentId() {
        return parentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getName() {
        return name;
    }

    public boolean isPublicSpaces() {
        return publicSpaces;
    }

    public boolean hasAllowTrading() {
        return allowTrading;
    }

    public PlayerRank getMinimumRoleAccess() {
        return minimumRoleAccess;
    }

    public PlayerRank getMinimumRoleSetFlat() {
        return minimumRoleSetFlat;
    }

    public boolean isNode() {
        return isNode;
    }

    public boolean isClubOnly() {
        return isClubOnly;
    }
}
