package org.alexdev.havana.messages.incoming.ecotron;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.ecotron.RECYCLER_STATUS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class GET_RECYCLER_STATUS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        player.send(new RECYCLER_STATUS(1));
    }
}
