package org.alexdev.havana.game.room.models.triggers;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.tasks.SpaceCafeTask;
import org.alexdev.havana.game.triggers.GenericTrigger;

import java.util.concurrent.TimeUnit;

public class SpaceCafeTrigger extends GenericTrigger {
    @Override
    public void onRoomEntry(Entity entity, Room room, boolean firstEntry, Object... customArgs) {
        if (!firstEntry) {
            return;
        }

        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        room.getTaskManager().scheduleTask("SpaceCafeTask", new SpaceCafeTask(room), 0, 500, TimeUnit.MILLISECONDS);
    }
}
