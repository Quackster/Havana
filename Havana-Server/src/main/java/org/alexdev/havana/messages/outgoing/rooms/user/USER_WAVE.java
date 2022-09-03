package org.alexdev.havana.messages.outgoing.rooms.user;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class USER_WAVE extends MessageComposer {
    private final int instanceId;

    public USER_WAVE(int instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.instanceId);
    }

    @Override
    public short getHeader() {
        return 481;
    }
}
