package org.alexdev.havana.messages.incoming.handshake;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.handshake.SESSION_PARAMETERS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class GET_SESSION_PARAMETERS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        player.send(new SESSION_PARAMETERS(player.getDetails()));
    }
}
