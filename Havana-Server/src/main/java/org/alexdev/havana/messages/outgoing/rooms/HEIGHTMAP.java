package org.alexdev.havana.messages.outgoing.rooms;

import org.alexdev.havana.game.room.models.RoomModel;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class HEIGHTMAP extends MessageComposer {
    private final String heightmap;

    public HEIGHTMAP(String heightmap) {
        this.heightmap = heightmap;
    }

    public HEIGHTMAP(RoomModel roomModel) {
        this.heightmap = roomModel.getHeightmap();
    }

    @Override
    public void compose(NettyResponse response) {
        response.write(this.heightmap);
    }

    @Override
    public short getHeader() {
        return 31; // "@_"
    }
}
