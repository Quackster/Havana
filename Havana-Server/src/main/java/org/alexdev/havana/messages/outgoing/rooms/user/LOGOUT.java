package org.alexdev.havana.messages.outgoing.rooms.user;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class LOGOUT extends MessageComposer {
    private final int instanceId;

    public LOGOUT(int instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeString(this.instanceId);
    }

    @Override
    public short getHeader() {
        return 29; // "@]
    }
}
