package org.alexdev.havana.messages.incoming.messenger;

import org.alexdev.havana.game.messenger.Messenger;
import org.alexdev.havana.game.messenger.MessengerManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.messenger.MESSENGER_INIT;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class MESSENGERINIT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Messenger messenger = MessengerManager.getInstance().getMessengerData(player.getDetails().getId());
        player.send(new MESSENGER_INIT(player, messenger));
    }
}
