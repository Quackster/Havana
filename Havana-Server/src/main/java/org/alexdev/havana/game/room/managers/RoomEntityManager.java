package org.alexdev.havana.game.room.managers;

import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.dao.mysql.RoomRightsDao;
import org.alexdev.havana.dao.mysql.RoomVoteDao;
import org.alexdev.havana.game.GameScheduler;
import org.alexdev.havana.game.bot.Bot;
import org.alexdev.havana.game.bot.BotData;
import org.alexdev.havana.game.bot.BotManager;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.events.EventsManager;
import org.alexdev.havana.game.games.player.GamePlayer;
import org.alexdev.havana.game.games.wobblesquabble.WobbleSquabbleManager;
import org.alexdev.havana.game.games.wobblesquabble.WobbleSquabblePlayer;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.interactors.types.TeleportInteractor;
import org.alexdev.havana.game.item.publicrooms.PublicItemParser;
import org.alexdev.havana.game.misc.figure.FigureManager;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.game.room.mapping.RoomTile;
import org.alexdev.havana.messages.outgoing.events.ROOMEEVENT_INFO;
import org.alexdev.havana.messages.outgoing.rooms.FLATPROPERTY;
import org.alexdev.havana.messages.outgoing.rooms.ROOM_READY;
import org.alexdev.havana.messages.outgoing.rooms.UPDATE_VOTES;
import org.alexdev.havana.messages.outgoing.rooms.user.HOTEL_VIEW;
import org.alexdev.havana.messages.outgoing.rooms.user.LOGOUT;
import org.alexdev.havana.messages.outgoing.rooms.user.USER_OBJECTS;
import org.alexdev.havana.util.FigureUtil;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RoomEntityManager {
    private Room room;
    private AtomicInteger counter;

    public RoomEntityManager(Room room) {
        this.room = room;
        this.counter = new AtomicInteger(0);
    }

    /**
     * Generates a unique ID for the entities in a room. Will be used for pets
     * and bots in future.
     *
     * @return the unique ID
     */
    public int generateUniqueId() {
        return this.counter.getAndIncrement();
    }

    /**
     * Return the list of entities currently in this room by its
     * given class.
     *
     * @param entityClass the entity class
     * @return List<T extends Entity> list of entities
     */
    public <T extends Entity> List<T> getEntitiesByClass(Class<T> entityClass) {
        List<T> entities = new ArrayList<>();

        for (Entity entity : this.room.getEntities()) {
            if (entity.getClass().isAssignableFrom(entityClass)) {
                entities.add(entityClass.cast(entity));
            }
        }

        return entities;
    }

    /**
     * Return the list of players currently in this room by its
     * given class.
     *
     * @return List<Player> list of players
     */
    public List<Player> getPlayers() {
        return getEntitiesByClass(Player.class);
    }

    /**
     * Get an entity by instance id.
     *
     * @param instanceId the instance id to get by
     * @return the entity
     */
    public Entity getByInstanceId(int instanceId) {
        for (Entity entity : this.room.getEntities()) {
            if (entity.getRoomUser().getInstanceId() == instanceId) {
                return entity;
            }
        }

        return null;
    }

    /**
     * Get an entity by id.
     *
     * @param id the instance id to get by
     * @return the entity
     */
    public Entity getById(int id, EntityType entityType) {
        for (Entity entity : this.room.getEntities()) {
            if (entity.getDetails().getId() == id && entity.getType() == entityType) {
                return entity;
            }
        }

        return null;
    }

    /**
     * Adds a generic entity to the room.
     * Will send packets if the entity is a player.
     *
     * @param entity      the entity to add
     * @param destination the (optional) destination to take the user to when they enter
     */
    public void enterRoom(Entity entity, Position destination) {
        this.silentlyEnterRoom(entity, destination);

        // From this point onwards we send packets for the user to enter
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        player.send(new ROOM_READY(this.room.getId(), this.room.getModel().getName()));
        player.send(new FLATPROPERTY("landscape", this.room.getData().getLandscape()));

        if (this.room.getData().getWallpaper() > 0) {
            player.send(new FLATPROPERTY("wallpaper", this.room.getData().getWallpaper()));
        }

        if (this.room.getData().getFloor() > 0) {
            player.send(new FLATPROPERTY("floor", this.room.getData().getFloor()));
        }

        // Only refresh rights when in private room
        if (!this.room.isPublicRoom()) {
            this.room.refreshRights(player, false);
        }

        // Don't let the room owner vote on it's own room
        boolean voted = this.room.isOwner(player.getDetails().getId()) || this.room.hasVoted(player);

        // Send -1 to users who haven't voted yet, and vote count to others
        // We do this to make the vote UI pop up
        if (voted) {
            player.send(new UPDATE_VOTES(this.room.getData().getRating()));
        } else {
            player.send(new UPDATE_VOTES(-1));
        }

        player.send(new ROOMEEVENT_INFO(EventsManager.getInstance().getEventByRoomId(this.room.getId())));

    }

    public void silentlyEnterRoom(Entity entity, Position destination) {
        if (entity.getRoomUser().getRoom() != null) {
            entity.getRoomUser().getRoom().getEntityManager().leaveRoom(entity, false);
        }

        // If the room is not loaded, add room, as we intend to join it. Game arena rooms don't get added.
        if (!RoomManager.getInstance().hasRoom(this.room.getId()) && !this.room.isGameArena() && !this.room.getData().isCustomRoom()) {
            RoomManager.getInstance().addRoom(this.room);
        }

        entity.getRoomUser().reset();
        entity.getRoomUser().setRoom(this.room);
        entity.getRoomUser().setInstanceId(this.generateUniqueId());
        entity.getRoomUser().setEnteredRoomAt();

        Position entryPosition = this.room.getModel().getDoorLocation();

        if (destination != null) {
            entryPosition = destination.copy();
        }

        entity.getRoomUser().setPosition(entryPosition);

        if (entity.getType() == EntityType.PLAYER) {
            Player player = (Player) entity;
            GamePlayer gamePlayer = player.getRoomUser().getGamePlayer();

            if (gamePlayer != null && gamePlayer.isEnteringGame()) {
                entity.getRoomUser().setInstanceId(gamePlayer.getObjectId()); // Instance ID will always be player id
                entity.getRoomUser().setWalkingAllowed(false); // Block walking initially when joining game
            }
        }

        if (entity.getType() != EntityType.PLAYER) {
            if (this.getPlayers().size() > 0) {
                this.room.send(new USER_OBJECTS(entity));
            }
        }

        this.room.getEntities().add(entity);
        this.room.getData().setVisitorsNow(this.room.getEntityManager().getPlayers().size());

        if (entity.getType() == EntityType.BOT) {
            if (entity.getDetails().getName().equals("Abigail.Ryan")) {
                entity.getRoomUser().useEffect(13); // Ghost effect
            }
        }

        // From this point onwards we send packets for the user to enter
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;
        player.getRoomUser().setAuthenticateId(-1);

        this.tryInitialiseRoom(player);

        if (player.getRoomUser().getAuthenticateTelporterId() != -1) {
            Item teleporter = this.room.getItemManager().getByDatabaseId(player.getRoomUser().getAuthenticateTelporterId());

            if (teleporter != null) {
                player.getRoomUser().setWalkingAllowed(false);
                entity.getRoomUser().setPosition(teleporter.getPosition().copy());

                GameScheduler.getInstance().getService().schedule(() -> {
                    teleporter.setCustomData(TeleportInteractor.TELEPORTER_EFFECTS);
                    teleporter.updateStatus();
                }, 0, TimeUnit.SECONDS);

                GameScheduler.getInstance().getService().schedule(() -> {
                    teleporter.setCustomData(TeleportInteractor.TELEPORTER_OPEN);
                    teleporter.updateStatus();

                    player.getRoomUser().walkTo(
                            teleporter.getPosition().getSquareInFront().getX(),
                            teleporter.getPosition().getSquareInFront().getY());
                }, 1000, TimeUnit.MILLISECONDS);

                GameScheduler.getInstance().getService().schedule(() -> {
                    teleporter.setCustomData(TeleportInteractor.TELEPORTER_CLOSE);
                    teleporter.updateStatus();

                    player.getRoomUser().setWalkingAllowed(true);
                }, 1500, TimeUnit.MILLISECONDS);


            }

            player.getRoomUser().setAuthenticateTelporterId(-1);
        }

        GamePlayer gamePlayer = player.getRoomUser().getGamePlayer();

        if (gamePlayer != null && gamePlayer.isEnteringGame()) {
            gamePlayer.setEnteringGame(false);
        }

        player.getMessenger().sendStatusUpdate(); // Let friends know I entered this room by updating their console :)
        RoomDao.saveVisitors(this.room.getId(), this.room.getEntityManager().getPlayers().size()); // Save increased visitor count of this room
    }

    /**
     * Setup the room initially for room entry.
     */
    private void tryInitialiseRoom(Player player) {
        if (!this.room.isActive()) {
            if (!this.room.isGameArena() && !this.room.getData().isCustomRoom()) {
                this.room.getItems().clear();
                this.room.getRights().clear();
                this.room.getVotes().clear();

                if (this.room.isPublicRoom()) {
                    this.room.getItems().addAll(PublicItemParser.getPublicItems(this.room.getId(), this.room.getModel().getId()));
                } else {
                    this.room.getRights().addAll(RoomRightsDao.getRoomRights(this.room.getData()));
                    this.room.getVotes().addAll(RoomVoteDao.getRatings(this.room.getId()));
                }

                this.room.getItems().addAll(ItemDao.getRoomItems(this.room.getData()));
                this.room.getItemManager().resetItemStates();
            }

            this.room.getMapping().regenerateCollisionMap();
            this.room.getTaskManager().startTasks();
        };

        if (player.getDetails().getName().equals("Abigail.Ryan")) {
            player.getRoomUser().useEffect(13);  // Ghost effect
        }
    }

    /**
     * Setup the room initially for room entry.
     *
     * @return whether it was first entry
     */
    public boolean tryRoomEntry(Player player) {
        boolean isRoomActive = this.room.isActive();

        if (!this.room.isActive()) {
            this.room.setActive(true);
        };

        if (this.room.getModel().getRoomTrigger() != null) {
            this.room.getModel().getRoomTrigger().onRoomEntry(player, this.room, !isRoomActive);
        }

        // Load bot data if first entry
        if (!isRoomActive) {
            BotManager.getInstance().addBots(this.room);
        }

        return !isRoomActive;
    }

    /**
     * Setup handler for the entity to leave room.
     *
     * @param entity the entity to leave
     */
    public void leaveRoom(Entity entity, boolean hotelView) {
        if (!this.room.getEntities().contains(entity)) {
            return;
        }

        this.room.getEntities().remove(entity);

        // Set up trigger for leaving a current item
        if (entity.getRoomUser().getCurrentItem() != null) {
            if (entity.getRoomUser().getCurrentItem().getDefinition().getInteractionType().getTrigger() != null) {
                entity.getRoomUser().getCurrentItem().getDefinition().getInteractionType().getTrigger().onEntityLeave(entity, entity.getRoomUser(), entity.getRoomUser().getCurrentItem());
            }
        }

        // Trigger for leaving room
        if (this.room.getModel().getRoomTrigger() != null) {
            this.room.getModel().getRoomTrigger().onRoomLeave(entity, this.room);
        }

        // Entity tile removal
        RoomTile tile = entity.getRoomUser().getTile();

        if (tile != null) {
            tile.removeEntity(entity);
        }

        if (entity.getRoomUser().getNextPosition() != null) {
            RoomTile nextTile = this.room.getMapping().getTile(entity.getRoomUser().getNextPosition());

            if (nextTile != null) {
                nextTile.removeEntity(entity);
            }
        }

        var players = this.room.getEntityManager().getPlayers();

        // Handle the room logic behind the entity removal
        this.room.getData().setVisitorsNow(this.room.getEntityManager().getPlayers().size());
        this.room.send(new LOGOUT(entity.getRoomUser().getInstanceId()));
        this.room.tryDispose();

        entity.getRoomUser().reset();

        // From this point onwards we send packets for the user to leave
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        if (hotelView) {
            player.send(new HOTEL_VIEW());
        }

        // End game if we leave during the middle of Wobble Squabble
        if (WobbleSquabbleManager.getInstance().isPlaying(player)) {
            WobbleSquabblePlayer wsPlayer = WobbleSquabbleManager.getInstance().getPlayer(player);

            // End game with a tie
            wsPlayer.getGame().endGame(-1);
        }

        // If we left room while in a game, leave the game
        GamePlayer gamePlayer = player.getRoomUser().getGamePlayer();

        if (gamePlayer != null && !gamePlayer.isEnteringGame()) {
            if (gamePlayer.getGame() != null) {
                gamePlayer.getGame().leaveGame(gamePlayer);
            } else {
                player.getRoomUser().setGamePlayer(null);
            }
        }

        player.getMessenger().sendStatusUpdate();
        RoomDao.saveVisitors(this.room.getId(), this.room.getEntityManager().getPlayers().size());

    }

    /**
     * Get UUID counter.
     *
     * @return the counter
     */
    public AtomicInteger getCounter() {
        return counter;
    }
}
