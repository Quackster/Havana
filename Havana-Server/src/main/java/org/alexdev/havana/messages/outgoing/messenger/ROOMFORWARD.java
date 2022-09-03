package org.alexdev.havana.messages.outgoing.messenger;

import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class ROOMFORWARD extends MessageComposer {
    private final boolean isPublic;
    private int roomId;

    public ROOMFORWARD(boolean isPublic, int roomId) {
        this.isPublic = isPublic;
        this.roomId = roomId;
    }


    @Override
    public void compose(NettyResponse response) {
        response.writeBool(this.isPublic);
        response.writeInt(this.roomId);
    }

    @Override
    public short getHeader() {
        return 286; // "D^"
    }
}
