package org.alexdev.havana.messages.outgoing.rooms;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class ROOM_URL extends MessageComposer {

    @Override
    public void compose(NettyResponse response) {
        response.writeString("/client/");
    }

    @Override
    public short getHeader() {
        return 166; // "Bf"
    }
}
