package org.alexdev.havana.messages.outgoing.ecotron;

import org.alexdev.havana.game.ecotron.EcotronManager;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class RECYCLER_PRIZES extends MessageComposer {
    @Override
    public void compose(NettyResponse response) {
        EcotronManager.getInstance().appendRewards(response);
    }

    @Override
    public short getHeader() {
        return 506;
    }
}
