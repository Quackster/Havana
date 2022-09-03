package org.alexdev.havana.messages.incoming.messenger;

import org.alexdev.havana.game.messenger.MessengerMessage;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.messenger.MESSENGER_MSG;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class MESSENGER_GETMESSAGES implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        for (MessengerMessage offlineMessage : player.getMessenger().getOfflineMessages().values()) {
            player.send(new MESSENGER_MSG(offlineMessage));
        }
    }
}
