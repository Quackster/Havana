package org.alexdev.havana.game.games.battleball;

import org.alexdev.havana.dao.Storage;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.games.Game;
import org.alexdev.havana.game.games.GameEvent;
import org.alexdev.havana.game.games.GameObject;
import org.alexdev.havana.game.games.battleball.enums.BattleBallPlayerState;
import org.alexdev.havana.game.games.battleball.events.PlayerMoveEvent;
import org.alexdev.havana.game.games.battleball.objects.PlayerUpdateObject;
import org.alexdev.havana.game.games.battleball.objects.PowerUpUpdateObject;
import org.alexdev.havana.game.games.enums.GameState;
import org.alexdev.havana.game.games.player.GamePlayer;
import org.alexdev.havana.game.games.player.GameTeam;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.pathfinder.Rotation;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.entities.RoomEntity;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.game.room.mapping.RoomTile;
import org.alexdev.havana.messages.outgoing.games.GAMESTATUS;
import org.alexdev.havana.util.StringUtil;
import org.oldskooler.simplelogger4j.SimpleLog;

import java.util.ArrayList;
import java.util.List;

public class BattleBallTask implements Runnable {
    private final Room room;
    private final BattleBallGame game;

    public BattleBallTask(Room room, BattleBallGame game) {
        this.room = room;
        this.game = game;
    }

    @Override
    public void run() {
        try {
            if (this.game.getActivePlayers().isEmpty() || this.game.getGameState() == GameState.ENDED) {
                return; // Don't send any packets or do any logic checks during when the game is finished
            }

            List<GameObject> objects = new ArrayList<>();
            List<GameEvent> events = new ArrayList<>();

            List<BattleBallTile> updateTiles = new ArrayList<>();
            List<BattleBallTile> fillTiles = new ArrayList<>();

            this.game.getEventsQueue().drainTo(events);
            this.game.getObjectsQueue().drainTo(objects);
            this.game.getUpdateTilesQueue().drainTo(updateTiles);
            this.game.getFillTilesQueue().drainTo(fillTiles);
            this.game.getTeams().values().forEach(GameTeam::calculateScore);

            for (GamePlayer gamePlayer : this.game.getActivePlayers()) {
                Player player = gamePlayer.getPlayer();

                if (player.getRoomUser().getRoom() != this.room) {
                    continue;
                }

                if (gamePlayer.getPlayerState() == BattleBallPlayerState.CLIMBING_INTO_CANNON ||
                        gamePlayer.getPlayerState() == BattleBallPlayerState.FLYING_THROUGH_AIR) {
                    continue;
                }

                if (this.game.getStoredPowers().containsKey(gamePlayer)) {
                    for (BattleBallPowerUp powerUp : this.game.getStoredPowers().get(gamePlayer)) {
                        objects.add(new PowerUpUpdateObject(powerUp));
                    }
                }

                player.getRoomUser().handleSpamTicks();
                this.processEntity(gamePlayer, objects, events, updateTiles, fillTiles);

                objects.add(new PlayerUpdateObject(gamePlayer));
            }

            this.game.send(new GAMESTATUS(this.game, this.game.getTeams().values(), objects, events, updateTiles, fillTiles));
        } catch (Exception ex) {
            SimpleLog.of(BattleBallTask.class).error("GameTask crashed: ", ex);
        }
    }

    /**
     * Process entity.
     */
    private void processEntity(GamePlayer gamePlayer, List<GameObject> objects, List<GameEvent> events, List<BattleBallTile> updateTiles, List<BattleBallTile> fillTiles) {
        Entity entity = (Entity) gamePlayer.getPlayer();
        Game game = gamePlayer.getGame();

        RoomEntity roomEntity = entity.getRoomUser();

        Position position = roomEntity.getPosition();
        Position goal = roomEntity.getGoal();

        if (roomEntity.isWalking()) {
            // Apply next tile from the tile we removed from the list the cycle before
            if (roomEntity.getNextPosition() != null) {
                /*roomEntity.getPosition().setX(roomEntity.getNextPosition().getX());
                roomEntity.getPosition().setY(roomEntity.getNextPosition().getY());
                roomEntity.updateNewHeight(roomEntity.getPosition());*/
                /*RoomTile nextTile = roomEntity.getRoom().getMapping().getTile(roomEntity.getNextPosition());
                boolean isRedirected = false;
                if (nextTile.getEntities().size() > 0 && !nextTile.containsEntity(entity)) {
                    roomEntity.setNextPosition(roomEntity.getPosition().copy());
                    isRedirected = true;
                }*/

                roomEntity.getPosition().setX(roomEntity.getNextPosition().getX());
                roomEntity.getPosition().setY(roomEntity.getNextPosition().getY());
                roomEntity.updateNewHeight(roomEntity.getPosition());

                if (roomEntity.getNextPosition() != null) {
                    RoomTile nextTile = roomEntity.getRoom().getMapping().getTile(roomEntity.getNextPosition());

                    if (nextTile != null && nextTile.getOtherEntities(entity).size() < 1) {
                        BattleBallTile tile = (BattleBallTile) game.getTile(roomEntity.getNextPosition().getX(), roomEntity.getNextPosition().getY());

                        if (tile != null) {
                            tile.interact(gamePlayer, objects, events, updateTiles, fillTiles);
                        }
                    }
                }
            }

            // We still have more tiles left, so lets continue moving
            if (roomEntity.getPath().size() > 0) {
                Position next = roomEntity.getPath().pop();

                RoomTile previousTile = roomEntity.getTile();
                RoomTile nextTile = roomEntity.getRoom().getMapping().getTile(next);

                // Tile was invalid after we started walking, so lets try again!
                if (nextTile == null || !RoomTile.isValidTile(this.room, entity, next) && !next.equals(goal)) {
                    entity.getRoomUser().getPath().clear();
                    roomEntity.walkTo(goal.getX(), goal.getY());
                    this.processEntity(gamePlayer, objects, events, updateTiles, fillTiles);
                    return;
                }

                if (previousTile != null) {
                    previousTile.removeEntity(entity);
                }

                nextTile.addEntity(entity);

                roomEntity.removeStatus(StatusType.LAY);
                roomEntity.removeStatus(StatusType.SIT);

                int rotation = Rotation.calculateWalkDirection(position.getX(), position.getY(), next.getX(), next.getY());
                double height = nextTile.getWalkingHeight();

                roomEntity.getPosition().setRotation(rotation);
                roomEntity.setStatus(StatusType.MOVE, next.getX() + "," + next.getY() + "," + StringUtil.format(height));
                roomEntity.setNextPosition(next);

                // Add next position if moving
                events.add(new PlayerMoveEvent(gamePlayer, roomEntity.getNextPosition().copy()));
            } else {
                roomEntity.stopWalking();

                RoomTile previousTile = roomEntity.getRoom().getMapping().getTile(roomEntity.getPosition());
                RoomTile nextTile = roomEntity.getRoom().getMapping().getTile(roomEntity.getPosition().getSquareBehind());

                // If an entity exists on the tile, push them back
                if (previousTile != null && !(previousTile.getOtherEntities(entity).size() < 1)) {
                    previousTile.removeEntity(entity);
                    nextTile.addEntity(entity);

                    // Set new position
                    entity.getRoomUser().setPosition(roomEntity.getPosition().getSquareBehind());
                }
            }

            // If we're walking, make sure to tell the server
            roomEntity.setNeedsUpdate(true);
        }
    }
}