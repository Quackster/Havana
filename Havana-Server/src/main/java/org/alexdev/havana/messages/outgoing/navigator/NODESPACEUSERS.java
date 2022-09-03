package org.alexdev.havana.messages.outgoing.navigator;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.List;

public class NODESPACEUSERS extends MessageComposer {
    private final List<Player> players;

    public NODESPACEUSERS(List<Player> players) {
        this.players = players;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(-1); //  tNodeId = string(tConn.GetIntFrom())
        response.writeInt(this.players.size());

        for (Player player : this.players) {
            response.writeString(player.getDetails().getName());
        }
    }

    @Override
    public short getHeader() {
        return 223; // "C_"
    }
}
