package org.alexdev.havana.messages.outgoing.rooms.pool;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class JUMPINGPLACE_OK extends MessageComposer {
    @Override
    public void compose(NettyResponse response) {

    }

    @Override
    public short getHeader() {
        return 125; // "A}"
    }
}
