package org.alexdev.havana.messages.outgoing.rooms.items;

import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class IDATA extends MessageComposer {
    private String colour;
    private String text;
    private Item item;

    public IDATA(Item item, String colour, String text) {
        this.item = item;
        this.colour = colour;
        this.text = text;
    }

    public IDATA(Item item) {
        this.item = item;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeString(this.item.getVirtualId());

        if (this.item.hasBehaviour(ItemBehaviour.POST_IT)) {
            response.write(this.colour, ' ');
            response.writeString(this.text);
        } else {
            response.writeString(Long.toString(item.getVirtualId()) + " " + item.getCustomData());
        }
    }

    @Override
    public short getHeader() {
        return 48; // "@p"
    }
}

