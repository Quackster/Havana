package org.alexdev.havana.messages.incoming.rooms.user;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class STOP implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        String stopWhat = reader.contents();

        if (stopWhat.equals("Dance")) {
            player.getRoomUser().stopDancing();
        }

        if (stopWhat.equals("CarryItem")) {
            player.getRoomUser().stopCarrying();
        }

        player.getRoomUser().getTimerManager().resetRoomTimer();
    }
}
