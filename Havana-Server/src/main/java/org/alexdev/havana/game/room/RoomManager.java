package org.alexdev.havana.game.room;

import org.alexdev.havana.dao.mysql.*;
import org.alexdev.havana.game.messenger.MessengerUser;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.room.handlers.walkways.WalkwaysEntrance;
import org.alexdev.havana.game.room.handlers.walkways.WalkwaysManager;
import org.alexdev.havana.game.room.managers.VoteData;
import org.alexdev.havana.util.config.GameConfiguration;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class RoomManager {
    public static final int PUBLIC_ROOM_OFFSET = 1000; // Used as the "port" for the public room, in NAVNODEINFO and friend following
    private static RoomManager instance = null;
    private Map<Integer, List<String>> roomEntryBadges;

    private ConcurrentHashMap<Integer, Room> roomMap;

    public RoomManager() {
        this.roomMap = new ConcurrentHashMap<>();
        this.roomEntryBadges = BadgeDao.getRoomBadges();

        int stoutRoomId = GameConfiguration.getInstance().getInteger("stout.room");

        if (stoutRoomId > 0) {
            if (!this.roomEntryBadges.containsKey(stoutRoomId)) {
                this.roomEntryBadges.put(stoutRoomId, List.of("STOUT"));
            }
        }
    }

    /**
     * Get the child rooms by room id - for rooms with walkways.
     *
     * @param room the room to check
     */
    public List<Room> getChildRooms(Room room) {
        List<Room> roomList = new ArrayList<>();

        if (room.isPublicRoom()) {
            getSubRooms(room.getId(), roomList);

            for (Room r : roomList) {
                getSubRooms(r.getId(), roomList);
            }
        }

        return roomList;
    }

    /**
     * Get child rooms, if they have a walkway.
     *
     * @param id the id to check
     * @param roomList the list to add to
     */
    private void getSubRooms(int id, List<Room> roomList) {
        for (WalkwaysEntrance walkway : WalkwaysManager.getInstance().getWalkways()) {
            if (walkway.getRoomTargetId() == id && walkway.getRoomId() != id) {
                if (roomList.stream().noneMatch(r -> r.getId() == walkway.getRoomId()) &&
                    roomList.stream().noneMatch(r -> r.getId() == walkway.getRoomTargetId())) {
                    roomList.add(this.getRoomById(walkway.getRoomId()));
                }
            }
        }
    }

    /**
     * Find a room by its model.
     *
     * @param model the model to find the room by
     * @return the room found, else null
     */
    public Room getRoomByModel(String model) {
        int roomId = RoomDao.getRoomIdByModel(model);
        return getRoomById(roomId);
    }

    /**
     * Find a room by room id.
     *
     * @param roomId the id of the room to find
     * @return the loaded room instance, if successful, else query the db
     */
    public Room getRoomById(int roomId) {
        if (this.roomMap.containsKey(roomId)) {
            return this.roomMap.get(roomId);
        }

        return RoomDao.getRoomById(roomId);
    }

    /**
     * Check whether the room is active.
     *
     * @param roomId the room id to check
     * @return true, is successful
     */
    public boolean hasRoom(int roomId) {
        return this.roomMap.containsKey(roomId);
    }

    /**
     * Removes a room from the map by room id as key.
     *
     * @param roomId the id of the room to remove
     */
    public void removeRoom(int roomId) {
        this.roomMap.remove(roomId);
    }

    /**
     * Add a room instance to the map.
     *
     * @param room the instance of the room
     */
    public void addRoom(Room room) {
        if (room == null) {
            return;
        }

        if (this.roomMap.containsKey(room.getId())) {
            return;
        }

        this.roomMap.put(room.getData().getId(), room);
    }

    /**
     * Will sort a list of rooms returned by MySQL query and
     * replace any with loaded rooms that it finds.
     *
     * @param queryRooms the list of rooms returned by query
     * @return a possible list of actual loaded rooms
     */
    public List<Room> replaceQueryRooms(List<Room> queryRooms) {
        List<Room> roomList = new ArrayList<>();

        for (Room room : queryRooms) {
            if (this.roomMap.containsKey(room.getId())) {
                roomList.add(this.getRoomById(room.getData().getId()));
            } else {
                roomList.add(room);
            }
        }

        return roomList;
    }

    /**
     * Get a list of favourite rooms by user id
     *
     * @param userId the user to get the favourites for
     * @return the list of favourites
     */
    public List<Room> getFavouriteRooms(int userId, boolean privateRoomsOnly) {
        List<Integer> roomIds = RoomFavouritesDao.getFavouriteRooms(userId, privateRoomsOnly);
        Collections.reverse(roomIds); // To most recent favourite added at the top

        List<Room> rooms = new ArrayList<>();

        for (int roomId : roomIds) {
            Room room = this.getRoomById(roomId);

            if (room != null) {
                rooms.add(room);
            }
        }

        return rooms;
    }

    /**
     * Performs a santiy check and recounts the given room, to make sure
     * that have had their votes expired and is recounted properly.
     *
     * @param roomList the list of rooms to do the santiy check for
     */
    public void ratingSantiyCheck(List<Room> roomList) {
        for (Room room : roomList) {
            if (room.isPublicRoom()) {
                continue;
            }

            if (room.getData().getVisitorsNow() > 0) {
                continue;
            }

            if (!(room.getData().getRating() > 0)) {
                return;
            }

            RoomVoteDao.removeExpiredVotes(room.getId());
            int newRating = RoomVoteDao.getRatings(room.getId()).stream().mapToInt(VoteData::getVote).sum();

            if (newRating < 0) {
                newRating = 0;
            }

            if (newRating != room.getData().getRating()) {
                RoomDao.saveRating(room.getId(), newRating);
            }
        }
    }

    /**
     * Sort the list of rooms by higher populated rooms appearing first.
     *
     * @param roomList the list of rooms to sort
     */
    public void sortRooms(List<Room> roomList) {
        Comparator<Room> roomComparator = Comparator
                .comparing((Room room) -> room.getData().getTotalVisitorsNow())
                .thenComparing((Room room) -> room.getData().getRating()).reversed();

        roomList.sort(roomComparator);
    }

    /**
     * Get the entire list of rooms.
     *
     * @return the collection of rooms
     */
    public Collection<Room> getRooms() {
        return this.roomMap.values();
    }

    /**
     * Get the instance of {@link RoomManager}
     *
     * @return the instance
     */
    public static RoomManager getInstance() {
        if (instance == null) {
            instance = new RoomManager();
        }

        return instance;
    }

    /**
     * Reload badges given upon room entry.
     */
    public void reloadBadges() {
        this.roomEntryBadges = BadgeDao.getRoomBadges();
    }

    /**
     * Give badges to everybody in the room already.
     */
    public void giveBadges() {
        for (Room room : this.roomMap.values()) {
            if (!this.roomEntryBadges.containsKey(room.getId())) {
                continue;
            }

            for (String badge : this.roomEntryBadges.get(room.getId())) {
                for (Player player : room.getEntityManager().getPlayers()) {
                    player.getBadgeManager().tryAddBadge(badge, null);
                }
            }
        }
     }

    /**
     * Get list of badges to receive on room entry
     * @return
     */
    public Map<Integer, List<String>> getRoomEntryBadges() {
        return roomEntryBadges;
    }

    /**
     * Get rooms by flash R34 mode
     * @param mode
     * @param player
     * @return
     */
    public List<Room> getRoomsByMode(int mode, Player player) {
        List<Room> roomList = new ArrayList<>();

        switch (mode) {
            case 1: // Popular rooms
            {
                roomList = RoomManager.getInstance().replaceQueryRooms(NavigatorDao.getRopularRooms(30, false));
                break;
            }
            case 2: // Highest score
            {
                roomList = RoomManager.getInstance().replaceQueryRooms(RoomDao.getHighestRatedRooms(30, 0));
                break;
            }
            case 5: // My rooms
            {
                roomList = RoomManager.getInstance().replaceQueryRooms(RoomDao.getRoomsByUserId(player.getDetails().getId()));
                break;
            }
            case 6: // My favourites
            {
                roomList = RoomManager.getInstance().getFavouriteRooms(player.getDetails().getId(), true);
                break;
            }
            case 7: // My visited rooms
            {
                roomList = RoomManager.getInstance().replaceQueryRooms(NavigatorDao.getRecentlyVisited(30, player.getDetails().getId()));
                break;
            }
            case 4: // Friends in rooms
            {
                for (MessengerUser messengerUser : player.getMessenger().getFriends().values()) {
                    if (!messengerUser.isOnline()) {
                        continue;
                    }

                    var friend = PlayerManager.getInstance().getPlayerById(messengerUser.getUserId());
                    var friendRoom = friend.getRoomUser().getRoom();

                    if (friendRoom != null && !friendRoom.isPublicRoom()) {
                        if (roomList.stream().noneMatch(room -> room.getId() == friendRoom.getId())) {
                            roomList.add(friendRoom);
                        }
                    }
                }

                break;
            }
            case 3: // Friends rooms
            {
                roomList = RoomManager.getInstance().replaceQueryRooms(NavigatorDao.getFriendRooms(30, player.getMessenger().getFriends().values().stream().map(x -> String.valueOf(x.getUserId())).collect(Collectors.toList())));
                break;
            }
        }

        return roomList;
    }
}
