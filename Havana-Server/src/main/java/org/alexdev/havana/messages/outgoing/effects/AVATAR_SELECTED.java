package org.alexdev.havana.messages.outgoing.effects;

import org.alexdev.havana.game.effects.Effect;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class AVATAR_SELECTED extends MessageComposer {
    private final Effect activatedEffect;

    public AVATAR_SELECTED(Effect activatedEffect) {
        this.activatedEffect = activatedEffect;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.activatedEffect.getEffectId());
    }

    @Override
    public short getHeader() {
        return 464;
    }
}
