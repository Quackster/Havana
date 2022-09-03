package org.alexdev.havana.messages.outgoing.trade;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class TRADEACCEPT extends MessageComposer {
    private final int userId;
    private final boolean status;

    public TRADEACCEPT(int id, boolean status) {
        this.userId = id;
        this.status = status;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.userId);
        response.writeBool(this.status);
    }

    @Override
    public short getHeader() {
        return 109;
    }
}
