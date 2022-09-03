package org.alexdev.havana.messages.incoming.rooms.user;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class QUIT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        // Remove authentication values when user manually leaves
        player.getRoomUser().setAuthenticateTelporterId(-1);
        player.getRoomUser().setAuthenticateId(-1);

        player.getRoomUser().getRoom().getEntityManager().leaveRoom(player, false);
    }
}
