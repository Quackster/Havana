package org.alexdev.havana.messages.incoming.rooms.items;

import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.util.MalformedPacketException;

import java.sql.SQLException;

public class REMOVEITEM implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws SQLException, MalformedPacketException {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        int itemId = reader.readInt();
        Item item = room.getItemManager().getById(itemId);

        if (item == null) {
            return;
        }

        if (!room.isOwner(player.getDetails().getId()) &&
                !((item.hasBehaviour(ItemBehaviour.PHOTO) && player.hasFuse(Fuseright.REMOVE_PHOTOS)) ||
                        (item.hasBehaviour(ItemBehaviour.POST_IT) && player.hasFuse(Fuseright.REMOVE_STICKIES)))) {
            return;
        }


        // Set up trigger for leaving a current item
        if (player.getRoomUser().getCurrentItem() != null) {
            if (player.getRoomUser().getCurrentItem().getDefinition().getInteractionType().getTrigger() != null) {
                player.getRoomUser().getCurrentItem().getDefinition().getInteractionType().getTrigger().onEntityLeave(player, player.getRoomUser(), player.getRoomUser().getCurrentItem());
            }
        }

        room.getMapping().pickupItem(player, item);
        item.delete();

        if (player.getNetwork().isFlashConnection()) {
            player.getInventory().getView("new");
        }

        player.getRoomUser().getTimerManager().resetRoomTimer();
    }
}
