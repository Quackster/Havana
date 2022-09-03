package org.alexdev.havana.messages.outgoing.effects;

import org.alexdev.havana.game.effects.Effect;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class AVATAR_EFFECT_ADDED extends MessageComposer {
    private final Effect effect;

    public AVATAR_EFFECT_ADDED(Effect effect) {
        this.effect = effect;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.effect.getEffectId());
        response.writeInt(this.effect.getTimeLeft());
    }

    @Override
    public short getHeader() {
        return 461;
    }
}
