package org.alexdev.havana.game.room.models;

import org.alexdev.havana.game.room.models.triggers.*;
import org.alexdev.havana.game.triggers.GenericTrigger;

public enum RoomModelTriggerType {
    FLAT_TRIGGER(new FlatTrigger()),
    BATTLEBALL_LOBBY_TRIGGER(new BattleballLobbyTrigger()),
    SNOWSTORM_LOBBY_TRIGGER(new SnowStormLobbyTrigger()),
    SPACE_CAFE_TRIGGER(new SpaceCafeTrigger()),
    HABBO_LIDO_TRIGGER(new HabboLidoTrigger()),
    ROOFTOP_RUMBLE_TRIGGER(new RooftopRumbleTrigger()),
    DIVING_DECK_TRIGGER(new DivingDeckTrigger()),
    INFOBUS_PARK(new InfobusParkTrigger()),
    INFOBUS_POLL(new InfobusPollTrigger()),
    NONE(null);

    private GenericTrigger roomTrigger;

    RoomModelTriggerType(GenericTrigger trigger) {
        this.roomTrigger = trigger;
    }

    public GenericTrigger getRoomTrigger() {
        return roomTrigger;
    }
}
