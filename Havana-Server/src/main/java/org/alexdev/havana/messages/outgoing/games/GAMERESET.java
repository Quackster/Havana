package org.alexdev.havana.messages.outgoing.games;

import org.alexdev.havana.game.games.Game;
import org.alexdev.havana.game.games.GameObject;
import org.alexdev.havana.game.games.enums.GameType;
import org.alexdev.havana.game.games.player.GamePlayer;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.List;

public class GAMERESET extends MessageComposer {
    private final Game game;
    private int timeUntilGameStart;
    private List<GamePlayer> gamePlayerList;

    public GAMERESET(int timeUntilGameStart, List<GamePlayer> gamePlayerList, Game game) {
        this.timeUntilGameStart = timeUntilGameStart;
        this.gamePlayerList = gamePlayerList;
        this.game = game;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.timeUntilGameStart);

        if (this.game.getGameType() == GameType.BATTLEBALL) {
            response.writeInt(this.gamePlayerList.size());

            for (GamePlayer gamePlayer : this.gamePlayerList) {
                response.writeInt(gamePlayer.getObjectId());
                response.writeInt(gamePlayer.getPlayer().getRoomUser().getPosition().getX());
                response.writeInt(gamePlayer.getPlayer().getRoomUser().getPosition().getY());
                response.writeInt(gamePlayer.getPlayer().getRoomUser().getPosition().getRotation());
            }
        } else {
            response.writeInt(this.game.getObjects().size());

            for (GameObject gamePlayer : this.game.getObjects()) {
                // -- System.out.println("OBJECT: " +gamePlayer.getGameObjectType().name());
                gamePlayer.serialiseObject(response);
            }
        }
    }

    @Override
    public short getHeader() {
        return 249;
    }
}
