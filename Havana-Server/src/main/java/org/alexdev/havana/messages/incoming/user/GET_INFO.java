package org.alexdev.havana.messages.incoming.user;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.user.USER_OBJECT;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class GET_INFO implements MessageEvent {

    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (!player.isLoggedIn()) {
            return;
        }

        player.send(new USER_OBJECT(player.getDetails()));
    }
}
