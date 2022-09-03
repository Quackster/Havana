package org.alexdev.havana.messages.outgoing.rooms;

import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.List;

public class ITEMS extends MessageComposer {
    private final List<Item> items;

    public ITEMS(Room room) {
        this.items = room.getItemManager().getWallItems();
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.items.size());

        for (Item item : this.items) {
            item.serialise(response);
        }
    }
    @Override
    public short getHeader() {
        return 45; // "@m"
    }
}
