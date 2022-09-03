package org.alexdev.havana.messages.outgoing.effects;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class AVATAR_EFFECT_EXPIRED extends MessageComposer {
    private final int effectId;

    public AVATAR_EFFECT_EXPIRED(int effectId) {
        this.effectId = effectId;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.effectId);
    }

    @Override
    public short getHeader() {
        return 463;
    }
}
