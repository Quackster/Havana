package org.alexdev.havana.messages.outgoing.rooms.items;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class CANNOT_PLACE_STUFF_FROM_STRIP extends MessageComposer {
    private final int reasonCode;

    public CANNOT_PLACE_STUFF_FROM_STRIP(int reasonCode) {
        this.reasonCode = reasonCode;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.reasonCode);
    }

    @Override
    public short getHeader() {
        return 516;
    }
}
