package org.alexdev.havana.messages.flash.incoming.navigator.beta;

import org.alexdev.havana.dao.mysql.NavigatorDao;
import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.messenger.MessengerUser;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.flash.outgoing.navigator.FLASH_GET_GUEST_ROOM_RESULT;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FLASH_GETGUESTROOMS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int forceDisplay = reader.readInt();
        int mode = reader.readInt();

        List<Room> roomList = RoomManager.getInstance().getRoomsByMode(mode, player);//RoomManager.getInstance().replaceQueryRooms(RoomDao.getRoomsByUserId(player.getDetails().getId()));

        RoomManager.getInstance().sortRooms(roomList);
        RoomManager.getInstance().ratingSantiyCheck(roomList);

        player.send(new FLASH_GET_GUEST_ROOM_RESULT(forceDisplay, mode, roomList));
    }
}
