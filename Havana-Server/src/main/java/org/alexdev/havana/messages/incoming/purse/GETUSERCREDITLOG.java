package org.alexdev.havana.messages.incoming.purse;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.user.currencies.CREDIT_BALANCE;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class GETUSERCREDITLOG implements MessageEvent {

    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        
    }
}
