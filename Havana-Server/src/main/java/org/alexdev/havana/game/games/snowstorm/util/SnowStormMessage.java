package org.alexdev.havana.game.games.snowstorm.util;

import org.alexdev.havana.game.games.player.GamePlayer;
import org.alexdev.havana.game.games.snowstorm.SnowStormGame;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.util.MalformedPacketException;

public interface SnowStormMessage {
    void handle(NettyRequest request, SnowStormGame snowStormGame, GamePlayer gamePlayer) throws MalformedPacketException;
}
