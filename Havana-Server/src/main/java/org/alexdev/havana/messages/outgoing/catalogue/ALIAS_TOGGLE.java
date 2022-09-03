package org.alexdev.havana.messages.outgoing.catalogue;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class ALIAS_TOGGLE extends MessageComposer {
    @Override
    public void compose(NettyResponse response) {
        response.writeBool(false);
    }

    @Override
    public short getHeader() {
        return 297; // "Di"
    }
}
