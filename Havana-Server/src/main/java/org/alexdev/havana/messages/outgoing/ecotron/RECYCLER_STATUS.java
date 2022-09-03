package org.alexdev.havana.messages.outgoing.ecotron;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class RECYCLER_STATUS extends MessageComposer {
    private final int status;

    public RECYCLER_STATUS(int status) {
        this.status = status;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.status);
    }

    @Override
    public short getHeader() {
        return 507;
    }
}
