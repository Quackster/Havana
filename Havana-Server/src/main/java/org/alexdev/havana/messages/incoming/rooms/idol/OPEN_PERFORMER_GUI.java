package org.alexdev.havana.messages.incoming.rooms.idol;

import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.Map;

public class OPEN_PERFORMER_GUI extends MessageComposer {
    private final Map<Item, String> userDisks;

    public OPEN_PERFORMER_GUI(Map<Item, String> userDisks) {
        this.userDisks = userDisks;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.userDisks.size());

        for (var kvp : this.userDisks.entrySet()) {
            response.writeInt(kvp.getKey().getVirtualId());
            response.writeString(kvp.getValue());

        }
    }

    @Override
    public short getHeader() {
        return 491;
    }
}
