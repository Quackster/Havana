package org.alexdev.havana.game.room.models.triggers;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.triggers.GenericTrigger;
import org.alexdev.havana.messages.outgoing.rooms.items.SHOWPROGRAM;

import java.util.concurrent.TimeUnit;

public class RooftopRumbleTrigger extends GenericTrigger {
    @Override
    public void onRoomEntry(Entity entity, Room room, boolean firstEntry, Object... customArgs) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player)entity;

        if (room.getTaskManager().hasTask("DivingCamera")) {
            DivingDeckTrigger.PoolCamera task = (DivingDeckTrigger.PoolCamera) room.getTaskManager().getTask("DivingCamera");

            if (task.getPlayer() == null) {
                task.spectateNewPlayer();
            }

            if (task.getPlayer() != null) {
                player.send(new SHOWPROGRAM(new String[]{"cam1", "setcamera", String.valueOf(task.getCameraType())}));
            }
        } else {
            room.getTaskManager().scheduleTask("DivingCamera", new DivingDeckTrigger.PoolCamera(room), 3, 10, TimeUnit.SECONDS);
        }
    }

    @Override
    public void onRoomLeave(Entity entity, Room room, Object... customArgs)  {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        if (room.getEntityManager().getPlayers().isEmpty()) {
            return;
        }

        Player player = (Player)entity;

        DivingDeckTrigger.PoolCamera task = (DivingDeckTrigger.PoolCamera) room.getTaskManager().getTask("DivingCamera");

        if (task.getPlayer() == player) {
            task.spectateNewPlayer();
        }
    }
}
