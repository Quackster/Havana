package org.alexdev.havana.messages.outgoing.effects;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class USER_AVATAR_EFFECT extends MessageComposer {
    private final int instanceId;
    private final int effectId;

    public USER_AVATAR_EFFECT(int instanceId, int effectId) {
        this.instanceId = instanceId;
        this.effectId = effectId;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.instanceId);
        response.writeInt(this.effectId);
    }

    @Override
    public short getHeader() {
        return 485;
    }
}
