package org.alexdev.havana.messages.flash.incoming.navigator;

import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.flash.outgoing.navigator.FLASH_FLAT_RESULTS;
import org.alexdev.havana.messages.flash.outgoing.navigator.FLASH_GET_GUEST_ROOM_RESULT;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.List;

public class FLASH_USERFLATS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        List<Room> roomList = RoomManager.getInstance().replaceQueryRooms(RoomDao.getRoomsByUserId(player.getDetails().getId()));

        RoomManager.getInstance().sortRooms(roomList);
        RoomManager.getInstance().ratingSantiyCheck(roomList);

        player.send(new FLASH_FLAT_RESULTS(roomList, -3, false));
    }
}
