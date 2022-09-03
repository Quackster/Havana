package org.alexdev.havana.messages.outgoing.games;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class PLAYERREJOINED extends MessageComposer {
    private final int instanceId;

    public PLAYERREJOINED(int instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.instanceId);
    }

    @Override
    public short getHeader() {
        return 245; // "Cu"
    }
}
