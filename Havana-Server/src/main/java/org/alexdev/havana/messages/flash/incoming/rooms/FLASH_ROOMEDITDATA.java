package org.alexdev.havana.messages.flash.incoming.rooms;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.flash.outgoing.rooms.FLASH_EDITDATA;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class FLASH_ROOMEDITDATA implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int roomId = reader.readInt();

        Room room = RoomManager.getInstance().getRoomById(roomId);

        if (room == null || !room.isOwner(player.getDetails().getId())) {
            return;
        }

        player.send(new FLASH_EDITDATA(room));
    }
}
