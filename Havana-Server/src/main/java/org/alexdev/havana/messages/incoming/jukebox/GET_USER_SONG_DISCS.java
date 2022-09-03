package org.alexdev.havana.messages.incoming.jukebox;

import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.jukebox.USER_SONG_DISKS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.HashMap;
import java.util.Map;

public class GET_USER_SONG_DISCS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (room.getItemManager().getSoundMachine() == null) {
            return;
        }

        if (!room.hasRights(player.getDetails().getId()) && !player.hasFuse(Fuseright.MOD)) {
            return;
        }

        Map<Item, Integer> userDisks = new HashMap<>();

        for (Item item : player.getInventory().getItems()) {
            if (!item.isVisible()) {
                continue;
            }

            if (item.hasBehaviour(ItemBehaviour.SONG_DISK)) {
                userDisks.put(item, item.getVirtualId());
            }
        }

        player.send(new USER_SONG_DISKS(userDisks));
    }
}
