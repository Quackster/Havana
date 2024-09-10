package org.alexdev.havana.messages.flash.incoming.rooms;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.flash.outgoing.rooms.FLASH_PUBLICROOMCASTS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class FLASH_GETPUBLICROOMDATA implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int roomId = reader.remainingBytes().length > 0 ? reader.readInt() : player.getRoomUser().getRoom().getId();;/* - 1000;  -    WAS USED FOR RELEAS38 */

        Room room = RoomManager.getInstance().getRoomById(roomId);

        if (room == null) {
            return;
        }

        if (room.getData().isNavigatorHide()) {
            roomId = room.getFollowRedirect();
        }

        player.send(new FLASH_PUBLICROOMCASTS(roomId, room.getData().getCcts()));
    }
}
