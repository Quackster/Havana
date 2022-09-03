package org.alexdev.havana.game.messenger;

import org.alexdev.havana.dao.mysql.MessengerDao;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.messenger.ADD_BUDDY;
import org.alexdev.havana.messages.outgoing.messenger.FRIENDS_UPDATE;
import org.alexdev.havana.messages.outgoing.messenger.FRIEND_REQUEST;
import org.alexdev.havana.util.config.GameConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Messenger {
    private final boolean officialStatusUpdateSpeed;
    private Player player;
    private Map<Integer, MessengerUser> friends;
    private Map<Integer, MessengerUser> requests;
    private Map<Integer, MessengerMessage> offlineMessages;

    private List<MessengerCategory> messengerCategories;

    private BlockingQueue<MessengerUser> friendsUpdate;
    private int friendsLimit;
    private boolean allowsFriendRequests;
    private MessengerUser user;
    private Room followed;

    public Messenger(Player player) {
        this(player.getDetails());
        this.player = player;
    }

    public Messenger(PlayerDetails details) {
        this.officialStatusUpdateSpeed = GameConfiguration.getInstance().getBoolean("messenger.enable.official.update.speed");
        this.user = new MessengerUser(details);
        this.friends = MessengerDao.getFriends(details.getId());
        this.requests = MessengerDao.getRequests(details.getId());
        this.offlineMessages = MessengerDao.getUnreadMessages(details.getId());
        this.allowsFriendRequests = details.isAllowFriendRequests();

        this.messengerCategories = MessengerDao.getCategories(details.getId());
        this.friendsUpdate = new LinkedBlockingQueue<>();

        if (details.getRank().getRankId() <= 1) {
            if (details.hasClubSubscription()) {
                this.friendsLimit = GameConfiguration.getInstance().getInteger("messenger.max.friends.club");
            } else {
                this.friendsLimit = GameConfiguration.getInstance().getInteger("messenger.max.friends.nonclub");
            }
        } else {
            this.friendsLimit = Integer.MAX_VALUE;
        }
    }

    /**
     * Sends the status update when a friend enters or leaves a room, logs in or disconnects.
     */
    public void sendStatusUpdate() {
        if (this.user == null) {
            return;
        }
        var onlineFriends = this.getOnlineFriends();

        /*for (var user : this.friends.values()) {
            int userId = user.getUserId();

            Player friend = PlayerManager.getInstance().getPlayerById(userId);

            if (friend != null && friend.getMessenger() != null) {
                var youAsFriend = ;

                if (youAsFriend != null) {
                    friend.getMessenger().queueFriendUpdate(youAsFriend);
                }
            }
        }*/

        for (Player friend : onlineFriends) {
            friend.getMessenger().queueFriendUpdate(friend.getMessenger().getFriend(this.user.getUserId()));
        }

        if (!this.officialStatusUpdateSpeed) {
            for (Player friend : onlineFriends) {
                friend.send(new FRIENDS_UPDATE(friend, friend.getMessenger()));
            }
        }
    }

    /***
     * Get the list of online friends.
     *
     * @return the list of online friends
     */
    private List<Player> getOnlineFriends() {
        List<Player> friends = new ArrayList<>();

        for (var user : this.friends.values()) {
            int userId = user.getUserId();

            Player friend = PlayerManager.getInstance().getPlayerById(userId);

            if (friend != null && friend.getMessenger() != null) {
                var youAsFriend = friend.getMessenger().getFriend(this.user.getUserId());

                if (youAsFriend != null) {
                    friends.add(friend);
                }
            }
        }

        return friends;
    }

    /**
     * Get if the user already has a request from this user id.
     *
     * @param userId the user id to check for
     * @return true, if successful
     */
    public boolean hasRequest(int userId) {
        return this.getRequest(userId) != null;
    }

    /**
     * Get if the user already has a friend with this user id.
     *
     * @param userId the user id to check for
     * @return true, if successful
     */
    public boolean hasFriend(int userId) {
        return this.getFriend(userId) != null;
    }

    /**
     * Method to add new friend.
     *
     * @param newBuddy the new friend to add
     */
    public void addFriend(MessengerUser newBuddy) {
        if (this.hasFriend(newBuddy.getUserId())) {
            return;
        }

        MessengerDao.removeRequest(newBuddy.getUserId(), this.user.getUserId());

        MessengerDao.newFriend(player.getDetails().getId(), newBuddy.getUserId());
        MessengerDao.newFriend(newBuddy.getUserId(), player.getDetails().getId());

        this.player.send(new ADD_BUDDY(player, new MessengerUser(PlayerDao.getDetails(newBuddy.getUserId()))));
        this.requests.remove(newBuddy.getUserId());
        this.friends.put(newBuddy.getUserId(), newBuddy);

        Player friend = PlayerManager.getInstance().getPlayerById(newBuddy.getUserId());

        if (friend != null) {
            MessengerUser meAsBuddy = player.getMessenger().getMessengerUser();
            friend.getMessenger().getFriends().put(meAsBuddy.getUserId(), meAsBuddy);
            friend.send(new ADD_BUDDY(friend, meAsBuddy));
        }
    }

    /**
     * Add request method.
     *
     * @param requester method to add request
     */
    public void addRequest(MessengerUser requester) {
        MessengerDao.newRequest(requester.getUserId(), this.user.getUserId());
        this.requests.put(requester.getUserId(), requester);

        Player requested = PlayerManager.getInstance().getPlayerById(this.user.getUserId());

        if (requested != null) {
            requested.send(new FRIEND_REQUEST(requester));
        }
    }

    /**
     * Decline request by friend.
     *
     * @param requester the requester
     */
    public void declineRequest(MessengerUser requester) {
        MessengerDao.removeRequest(requester.getUserId(), this.user.getUserId());
        this.requests.remove(requester.getUserId());
    }

    /**
     * Decline all friend requests.
     */
    public void declineAllRequests() {
        MessengerDao.removeAllRequests(this.user.getUserId());
        this.requests.clear();
    }

    /**
     * Get if the friend limit is reached. Limit is dependent upon club subscription
     *
     * @return true, if limit reached
     */
    public boolean isFriendsLimitReached() {
        return this.friends.size() >= this.getFriendsLimit();
    }

    /**
     * Get the friends list amount
     * @return
     */
    public int getFriendsLimit() {
        return this.friendsLimit;
    }

    /**
     * Get the messenger user instance with this user id.
     *
     * @param userId the user id to check for
     * @return the messenger user instance
     */
    public MessengerUser getRequest(int userId) {
        return this.requests.get(userId);
    }

    /**
     * Get the messenger user instance with this user id.
     *
     * @param userId the user to check for
     * @return the messenger user instance
     */
    public MessengerUser getFriend(int userId) {
        return this.friends.get(userId);
    }

    /**
     * Remove friend from friends list
     *
     * @param userId
     * @return boolean indicating success
     */
    public boolean removeFriend(int userId) {
        this.friends.remove(userId);

        MessengerDao.removeFriend(userId, this.user.getUserId());
        MessengerDao.removeFriend(this.user.getUserId(), userId);

        return true;
    }

    /**
     * Get the list of offline messages.
     *
     * @return the list of offline messages
     */
    public Map<Integer, MessengerMessage> getOfflineMessages() {
        return offlineMessages;
    }

    /**
     * Get the list of friends.
     *
     * @return the list of friends
     */
    public Map<Integer, MessengerUser> getFriends() {
        return this.friends;
    }

    /**
     * Get the messenger user instance of this person
     * @return the instance
     */
    public MessengerUser getMessengerUser() {
        return this.user;
    }

    /**
     * Get the list of friends.
     *
     * @return the list of friends
     */
    public List<MessengerUser> getRequests() {
        return new ArrayList<>(this.requests.values());
    }

    /**
     * Get on whether the user allows friend requests
     * @return true, if scuessful
     */
    public boolean allowsFriendRequests() {
        return allowsFriendRequests;
    }

    /**
     * Gets the queue for the next friends came online update.
     *
     * @return the queue
     */
    public BlockingQueue<MessengerUser> getFriendsUpdate() {
        return friendsUpdate;
    }

    /**
     * Adds a user friend left to the events update console queue, removes any previous mentions of this friend.
     *
     * @param friend the friend to update
     */
    public void queueFriendUpdate(MessengerUser friend) {
        this.friendsUpdate.removeIf(f -> f.getUserId() == friend.getUserId());
        this.friendsUpdate.add(friend);
    }

    public List<MessengerCategory> getCategories() {
        return messengerCategories;
    }

    public void hasFollowed(Room friendRoom) {
        this.followed = friendRoom;
    }

    public Room getFollowed() {
        return followed;
    }
}