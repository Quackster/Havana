package org.alexdev.havana.messages.incoming.rooms;

import org.alexdev.havana.game.games.player.GamePlayer;
import org.alexdev.havana.game.games.snowstorm.SnowStormGame;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.games.FULLGAMESTATUS;
import org.alexdev.havana.messages.outgoing.rooms.FLOOR_MAP;
import org.alexdev.havana.messages.outgoing.rooms.HEIGHTMAP;
import org.alexdev.havana.messages.outgoing.rooms.HEIGHTMAP_UPDATE;
import org.alexdev.havana.messages.outgoing.rooms.OBJECTS_WORLD;
import org.alexdev.havana.messages.outgoing.rooms.user.USER_OBJECTS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.List;

public class G_HMAP implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        GamePlayer gamePlayer = player.getRoomUser().getGamePlayer();

        if (gamePlayer != null && gamePlayer.getGame() instanceof SnowStormGame) {
            SnowStormGame game = (SnowStormGame) gamePlayer.getGame();
            player.send(new HEIGHTMAP(game.getMap().getHeightMap()));
        } else {
            player.send(new HEIGHTMAP(player.getRoomUser().getRoom().getModel()));
        }

        if (gamePlayer != null) {
            player.send(new FULLGAMESTATUS(gamePlayer.getGame()));

            if (gamePlayer.getGame() instanceof SnowStormGame) {
                SnowStormGame game = (SnowStormGame) gamePlayer.getGame();
                player.send(new OBJECTS_WORLD(game.getMap().getCompiledItems()));
            }

            return;
        }

        if (player.getNetwork().isFlashConnection()) {
            player.send(new FLOOR_MAP(player.getRoomUser().getRoom().getModel()));

            if (!player.getRoomUser().getRoom().isPublicRoom()) {
                player.send(new HEIGHTMAP_UPDATE(player.getRoomUser().getRoom(), player.getRoomUser().getRoom().getModel()));
            }

            player.send(new USER_OBJECTS(List.of()));
        }
    }
}
