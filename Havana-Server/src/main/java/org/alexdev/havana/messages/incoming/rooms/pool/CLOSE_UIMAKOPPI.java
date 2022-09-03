package org.alexdev.havana.messages.incoming.rooms.pool;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.handlers.PoolHandler;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class CLOSE_UIMAKOPPI implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.getData().getModel().equals("pool_a") && !room.getData().getModel().equals("md_a")) {
            return;
        }

        PoolHandler.exitBooth(player);
    }
}
