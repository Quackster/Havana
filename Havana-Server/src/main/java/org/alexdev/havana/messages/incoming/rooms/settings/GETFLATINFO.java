package org.alexdev.havana.messages.incoming.rooms.settings;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.outgoing.rooms.settings.FLATINFO;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class GETFLATINFO implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int roomId = reader.readInt();

        Room room = RoomManager.getInstance().getRoomById(roomId);

        if (room == null) {
            return;
        }

        boolean overrideLock = false;

        if (player.getRoomUser().getAuthenticateId() == roomId) {
            overrideLock = true;
        }

        player.send(new FLATINFO(player, room, overrideLock));
    }
}
