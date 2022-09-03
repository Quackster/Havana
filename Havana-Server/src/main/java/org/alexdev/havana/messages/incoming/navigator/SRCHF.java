package org.alexdev.havana.messages.incoming.navigator;

import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.outgoing.navigator.NOFLATS;
import org.alexdev.havana.messages.outgoing.navigator.SEARCH_FLAT_RESULTS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.SearchUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SRCHF implements MessageEvent {
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

        List<Room> roomList = RoomManager.getInstance().replaceQueryRooms(RoomDao.searchRooms(searchQuery, roomOwner, RoomDao.SHOCKWAVE_SEARCH_LIMIT));

        if (roomList.size() > 0) {
            RoomManager.getInstance().sortRooms(roomList);
            RoomManager.getInstance().ratingSantiyCheck(roomList);

            player.send(new SEARCH_FLAT_RESULTS(roomList, player));
        } else {
            player.send(new NOFLATS());
        }
    }
}
