package org.alexdev.havana.messages.flash.incoming;

import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.flash.outgoing.HOME_ROOM;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class SET_HOME_ROOM implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int roomId = reader.readInt();

        if (roomId > 0) {
             Room room = RoomDao.getRoomById(roomId);

             if (room == null || room.isPublicRoom() || room.getData().getOwnerId() != player.getDetails().getId()) {
                 return;
             }
        } else {
            roomId = 0;
        }

        PlayerDao.saveHomeRoom(player.getDetails().getId(), roomId);
        player.send(new HOME_ROOM(roomId));
    }
}
