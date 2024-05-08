package org.alexdev.havana.game.item.interactors.types;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.pets.Pet;
import org.alexdev.havana.game.pets.PetAction;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.entities.RoomEntity;
import org.alexdev.havana.game.room.tasks.EntityTask;
import org.alexdev.havana.game.triggers.GenericTrigger;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.game.room.mapping.RoomTile;
import org.alexdev.havana.messages.outgoing.effects.USER_AVATAR_EFFECT;
import org.alexdev.havana.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BedInteractor extends GenericTrigger {
    @Override
    public void onInteract(Player player, Room room, Item item, int status) {
        InteractionType.DEFAULT.getTrigger().onInteract(player, room, item, status);
    }

    @Override
    public void onEntityStop(Entity entity, RoomEntity roomEntity, Item item, boolean isRotation) {
        Position destination = entity.getRoomUser().getPosition().copy();

        if (!isValidPillowTile(item, destination)) {
            destination = convertToPillow(destination, item);
        }

        if (isValidPillowTile(item, destination)) {
            if (!RoomTile.isValidTile(roomEntity.getRoom(), roomEntity.getEntity(), destination)) {
                return;
            }

            entity.getRoomUser().warp(destination, false, false);

            roomEntity.stopCarrying();
            roomEntity.stopDancing();

            double topHeight = item.getDefinition().getTopHeight();
            roomEntity.getPosition().setRotation(item.getPosition().getRotation());
            roomEntity.setStatus(StatusType.LAY, StringUtil.format(topHeight));
        }

        roomEntity.setNeedsUpdate(true);

        if (entity.getRoomUser().isUsingEffect()) {
            if (!roomEntity.getRoom().getTaskManager().hasTask("EntityTask")) {
                return;
            }

            EntityTask entityTask = (EntityTask) roomEntity.getRoom().getTaskManager().getTask("EntityTask");
            entityTask.getQueueAfterLoop().add(new USER_AVATAR_EFFECT(roomEntity.getInstanceId(), roomEntity.getEffectId()));
        }
    }

    @Override
    public void onEntityLeave(Entity entity, RoomEntity roomEntity, Item item, Object... customArgs) {

    }

    /**
     * Converts any coordinate within the bed dimensions to the closest pillow.
     *
     * @param position to check
     * @param item the item checking against
     * @return the pillow position
     */
    public static Position convertToPillow(Position position, Item item) {
        Position destination = position.copy();

        if (!isValidPillowTile(item, position)) {
            for (Position tile : getValidPillowTiles(item)) {
                if (item.getPosition().getRotation() == 0) {
                    destination.setY(tile.getY());
                } else {
                    destination.setX(tile.getX());
                }

                break;
            }
        }

        return destination;
    }

    /**
     * Validates if the users tile is a valid pillow tile on a bed.
     *
     * @param item the bed to check for
     * @param entityPosition the entity position to check against
     * @return true, if successful
     */
    public static boolean isValidPillowTile(Item item, Position entityPosition) {
        if (entityPosition.equals(item.getPosition())) {
            return true;
        } else {
            for (Position validTile : getValidPillowTiles(item)) {
                if (validTile.equals(entityPosition)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Gets the valid pillow tile list for a bed.
     *
     * @param item the item to check for
     * @return the list of valid coordinates
     */
    public static List<Position> getValidPillowTiles(Item item) {
        if (item == null || item.getPosition() == null) {
            return List.of();
        }

        List<Position> tiles = new ArrayList<>();
        tiles.add(new Position(item.getPosition().getX(), item.getPosition().getY()));

        int validPillowX = -1;
        int validPillowY = -1;

        if (item.getPosition().getRotation() == 0) {
            validPillowX = item.getPosition().getX() + 1;
            validPillowY = item.getPosition().getY();
        }

        if (item.getPosition().getRotation() == 2) {
            validPillowX = item.getPosition().getX();
            validPillowY = item.getPosition().getY() + 1;
        }

        tiles.add(new Position(validPillowX, validPillowY));
        return tiles;
    }
}
