package org.alexdev.havana.game.item.interactors.types;

import org.alexdev.havana.game.GameScheduler;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.room.entities.RoomEntity;
import org.alexdev.havana.game.triggers.GenericTrigger;

import java.util.concurrent.TimeUnit;

public class StepLightInteractor extends GenericTrigger {
    @Override
    public void onEntityStep(Entity entity, RoomEntity roomEntity, Item item, Position oldPosition) {
        item.setCustomData("1");
        item.updateStatus();

        GameScheduler.getInstance().getService().schedule(()-> {
            item.setCustomData("0");
            item.updateStatus();
            item.save();
        }, 500, TimeUnit.MILLISECONDS);
    }
}
