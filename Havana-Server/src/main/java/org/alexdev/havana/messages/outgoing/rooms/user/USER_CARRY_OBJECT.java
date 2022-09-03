package org.alexdev.havana.messages.outgoing.rooms.user;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class USER_CARRY_OBJECT extends MessageComposer {
    private final int userId;
    private final int carryId;
    private final String carryName;

    public USER_CARRY_OBJECT(int userId, int carryId, String carryName) {
        this.userId = userId;
        this.carryId = carryId;
        this.carryName = carryName;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.userId);
        response.writeInt(this.carryId);
        response.writeString(this.carryName);
    }

    @Override
    public short getHeader() {
        return 482;
    }
}
