package org.alexdev.havana.messages.incoming.rooms.items;

import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.StringUtil;

public class SETITEMDATA implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        //if (!room.hasRights(player.getDetails().getVirtualId())) {
        //    return;
        //}

        //String contents = reader.contents();

        int itemId = reader.readInt();
        String contents = reader.readString();

        String colour = contents.substring(0, 6);
        String newMessage = StringUtil.filterInput(contents.substring(7), false);

        if (!colour.equals("FFFFFF") &&
                !colour.equals("FFFF33") &&
                !colour.equals("FF9CFF") &&
                !colour.equals("9CFF9C") &&
                !colour.equals("9CCEFF")) {
            return;
        }

        Item item = room.getItemManager().getById(itemId);

        if (item == null) {
            return;
        }

        if (!item.hasBehaviour(ItemBehaviour.POST_IT)) {
            return;
        }

        String oldText = "";
        String oldColour = "FFFFFF";

        if (item.getCustomData().length() > 6) { // Strip colour code
            oldText = item.getCustomData().substring(6);
            oldColour = item.getCustomData().substring(0, 6);
        }

        // If the user doesn't have rights, make sure they can only append to the sticky, not remove the existing information before it
        if (!room.hasRights(player.getDetails().getId()) && !player.hasFuse(Fuseright.MOD)) {
            if (oldText.length() > 0 && !newMessage.startsWith(oldText)) {
                return;
            }
        }

        // Validate the colour is the same if the user doesn't have any rights
        if (!room.isOwner(player.getDetails().getId()) && !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            if (!oldColour.equals(colour)) {
                return;
            }
        }

        if (newMessage.length() > 684) {
            newMessage = newMessage.substring(0, 684);
        }

        item.setCustomData(colour + newMessage);
        item.updateStatus();
        item.save();
    }
}
