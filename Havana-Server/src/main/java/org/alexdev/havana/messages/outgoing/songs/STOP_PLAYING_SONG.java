package org.alexdev.havana.messages.outgoing.songs;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class STOP_PLAYING_SONG extends MessageComposer {
    private final int id;

    public STOP_PLAYING_SONG(int id) {
        this.id = id;
    }

    @Override
    public void compose(NettyResponse response) {

    }

    @Override
    public short getHeader() {
        return 494;
    }
}
