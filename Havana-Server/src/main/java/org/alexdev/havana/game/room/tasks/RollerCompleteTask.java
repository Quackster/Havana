package org.alexdev.havana.game.room.tasks;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.room.Room;

import java.util.Collection;
import java.util.Set;

public class RollerCompleteTask implements Runnable {
    private final Room room;
    private final Collection<Item> rollingItems;
    private final Set<Entity> rollingEntities;

    public RollerCompleteTask(Collection<Item> rollingItems, Set<Entity> rollingEntities, Room room) {
        this.rollingItems = rollingItems;
        this.rollingEntities = rollingEntities;
        this.room = room;
    }

    @Override
    public void run() {
        for (Item item : this.rollingItems) {
            if (item.getRollingData() == null) {
                continue;
            }

            /*if (item.getRollingData().getHeightUpdate() > 0) {
                item.getPosition().setZ(item.getPosition().getZ() + item.getRollingData().getHeightUpdate());
                this.room.send(new MOVE_FLOORITEM(item));
            }*/

            item.setCurrentRollBlocked(false);
            item.setRollingData(null);
        }

        for (Entity entity : this.rollingEntities) {
            if (entity.getRoomUser().getRollingData() == null) {
                continue;
            }

            entity.getRoomUser().invokeItem(null, true);
            entity.getRoomUser().setRollingData(null);
        }
    }
}
