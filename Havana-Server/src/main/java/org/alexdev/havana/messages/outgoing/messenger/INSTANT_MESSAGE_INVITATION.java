package org.alexdev.havana.messages.outgoing.messenger;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class INSTANT_MESSAGE_INVITATION extends MessageComposer {
    private final int userId;
    private final String message;

    public INSTANT_MESSAGE_INVITATION(int userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.userId);
        response.writeString(this.message);
    }

    @Override
    public short getHeader() {
        return 135;
    }
}
