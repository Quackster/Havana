package org.alexdev.havana.messages.incoming.rooms.items;

import org.alexdev.havana.game.GameScheduler;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.mapping.RoomTile;
import org.alexdev.havana.messages.outgoing.rooms.items.CHANGESTATUS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.concurrent.TimeUnit;

public class ENTER_ONEWAY_DOOR implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        int itemId = reader.readInt();
        Item item = room.getItemManager().getById(itemId);

        if (item == null || !item.hasBehaviour(ItemBehaviour.ONE_WAY_GATE)) {
            return;
        }

        if (!item.getPosition().getSquareInFront().equals(player.getRoomUser().getPosition())) {
            return;
        }

        Position destination = item.getPosition().getSquareBehind();

        if (!RoomTile.isValidTile(room, player, destination)) {
            return;
        }

        room.send(new CHANGESTATUS(itemId, 1));
        item.setCustomData("1");

        player.getRoomUser().walkTo(destination.getX(), destination.getY());

        if (!player.getRoomUser().isWalking()) {
            return;
        }

        player.getRoomUser().setEnableWalkingOnStop(true);

        GameScheduler.getInstance().getService().schedule(()->{
            room.send(new CHANGESTATUS(itemId, 0));
            item.setCustomData("0");
        }, 1, TimeUnit.SECONDS);
    }
}
