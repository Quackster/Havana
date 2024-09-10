package org.alexdev.havana.messages.flash.outgoing.navigator;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class FLASH_FAVOURITE_STATUS extends MessageComposer {
    private int roomId;
    private boolean isAdded;

    public FLASH_FAVOURITE_STATUS(int roomId, boolean isAdded) {
        this.roomId = roomId;
        this.isAdded = isAdded;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.roomId);
        response.writeBool(this.isAdded);
    }

    @Override
    public short getHeader() {
        return 459;
    }
}
