package org.alexdev.havana.messages.incoming.navigator;

import org.alexdev.havana.dao.mysql.RoomFavouritesDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class DEL_FAVORITE_ROOM implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int roomType = reader.readInt();
        int roomId = reader.readInt();

        if (roomType == 1) {
            roomId = (roomId - RoomManager.PUBLIC_ROOM_OFFSET);
        }

        RoomFavouritesDao.removeFavouriteRoom(player.getDetails().getId(), roomId);
    }
}
