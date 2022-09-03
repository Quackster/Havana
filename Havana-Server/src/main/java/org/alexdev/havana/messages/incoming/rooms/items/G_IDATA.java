package org.alexdev.havana.messages.incoming.rooms.items;

import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.rooms.items.IDATA;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class G_IDATA implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        int itemId = reader.readInt();
        Item item = room.getItemManager().getById(itemId);

        if (item == null) {
            return;
        }

        if (item.hasBehaviour(ItemBehaviour.POST_IT)) {
            String colour = item.getCustomData().substring(0, 6);
            String text = "";

            if (item.getCustomData().length() > 6) {
                text = item.getCustomData().substring(6);
            }

            player.send(new IDATA(item, colour, text));
        } else {
            player.send(new IDATA(item));
        }
    }
}
