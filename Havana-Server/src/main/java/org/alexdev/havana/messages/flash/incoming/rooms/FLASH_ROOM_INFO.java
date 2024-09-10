package org.alexdev.havana.messages.flash.incoming.rooms;

import org.alexdev.havana.messages.flash.outgoing.FLASH_FLATINFO;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class FLASH_ROOM_INFO implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int roomId = reader.readInt();

        Room room = RoomManager.getInstance().getRoomById(roomId);

        if (room == null) {
            return;
        }

        boolean isLoading = reader.readBoolean();
        boolean checkEntry = reader.readBoolean();
        boolean overrideLock = false;

        if (room.isOwner(player.getDetails().getId())) {
            overrideLock = true;
        }

        if (player.getRoomUser().getAuthenticateId() == roomId) {
            overrideLock = true;
        }

        player.send(new FLASH_FLATINFO(player, room, overrideLock, isLoading, checkEntry));
    }
}
