package org.alexdev.havana.messages.outgoing.tutorial;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class INVITE_FOLLOW_FAILED extends MessageComposer {
    @Override
    public void compose(NettyResponse response) {

    }

    @Override
    public short getHeader() {
        return 359;
    }
}
