package org.alexdev.havana.messages.types;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public abstract class PlayerMessageComposer extends MessageComposer {
    private Player player;

    /**
     * The player currently handling the packet for
     *
     * @return the player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player currently being handled for.
     *
     * @param player the player
     */
    public void setPlayer(Player player) throws Exception {
        this.player = player;
    }
}
