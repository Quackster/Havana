package org.alexdev.havana.messages.incoming.messenger;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.messenger.FRIENDS_UPDATE;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class FRIENDLIST_UPDATE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        player.send(new FRIENDS_UPDATE(player, player.getMessenger()));
    }
}
