package org.alexdev.havana.messages.incoming.rooms.items;

import org.alexdev.havana.game.GameScheduler;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.entities.RoomEntity;
import org.alexdev.havana.game.room.tasks.FortuneTask;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.concurrent.TimeUnit;


public class SPIN_WHEEL_OF_FORTUNE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        RoomEntity roomEntity = player.getRoomUser();
        Room room = roomEntity.getRoom();

        if (room == null) {
            return;
        }

        if (!room.hasRights(player.getDetails().getId()) && !player.hasFuse(Fuseright.MOD)) {
            return;
        }

        int itemId = reader.readInt();

        // Get item by ID
        Item item = room.getItemManager().getById(itemId);

        // Check if item exists and if it is a wheel of fortune
        if (item == null || !item.hasBehaviour(ItemBehaviour.WHEEL_OF_FORTUNE)) {
            return;
        }

        // Spin already being executed, return
        if (item.getRequiresUpdate()) {
            return;
        }

        // Send spinning animation to room
        item.setCustomData("-1");
        item.updateStatus();

        item.setRequiresUpdate(true);

        GameScheduler.getInstance().getService().schedule(new FortuneTask(item), 4250, TimeUnit.MILLISECONDS);
    }
}