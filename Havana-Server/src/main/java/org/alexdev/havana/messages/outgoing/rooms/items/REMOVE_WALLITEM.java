package org.alexdev.havana.messages.outgoing.rooms.items;

import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class REMOVE_WALLITEM extends MessageComposer {
    private final Item item;

    public REMOVE_WALLITEM(Item item) {
        this.item = item;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeString(this.item.getVirtualId());
    }

    @Override
    public short getHeader() {
        return 84; // "AT"
    }
}
