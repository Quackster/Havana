package org.alexdev.havana.messages.incoming.handshake;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class VERSIONCHECK implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        
    }
}
