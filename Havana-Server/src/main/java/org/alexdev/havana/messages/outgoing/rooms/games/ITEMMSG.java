package org.alexdev.havana.messages.outgoing.rooms.games;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class ITEMMSG extends MessageComposer {
    private final String[] commands;

    public ITEMMSG(String[] commands) {
        this.commands = commands;
    }

    @Override
    public void compose(NettyResponse response) {
        response.write(String.join(Character.toString((char)13), this.commands));
    }

    @Override
    public short getHeader() {
        return 144; // "BP"
    }
}
