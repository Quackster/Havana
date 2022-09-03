package org.alexdev.havana.game.item.interactors.types.pool;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.entities.RoomEntity;
import org.alexdev.havana.game.room.tasks.StatusTask;
import org.alexdev.havana.game.triggers.GenericTrigger;
import org.alexdev.havana.messages.outgoing.user.currencies.NO_TICKETS;

public class PoolQueueInteractor extends GenericTrigger {

    @Override
    public void onEntityStep(Entity entity, RoomEntity roomEntity, Item item, Position oldPosition) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player)entity;

        if (player.getDetails().getTickets() == 0 || player.getDetails().getPoolFigure().isEmpty()) {
            oldPosition.setRotation(2); // Make user face this way, like the original Lido behaviour
            player.getRoomUser().stopWalking();
            player.getRoomUser().warp(oldPosition, false, false);

            if (player.getDetails().getTickets() == 0) {
                player.send(new NO_TICKETS());
            }
        }
    }

    @Override
    public void onEntityStop(Entity entity, RoomEntity roomEntity, Item item, boolean isRotation) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player)entity;

        if (player.getDetails().getTickets() == 0 || player.getDetails().getPoolFigure().isEmpty()) {
            return;
        }

        // When they stop walking, check if the player is on a pool lido queue and walk to the next one
        StatusTask.processPoolQueue(player);
    }

    @Override
    public void onEntityLeave(Entity entity, RoomEntity roomEntity, Item item, Object... customArgs) {

    }
}
