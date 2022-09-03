package org.alexdev.havana.messages.outgoing.songs;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class START_PLAYING_SONG extends MessageComposer {
    private final int id;

    public START_PLAYING_SONG(int id) {
        this.id = id;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.id);
    }

    @Override
    public short getHeader() {
        return 493;
    }
}
