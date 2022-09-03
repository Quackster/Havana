package org.alexdev.havana.messages.outgoing.rooms.items;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class SHOWPROGRAM extends MessageComposer {
    private final String[] arguments;
    public SHOWPROGRAM(String[] arguments) {
        this.arguments = arguments;
    }

    @Override
    public void compose(NettyResponse response) {
        response.write(String.join(" ", this.arguments));
    }

    @Override
    public short getHeader() {
        return 71; // "AG"
    }
}
