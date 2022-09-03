package org.alexdev.havana.messages.incoming.messenger;

import org.alexdev.havana.game.messenger.MessengerUser;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class MESSENGER_DECLINEBUDDY implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        boolean declineAll = reader.readBoolean();

        if (declineAll) {
            player.getMessenger().declineAllRequests();
            return;
        }

        int amount = reader.readInt();

        for (int i = 0; i < amount; i++) {
            int userId = reader.readInt();

            if (!player.getMessenger().hasRequest(userId)) {
                continue;
            }

            MessengerUser requester = player.getMessenger().getRequest(userId);
            player.getMessenger().declineRequest(requester);
        }
    }
}
