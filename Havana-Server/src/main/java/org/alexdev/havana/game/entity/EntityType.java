package org.alexdev.havana.game.entity;

import org.alexdev.havana.game.player.Player;

public enum EntityType {
    PLAYER(Player.class, 1),
    PET(Entity.class, 2),
    BOT(Entity.class, 3);
    
    Class<? extends Entity> clazz;
    int typeId;
    
    EntityType(Class<? extends Entity> clazz, int typeId) {
        this.clazz = clazz;
        this.typeId = typeId;
    }

    public Class<? extends Entity> getEntityClass() {
        return clazz;
    }

    public int getTypeId() {
        return this.typeId;
    }
}