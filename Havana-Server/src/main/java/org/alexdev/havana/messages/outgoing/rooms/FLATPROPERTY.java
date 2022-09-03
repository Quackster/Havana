package org.alexdev.havana.messages.outgoing.rooms;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class FLATPROPERTY extends MessageComposer {
    private final String property;
    private final String value;

    public FLATPROPERTY(String property, Object value) {
        this.property = property;
        this.value = String.valueOf(value);
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeString(this.property);
        response.writeString(this.value);
    }

    @Override
    public short getHeader() {
        return 46; // "@n"
    }
}
