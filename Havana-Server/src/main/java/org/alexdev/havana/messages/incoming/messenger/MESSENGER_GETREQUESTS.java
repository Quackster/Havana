package org.alexdev.havana.messages.incoming.messenger;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.messenger.FRIEND_REQUESTS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class MESSENGER_GETREQUESTS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        player.send(new FRIEND_REQUESTS(player.getMessenger().getRequests()));
    }
}
