package org.alexdev.havana.game.item.interactors.types.wobblesquabble;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.games.wobblesquabble.WobbleSquabbleGame;
import org.alexdev.havana.game.games.wobblesquabble.WobbleSquabbleManager;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.entities.RoomEntity;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.game.room.mapping.RoomTile;
import org.alexdev.havana.game.triggers.GenericTrigger;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.outgoing.wobblesquabble.PT_PREPARE;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class WobbleSquabbleTileStart extends GenericTrigger {

    @Override
    public void onEntityStop(Entity entity, RoomEntity roomEntity, Item item, boolean isRotation) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        if (roomEntity.getRoom().getTaskManager().hasTask(WobbleSquabbleManager.getInstance().getName())) {
            return;
        }

        String[] otherTilePlayer = item.getCurrentProgram().split(",");

        Position teleportPosition = new Position(
                Integer.parseInt(otherTilePlayer[0]),
                Integer.parseInt(otherTilePlayer[1])
        );

        RoomTile roomTile = roomEntity.getRoom().getMapping().getTile(teleportPosition);

        if (roomTile == null || !(roomTile.getEntities().size() > 0)) {
            return;
        }

        entity.getRoomUser().setTeleporting(false);

        // Two players! :3
        WobbleSquabbleGame wsGame = new WobbleSquabbleGame((Player)entity, (Player) roomTile.getEntities().get(0));

        for (int i = 0; i < 2; i++) {
            Player player = wsGame.getPlayer(i).getPlayer();

            if (player.getDetails().getTickets() < WobbleSquabbleManager.WS_GAME_TICKET_COST) {
                player.send(new ALERT("You need at least " + WobbleSquabbleManager.WS_GAME_TICKET_COST + " ticket(s) to play Wobble Squabble!"));

                int newX = player.getRoomUser().getPosition().getX() + (ThreadLocalRandom.current().nextBoolean() ? -1 : 1);
                int newY = player.getRoomUser().getPosition().getY();

                Position position = new Position(newX, newY);
                position.setRotation(player.getRoomUser().getPosition().getRotation());

                player.getRoomUser().setStatus(StatusType.SWIM, "");
                player.getRoomUser().warp(position, true, false);

                return; // Too poor!
            }
        }


        // Disable walking requests
        wsGame.getPlayer(0).getPlayer().getRoomUser().setWalkingAllowed(false);
        wsGame.getPlayer(1).getPlayer().getRoomUser().setWalkingAllowed(false);

        // Schedule worker task
        String wsTaskName = WobbleSquabbleManager.getInstance().getName();
        roomEntity.getRoom().getTaskManager().scheduleTask(wsTaskName, wsGame, TimeUnit.SECONDS.toMillis(3),200, TimeUnit.MILLISECONDS);

        // Announce game starting
        wsGame.send(new PT_PREPARE(wsGame.getPlayer(0), wsGame.getPlayer(1)));
    }
}
