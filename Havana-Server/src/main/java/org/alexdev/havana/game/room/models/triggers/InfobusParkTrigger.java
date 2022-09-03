package org.alexdev.havana.game.room.models.triggers;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.infobus.InfobusManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.triggers.GenericTrigger;
import org.alexdev.havana.messages.outgoing.infobus.BUS_DOOR;
import org.alexdev.havana.messages.types.MessageComposer;

import java.util.ArrayList;
import java.util.List;

public class InfobusParkTrigger extends GenericTrigger {
    @Override
    public void onRoomEntry(Entity entity, Room room, boolean firstEntry, Object... customArgs) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;
        player.send(new BUS_DOOR(InfobusManager.getInstance().isDoorOpen()));

        List<MessageComposer> messageComposers = new ArrayList<>();
        player.getRoomUser().getPacketQueueAfterRoomLeave().drainTo(messageComposers);

        for (var composer : messageComposers) {
            player.send(composer);
        }
    }

    @Override
    public void onRoomLeave(Entity entity, Room room, Object... customArgs) {

    }
}
