package org.alexdev.havana.game.games.battleball.events;

import org.alexdev.havana.game.games.GameEvent;
import org.alexdev.havana.game.games.enums.GameEventType;
import org.alexdev.havana.game.games.player.GamePlayer;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class PlayerMoveEvent extends GameEvent {
    private final GamePlayer gamePlayer;
    private final Position nextPosition;

    public PlayerMoveEvent(GamePlayer gamePlayer, Position nextPosition) {
        super(GameEventType.BATTLEBALL_PLAYER_EVENT);
        this.gamePlayer = gamePlayer;
        this.nextPosition = nextPosition;
    }

    @Override
    public void serialiseEvent(NettyResponse response) {
        response.writeInt(this.gamePlayer.getPlayer().getRoomUser().getInstanceId());
        response.writeInt(this.nextPosition.getX());
        response.writeInt(this.nextPosition.getY());
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }
}
