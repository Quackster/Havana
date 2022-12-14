package org.alexdev.havana.messages.outgoing.events;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class ROOMEVENT_TYPES extends MessageComposer {
    private final int count;

    public ROOMEVENT_TYPES(int count) {
        this.count = count;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.count);
    }

    @Override
    public short getHeader() {
        return 368;
    }
}
