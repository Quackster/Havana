package org.alexdev.havana.messages.incoming.games;

import org.alexdev.havana.game.games.Game;
import org.alexdev.havana.game.games.GameManager;
import org.alexdev.havana.game.games.enums.GameState;
import org.alexdev.havana.game.games.player.GamePlayer;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.triggers.GameLobbyTrigger;
import org.alexdev.havana.messages.outgoing.games.GAMEINSTANCE;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class WATCHGAME implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (!(room.getModel().getRoomTrigger() instanceof GameLobbyTrigger)) {
            return;
        }

        if (player.getRoomUser().getGamePlayer() != null) {
            return;
        }

        int gameId = reader.readInt();

        Game game = GameManager.getInstance().getGameById(gameId);

        if (game == null) {
            return;
        }

        GamePlayer gamePlayer = new GamePlayer(player);
        gamePlayer.setGameId(gameId);
        gamePlayer.setSpectator(true);

        player.getRoomUser().setGamePlayer(gamePlayer);

        game.getSpectators().add(gamePlayer);

        if (game.getGameState() == GameState.STARTED) {
            game.sendSpectatorToArena(gamePlayer);
        } else {
            game.send(new GAMEINSTANCE(game));
            game.sendObservers(new GAMEINSTANCE(game));
        }
    }
}
