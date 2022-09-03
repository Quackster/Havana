package org.alexdev.havana.messages.outgoing.rooms.user;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class USER_USE_OBJECT extends MessageComposer {
    private final int userId;
    private final int carryId;

    public USER_USE_OBJECT(int userId, int carryId) {
        this.userId = userId;
        this.carryId = carryId;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.userId);
        response.writeInt(this.carryId);
    }

    @Override
    public short getHeader() {
        return 488;
    }
}
