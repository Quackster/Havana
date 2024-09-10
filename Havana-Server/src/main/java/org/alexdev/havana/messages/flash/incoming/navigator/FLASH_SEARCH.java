package org.alexdev.havana.messages.flash.incoming.navigator;

import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.flash.outgoing.navigator.FLASH_SEARCHRESULTS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.SearchUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FLASH_SEARCH implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        String searchQuery = reader.readString();
        int roomOwner = -1;

        if (searchQuery.contains("owner:")) {
            var ownerTag = SearchUtil.getOwnerTag(searchQuery);

            if (ownerTag != null) {
                roomOwner = PlayerDao.getId(ownerTag.replaceFirst("owner:", ""));
            }

            searchQuery = Arrays.stream(searchQuery.split(" ")).filter(s -> !s.toLowerCase().startsWith("owner:")).collect(Collectors.joining(","));
        }

        List<Room> roomList = RoomManager.getInstance().replaceQueryRooms(RoomDao.searchRooms(searchQuery, roomOwner, RoomDao.FLASH_SEARCH_LIMIT));

        RoomManager.getInstance().sortRooms(roomList);
        RoomManager.getInstance().ratingSantiyCheck(roomList);

        player.send(new FLASH_SEARCHRESULTS(roomList, searchQuery));
    }
}
