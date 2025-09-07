package org.alexdev.havana.game.item;

import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.dao.mysql.PhotoDao;
import org.alexdev.havana.game.GameScheduler;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.item.base.ItemDefinition;
import org.alexdev.havana.game.item.extradata.ExtraDataManager;
import org.alexdev.havana.game.item.extradata.types.TrophyExtraData;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.item.interactors.types.TeleportInteractor;
import org.alexdev.havana.game.item.roller.RollingData;
import org.alexdev.havana.game.pathfinder.AffectedTile;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.game.room.mapping.RoomTile;
import org.alexdev.havana.game.room.mapping.RoomTileState;
import org.alexdev.havana.messages.outgoing.rooms.items.SHOWPROGRAM;
import org.alexdev.havana.messages.outgoing.rooms.items.STUFFDATAUPDATE;
import org.alexdev.havana.server.netty.streams.NettyResponse;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.StringUtil;
import org.alexdev.havana.util.config.GameConfiguration;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class Item {
    public static final String DEFAULT_ROOMDIMMER_CUSTOM_DATA = "1,1,1,#000000,255";
    public static final String PRESENT_DELIMETER = "|";

    private int id;
    private long databaseId;
    private int orderId;
    private int ownerId;
    private int roomId;

    private ItemDefinition definition;
    private int definitionId;

    private Position position;
    private String wallPosition;
    private String customData;
    private String currentProgram;
    private String currentProgramValue;

    private boolean requiresUpdate;
    private boolean isCurrentRollBlocked;
    private RollingData rollingData;
    private boolean isHidden;
    private boolean isInTrade;
    private long expireTime;
    private long createdAt;
    private long lastPlacedTime;
    private boolean isDeleted;
    private String trophyData;
    private Room temporaryRoom;
    private Position teleportTo;
    private Position swimTo;

    public Item() {
        this.id = 0;
        this.definition = new ItemDefinition();
        this.position = new Position();
        this.customData = "";
        this.wallPosition = "";
        this.currentProgram = "";
        this.currentProgramValue = "";
        this.requiresUpdate = false;
        this.rollingData = null;
        this.expireTime = -1;
        this.createdAt = DateUtil.getCurrentTimeSeconds();
        this.temporaryRoom = null;
    }

    public void fill(long id, int orderId, int ownerId, int roomId, int definitionId, int X, int Y, double Z, int rotation, String wallPosition, String customData, boolean isHidden, boolean isInTrade, long expireTime, long createdAt) {
        this.databaseId = id;
        this.orderId = orderId;
        this.ownerId = ownerId;
        this.roomId = roomId;
        this.definition = null;
        this.definitionId = definitionId;
        this.position = new Position(X, Y, Z, rotation, rotation);
        this.wallPosition = wallPosition;
        this.customData = customData;
        this.rollingData = null;
        this.isHidden = isHidden;
        this.isInTrade = isInTrade;
        this.setDefinitionId(this.definitionId);
        this.expireTime = expireTime;
        this.createdAt = createdAt;

        //performPhotoDateCheck();
    }

    /**
     * Temporary method to fix camera so it shows / instead of - in camera photo.
     */
    private void performPhotoDateCheck() {
        if (!this.getDefinition().hasBehaviour(ItemBehaviour.PHOTO)) {
            return;
        }

        if (!this.customData.contains(Character.toString((char) 13))) {
            return;
        }

        String[] parts = this.customData.split(Character.toString((char) 13));
        String photoMessage = this.customData.substring(parts[0].length() + 1);
        try {
            if (parts[0].contains("-")) {
                Photo photo = PhotoDao.getPhoto(this.databaseId);
                this.customData = DateUtil.getDate(photo.getTime(), DateUtil.CAMERA_DATE) + (char)13 + photoMessage;
                ItemDao.updateItem(this);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Broadcast item program to current room, used for the pool lift, booth, pool ladders, etc
     * for special effects like splashing, closing/open curtains, etc.
     *
     * @param value the new program value to show
     */
    public void showProgram(String value) {
        if (value != null) {
            this.currentProgramValue = value;
        }

        Room room = this.getRoom();

        if (room != null) {
            if (StringUtil.isNullOrEmpty(this.currentProgramValue)) {
                room.send(new SHOWPROGRAM(new String[]{this.currentProgram }));
            } else {
                room.send(new SHOWPROGRAM(new String[]{this.currentProgram, this.currentProgramValue}));
            }
        }
    }

    /**
     * Update user statuses on items with their old position and new position.
     * The old position is never null if the item is moved.
     *
     * @param oldPosition the old position of the item
     */
    public void updateEntities(Position oldPosition) {
        if (this.hasBehaviour(ItemBehaviour.WALL_ITEM)) {
            return;
        }

        if (this.getRoom() == null) {
            return;
        }

        List<Entity> entitiesToUpdate = new ArrayList<>();

        if (oldPosition != null) {
            for (Position position : AffectedTile.getAffectedTiles(this, oldPosition.getX(), oldPosition.getY(), oldPosition.getRotation())) {
                RoomTile tile = this.getRoom().getMapping().getTile(position);

                if (tile == null) {
                    continue;
                }

                entitiesToUpdate.addAll(tile.getEntireEntities());
            }
        }

        for (Position position : AffectedTile.getAffectedTiles(this)) {
            RoomTile tile = this.getRoom().getMapping().getTile(position);

            if (tile == null) {
                continue;
            }

            /*var entities = tile.getEntities();

            for (Entity entity : entities) {
                var currentItem = entity.getRoomUser().getCurrentItem();

                if (currentItem != null) {
                    RoomTile tile1 = this.getRoom().getMapping().getTile(entity.getRoomUser().getPosition());

                    if (tile1.getHighestItem() != currentItem) {
                        if (currentItem.getDefinition().getInteractionType().getTrigger() != null) {
                            currentItem.getDefinition().getInteractionType().getTrigger().onEntityStop(entity, entity.getRoomUser(), currentItem);
                        }
                    }
                }
            }*/

            entitiesToUpdate.addAll(tile.getEntireEntities());
        }

        for (Entity entity : entitiesToUpdate) {
            entity.getRoomUser().invokeItem(oldPosition, true);
        }
    }

    /**
     * Get the total height, which is the height of the item plus stack size.
     *
     * @return the total height
     */
    public double getTotalHeight() {
        double z = 0;

        if (this.getDefinition().getTopHeight() < 0) {
            z = this.position.getZ() + ItemDefinition.DEFAULT_TOP_HEIGHT;
        } else {
            if (this.getDefinition().getInteractionType() == InteractionType.MULTI_HEIGHT) {
                int currentState = (this.customData.length() > 0 && StringUtils.isNumeric(this.customData)) ? Integer.parseInt(this.customData) : 0;

                if (this.getDefinition().getHeights().isEmpty()) {
                    z += this.getDefinition().getTopHeight();
                } else{
                    if (currentState + 1> this.getDefinition().getHeights().size()) {
                        currentState = 0;
                        this.customData = "0";
                    }

                    z += this.getDefinition().getHeights().get(currentState) + this.getDefinition().getTopHeight();
                }


            } else {
                z = this.getDefinition().getTopHeight();
            }
        }


        return this.position.getZ() + z;
    }

    /**
     * Get whether or not the item is walkable.
     *
     * @return true, if successful.
     */
    public boolean isWalkable(Entity entity, Position selectedPosition) {
        if (entity != null) {
            if (entity.getType() == EntityType.PLAYER) {
                Player player = (Player) entity;

                if (player.getNetwork().isFlashConnection()) {
                    if (this.getDefinition().getInteractionType() == InteractionType.IDOL_SCOREBOARD) {
                        return false;
                    }

                    if (this.getDefinition().getInteractionType() == InteractionType.IDOL_VOTE_CHAIR) {
                        return false;
                    }
                }
            }
        }

        if (entity == null && this.hasBehaviour(ItemBehaviour.CAN_NOT_STACK_ON_TOP))
            return false;

        if (this.getDefinition().getSprite().equals("poolLift")) {
            return this.currentProgramValue.equals("open");
        }

        if (this.getDefinition().getSprite().equals("poolBooth")) {
            return this.currentProgramValue.equals("open");
        }

        if (this.hasBehaviour(ItemBehaviour.CAN_SIT_ON_TOP)) {
            return true;
        }

        if (this.hasBehaviour(ItemBehaviour.CAN_LAY_ON_TOP)) {
            return true;
        }

        if (this.hasBehaviour(ItemBehaviour.CAN_STAND_ON_TOP)) {
            return true;
        }

        if (this.hasBehaviour(ItemBehaviour.TELEPORTER)) {
            return this.customData.equals(TeleportInteractor.TELEPORTER_OPEN) || this.hasBehaviour(ItemBehaviour.DOOR_TELEPORTER);
        }

        if (this.hasBehaviour(ItemBehaviour.GATE) || this.hasBehaviour(ItemBehaviour.ONE_WAY_GATE)) {
            return this.isGateOpen();
        }

        if (this.hasBehaviour(ItemBehaviour.SOLID_SINGLE_TILE)) {
            return !(this.position.equals(selectedPosition));
        }
        
        return false;
    }

    public boolean isGateOpen() {
        if (this.hasBehaviour(ItemBehaviour.GATE) || this.hasBehaviour(ItemBehaviour.ONE_WAY_GATE)) {
            return this.customData.equals("1");
        }

        return false;
    }

    /**
     * Send status update of the item.
     */
    public void updateStatus() {
        Room room = this.getRoom();

        if (room != null) {
            room.send(new STUFFDATAUPDATE(this));
        }
    }

    /**
     * Queue item saving.
     */
    public void save() {
        GameScheduler.getInstance().queueSaveItem(this);
    }

    /**
     * Queue item deletion.
     */
    public void delete() {
        this.isDeleted = true;
        GameScheduler.getInstance().queueDeleteItem(this.getDatabaseId());
    }

    /**
     * Serialise item function for item handling packets.
     *
     * @param response the response to serialise to
     */
    public void serialise(NettyResponse response) {
        ItemDefinition definition = this.getDefinition();

        if (definition.hasBehaviour(ItemBehaviour.PUBLIC_SPACE_OBJECT)) {
            boolean hasDimensions = this.hasBehaviour(ItemBehaviour.EXTRA_PARAMETER);

            response.writeInt(hasDimensions ? 1 : 0);
            response.writeString(this.customData);
            response.writeString(definition.getSprite());
            response.writeInt(this.position.getX());
            response.writeInt(this.position.getY());
            response.writeInt((int) this.position.getZ());

            if (!hasDimensions) {
                response.writeInt(this.position.getRotation());
            } else {
                response.writeInt(this.getDefinition().getLength());
                response.writeInt(this.getDefinition().getWidth());
            }
        } else {
            if (this.hasBehaviour(ItemBehaviour.WALL_ITEM)) {
                response.writeString(this.id);
                response.writeInt(definition.getSpriteId());
                response.writeString(this.wallPosition);

                if (this.hasBehaviour(ItemBehaviour.POST_IT)) {
                    response.writeString(this.customData.substring(0, 6)); // Only show post-it colour
                } else {
                    response.writeString(this.customData);
                }
            } else {
                response.writeInt(this.id);
                response.writeInt(definition.getSpriteId());
                response.writeInt(this.position.getX());
                response.writeInt(this.position.getY());
                response.writeInt(this.position.getRotation());
                response.writeString(StringUtil.format(this.position.getZ()));
                response.writeInt(this.hasBehaviour(ItemBehaviour.ROLLER) ? 2 : 0); // Required 2 for rollers to enable animation when rollers are used!

                if (this.hasBehaviour(ItemBehaviour.PRESENT)) {
                    String[] presentData = this.customData.split(Pattern.quote(Item.PRESENT_DELIMETER));
                    response.writeString("!" + presentData[2]);
                } else if (this.hasBehaviour(ItemBehaviour.ECO_BOX)) {
                    response.writeString(DateUtil.getDate(this.createdAt, "dd-MM-yyyy"));
                } else if (this.hasBehaviour(ItemBehaviour.REDEEMABLE)) {
                    response.writeString(definition.getSprite().split("_")[1]);
                } else if (this.hasBehaviour(ItemBehaviour.PRIZE_TROPHY)) {
                    if (this.trophyData == null) {
                        TrophyExtraData trophyExtraData = null;

                        try {
                            trophyExtraData = ExtraDataManager.getJsonData(this, TrophyExtraData.class);
                        } catch (Exception ex) {

                        }

                        if (trophyExtraData == null)
                            trophyExtraData = new TrophyExtraData(1, "", 0);

                        var playerData = PlayerManager.getInstance().getPlayerData(trophyExtraData.getUserId());

                        if (playerData == null) {
                            playerData = new PlayerDetails();
                            playerData.fill(1, "null", "", "", "M");
                        }

                        this.trophyData = "";
                        this.trophyData += playerData.getName();
                        this.trophyData += (char) 9;
                        this.trophyData += DateUtil.getDate(trophyExtraData.getDate(), DateUtil.SHORT_DATE);
                        this.trophyData += (char) 9;
                        this.trophyData += StringUtil.filterInput(trophyExtraData.getMessage(), true);
                    }

                    response.writeString(trophyData);
                } else {
                    response.writeString(this.customData);
                }

                response.writeInt(this.expireTime > -1 ? (int) TimeUnit.SECONDS.toMinutes(this.expireTime - DateUtil.getCurrentTimeSeconds()) : -1);

                if (definition.getSpriteId() < 0) {
                    response.writeString(definition.getSprite());
                }
            }
        }
    }

    /**
     * Check if the move is valid before moving an item. Will prevent long
     * furniture from being on top of rollers, will prevent placing rollers on top of other rollers.
     * Will prevent items being placed on closed tile states.
     *
     * @param room the room to check inside
     * @param x the new x to check
     * @param y the new y to check
     * @param rotation the new rotation to check
     * @return true, if successful
     */
    public boolean isValidMove(Item item, Room room, int x, int y, int rotation) {
        RoomTile tile = room.getMapping().getTile(x, y);

        if (tile == null) {
            return false;
        }

        boolean isRotation = (item.getPosition().getRotation() != rotation) && (new Position(x, y).equals(item.getPosition())
                || (item.getRollingData() != null && new Position(x, y).equals(item.getRollingData().getNextPosition()))
                || (item.getRollingData() != null && new Position(x, y).equals(item.getRollingData().getFromPosition())));

        if (isRotation) {
            if (item.getRollingData() != null) {
                return false; // Don't allow rotating items when they're rolling
            }

            if (item.getDefinition().getLength() <= 1 && item.getDefinition().getWidth() <= 1) {
                return true;
            }
        }

        for (Position position : AffectedTile.getAffectedTiles(this, x, y, rotation)) {
            tile = room.getMapping().getTile(position);

            if (tile == null) {
                return false;
            }

            if (room.getModel().getTileState(position.getX(), position.getY()) == RoomTileState.CLOSED) {
                return false;
            }

            if ((tile.getWalkingHeight() + item.getDefinition().getPositiveTopHeight()) > GameConfiguration.getInstance().getInteger("stack.height.limit")) {
                return false;
            }

            if (!isRotation/* && !this.hasBehaviour(ItemBehaviour.CAN_SIT_ON_TOP) && !this.hasBehaviour(ItemBehaviour.CAN_LAY_ON_TOP)*/)
            {
                if (tile.getEntireEntities().size() > 0)
                    return false;
            }

            Item highestItem = tile.getHighestItem();

            if (highestItem != null && highestItem.getVirtualId() != item.getVirtualId()) {
                if (!this.canPlaceOnTop(item, highestItem, new Position(x, y))) {
                    return false;
                }
            }

            for (Item tileItem : tile.getItems()) {
                if (tileItem.getVirtualId() == item.getVirtualId()) {
                    continue;
                }

                if (!this.canPlaceOnTop(item, tileItem, new Position(x, y))) {
                    return false;
                }

                if (tileItem.hasBehaviour(ItemBehaviour.ROLLER)) {
                    if (this.hasBehaviour(ItemBehaviour.ROLLER)) {
                        return false; // Can't place rollers on top of rollers
                    }

                    if ((this.getDefinition().getLength() > 1 || this.getDefinition().getWidth() > 1) && (/*this.hasBehaviour(ItemBehaviour.CAN_SIT_ON_TOP) || */this.hasBehaviour(ItemBehaviour.CAN_LAY_ON_TOP))) {
                        return false; // Chair or bed is too big to place on rollers.
                    }
                }
            }
        }


        return true;
    }

    /**
     * Get if placing an item on top of another item is allowed.
     * @param item the item to place
     * @param tileItem the item to check if they're allowed to place on top of
     * @return true, if successful
     */
    private boolean canPlaceOnTop(Item item, Item tileItem, Position targetTile) {
        if (tileItem.hasBehaviour(ItemBehaviour.CAN_NOT_STACK_ON_TOP)) {
            return false;
        }

        // Don't allow putting rollers on top of stackable objects
        if (item.hasBehaviour(ItemBehaviour.ROLLER) && tileItem.hasBehaviour(ItemBehaviour.CAN_STACK_ON_TOP) && !tileItem.hasBehaviour(ItemBehaviour.PLACE_ROLLER_ON_TOP)) {
            if (tileItem.getDefinition().getTopHeight() >= 0.1) {
                return false;
            }
        }

        // If the item is rolling, we can place on the square
        if (tileItem.rollingData != null) {
            return true;
        }

        // Can't place items on solid objects
        if ((tileItem.hasBehaviour(ItemBehaviour.SOLID) || tileItem.hasBehaviour(ItemBehaviour.SOLID_SINGLE_TILE)) && !tileItem.hasBehaviour(ItemBehaviour.CAN_STACK_ON_TOP)) {
            return false;
        }

        // Can't place items on objects where if only the first tile is solid and the item is placed on the furni BUT NOT placed on the solid tile
        if (tileItem.hasBehaviour(ItemBehaviour.SOLID_SINGLE_TILE) && tileItem.hasBehaviour(ItemBehaviour.CAN_STACK_ON_TOP)) {
            return tileItem.getPosition().equals(targetTile);
        }

        if (tileItem.hasBehaviour(ItemBehaviour.ONE_WAY_GATE)) {
            return false;
        }

        // Can't place gates on solid rollers
        /*if (tileItem.hasBehaviour(ItemBehaviour.ROLLER) && (item.hasBehaviour(ItemBehaviour.GATE) || this.hasBehaviour(ItemBehaviour.ONE_WAY_GATE))) {
            return false;
        }*/

        // Can't place items on sittable items
        if (tileItem.hasBehaviour(ItemBehaviour.CAN_SIT_ON_TOP)) {
            return false;
        }

        // Can't place item on layable items
        if (tileItem.hasBehaviour(ItemBehaviour.CAN_LAY_ON_TOP)) {
            return false;
        }

        return true;
    }

    /**
     * Get the room tile this item is on.
     *
     * @return the room tile, else null
     */
    public RoomTile getTile() {
        Room room = this.getRoom();

        if (room != null) {
            return this.getRoom().getMapping().getTile(this.position);
        }

        return null;
    }

    /**
     * Get if the item has a type of behaviour.
     *
     * @param behaviour the behaviour to check
     * @return true, if successful
     */
    public boolean hasBehaviour(ItemBehaviour behaviour) {
        return this.getDefinition().hasBehaviour(behaviour);
    }

    /**
     * Gets the definition instance, if there's a instance attached, it will return that instead.
     *
     * @return the definition
     */
    public ItemDefinition getDefinition() {
        if (this.definition != null) { // Used for public room items
            return this.definition;
        }

        // Always use ItemManager to retrieve private flat definitions
        return ItemManager.getInstance().getDefinition(this.definitionId);
    }

    /**
     * Sets the definition id used by the database and removes the instance.
     *
     * @param definitionId the definition id
     */
    public void setDefinitionId(int definitionId) {
        this.definition = null;
        this.definitionId = definitionId;
    }

    /**
     * Get the virtual id of the item.
     *
     * @return the virtual id
     */
    public int getVirtualId() {
        return id;
    }

    /**
     * Sets the virtual id.
     */
    public void assignVirtualId() {
        this.id = ItemManager.getInstance().getVirtualIdCounter().incrementAndGet();
        //System.out.println("Assigned virtual id " + this.id + " to item with db id " + this.databaseId);
    }

    /**
     * Sets the virtual id to a specific number
     */
    public void setVirtualId(int id) {
        this.id = id;
    }

    /**
     * Get the owner of this item.
     *
     * @return the owner
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * Set the owner id of this item.
     *
     * @param ownerId the owner id
     */
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Get the item position.
     *
     * @return the item position
     */
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getWallPosition() {
        return wallPosition;
    }

    public void setWallPosition(String wallPosition) {
        this.wallPosition = wallPosition;
    }

    public String getCurrentProgram() {
        return currentProgram;
    }

    public void setCurrentProgram(String currentProgram) {
        this.currentProgram = currentProgram;
    }

    public String getCustomData() {
        return customData;
    }

    public void setCustomData(String customData) {
        this.customData = customData;
    }

    public String getCurrentProgramValue() {
        return currentProgramValue;
    }

    public Room getRoom() {
        return RoomManager.getInstance().getRoomById(this.roomId);
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Item getItemBelow() {
        var tile = this.getTile();

        if (tile == null || tile.getItems() == null) {
            return null;
        }

        var items = tile.getItems();
        int position = items.indexOf(this);

        if (position > -1) {
            int nextPosition = position - 1;

            if (nextPosition < 0) {
                return null;
            }

            return items.get(nextPosition);

        }

        return null;
    }

    public Item getItemAbove() {
        var tile = this.getTile();

        if (tile == null || tile.getItems() == null) {
            return null;
        }

        var items = tile.getItems();
        int position = items.indexOf(this);

        if (position > -1) {
            int nextPosition = position + 1;

            if (nextPosition >= tile.getItems().size()) {
                return null;
            }

            return items.get(nextPosition);

        }

        return null;
    }

    public boolean getRequiresUpdate() {
        return requiresUpdate;
    }

    public void setRequiresUpdate(boolean requiresUpdate) {
        this.requiresUpdate = requiresUpdate;
    }

    public RollingData getRollingData() {
        return rollingData;
    }

    public void setRollingData(RollingData rollingData) {
        this.rollingData = rollingData;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public long getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(long databaseId) {
        this.databaseId = databaseId;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public boolean isCurrentRollBlocked() {
        return isCurrentRollBlocked;
    }

    public void setCurrentRollBlocked(boolean currentRollBlocked) {
        isCurrentRollBlocked = currentRollBlocked;
    }

    public long getLastPlacedTime() {
        return lastPlacedTime;
    }

    public void setLastPlacedTime(long lastPlacedTime) {
        this.lastPlacedTime = lastPlacedTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Room getTemporaryRoom() {
        return temporaryRoom;
    }

    public void setTemporaryRoom(Room temporaryRoom) {
        this.temporaryRoom = temporaryRoom;
    }

    public boolean isInTrade() {
        return isInTrade;
    }

    public void setInTrade(boolean inTrade) {
        isInTrade = inTrade;
    }

    public boolean isVisible() {
        return !this.isInTrade && !this.isHidden;
    }

    public Position getTeleportTo() {
        return teleportTo;
    }

    public void setTeleportTo(Position teleportTo) {
        this.teleportTo = teleportTo;
    }

    public Position getSwimTo() {
        return swimTo;
    }

    public void setSwimTo(Position swimTo) {
        this.swimTo = swimTo;
    }
}

