package org.alexdev.havana.messages.incoming.wobblesquabble;

import org.alexdev.havana.game.games.wobblesquabble.WobbleSquabbleManager;
import org.alexdev.havana.game.games.wobblesquabble.WobbleSquabbleMove;
import org.alexdev.havana.game.games.wobblesquabble.WobbleSquabblePlayer;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class PTM implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (!WobbleSquabbleManager.getInstance().isPlaying(player)) {
            return;
        }

        int moveId = reader.readInt();

        if (moveId < 0 || moveId > 8) {
            return;
        }

        WobbleSquabblePlayer wsPlayer = WobbleSquabbleManager.getInstance().getPlayer(player);

        if (wsPlayer == null || WobbleSquabbleMove.getMove(moveId) == null) {
            return;
        }

        wsPlayer.setMove(WobbleSquabbleMove.getMove(moveId));
        wsPlayer.setRequiresUpdate(true);
    }
}
