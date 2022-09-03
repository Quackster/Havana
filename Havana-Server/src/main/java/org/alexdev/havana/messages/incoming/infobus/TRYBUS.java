package org.alexdev.havana.messages.incoming.infobus;

import org.alexdev.havana.game.infobus.InfobusManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.infobus.CANNOT_ENTER_BUS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class TRYBUS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        // Do not process public room items
        if (!player.getRoomUser().getRoom().isPublicRoom()) {
            return;
        }

        if (!InfobusManager.getInstance().isDoorOpen()) {
            player.send(new CANNOT_ENTER_BUS("The Infobus is closed, there is no event right now. Please check back later."));
            return;
        }

        player.getRoomUser().walkTo(
                InfobusManager.getInstance().getDoorX(),
                InfobusManager.getInstance().getDoorY()); // Walk to enter square
    }
}
