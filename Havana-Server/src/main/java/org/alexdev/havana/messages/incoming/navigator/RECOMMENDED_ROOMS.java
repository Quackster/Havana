package org.alexdev.havana.messages.incoming.navigator;

import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.outgoing.navigator.RECOMMENDED_ROOM_LIST;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.List;

public class RECOMMENDED_ROOMS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        List<Room> roomList = RoomManager.getInstance().replaceQueryRooms(RoomDao.getRecommendedRooms(3,  0));

        RoomManager.getInstance().sortRooms(roomList);
        RoomManager.getInstance().ratingSantiyCheck(roomList);

        /*if (roomList.size() < roomLimit) {
            //int difference = roomLimit - roomList.size();

            for (Room room : RoomManager.getInstance().replaceQueryRooms(RoomDao.getHighestRatedRooms(roomLimit, false))) {
                if (roomList.size() == roomLimit) {
                    break;
                }

                roomList.add(room);
            }
        }*/

        player.send(new RECOMMENDED_ROOM_LIST(player, roomList));
    }
}
