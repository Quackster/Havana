package org.alexdev.havana.game.item.interactors.types;

import org.alexdev.havana.dao.mysql.PetDao;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.pets.Pet;
import org.alexdev.havana.game.pets.PetDetails;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.triggers.GenericTrigger;

public class PetNestInteractor extends GenericTrigger {
    @Override
    public void onItemPlaced(Player player, Room room, Item item) {
        PetDetails petDetails = PetDao.getPetDetails(item.getDatabaseId());

        if (petDetails != null) {
            Pet pet = this.addPet(room, petDetails, item.getPosition());

            PetDao.saveCoordinates(petDetails.getId(),
                    pet.getRoomUser().getPosition().getX(),
                    pet.getRoomUser().getPosition().getY(),
                    pet.getRoomUser().getPosition().getRotation());
        }
    }

    @Override
    public void onItemPickup(Player player, Room room, Item item) {
        PetDetails petDetails = PetDao.getPetDetails(item.getDatabaseId());

        if (petDetails == null) {
            return;
        }

        int petId = petDetails.getId();
        Pet pet = (Pet)room.getEntityManager().getById(petId, EntityType.PET);

        if (pet == null) {
            return;
        }

        room.getEntityManager().leaveRoom(pet, false);
    }

    /*@Override
    public void onEntityStop(Entity entity, RoomEntity roomEntity, Item item, boolean isRotation) {
        if (entity.getType() != EntityType.PET) {
            return;
        }

        Pet pet = (Pet) entity;

        if (pet.getDetails().getItemId() != item.getDatabaseId()) {
            return;
        }

        if (pet.getRoomUser().getItem() == null || pet.getAction() != PetAction.SLEEP) {
            return;
        }

        pet.getRoomUser().setStatus(StatusType.SLEEP, StringUtil.format(pet.getRoomUser().getPosition().getZ()) + " null");
        pet.getRoomUser().setNeedsUpdate(true);

        pet.setAction(PetAction.SLEEP);
        pet.setActionDuration(ThreadLocalRandom.current().nextInt(20, 60));
    }*/

    /**
     * Add a pet by given pet id.
     *
     * @param room the room to add the pet to
     * @param petDetails the details of the pet
     * @param position the position of the pet
     *
     * @return the pet instance created
     */
    public Pet addPet(Room room, PetDetails petDetails, Position position) {
        Pet pet = new Pet(petDetails);
        position.setZ(room.getMapping().getTile(position.getX(), position.getY()).getWalkingHeight());

        room.getEntityManager().enterRoom(pet, position);
        room.getMapping().getTile(position).addEntity(pet);

        /*GameScheduler.getInstance().getService().scheduleAtFixedRate(()-> {
            pet.getRoomUser().walkTo(room.getModel().getRandomBound(0), room.getModel().getRandomBound(0));
        }, 0, 5, TimeUnit.SECONDS);*/

        return pet;
    }
}
