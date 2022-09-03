package org.alexdev.havana.messages.outgoing.guides;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class INIT_TUTOR_SERVICE_STATUS extends MessageComposer {
    private final int status;

    public INIT_TUTOR_SERVICE_STATUS(int status) {
        this.status = status;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.status);
    }

    @Override
    public short getHeader() {
        return 425; // "Fi"
    }
}
