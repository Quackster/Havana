package org.alexdev.havana.messages.outgoing.moderation;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class MODERATOR_ALERT extends MessageComposer {
    private final String message;

    public MODERATOR_ALERT(String message){
        this.message = message;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeString(this.message);
        response.writeString("");
    }

    @Override
    public short getHeader() {
        return 161;
    }
}
