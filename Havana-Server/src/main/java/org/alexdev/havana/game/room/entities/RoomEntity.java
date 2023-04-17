package org.alexdev.havana.game.room.entities;

import org.alexdev.havana.game.bot.BotManager;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.item.interactors.types.BedInteractor;
import org.alexdev.havana.game.item.roller.RollingData;
import org.alexdev.havana.game.moderation.ChatManager;
import org.alexdev.havana.game.pathfinder.Pathfinder;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.pathfinder.Rotation;
import org.alexdev.havana.game.pets.PetManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.game.room.RoomUserStatus;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.game.room.handlers.PublicRoomRedirection;
import org.alexdev.havana.game.room.handlers.walkways.WalkwaysEntrance;
import org.alexdev.havana.game.room.handlers.walkways.WalkwaysManager;
import org.alexdev.havana.game.room.managers.RoomTimerManager;
import org.alexdev.havana.game.room.mapping.RoomTile;
import org.alexdev.havana.game.wordfilter.WordfilterManager;
import org.alexdev.havana.messages.outgoing.effects.USER_AVATAR_EFFECT;
import org.alexdev.havana.messages.outgoing.rooms.user.*;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.config.GameConfiguration;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class RoomEntity {
    private Entity entity;
    private Position position;
    private Position goal;
    private Position nextPosition;
    private Room room;

    private RollingData rollingData;
    private RoomTimerManager timerManager;

    private Map<String, RoomUserStatus> statuses;
    private LinkedList<Position> path;

    private BlockingQueue<MessageComposer> packetQueueAfterRoomLeave;
    private CopyOnWriteArrayList<String> chatMessages;

    private int instanceId;
    private Item lastItemInteraction;

    private boolean isTeleporting;
    private boolean isWalking;
    private boolean isWalkingAllowed;
    private boolean beingKicked;
    private boolean needsUpdate;
    private boolean enableWalkingOnStop;

    private int danceId;
    private int effectId;
    private boolean isSleeping;

    private AtomicInteger pixelAvailableTick;

    private boolean hasItemDebug;

    private int carryId;
    private String carryValue;

    private long enteredRoomAt;

    public RoomEntity(Entity entity) {
        this.entity = entity;
        this.statuses = new ConcurrentHashMap<>();
        this.path = new LinkedList<>();
        this.packetQueueAfterRoomLeave = new LinkedBlockingQueue<MessageComposer>();
        this.timerManager = new RoomTimerManager(this);
        this.enteredRoomAt = DateUtil.getCurrentTimeSeconds();
    }

    public void reset() {
        this.statuses.clear();
        this.path.clear();
        this.nextPosition = null;
        this.goal = null;
        this.room = null;
        this.rollingData = null;
        this.lastItemInteraction = null;
        this.isWalking = false;
        this.danceId = 0;
        //this.effectId = 0;
        this.isSleeping = false;
        this.isWalkingAllowed = true;
        this.beingKicked = false;
        this.instanceId = -1;
        this.carryId = 0;
        this.carryValue = "";
        this.pixelAvailableTick = new AtomicInteger(GameConfiguration.getInstance().getInteger("pixels.max.tries.single.room.instance"));
        this.chatMessages = new CopyOnWriteArrayList<>();
        this.timerManager.resetTimers();
        this.enteredRoomAt = 0;
    }

    /**
     * Kick a user from the room.
     *
     * @param allowWalking whether the user can interrupt themselves walking towards the door
     */
    public void kick(boolean allowWalking, boolean isBeingKicked) {
        try {
            if (this.entity.getType() == EntityType.PET) {
                return;
            }

            Position doorLocation = this.getRoom().getModel().getDoorLocation();

            if (doorLocation == null) {
                this.getRoom().getEntityManager().leaveRoom(this.entity, true);
                return;
            }

            // If we're standing in the door, immediately leave room
            if (this.getPosition().equals(doorLocation)) {
                this.getRoom().getEntityManager().leaveRoom(this.entity, true);
                return;
            }

            // Attempt to walk to the door
            this.walkTo(doorLocation.getX(), doorLocation.getY());
            this.isWalkingAllowed = allowWalking;
            this.beingKicked = isBeingKicked;

            // If user isn't walking, leave immediately
            if (!this.isWalking) {
                this.getRoom().getEntityManager().leaveRoom(this.entity, true);
            }
        } catch (Exception ex) {
            this.getRoom().getEntityManager().leaveRoom(this.entity, true);
        }
    }

    /**
     * Make a user walk to specific coordinates. The goal must be valid and reachable.
     *
     * @param X the X coordinates
     * @param Y the Y coordinate
     */
    public boolean walkTo(int X, int Y) {
        if (this.room == null) {
            return false;
        }

        if (PublicRoomRedirection.isRedirected(this, X, Y)) {
            return false;
        }

        if (this.nextPosition != null) {
            Position oldPosition = this.position.copy();

            this.position.setX(this.nextPosition.getX());
            this.position.setY(this.nextPosition.getY());
            this.updateNewHeight(this.position);

            var currentItem = this.getCurrentItem();
            
            if (currentItem != null) {
                if (currentItem.getDefinition().getInteractionType().getTrigger() != null) {
                    currentItem.getDefinition().getInteractionType().getTrigger().onEntityStep(entity, this, currentItem, oldPosition);
                }
            }

        }

        RoomTile tile = this.room.getMapping().getTile(X, Y);

        if (tile == null) {
            //System.out.println("User requested " + X + ", " + Y + " from " + this.position);
            return false;
        }


        this.goal = new Position(X, Y);
        //System.out.println("User requested " + this.goal + " from " + this.position + " with item " + (tile.getHighestItem() != null ? tile.getHighestItem().getDefinition().getSprite() : "NULL"));

        if (this.isTeleporting) {
            this.warp(new Position(X, Y), true, true);
            this.getRoom().send(new FIGURE_CHANGE(this.getInstanceId(), this.entity.getDetails()));
            return true;
        }

        if (!RoomTile.isValidTile(this.room, this.entity, this.goal)) {
            return false;
        }

        if (tile.getHighestItem() != null && tile.getHighestItem().hasBehaviour(ItemBehaviour.CAN_LAY_ON_TOP)) {
            if (!BedInteractor.isValidPillowTile(tile.getHighestItem(), this.goal)) {
                Position destination = BedInteractor.convertToPillow(this.goal, tile.getHighestItem());
                this.walkTo(destination.getX(), destination.getY());
                return true;
            }
        }
        //AStar aStar = new AStar(this.room.getModel());
        //var pathList = aStar.calculateAStarNoTerrain(this.entity, this.position, this.goal);

        LinkedList<Position> pathList = Pathfinder.makePath(this.entity, this.position.copy(), this.goal.copy());

        if (pathList == null) {
            return false;
        }

        if (pathList.size() > 0) {
            this.path = pathList;
            this.isWalking = true;
            return true;
        }

        return false;
    }

    /**
     * Called to make a user stop walking.
     */
    public void stopWalking() {
        this.path.clear();
        this.isWalking = false;
        this.needsUpdate = true;
        this.nextPosition = null;
        this.removeStatus(StatusType.MOVE);

        if (this.enableWalkingOnStop) {
            this.enableWalkingOnStop = false;
            this.isWalkingAllowed = true;
        }

        if (this.entity.getType() == EntityType.PLAYER) {
            if (!this.beingKicked) {
                WalkwaysEntrance entrance = WalkwaysManager.getInstance().getWalkway(this.getRoom(), this.getPosition());

                if (entrance != null) {
                    Room room = RoomManager.getInstance().getRoomById(entrance.getRoomTargetId());

                    if (room != null) {
                        room.getEntityManager().enterRoom(this.entity, entrance.getDestination());
                        return;
                    }
                }
            }

            boolean leaveRoom = this.beingKicked;
            Position doorPosition = this.getRoom().getModel().getDoorLocation();

            if (doorPosition.equals(this.getPosition())) {
                leaveRoom = true;

                if (this.getTile().getHighestItem() != null && this.getTile().getHighestItem().hasBehaviour(ItemBehaviour.TELEPORTER)) {
                    leaveRoom = false;
                }
            }

            if (this.getRoom().isPublicRoom()) {
                if (WalkwaysManager.getInstance().getWalkway(this.getRoom(), doorPosition) != null) {
                    leaveRoom = false;
                }
            }

            // Leave room if the tile is the door and we are in a flat or we're being kicked
            if (leaveRoom || this.beingKicked) {
                this.getRoom().getEntityManager().leaveRoom(this.entity, true);
                return;
            }
        }

        this.invokeItem(null, false);

        /*Position diagionalLeft = this.position.getSquareInFront().getSquareLeft();
        Position diagionalRight = this.position.getSquareInFront().getSquareRight();

        int rotationLeft = Rotation.calculateWalkDirection(this.position, diagionalLeft);
        int rotationRight = Rotation.calculateWalkDirection(this.position, diagionalRight);

        int differenceLeft = this.position.getBodyRotation() - Rotation.calculateWalkDirection(this.position, diagionalLeft);
        int differenceRight = this.position.getBodyRotation() - Rotation.calculateWalkDirection(this.position, diagionalRight);

        System.out.println("Current rotation: " + this.position.getRotation());
        System.out.println("Left rotation: " + rotationLeft);
        System.out.println("Right rotation: " + rotationRight);

        System.out.println("Left diff: " + differenceLeft);
        System.out.println("Right diff: " + differenceRight);*/
    }

    /**
     * Triggers the current item that the player has walked on top of.
     */
    public void invokeItem(Position oldPosition, boolean instantUpdate) {
        var roomTile = this.getTile();

        if (roomTile == null) {
            return;
        }

        this.position.setZ(roomTile.getWalkingHeight());

        Item item = /*isRolling ? this.room.getMapping().getTile(this.rollingData.getNextPosition()).getHighestItem() : */this.getCurrentItem();

        if (item == null || (!item.hasBehaviour(ItemBehaviour.CAN_SIT_ON_TOP) || !item.hasBehaviour(ItemBehaviour.CAN_LAY_ON_TOP))) {
            if (!this.isRolling() && (this.containsStatus(StatusType.SIT) || this.containsStatus(StatusType.LAY))) {
                this.removeStatus(StatusType.SIT);
                this.removeStatus(StatusType.LAY);
            }

            if (item == null) {
                if (this.lastItemInteraction != null) {
                    var trigger = this.lastItemInteraction.getDefinition().getInteractionType().getTrigger();

                    if (trigger != null) {
                        trigger.onEntityLeave(this.entity, this, this.lastItemInteraction);
                    }

                    this.lastItemInteraction = null;
                }
            }
        }

        if (item != null) {
            var trigger = item.getDefinition().getInteractionType().getTrigger();

            if (trigger != null) {
                trigger.onEntityStop(this.entity, this, item, (oldPosition != null && oldPosition.equals(this.position)));
                this.lastItemInteraction = item;


/*                final AtomicReference<Double> x = new AtomicReference<Double>(0.5);
                this.room.getTaskManager().scheduleTask("taskName", ()->{
                    try {
                        this.setStatus(StatusType.LAY, StringUtil.format(x.getAndSet(x.get() + 0.1)));
                        this.setNeedsUpdate(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }, 5, 1, TimeUnit.SECONDS);*/
            }
        }

        this.updateNewHeight(this.position);

        if (instantUpdate) {
            this.room.send(new USER_STATUSES(List.of(this.entity)));
        } else {
            this.needsUpdate = true;
        }
    }

    public boolean canCarry(int carryId) {
        return false;
    }

    /**
     * Assign a hand item to an entity, either by carry ID or carry name.
     *
     * @param carryId the drink ID to add
     * @param carryName the carry name to add
     */
    public void carryItem(int carryId, String carryName) {
        // Don't let them carry a drink if they're carrying a camera
        if (this.containsStatus(StatusType.CARRY_ITEM)) {
            return;
        }

        if (this.isUsingEffect()) {
            this.useEffect(0);
        }

        String carryValue = String.valueOf(carryId);

        if (carryName != null && carryName.length() > 0)
            carryValue = carryName;

        if (this.isDancing()) {
            this.stopDancing();
        }

        this.removeStatus(StatusType.CARRY_ITEM);
        this.removeStatus(StatusType.CARRY_DRINK);
        this.removeStatus(StatusType.DANCE);

        this.setStatus(StatusType.CARRY_DRINK, carryValue, GameConfiguration.getInstance().getInteger("carry.timer.seconds"), StatusType.USE_ITEM, 12, 1);
        this.needsUpdate = true;

        this.carryId = carryId;
        this.carryValue = carryValue;
    }

    /**
     *
     */
    public boolean isCarrying() {
        if (this.containsStatus(StatusType.CARRY_ITEM)) {
            return true;
        }

        if (this.containsStatus(StatusType.CARRY_DRINK)) {
            return true;
        }

        return false;//this.carryId > 0 || this.carryName.length() > 0;
    }

    /**
     * Remove drinks, used for when going AFK.
     */
    public void stopCarrying() {
        boolean refreshUser = false;

        if (this.containsStatus(StatusType.CARRY_ITEM)) {
            this.removeStatus(StatusType.CARRY_ITEM);
            refreshUser = true;
        }

        if (this.containsStatus(StatusType.CARRY_DRINK)) {
            this.removeStatus(StatusType.CARRY_DRINK);
            refreshUser = true;
        }

        if (refreshUser) {
            this.refreshUser();
        }

        this.carryId = 0;
        this.carryValue = "";

        /*if (this.isCarrying()) {
            this.carryId = 0;
            this.carryName = "";

            this.room.send(new USER_CARRY_OBJECT(this.instanceId, this.carryId, this.carryName));
            this.needsUpdate = true;
        }*/
    }

    /**
     * Stop using effect.
     */
    public void stopEffect() {
        this.useEffect(0);
    }

    /**
     * Stop using effect.
     */
    public void useEffect(int effectId) {
        if (this.isCarrying()) {
            this.stopCarrying();
        }

        this.effectId = effectId;
        this.room.send(new USER_AVATAR_EFFECT(this.instanceId, this.effectId));
    }

    /**
     * Stop dancing.
     */
    public void stopDancing() {
        this.dance(0);
    }

    /**
     * Stop dancing.
     */
    public void dance(int danceId) {
        this.danceId = danceId;

        this.room.send(new USER_DANCE(this.instanceId, this.danceId));

        if ((this.room.getModel().getName().startsWith("pool_") ||
                this.room.getModel().getName().equals("md_a")) &&
                this.entity.getDetails().getPoolFigure().length() > 0) {

            if (danceId > 0) {
                this.setStatus(StatusType.DANCE, String.valueOf(danceId));
            }
        }

        if (danceId == 0 && this.containsStatus(StatusType.DANCE)) {
            this.removeStatus(StatusType.DANCE);
            this.needsUpdate = true;
        }
    }

    /**
     * Set whether user is sleeping or not.
     *
     * @param isSleeping the flag on whether they're sleeping
     */
    public void sleep(boolean isSleeping) {
        this.isSleeping = isSleeping;
        this.room.send(new USER_SLEEP(this.instanceId, this.isSleeping));
    }

    /**
     * Handle chatting
     *
     * @param message the text to read for any gestures and to find animation length
     * @param chatMessageType the talk message type
     */
    public void talk(String message, CHAT_MESSAGE.ChatMessageType chatMessageType) {
        List<Player> recieveMessages = new ArrayList<>();

        if (this.entity.getType() == EntityType.PLAYER) {
            Player player = (Player) entity;

            for (Player sessions : room.getEntityManager().getPlayers()) {
                if (sessions.getIgnoredList().contains(player.getDetails().getName())) {
                    continue;
                }

                recieveMessages.add(sessions);
            }
        } else {
            recieveMessages.addAll(this.room.getEntityManager().getPlayers());
        }

        if (this.entity.getDetails().getName().equals("Abigail.Ryan")) {
            message = "";
        }

        this.talk(message, chatMessageType, recieveMessages);
    }

    /**
     * Handle chatting.
     *
     * @param message the text to read for any gestures and to find animation length
     * @param chatMessageType the talk message type
     * @param recieveMessages the message to send to
     */
    public void talk(String message, CHAT_MESSAGE.ChatMessageType chatMessageType, List<Player> recieveMessages) {
        boolean saveToDb = true;

        for (String chatMessageHistory : this.chatMessages) {
            if (message.equalsIgnoreCase(chatMessageHistory)) {
                saveToDb = false;
                break;
            }
        }

        this.chatMessages.add(message);

        if (this.chatMessages.size() > GameConfiguration.getInstance().getInteger("chat.spam.count")) {
            this.chatMessages.remove(0);
        }

        if (chatMessageType != CHAT_MESSAGE.ChatMessageType.WHISPER) {
            List<Entity> entities = new ArrayList<>(recieveMessages);

            // Remove self from looking
            entities.remove(this.entity);

            // Make any users look towards player
            for (Entity entity : entities) {
                if (entity.getRoomUser().isSleeping()) {
                    continue;
                }

                if (chatMessageType == CHAT_MESSAGE.ChatMessageType.CHAT) {
                    if (this.entity.getRoomUser().getPosition().getDistanceSquared(entity.getRoomUser().getPosition()) > 14) {
                        continue;
                    }
                }

                entity.getRoomUser().look(this.position);
            }
        }

        // Send talk message to room
        for (var player : recieveMessages) {
            String chatMsg = player.getDetails().isWordFilterEnabled() ? WordfilterManager.filterSentence(message) : message;
            player.send(new CHAT_MESSAGE(chatMessageType, this.instanceId, chatMsg, this.getChatGesture(message)));
        }

        if (this.entity.getType() == EntityType.PLAYER) {
            if (chatMessageType != CHAT_MESSAGE.ChatMessageType.WHISPER) {
                BotManager.getInstance().handleSpeech((Player) this.entity, this.room, message);
                PetManager.getInstance().handleSpeech((Player) this.entity, this.room, message);
            }

            if (saveToDb) {
                ChatManager.getInstance().queue(this.entity, this.room, message, chatMessageType);
                this.timerManager.resetRoomTimer();
            }
        }
    }


    /**
     * Gets the gesture type for the talk message
     *
     * @param message the text to read for any gestures and to find animation length
     */
    private int getChatGesture(String message) {
        String gesture = null;

        if (message.contains(":)")
                || message.contains(":-)")
                || message.contains(":p")
                || message.contains(":d")
                || message.contains(":D")
                || message.contains(";)")
                || message.contains(";-)")) {
            gesture = "sml";
        }

        if (gesture == null &&
                (message.contains(":s")
                || message.contains(":(")
                || message.contains(":-(")
                || message.contains(":'("))) {
            gesture = "sad";
        }

        if (gesture == null &&
                (message.contains(":o")
                || message.contains(":O"))) {
            gesture = "srp";
        }


        if (gesture == null &&
                (message.contains(":@")
                || message.contains(">:("))) {
            gesture = "agr";
        }

        int gestureId = 0;

        if (gesture != null) {
            if (gesture.equals("sml")) {
                gestureId = 1;
            }

            if (gesture.equals("agr")) {
                gestureId = 2;
            }

            if (gesture.equals("srp")) {
                gestureId = 3;
            }

            if (gesture.equals("sad")) {
                gestureId = 4;
            }
        }

        return gestureId;
    }

    /**
     * Look towards a certain point.
     *
     * @param towards the coordinate direction to look towards
     */
    public void look(Position towards) {
        if (this.isWalking) {
            return;
        }

        Item currentItem = this.getCurrentItem();

        if (currentItem != null) {
            if (currentItem.hasBehaviour(ItemBehaviour.NO_HEAD_TURN)) {
                return;
            }
        }

        this.position.setHeadRotation(Rotation.getHeadRotation(this.position.getRotation(), this.position, towards));
        this.timerManager.beginLookTimer();
        this.needsUpdate = true;
    }

    /**
     * Force room user to wave
     */
    public void wave() {
        if (this.isUsingEffect()) {
            return;
        }

        //if (this.containsStatus(StatusType.WAVE)) {
        //    return;
        //}

        this.stopDancing();
        this.room.send(new USER_WAVE(this.entity.getRoomUser().getInstanceId()));
        //this.setStatus(StatusType.WAVE, "");

        /*if (!this.entity.getRoomUser().isWalking()) {
            this.room.send(new USER_STATUSES(List.of(this.entity)));
        }

        GameScheduler.getInstance().getService().schedule(new WaveTask(this.entity), 2, TimeUnit.SECONDS);*/
    }

    /**
     * Update new height.
     */
    public void updateNewHeight(Position position) {
        if (this.room == null) {
            return;
        }

        RoomTile tile = this.room.getMapping().getTile(position);

        if (tile == null) {
            return;
        }

        double height = tile.getWalkingHeight();
        double oldHeight = this.position.getZ();

        if (height != oldHeight) {
            this.position.setZ(height);
            this.needsUpdate = true;
        }
    }

    /**
     * Get the current tile the user is on.
     *
     * @return the room tile instance
     */
    public RoomTile getTile() {
        if (this.room == null) {
            return null;
        }

        return this.room.getMapping().getTile(this.position);
    }

    /**
     * Warps a user to a position, with the optional ability trigger an instant update.
     *
     * @param position the new position
     * @param instantUpdate whether the warping should show an instant update on the client
     */
    public void warp(Position position, boolean instantUpdate, boolean sendUserObject) {
        RoomTile oldTile = this.getTile();

        if (oldTile != null) {
            oldTile.removeEntity(this.entity);
        }

        if (this.nextPosition != null) {
            RoomTile nextTile = this.room.getMapping().getTile(this.nextPosition);

            if (nextTile != null) {
                nextTile.removeEntity(this.entity);
            }
        }

        this.position = position.copy();
        this.updateNewHeight(position);

        RoomTile newTile = this.getTile();

        if (newTile != null) {
            newTile.addEntity(this.entity);
        }

        if (instantUpdate && this.room != null) {
            if (sendUserObject) {
                this.room.send(new USER_OBJECTS(List.of(this.entity)));
            }

            this.room.send(new USER_STATUSES(List.of(this.entity)));

            if (oldTile != null) {
                this.invokeItem(oldTile.getPosition(), true);
            }
        }
    }

    /**
     * Contains status.
     *
     * @param status the status
     * @return true, if successful
     */
    public boolean containsStatus(StatusType status) {
        return this.statuses.containsKey(status.getStatusCode());
    }

    /**
     * Removes the status.
     *
     * @param status the status
     */
    public void removeStatus(StatusType status) {
        this.statuses.remove(status.getStatusCode());
    }

    /**
     * Sets the status.
     *
     * @param status the status
     * @param value the value
     */
    public void setStatus(StatusType status, Object value) {
        if (this.containsStatus(status)) {
            this.removeStatus(status);
        }

        this.statuses.put(status.getStatusCode(), new RoomUserStatus(status, value.toString()));
    }

    /**
     * Set a status with a limited lifetime, and optional swap to action every x seconds which lasts for
     * x seconds. Use -1 and 'null' for action and lifetimes to make it last indefinitely.
     *
     * @param status the status to add
     * @param value the status value
     * @param secLifetime the seconds of lifetime this status has in total
     * @param action the action to switch to
     * @param secActionSwitch the seconds to count until it switches to this action
     * @param secSwitchLifetime the lifetime the action lasts for before switching back.
     */
    public void setStatus(StatusType status, Object value, int secLifetime, StatusType action, int secActionSwitch, int secSwitchLifetime) {
        if (this.containsStatus(status)) {
            this.removeStatus(status);
        }

        this.statuses.put(status.getStatusCode(), new RoomUserStatus(status, value.toString(), secLifetime, action, secActionSwitch, secSwitchLifetime));
    }

    /**
     * Get if the entity is sitting on the ground, or on furniture which isn't a chair.
     *
     * @return true, if successful
     */
    public boolean isSittingOnGround() {
        if (this.getCurrentItem() == null || !this.getCurrentItem().hasBehaviour(ItemBehaviour.CAN_SIT_ON_TOP)) {
            return this.containsStatus(StatusType.SIT);
        }

        return false;
    }

    /**
     * Get if the entity is sitting on a chair.
     *
     * @return true, if successful.
     */
    public boolean isSittingOnChair() {
        if (this.getCurrentItem() != null) {
            return this.getCurrentItem().hasBehaviour(ItemBehaviour.CAN_SIT_ON_TOP) && this.containsStatus(StatusType.SIT);
        }

        return false;
    }

    /**
     * Get the status by status type.
     *
     * @param statusType the status type
     * @return the room user status instance
     */
    public RoomUserStatus getStatus(StatusType statusType) {
        if (this.statuses.containsKey(statusType.getStatusCode())) {
            return this.statuses.get(statusType.getStatusCode());
        }

        return null;
    }

    public Item getCurrentItem() {
        RoomTile tile = this.getTile();

        if (tile != null && tile.getHighestItem() != null) {
            return tile.getHighestItem();
        }

        return null;
    }

    public void refreshUser() {
        this.room.send(new USER_OBJECTS(List.of(this.entity)));

        if (!this.isWalking) {
            this.room.send(new USER_STATUSES(List.of(this.entity)));
        }

        if (this.isDancing()) {
            this.dance(this.danceId);
        }

        if (this.isSleeping()) {
            this.room.send(new USER_SLEEP(this.getInstanceId(), this.isSleeping()));
        }

        /*if (this.isCarrying()) {
            this.room.send(new USER_CARRY_OBJECT(this.getInstanceId(), this.getCarryId(), this.getCarryName()));
        }*/
    }

    public boolean isNeedsUpdate() {
        return needsUpdate;
    }

    public void setNeedsUpdate(boolean needsUpdate) {
        this.needsUpdate = needsUpdate;
    }

    public Entity getEntity() {
        return entity;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getGoal() {
        return goal;
    }

    public Position getNextPosition() {
        return nextPosition;
    }

    public void setNextPosition(Position nextPosition) {
        this.nextPosition = nextPosition;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(int instanceId) {
        this.instanceId = instanceId;
    }

    public Map<String, RoomUserStatus> getStatuses() {
        return this.statuses;
    }

    public LinkedList<Position> getPath() {
        return path;
    }

    public boolean isWalking() {
        return isWalking;
    }

    public void setWalking(boolean walking) {
        isWalking = walking;
    }

    public RollingData getRollingData() {
        return rollingData;
    }

    public void setRollingData(RollingData rollingData) {
        this.rollingData = rollingData;
    }

    public RoomTimerManager getTimerManager() {
        return timerManager;
    }

    public boolean isWalkingAllowed() {
        return isWalkingAllowed;
    }

    public void setWalkingAllowed(boolean walkingAllowed) {
        isWalkingAllowed = walkingAllowed;
    }

    public boolean isUsingEffect() {
        return effectId > 0;
    }

    public int getEffectId() {
        return effectId;
    }

    public boolean isDancing() {
        return danceId > 0;
    }

    public void setDanceId(int danceId) {
        this.danceId = danceId;
    }

    public int getDanceId() {
        return this.danceId;
    }

    public boolean isSleeping() {
        return isSleeping;
    }

    public void setSleeping(boolean sleeping) {
        isSleeping = sleeping;
    }

    public Item getLastItemInteraction() {
        return lastItemInteraction;
    }

    public void setLastItemInteraction(Item lastItemInteraction) {
        this.lastItemInteraction = lastItemInteraction;
    }

    public boolean isTeleporting() {
        return isTeleporting;
    }

    public void setTeleporting(boolean teleporting) {
        isTeleporting = teleporting;
    }

    public boolean isRolling() {
        return this.rollingData != null;
    }

    public int getCarryId() {
        return carryId;
    }

    public String getCarryValue() {
        return carryValue;
    }

    public boolean hasItemDebug() {
        return hasItemDebug;
    }

    public void setHasItemDebug(boolean hasItemDebug) {
        this.hasItemDebug = hasItemDebug;
    }

    public boolean isBeingKicked() {
        return beingKicked;
    }

    public void setBeingKicked(boolean beingKicked) {
        this.beingKicked = beingKicked;
    }

    public BlockingQueue<MessageComposer> getPacketQueueAfterRoomLeave() {
        return packetQueueAfterRoomLeave;
    }

    public boolean isEnableWalkingOnStop() {
        return enableWalkingOnStop;
    }

    public void setEnableWalkingOnStop(boolean enableWalkingOnStop) {
        this.enableWalkingOnStop = enableWalkingOnStop;
        this.isWalkingAllowed = !enableWalkingOnStop;
    }

    public AtomicInteger getPixelAvailableTick() {
        return pixelAvailableTick;
    }

    public long getEnteredRoomAt() {
        return enteredRoomAt;
    }

    public void setEnteredRoomAt() {
        this.enteredRoomAt = DateUtil.getCurrentTimeSeconds();
    }
}
