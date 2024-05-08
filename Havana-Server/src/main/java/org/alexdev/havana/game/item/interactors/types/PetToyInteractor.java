package org.alexdev.havana.game.item.interactors.types;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.pets.Pet;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.entities.RoomEntity;
import org.alexdev.havana.game.triggers.GenericTrigger;
import org.apache.commons.lang3.StringUtils;

public class PetToyInteractor extends GenericTrigger {
    public void onInteract(Player player, Room room, Item item, int status) {
        InteractionType.DEFAULT.getTrigger().onInteract(player, room, item, status);
    }

    @Override
    public void onEntityStop(Entity entity, RoomEntity roomEntity, Item item, boolean isRotation) {
        if (entity.getType() != EntityType.PET) {
            return;
        }

        var pet = (Pet) entity;
        var front = item.getPosition().getSquareInFront();

        pet.getRoomUser().look(front, true);
        pet.getRoomUser().getTask().startPlay();
    }

    public void onItemPickup(Player player, Room room, Item item) {
        cancelPetsPlaying(room, item.getPosition());
    }

    public void onItemMoved(Player player, Room room, Item item, boolean isRotation, Position oldPosition, Item itemBelow, Item itemAbove) {
        cancelPetsPlaying(room, oldPosition);
    }

    private void cancelPetsPlaying(Room room, Position position) {
        room.getMapping().getTile(position).getEntities().forEach(x -> {
            if (x.getType() == EntityType.PET) {
                ((Pet) x).getRoomUser().getTask().playingComplete(false);
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
