package org.alexdev.havana.game.item.interactors.types;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.pets.Pet;
import org.alexdev.havana.game.pets.PetAction;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.entities.RoomEntity;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.game.room.tasks.EntityTask;
import org.alexdev.havana.game.triggers.GenericTrigger;
import org.alexdev.havana.messages.outgoing.effects.USER_AVATAR_EFFECT;
import org.alexdev.havana.util.StringUtil;

import java.util.concurrent.ThreadLocalRandom;

public class ChairInteractor extends GenericTrigger {
    public void onInteract(Player player, Room room, Item item, int status) {
        InteractionType.DEFAULT.getTrigger().onInteract(player, room, item, status);
    }

    @Override
    public void onEntityStop(Entity entity, RoomEntity roomEntity, Item item, boolean isRotation) {
        boolean isRolling = entity.getRoomUser().isRolling();

        int headRotation = roomEntity.getPosition().getHeadRotation();
        double topHeight = item.getDefinition().getTopHeight();

        roomEntity.getPosition().setRotation(item.getPosition().getRotation());
        roomEntity.stopDancing();
        roomEntity.setStatus(StatusType.SIT, StringUtil.format(topHeight));
        roomEntity.setNeedsUpdate(true);

        if (isRolling) {
            if (roomEntity.getTimerManager().getLookTimer() > -1) {
                roomEntity.getPosition().setHeadRotation(headRotation);
            }
        }

        if (entity.getRoomUser().isUsingEffect()) {
            if (!roomEntity.getRoom().getTaskManager().hasTask("EntityTask")) {
                return;
            }

            EntityTask entityTask = (EntityTask) roomEntity.getRoom().getTaskManager().getTask("EntityTask");
            entityTask.getQueueAfterLoop().add(new USER_AVATAR_EFFECT(roomEntity.getInstanceId(), roomEntity.getEffectId()));
        }
    }

    @Override
    public void onEntityLeave(Entity entity, RoomEntity roomEntity, Item item, Object... customArgs) {

    }
}
