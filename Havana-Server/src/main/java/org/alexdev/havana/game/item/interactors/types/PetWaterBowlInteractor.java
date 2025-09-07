package org.alexdev.havana.game.item.interactors.types;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.pets.Pet;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.entities.RoomEntity;
import org.alexdev.havana.game.triggers.GenericTrigger;

public class PetWaterBowlInteractor extends GenericTrigger {
    public void onInteract(Player player, Room room, Item item, int status) {
        InteractionType.DEFAULT.getTrigger().onInteract(player, room, item, status);
    }

    @Override
    public void onItemPlaced(Player player, Room room, Item item) {
        if (item.getCustomData().isBlank()) {
            item.setCustomData("5");
            item.updateStatus();
            item.save();
        }
    }

    @Override
    public void onEntityStop(Entity entity, RoomEntity roomEntity, Item item, boolean isRotation) {
        if (entity.getType() != EntityType.PET) {
            return;
        }

        var pet = (Pet) entity;
        var front = item.getPosition().getSquareInFront();

        pet.getRoomUser().look(front, true);
        pet.getRoomUser().getTask().startDrinking();
    }

    public void onItemPickup(Player player, Room room, Item item) {
        cancelPetsDrinking(room, item.getPosition());
    }

    public void onItemMoved(Player player, Room room, Item item, boolean isRotation, Position oldPosition, Item itemBelow, Item itemAbove) {
        cancelPetsDrinking(room, oldPosition);
    }

    private void cancelPetsDrinking(Room room, Position position) {
        room.getMapping().getTile(position).getEntities().forEach(x -> {
            if (x.getType() == EntityType.PET) {
                ((Pet)x).getRoomUser().getTask().drinkingComplete(false);
            }
        });
    }

    /*
    @Override
    public void onEntityLeave(Entity entity, RoomEntity roomEntity, Item item) {
        if (entity.getType() != EntityType.PET) {
            return;
        }

        var pet = (Pet) entity;
        pet.getRoomUser().getTask().eatingComplete();
    }

     */
}
