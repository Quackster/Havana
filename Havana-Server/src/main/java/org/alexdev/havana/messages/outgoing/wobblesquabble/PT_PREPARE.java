package org.alexdev.havana.messages.outgoing.wobblesquabble;

import org.alexdev.havana.game.games.wobblesquabble.WobbleSquabblePlayer;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class PT_PREPARE extends MessageComposer {
    private final WobbleSquabblePlayer player1;
    private final WobbleSquabblePlayer player2;

    public PT_PREPARE(WobbleSquabblePlayer player1, WobbleSquabblePlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.player1.getPlayer().getRoomUser().getInstanceId());
        response.writeInt(this.player2.getPlayer().getRoomUser().getInstanceId());
    }

    @Override
    public short getHeader() {
        return 115; // "As"
    }
}
