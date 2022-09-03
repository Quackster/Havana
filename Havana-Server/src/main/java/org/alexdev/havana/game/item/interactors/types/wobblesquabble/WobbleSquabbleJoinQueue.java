package org.alexdev.havana.game.item.interactors.types.wobblesquabble;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.games.wobblesquabble.WobbleSquabbleManager;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.entities.RoomEntity;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.game.room.mapping.RoomTile;
import org.alexdev.havana.game.triggers.GenericTrigger;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;

public class WobbleSquabbleJoinQueue extends GenericTrigger {
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

        Player player = (Player) entity;

        if (player.getDetails().getTickets() < WobbleSquabbleManager.WS_GAME_TICKET_COST) {
            player.send(new ALERT("You need at least " + WobbleSquabbleManager.WS_GAME_TICKET_COST + " ticket(s) to play Wobble Squabble!"));
            return; // Too poor!
        }

        String[] teleportPositionData = item.getCurrentProgram().split(",");

        Position teleportPosition = new Position(
                Integer.parseInt(teleportPositionData[0]),
                Integer.parseInt(teleportPositionData[1])
        );

        teleportPosition.setRotation(Integer.parseInt(teleportPositionData[2]));
        RoomTile roomTile = roomEntity.getRoom().getMapping().getTile(teleportPosition);

        if (roomTile == null || roomTile.getEntities().size() > 0) {
            return;
        }

        player.getRoomUser().setTeleporting(false);
        roomEntity.removeStatus(StatusType.SWIM);
        roomEntity.warp(teleportPosition, true, false);

        InteractionType.WS_QUEUE_TILE.getTrigger().onEntityStop(entity, roomEntity, item, isRotation);
    }
}
