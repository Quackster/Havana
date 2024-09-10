package org.alexdev.havana.messages.flash.incoming.navigator;

import org.alexdev.havana.dao.mysql.NavigatorDao;
import org.alexdev.havana.game.navigator.NavigatorCategory;
import org.alexdev.havana.game.navigator.NavigatorManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.flash.outgoing.navigator.FLASH_FLAT_RESULTS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FLASH_POPULARROOMS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getNetwork().isBetaConnected()) {
            return;
        }

        var value = reader.readString();
        int mode = -1;

        List<Room> roomList = new ArrayList<>();

        if (StringUtils.isNumeric(value)) {
            mode = Integer.parseInt(value);

            NavigatorCategory navigatorCategory = NavigatorManager.getInstance().getCategoryById(mode);

            if (navigatorCategory != null) {
                roomList.addAll(RoomManager.getInstance().replaceQueryRooms(NavigatorDao.getRecentRooms(30, navigatorCategory.getId())));
            }
        }

        if (mode == -1) {
            roomList.addAll(RoomManager.getInstance().replaceQueryRooms(NavigatorDao.getRopularRooms(30, false)));
        }

        RoomManager.getInstance().sortRooms(roomList);
        RoomManager.getInstance().ratingSantiyCheck(roomList);

        player.send(new FLASH_FLAT_RESULTS(roomList, mode, false));
    }
}
