package org.alexdev.havana.messages.flash.incoming.navigator;

import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.navigator.NavigatorManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.flash.incoming.navigator.beta.BETA_FLASH_PUBLICROOMS;
import org.alexdev.havana.messages.flash.outgoing.navigator.FLASH_FRONTPAGERESULT;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.ArrayList;
import java.util.List;

public class FLASH_PUBLICROOMS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getNetwork().isBetaConnected()) {
            new BETA_FLASH_PUBLICROOMS().handle(player, reader);
            return;
        }

        List<Room> roomList = new ArrayList<>();

        for (Room room : RoomManager.getInstance().replaceQueryRooms(RoomDao.getRoomsByUserId(0))) {
            if (room.getData().isNavigatorHide()) {
                continue;
            }


            roomList.add(room);
        }

        roomList.removeIf(room -> NavigatorManager.getInstance().getNavigatorStyle(room.getId()) == null);
        RoomManager.getInstance().sortRooms(roomList);

        player.send(new FLASH_FRONTPAGERESULT(roomList));
    }
}
