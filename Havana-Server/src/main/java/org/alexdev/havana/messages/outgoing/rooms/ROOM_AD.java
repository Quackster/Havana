package org.alexdev.havana.messages.outgoing.rooms;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class ROOM_AD extends MessageComposer {
    @Override
    public void compose(NettyResponse response) {
        response.write(0);
    }

    @Override
    public short getHeader() {
        return 208; // "CP"
    }
}
