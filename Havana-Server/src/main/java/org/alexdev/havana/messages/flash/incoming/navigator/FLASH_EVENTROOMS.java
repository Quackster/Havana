package org.alexdev.havana.messages.flash.incoming.navigator;

import org.alexdev.havana.dao.mysql.NavigatorDao;
import org.alexdev.havana.game.events.EventsManager;
import org.alexdev.havana.game.navigator.NavigatorCategory;
import org.alexdev.havana.game.navigator.NavigatorManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.flash.outgoing.navigator.FLASH_FLAT_RESULTS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FLASH_EVENTROOMS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getNetwork().isBetaConnected()) {
            return;
        }

        int mode = Integer.parseInt(reader.readString());
        List<Room> roomList = new ArrayList<>();
        
        if (mode == -1) {
            roomList = EventsManager.getInstance().getEventList().stream().map(event -> RoomManager.getInstance().getRoomById(event.getRoomId())).collect(Collectors.toList());
        } else {
            roomList = EventsManager.getInstance().getEvents(mode).stream().map(event -> RoomManager.getInstance().getRoomById(event.getRoomId())).collect(Collectors.toList());
        }

        RoomManager.getInstance().sortRooms(roomList);
        RoomManager.getInstance().ratingSantiyCheck(roomList);

        player.send(new FLASH_FLAT_RESULTS(roomList, mode, true));
    }
}
