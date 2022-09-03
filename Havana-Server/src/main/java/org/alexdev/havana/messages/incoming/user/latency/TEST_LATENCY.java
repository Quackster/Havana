package org.alexdev.havana.messages.incoming.user.latency;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.user.LATENCY;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class TEST_LATENCY implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int latency = reader.readInt();
        player.send(new LATENCY(latency));
    }
}
