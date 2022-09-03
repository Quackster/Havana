package org.alexdev.havana.messages.outgoing.rooms.settings;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class GOTO_FLAT extends MessageComposer {
    private final int roomId;
    private final String roomName;

    public GOTO_FLAT(int roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.roomId);
        response.writeString(this.roomName);
    }

    @Override
    public short getHeader() {
        return 59; // "@{"
    }
}
