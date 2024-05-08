package org.alexdev.havana.messages.incoming.rooms.items;

import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class ADDSTRIPITEM implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.isOwner(player.getDetails().getId()) && !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            return;
        }

        //String content = reader.contents();
        //String[] data = content.split(" ");

        reader.readInt();

        int itemId = reader.readInt();//Integer.parseInt(data[2]);

        Item item = room.getItemManager().getById(itemId);

        if (item == null) {
            return;
        }

        if (item.hasBehaviour(ItemBehaviour.POST_IT)) {
            return; // The client does not allow picking up post-it's, thus neither will the server
        }

        if (item.getOwnerId() != player.getDetails().getId()) {
            item.setOwnerId(player.getDetails().getId());
            ItemDao.updateItemOwnership(item);
        }

        room.getMapping().pickupItem(player, item);

        player.getInventory().addItem(item);
        player.getInventory().getView("update");
    }
}
