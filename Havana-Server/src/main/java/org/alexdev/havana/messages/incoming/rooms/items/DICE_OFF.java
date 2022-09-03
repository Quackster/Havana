package org.alexdev.havana.messages.incoming.rooms.items;

import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.rooms.items.DICE_VALUE;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;


public class DICE_OFF implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        int itemId = reader.readInt();
        Item item = room.getItemManager().getById(itemId);

        if (item == null || !item.hasBehaviour(ItemBehaviour.DICE)) {
            return;
        }

        if (!player.getRoomUser().getPosition().touches(item.getPosition())) {
            return;
        }

        // Return if dice is already being rolled
        if (item.getRequiresUpdate()) {
            return;
        }

        item.setCustomData("0");
        item.updateStatus();
        item.save();

        room.send(new DICE_VALUE(itemId, false, 0));
    }
}