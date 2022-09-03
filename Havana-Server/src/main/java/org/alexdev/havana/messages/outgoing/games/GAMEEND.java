package org.alexdev.havana.messages.outgoing.games;

import org.alexdev.havana.game.games.GameManager;
import org.alexdev.havana.game.games.enums.GameType;
import org.alexdev.havana.game.games.player.GamePlayer;
import org.alexdev.havana.game.games.player.GameTeam;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.Map;

public class GAMEEND extends MessageComposer {
    private final GameType gameType;
    private final Map<Integer, GameTeam> teams;

    public GAMEEND(GameType game, Map<Integer, GameTeam> teams) {
        this.gameType = game;
        this.teams = teams;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(GameManager.getInstance().getRestartSeconds(this.gameType));
        response.writeInt(this.teams.size());

        for (GameTeam team : this.teams.values()) {
            var players = team.getPlayers();
            response.writeInt(players.size());

            if (players.size() > 0) {
                for (GamePlayer gamePlayer : players) {
                    response.writeInt(gamePlayer.getObjectId());
                    response.writeString(gamePlayer.getPlayer().getDetails().getName());
                    response.writeInt(gamePlayer.getScore());
                }

                response.writeInt(team.getScore());
            }
        }
    }

    @Override
    public short getHeader() {
        return 248;
    }
}
