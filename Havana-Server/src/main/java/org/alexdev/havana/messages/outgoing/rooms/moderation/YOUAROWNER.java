package org.alexdev.havana.messages.outgoing.rooms.moderation;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class YOUAROWNER extends MessageComposer {
    @Override
    public void compose(NettyResponse response) {

    }

    @Override
    public short getHeader() {
        return 47; // "@o"
    }
}
