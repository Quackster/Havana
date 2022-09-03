package org.alexdev.havana.messages.outgoing.rooms.user;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class RESPECT_NOTIFICATION extends MessageComposer {
    private final int userId;
    private final int respectPoints;

    public RESPECT_NOTIFICATION(int userId, int respectPoints) {
        this.userId = userId;
        this.respectPoints = respectPoints;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.userId);
        response.writeInt(this.respectPoints);
    }

    @Override
    public short getHeader() {
        return 440; // "Fx"
    }
}
