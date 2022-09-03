package org.alexdev.havana.messages.outgoing.rooms.items;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class CHANGESTATUS extends MessageComposer {
    private final int status;
    private final int itemId;

    public CHANGESTATUS(int itemId, int status) {
        this.itemId = itemId;
        this.status = status;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.itemId);
        response.writeInt(this.status);
    }

    @Override
    public short getHeader() {
        return 312;
    }
}
