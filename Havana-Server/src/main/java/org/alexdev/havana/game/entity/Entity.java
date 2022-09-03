package org.alexdev.havana.game.entity;

import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.room.entities.RoomEntity;

public abstract class Entity {

    /**
     * Checks for permission.
     *
     * @param permission the permission
     * @return true, if successful
     */
    public abstract boolean hasFuse(Fuseright permission);
    
    /**
     * Gets the details.
     *
     * @return the details
     */
    public abstract PlayerDetails getDetails();

    /**
     * Gets the room user.
     *
     * @return the room user
     */
    public abstract RoomEntity getRoomUser();
    
    /**
     * Gets the type.
     *
     * @return the type
     */
    public abstract EntityType getType();
    
    /**
     * Dispose.
     */
    public abstract void dispose();

    public boolean isMuted() { return false; }
}
