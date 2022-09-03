package org.alexdev.havana.messages.outgoing.rooms.user;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class USER_DANCE extends MessageComposer {
    private final int instanceId;
    private final int danceId;

    public USER_DANCE(int instanceId, int danceId) {
        this.instanceId = instanceId;
        this.danceId = danceId;
    }


    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.instanceId);
        response.writeInt(this.danceId);
    }

    @Override
    public short getHeader() {
        return 480;
    }
}
