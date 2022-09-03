package org.alexdev.havana.game.games;

import org.alexdev.havana.game.games.enums.GameObjectType;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public abstract class GameObject {
    private int id;
    private GameObjectType gameObjectType;

    public GameObject(int id, GameObjectType gameObjectType) {
        this.id = id;
        this.gameObjectType = gameObjectType;
    }

    public abstract void serialiseObject(NettyResponse response);

    /**
     * Get the game event type for the update loop
     *
     * @return the event type
     */
    public GameObjectType getGameObjectType() {
        return gameObjectType;
    }

    /**
     * Get the id of this object
     *
     * @return the object id
     */
    public int getId() {
        return id;
    }
}
