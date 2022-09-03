package org.alexdev.havana.messages.outgoing.games;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class GAMELOCATION extends MessageComposer {
    @Override
    public void compose(NettyResponse response) {
        response.writeInt(-1);
    }

    @Override
    public short getHeader() {
        return 241; // "Cq"
    }
}
