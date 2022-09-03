package org.alexdev.havana.messages.outgoing.handshake;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class UniqueIDMessageEvent extends MessageComposer {
    private final String uuid;

    public UniqueIDMessageEvent(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeString(this.uuid);
    }

    @Override
    public short getHeader() {
        return 439;
    }
}
