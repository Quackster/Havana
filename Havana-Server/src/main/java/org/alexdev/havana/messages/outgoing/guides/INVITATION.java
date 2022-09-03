package org.alexdev.havana.messages.outgoing.guides;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class INVITATION extends MessageComposer {
    private final Integer userId;
    private final String username;

    public INVITATION(Integer userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeString(this.userId);
        response.writeString(this.username);
    }

    @Override
    public short getHeader() {
        return 355; // "Ec"
    }
}
