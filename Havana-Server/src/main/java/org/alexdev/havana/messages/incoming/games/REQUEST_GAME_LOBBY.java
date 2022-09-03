package org.alexdev.havana.messages.incoming.games;

import org.alexdev.havana.game.games.enums.GameType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.concurrent.ThreadLocalRandom;

public class REQUEST_GAME_LOBBY implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (!player.isLoggedIn() || player.getRoomUser().getGamePlayer() != null) {
            return;
        }

        if (player.getRoomUser().getLastLobbyRedirection() == null) {
            if (ThreadLocalRandom.current().nextBoolean()) {
                player.getRoomUser().setLastLobbyRedirection(GameType.BATTLEBALL);
            } else {
                player.getRoomUser().setLastLobbyRedirection(GameType.SNOWSTORM);
            }
        } else {
            if (player.getRoomUser().getLastLobbyRedirection() == GameType.BATTLEBALL) {
                player.getRoomUser().setLastLobbyRedirection(GameType.SNOWSTORM);
            } else {
                player.getRoomUser().setLastLobbyRedirection(GameType.BATTLEBALL);
            }
        }

        RoomManager.getInstance().getRoomByModel(player.getRoomUser().getLastLobbyRedirection().getLobbyModel()).forward(player, false);
    }
}
