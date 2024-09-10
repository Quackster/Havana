package org.alexdev.havana.messages.flash.outgoing;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class HOME_ROOM extends MessageComposer {
    private final int roomId;

    public HOME_ROOM(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.roomId);
    }

    @Override
    public short getHeader() {
        return 455;
    }
}
