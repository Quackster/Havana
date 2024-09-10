package org.alexdev.havana.messages.flash.incoming.navigator.beta;

import org.alexdev.havana.dao.mysql.NavigatorDao;
import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.navigator.NavigatorCategory;
import org.alexdev.havana.game.navigator.NavigatorManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.flash.outgoing.navigator.FLASH_FRONTPAGERESULT;
import org.alexdev.havana.messages.flash.outgoing.navigator.beta.BETA_FLASH_FRONTPAGERESULT;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BETA_FLASH_PUBLICROOMS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        List<Room> popularRoomList = RoomManager.getInstance().replaceQueryRooms(NavigatorDao.getRopularRooms(6, false));

        RoomManager.getInstance().sortRooms(popularRoomList);
        RoomManager.getInstance().ratingSantiyCheck(popularRoomList);

        Map<String, Integer> categoryList = new LinkedHashMap<>();

        for (var kvp : NavigatorDao.getPopularCategories(3).entrySet()) {
            var categoryId = kvp.getKey();
            var roomVisitors = kvp.getValue();
            var category = NavigatorManager.getInstance().getCategoryById(categoryId);

            categoryList.put(category.getName(), roomVisitors);

        }

        player.send(new BETA_FLASH_FRONTPAGERESULT(popularRoomList,
                RoomManager.getInstance().getRoomsByMode(1, player).size(),
                RoomManager.getInstance().getRoomsByMode(4, player).size(),
                RoomManager.getInstance().getRoomsByMode(5, player).size(),
                RoomManager.getInstance().getRoomsByMode(6, player).size(),
                categoryList));
    }
}
