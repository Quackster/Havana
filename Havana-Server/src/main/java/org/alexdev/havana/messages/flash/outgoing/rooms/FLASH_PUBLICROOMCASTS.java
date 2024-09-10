package org.alexdev.havana.messages.flash.outgoing.rooms;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class FLASH_PUBLICROOMCASTS extends MessageComposer {
    private final int roomId;
    private final String ccts;

    public FLASH_PUBLICROOMCASTS(int roomId, String ccts) {
        this.roomId = roomId;
        this.ccts = ccts;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.roomId);
        response.writeString(this.ccts);
        response.writeInt(this.roomId);
    }

    @Override
    public short getHeader() {
        return 453;
    }
}
