package org.alexdev.havana.messages.outgoing.rooms.items;

import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class PLACE_FLOORITEM extends MessageComposer {
    private final Item item;

    public PLACE_FLOORITEM(Item item) {
        this.item = item;
    }

    @Override
    public void compose(NettyResponse response) {
        this.item.serialise(response);
    }

    @Override
    public short getHeader() {
        return 93; // "A]"
    }
}
