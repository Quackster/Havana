package org.alexdev.havana.game.games;

import org.alexdev.havana.game.games.enums.GameEventType;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public abstract class GameEvent {
    private GameEventType gameEventType;

    public GameEvent(GameEventType gameEventType) {
        this.gameEventType = gameEventType;
    }

    public abstract void serialiseEvent(NettyResponse response);

    /**
     * Get the game event type for the update loop
     *
     * @return the event type
     */
    public GameEventType getGameEventType() {
        return gameEventType;
    }
}
