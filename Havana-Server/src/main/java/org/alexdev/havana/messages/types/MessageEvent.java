package org.alexdev.havana.messages.types;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.util.MalformedPacketException;

public interface MessageEvent {
    
    /**
     * Handle the incoming client message.
     *
     * @param player the player
     * @param reader the reader
     */
    void handle(Player player, NettyRequest reader) throws Exception;
}
