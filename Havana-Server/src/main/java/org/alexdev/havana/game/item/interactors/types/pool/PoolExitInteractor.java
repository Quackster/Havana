package org.alexdev.havana.game.item.interactors.types.pool;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.room.entities.RoomEntity;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.game.triggers.GenericTrigger;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.room.handlers.PoolHandler;

public class PoolExitInteractor extends GenericTrigger {
    @Override
    public void onEntityStep(Entity entity, RoomEntity roomEntity, Item item, Position oldPosition) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        if (!entity.getRoomUser().containsStatus(StatusType.SWIM)) {
            return;
        }

        // Don't handle step event from RoomUser when changing paths
        //if (customArgs.length > 0) {
        //    return;
        //}

        Position warp = null;
        Position goal = null;

        if (item.getPosition().getX() == 21 && item.getPosition().getY() == 28) {
            warp = new Position(20, 28);
            goal = new Position(19, 28);
        }

        if (item.getPosition().getX() == 17 && item.getPosition().getY() == 22) {
            warp = new Position(17, 21);
            goal = new Position(17, 20);
        }

        if (item.getPosition().getX() == 31 && item.getPosition().getY() == 11) {
            warp = new Position(31, 10);
            goal = new Position(31, 9);
        }

        if (item.getPosition().getX() == 20 && item.getPosition().getY() == 19) {
            warp = new Position(19, 19);
            goal = new Position(18, 19);
        }

        if ((item.getPosition().getX() == 12 && item.getPosition().getY() == 11) ||
            (item.getPosition().getX() == 12 && item.getPosition().getY() == 12)) {
            warp = new Position(11, 11);
            goal = new Position(10, 11);
        }


        if (warp != null) {
            PoolHandler.warpSwim(item, entity, warp, goal, true);
        }
    }

    @Override
    public void onEntityStop(Entity entity, RoomEntity roomEntity, Item item, boolean isRotation) {

    }

    @Override
    public void onEntityLeave(Entity entity, RoomEntity roomEntity, Item item, Object... customArgs) {

    }
}
