package org.alexdev.havana.game.triggers;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.entities.RoomEntity;

public abstract class GenericTrigger {
    public void onInteract(Player player, Room room, Item item, int status) { }
    public void onItemPlaced(Player player, Room room, Item item) { }
    public void onItemMoved(Player player, Room room, Item item, boolean isRotation, Position oldPosition, Item itemBelow, Item itemAbove) { }
    public void onItemPickup(Player player, Room room, Item item) { }
    public void onRoomEntry(Entity entity, Room room, boolean firstEntry, Object... customArgs) { }
    public void onRoomLeave(Entity entity, Room room, Object... customArgs) { }
    public void onEntityStep(Entity entity, RoomEntity roomEntity, Item item, Position oldPosition) { }
    public void onEntityLeave(Entity entity, RoomEntity roomEntity, Item item, Position oldPosition) { }
    public void onEntityStop(Entity entity, RoomEntity roomEntity, Item item, boolean isRotation) { }
    public void onEntityLeave(Entity entity, RoomEntity roomEntity, Item item, Object... customArgs) { }
}
