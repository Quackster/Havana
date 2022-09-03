package org.alexdev.havana.messages.outgoing.rooms.pool;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class OPEN_UIMAKOPPI extends MessageComposer {

    @Override
    public void compose(NettyResponse response) {

    }

    @Override
    public short getHeader() {
        return 96; // "A`"
    }
}
