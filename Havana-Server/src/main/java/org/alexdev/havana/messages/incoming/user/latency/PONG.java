package org.alexdev.havana.messages.incoming.user.latency;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class PONG implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        // Nice pong :^)
        player.setPingOK(true);

        if (!player.isLoggedIn()) {
            return;
        }

        player.getAchievementManager().processAchievements(player, false);
    }
}
