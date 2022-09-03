package org.alexdev.havana.game.item.interactors.types.wobblesquabble;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.games.wobblesquabble.WobbleSquabbleManager;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.room.entities.RoomEntity;
import org.alexdev.havana.game.triggers.GenericTrigger;

public class WobbleSquabbleQueueTile extends GenericTrigger {
    public void onEntityStep(Entity entity, RoomEntity roomEntity, Item item, Position oldPosition) {

    }

    @Override
    public void onEntityStop(Entity entity, RoomEntity roomEntity, Item item, boolean isRotation) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        if (roomEntity.getRoom().getTaskManager().hasTask(WobbleSquabbleManager.getInstance().getName())) {
            return;
        }

        roomEntity.setTeleporting(false);
        roomEntity.walkTo(roomEntity.getPosition().getSquareInFront().getX(), roomEntity.getPosition().getSquareInFront().getY());
    }
}
