package org.alexdev.havana.messages.outgoing.rooms.user;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class USER_SLEEP extends MessageComposer {
    private final int instanceId;
    private final boolean isSleeping;

    public USER_SLEEP(int instanceId, boolean isSleeping) {
        this.instanceId = instanceId;
        this.isSleeping = isSleeping;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.instanceId);
        response.writeBool(this.isSleeping);
    }

    @Override
    public short getHeader() {
        return 486;
    }
}
