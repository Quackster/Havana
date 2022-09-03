package org.alexdev.havana.messages.outgoing.trade;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class TRADE_CLOSE extends MessageComposer {
    private int userClosedId;

    public TRADE_CLOSE(int userClosedId) {
        this.userClosedId = userClosedId;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.userClosedId);
    }

    @Override
    public short getHeader() {
        return 110; // "An"
    }
}
