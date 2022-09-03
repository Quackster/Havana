package org.alexdev.havana.messages.incoming.games;

import org.alexdev.havana.game.games.Game;
import org.alexdev.havana.game.games.player.GamePlayer;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.games.GAMEINSTANCE;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class UNOBSERVEINSTANCE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getObservingGameId() != -1) {
            player.getRoomUser().stopObservingGame();
        }

        /*
        if (player.getRoomUser().getGamePlayer() != null) {
            GamePlayer gamePlayer = player.getRoomUser().getGamePlayer();
            Game game = gamePlayer.getGame();

            if (game != null) {
                if (gamePlayer.getGame().getSpectators().contains(gamePlayer)) {
                    gamePlayer.getGame().leaveGame(gamePlayer);
                }
            }
        }
        */
    }
}
