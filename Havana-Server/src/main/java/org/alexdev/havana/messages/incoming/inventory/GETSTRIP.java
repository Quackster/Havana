package org.alexdev.havana.messages.incoming.inventory;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class GETSTRIP implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int stripId = reader.readInt();
        String stripView = null;

        if (stripId == 0) {
            stripView = "last";
        }

        if (stripId == 1) {
            stripView = "next";
        }

        if (stripId == 2) {
            stripView = "prev";
        }

        if (stripId == 3) {
            stripView = "new";
        }

        if (stripId == 4) {
            stripView = "current";
        }

        if (stripView == null) {
            return;
        }

        player.getInventory().getView(stripView);
    }
}
