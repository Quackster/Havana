package org.alexdev.havana.game.item.interactors.types.pool;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.room.entities.RoomEntity;
import org.alexdev.havana.game.triggers.GenericTrigger;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.rooms.pool.OPEN_UIMAKOPPI;

public class PoolBoothInteractor extends GenericTrigger {

    @Override
    public void onEntityStop(Entity entity, RoomEntity roomEntity, Item item, boolean isRotation) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player)entity;

        player.getRoomUser().setWalkingAllowed(false);
        player.getRoomUser().getTimerManager().resetRoomTimer(120); // Only allow 120 seconds when changing clothes, to stop someone from just afking in the booth for 15 minutes.
        player.send(new OPEN_UIMAKOPPI());

        item.showProgram("close");
    }

    @Override
    public void onEntityLeave(Entity entity, RoomEntity roomEntity, Item item, Object... customArgs) {
        if (roomEntity.isWalking()) {
            return;
        }

        item.showProgram("open");
    }
}
