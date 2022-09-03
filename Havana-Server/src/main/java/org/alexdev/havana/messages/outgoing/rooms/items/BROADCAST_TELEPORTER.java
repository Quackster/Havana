package org.alexdev.havana.messages.outgoing.rooms.items;

import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class BROADCAST_TELEPORTER extends MessageComposer {
    private final Item item;
    private final String name;
    private final boolean disappearUser;

    public BROADCAST_TELEPORTER(Item item, String name, boolean disappearUser) {
        this.item = item;
        this.name = name;
        this.disappearUser = disappearUser;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.item.getVirtualId());
        response.writeString(this.name);
    }

    @Override
    public short getHeader() {
        if (this.disappearUser) {
         return 89; // "AY"
         } else {
        return 92; // "A\"
         }
    }
}
