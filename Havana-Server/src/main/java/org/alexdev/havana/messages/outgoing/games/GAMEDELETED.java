package org.alexdev.havana.messages.outgoing.games;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class GAMEDELETED extends MessageComposer {
    private int gameId;

    public GAMEDELETED(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.gameId);
    }

    @Override
    public short getHeader() {
        return 237; // "Cm"
    }
}
