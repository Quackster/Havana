package org.alexdev.havana.game.pets;

import org.alexdev.havana.dao.mysql.PetDao;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.room.entities.RoomPet;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.util.DateUtil;

import java.util.concurrent.TimeUnit;

public class Pet extends Entity {
    private PetDetails petDetails;
    private RoomPet roomUser;

    public Pet(PetDetails petDetails) {
        this.petDetails = petDetails;
        this.roomUser = new RoomPet(this);
    }

    public int getAge() {
        return (int) TimeUnit.SECONDS.toDays(DateUtil.getCurrentTimeSeconds() - this.petDetails.getBorn());
    }

    public int getHunger() {
        return PetManager.getInstance().getPetStats(this.petDetails.getLastEat(), PetStat.HUNGER);
    }

    public int getThirst() {
        return PetManager.getInstance().getPetStats(this.petDetails.getLastDrink(), PetStat.THIRST);
    }

    public int getHappiness() {
        return PetManager.getInstance().getPetStats(this.petDetails.getLastPlayToy(), PetStat.HAPPINESS);
    }

    public int getEnergy() {
        return PetManager.getInstance().getPetStats(this.petDetails.getLastKip(), PetStat.ENERGY);
    }

    public int getFriendship() {
        return PetManager.getInstance().getPetStats(this.petDetails.getLastPlayUser(), PetStat.FRIENDSHIP);
    }

    public boolean isThirsty() {
        return this.getThirst() <= 2;
    }

    public boolean isHungry() {
        return this.getHunger() <= 3;
    }


    @Override
    public boolean hasFuse(Fuseright permission) {
        return false;
    }

    @Override
    public PetDetails getDetails() {
        return this.petDetails;
    }

    @Override
    public RoomPet getRoomUser() {
        return this.roomUser;
    }

    @Override
    public EntityType getType() {
        return EntityType.PET;
    }

    @Override
    public void dispose() {

    }

    public void saveDetails() {
        PetDao.saveDetails(this.getDetails().getId(), this.getDetails());
    }
}
