package org.alexdev.havana.messages.outgoing.rooms.games;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class CLOSEGAMEBOARD extends MessageComposer {
    private final String gameId;
    private final String type;

    public CLOSEGAMEBOARD(String gameId, String type) {
        this.gameId = gameId;
        this.type = type;
    }

    @Override
    public void compose(NettyResponse response) {
        response.write(this.gameId, (char)9);
        response.write(this.type, (char)9);
        //response.write("xo", (char)9);
    }

    @Override
    public short getHeader() {
        return 146; // "BQ"
    }
}
