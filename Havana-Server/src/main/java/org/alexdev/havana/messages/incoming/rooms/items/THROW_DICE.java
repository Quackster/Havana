package org.alexdev.havana.messages.incoming.rooms.items;

import org.alexdev.havana.game.GameScheduler;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.entities.RoomEntity;
import org.alexdev.havana.game.room.tasks.DiceTask;
import org.alexdev.havana.messages.outgoing.rooms.items.DICE_VALUE;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.concurrent.TimeUnit;


public class THROW_DICE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        RoomEntity roomEntity = player.getRoomUser();
        Room room = roomEntity.getRoom();

        if (room == null) {
            return;
        }

        int itemId = reader.readInt();
        Item item = room.getItemManager().getById(itemId);

        if (item == null || !item.hasBehaviour(ItemBehaviour.DICE)) {
            return;
        }

        // Return if dice is already being rolled
        if (item.getRequiresUpdate()) {
            return;
        }

        // Check if user is next to dice
        if (!roomEntity.getPosition().touches(item.getPosition())) {
            return;
        }

        // We reset the room timer here too as in casinos you might be in the same place for a while
        // And you don't want to get kicked while you're still actively rolling dices for people :)
        player.getRoomUser().getTimerManager().resetRoomTimer();

        room.send(new DICE_VALUE(itemId, true, -1));

        // Send spinning animation to room
        //item.setCustomData("1");
        //item.updateStatus();

        item.setRequiresUpdate(true);

        GameScheduler.getInstance().getService().schedule(new DiceTask(item), 2, TimeUnit.SECONDS);
    }
}