package org.alexdev.havana.messages.flash.incoming.navigator;

import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.dao.mysql.TagDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.flash.outgoing.navigator.FLASH_SEARCHRESULTS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.ArrayList;
import java.util.List;

public class FLASH_SEARCH_TAGS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        String searchTag = reader.readString();


        List<Room> roomList = RoomManager.getInstance().replaceQueryRooms(TagDao.querySearchRooms(searchTag));

        RoomManager.getInstance().sortRooms(roomList);
        RoomManager.getInstance().ratingSantiyCheck(roomList);

        player.send(new FLASH_SEARCHRESULTS(roomList, searchTag));
    }
}
