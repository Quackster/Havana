package org.alexdev.havana.messages.outgoing.moderation;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class CRY_RECEIVED extends MessageComposer {
    @Override
    public void compose(NettyResponse response) {
        response.writeString("H");
    }

    @Override
    public short getHeader() {
        return 321;
    }
}
