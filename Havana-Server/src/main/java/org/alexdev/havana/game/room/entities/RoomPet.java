package org.alexdev.havana.game.room.entities;

import org.alexdev.havana.dao.mysql.PetDao;
import org.alexdev.havana.game.GameScheduler;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.pets.Pet;
import org.alexdev.havana.game.pets.PetAction;
import org.alexdev.havana.game.pets.PetManager;
import org.alexdev.havana.game.pets.PetType;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.StringUtil;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RoomPet extends RoomEntity {
    private Pet pet;
    private Item item;
    private int lastActionLength;

    public RoomPet(Entity entity) {
        super(entity);
        this.pet = (Pet) entity;
        this.item = null;
    }

    /**
     * Triggers the current item that the player has walked on top of.
     */
    @Override
    public void invokeItem(Position oldPosition, boolean instantUpdate) {
        super.invokeItem(oldPosition, instantUpdate);
        this.pet.setWalkBeforeSitLay(false);
    }


    public void tryDrinking() {
        if (this.getRoom() == null) {
            return;
        }

        var room = this.getRoom();
        var bowlsInRoom = room.getItemManager().getFloorItems().stream().filter(item -> item.hasBehaviour(ItemBehaviour.PET_WATER_BOWL) &&
                item.getCustomData().equalsIgnoreCase("1")).collect(Collectors.toList());

        if (bowlsInRoom.size() < 1) {
            return;
        }

        Collections.shuffle(bowlsInRoom);
        Item item = bowlsInRoom.get(0);

        if (item == null) {
            return;
        }

        this.pet.getRoomUser().walkTo(item.getPosition().getX(), item.getPosition().getY());

        if (!this.pet.getRoomUser().isWalking()) {
            return;
        }

        this.lastActionLength = ThreadLocalRandom.current().nextInt(30);

        this.pet.setAction(PetAction.DRINK);
        this.pet.setActionDuration(this.lastActionLength);
        this.item = item;
    }

    public void tryEating() {
        if (this.getRoom() == null) {
            return;
        }

        var room = this.getRoom();
        var petType = PetManager.getInstance().getType(this.pet);

        List<Item> foodInRoom = null;

        if (petType == PetType.DOG) {
            foodInRoom = room.getItemManager().getFloorItems().stream().filter(item -> item.hasBehaviour(ItemBehaviour.PET_FOOD) || item.hasBehaviour(ItemBehaviour.PET_DOG_FOOD)).collect(Collectors.toList());
        }

        if (petType == PetType.CAT) {
            foodInRoom = room.getItemManager().getFloorItems().stream().filter(item -> item.hasBehaviour(ItemBehaviour.PET_FOOD) || item.hasBehaviour(ItemBehaviour.PET_CAT_FOOD)).collect(Collectors.toList());
        }

        if (petType == PetType.CROC) {
            foodInRoom = room.getItemManager().getFloorItems().stream().filter(item -> item.hasBehaviour(ItemBehaviour.PET_FOOD) || item.hasBehaviour(ItemBehaviour.PET_CROC_FOOD)).collect(Collectors.toList());
        }

        if (foodInRoom.size() < 1) {
            return;
        }

        Collections.shuffle(foodInRoom);
        Item item = foodInRoom.get(0);

        if (item == null) {
            return;
        }

        this.pet.getRoomUser().walkTo(item.getPosition().getX(), item.getPosition().getY());

        if (!this.pet.getRoomUser().isWalking()) {
            return;
        }

        this.lastActionLength = ThreadLocalRandom.current().nextInt(30);

        this.pet.setAction(PetAction.EAT);
        this.pet.setActionDuration(this.lastActionLength);
        this.item = item;
    }

    public void trySleep() {
        if (this.getRoom() == null) {
            return;
        }

        var room = this.getRoom();
        var bedsInRoom = room.getItemManager().getFloorItems().stream().filter(item -> item.getDefinition().getInteractionType() == InteractionType.PET_NEST &&
                item.getTile().getEntities().isEmpty()).collect(Collectors.toList());

        if (bedsInRoom.size() < 1) {
            return;
        }

        Collections.shuffle(bedsInRoom);
        Item item = bedsInRoom.get(0);

        if (item == null) {
            return;
        }

        this.pet.getRoomUser().walkTo(item.getPosition().getX(), item.getPosition().getY());

        if (!this.pet.getRoomUser().isWalking()) {
            return;
        }

        this.lastActionLength = ThreadLocalRandom.current().nextInt(20, 60);

        this.pet.setAction(PetAction.SLEEP);
        this.pet.setActionDuration(this.lastActionLength);
        this.item = item;
    }

    @Override
    public void stopWalking() {
        super.stopWalking();

        if (this.pet.getAction() == PetAction.DRINK) {
            if (this.item.getRoom() != null) {
                this.getPosition().setRotation(this.item.getPosition().getRotation());

                this.setStatus(StatusType.EAT, "");
                this.setNeedsUpdate(true);
                this.emptyPetBowl(this.pet);

                this.pet.getDetails().setLastDrink(DateUtil.getCurrentTimeSeconds());
                PetDao.saveDetails(this.pet.getDetails().getId(), this.pet.getDetails());
                return;
            }

        }

        if (this.pet.getAction() == PetAction.EAT) {
            if (this.item.getRoom() != null) {
                this.getPosition().setRotation(this.item.getPosition().getRotation());

                this.setStatus(StatusType.EAT, "");
                this.setNeedsUpdate(true);
                removeFoodItem(this.pet);

                this.pet.getDetails().setLastEat(DateUtil.getCurrentTimeSeconds());
                PetDao.saveDetails(this.pet.getDetails().getId(), this.pet.getDetails());
                return;
            }
        }

        if (this.pet.getAction() == PetAction.SLEEP) {
            if (this.item.getRoom() != null) {
                this.getPosition().setRotation(this.item.getPosition().getRotation());

                this.setStatus(StatusType.SLEEP, StringUtil.format(pet.getRoomUser().getPosition().getZ()) + " null");
                this.setNeedsUpdate(true);
                removeAwake(this.pet);

                this.pet.getDetails().setLastKip(DateUtil.getCurrentTimeSeconds());
                PetDao.saveDetails(this.pet.getDetails().getId(), this.pet.getDetails());
                return;
            }
        }

        this.pet.setAction(PetAction.NONE);
        this.pet.setActionDuration(0);

        // See PetNestInteractor -> "onEntityStop
        /*
        if (this.pet.getAction() == PetAction.SLEEP) {
            if (this.item.getRoom() != null) {
                this.getPosition().setRotation(this.item.getPosition().getRotation());

                this.setStatus(StatusType.SLEEP, "");
                this.setNeedsUpdate(true);
                removeAwake(this.pet);

                this.pet.getDetails().setLastKip(DateUtil.getCurrentTimeSeconds());
                PetDao.saveDetails(this.pet.getDetails().getId(), this.pet.getDetails());
            } else {
                this.pet.setAction(PetAction.NONE);
                this.pet.setActionDuration(0);
            }
        }
        */
    }

    private void emptyPetBowl(final Pet pet) {
        GameScheduler.getInstance().getService().schedule(()-> {
            if (item.getRoom() != null) {
                item.setCustomData("0");
                item.updateStatus();
                item.save();
            }

            removeStatus(StatusType.EAT);
            setNeedsUpdate(true);
        }, this.lastActionLength, TimeUnit.SECONDS);
    }

    private void removeFoodItem(final Pet pet) {
        GameScheduler.getInstance().getService().schedule(()-> {
            if (item.getRoom() != null) {
                getRoom().getMapping().removeItem(null, item);
                item.delete();
            }

            pet.setAction(PetAction.NONE);
            pet.setActionDuration(0);

            removeStatus(StatusType.EAT);
            setNeedsUpdate(true);
        }, this.lastActionLength, TimeUnit.SECONDS);
    }

    private void removeAwake(Pet pet) {
        GameScheduler.getInstance().getService().schedule(()-> {
            pet.setAction(PetAction.NONE);
            pet.setActionDuration(0);

            removeStatus(StatusType.SLEEP);
            setNeedsUpdate(true);
        }, this.lastActionLength, TimeUnit.SECONDS);
    }

    public Item getItem() {
        return item;
    }
}
