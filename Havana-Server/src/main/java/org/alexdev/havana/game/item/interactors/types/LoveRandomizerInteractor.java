package org.alexdev.havana.game.item.interactors.types;

import org.alexdev.havana.game.GameScheduler;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.entities.RoomEntity;
import org.alexdev.havana.game.triggers.GenericTrigger;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class LoveRandomizerInteractor extends GenericTrigger {
    public void onInteract(Player player, Room room, Item item, int status) {
        // Spin already being executed, return
        if (item.getRequiresUpdate()) {
            return;
        }

        // Send spinning animation to room
        item.setCustomData("0");
        item.updateStatus();

        item.setRequiresUpdate(true);

        GameScheduler.getInstance().getService().schedule(()->{
            if (!item.getRequiresUpdate()) {
                return;
            }

            item.setCustomData(String.valueOf(ThreadLocalRandom.current().nextInt(1, 5)));
            item.updateStatus();
            item.save();

            item.setRequiresUpdate(false);
        }, 4250, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onEntityStop(Entity entity, RoomEntity roomEntity, Item item, boolean isRotation) {
        InteractionType.CHAIR.getTrigger().onEntityStop(entity, roomEntity, item, isRotation);
    }
}
