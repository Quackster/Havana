package org.alexdev.havana.messages.outgoing.rooms.pool;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class JUMPDATA extends MessageComposer {
    private final int instanceId;
    private final String divingHandle;

    public JUMPDATA(int instanceId, String divingHandle) {
        this.instanceId = instanceId;
        this.divingHandle = divingHandle;
    }

    @Override
    public void compose(NettyResponse response) {
        response.write(this.instanceId, (char)13);
        response.write(this.divingHandle);
    }

    @Override
    public short getHeader() {
        return 74; // "AJ"
    }
}
