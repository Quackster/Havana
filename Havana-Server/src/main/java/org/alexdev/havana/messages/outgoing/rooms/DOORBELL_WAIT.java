package org.alexdev.havana.messages.outgoing.rooms;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class DOORBELL_WAIT extends MessageComposer {
    private final String username;

    public DOORBELL_WAIT() {
        this.username = "";
    }

    public DOORBELL_WAIT(String username) {
        this.username = username;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeString(this.username);
    }

    @Override
    public short getHeader() {
        return 91; // "A["
    }
}
