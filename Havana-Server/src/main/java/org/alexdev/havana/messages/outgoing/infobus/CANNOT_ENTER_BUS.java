package org.alexdev.havana.messages.outgoing.infobus;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class CANNOT_ENTER_BUS extends MessageComposer {
    private final String message;

    public CANNOT_ENTER_BUS(String message) {
        this.message = message;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeString(this.message);
    }

    @Override
    public short getHeader() {
        return 81;
    }
}
