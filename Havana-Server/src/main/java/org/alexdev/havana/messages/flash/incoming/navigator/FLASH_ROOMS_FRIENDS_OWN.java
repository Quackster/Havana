package org.alexdev.havana.messages.flash.incoming.navigator;

import org.alexdev.havana.dao.mysql.NavigatorDao;
import org.alexdev.havana.game.messenger.MessengerUser;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.flash.outgoing.navigator.FLASH_FLAT_RESULTS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.ArrayList;
import java.util.List;

public class FLASH_ROOMS_FRIENDS_OWN implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getNetwork().isBetaConnected()) {
            return;
        }

        List<String> friendsInRoom = new ArrayList<>();

        for (MessengerUser messengerUser : player.getMessenger().getFriends().values()) {
            friendsInRoom.add(String.valueOf(messengerUser.getUserId()));
        }

        var roomList = RoomManager.getInstance().replaceQueryRooms(NavigatorDao.getFriendRooms(30, friendsInRoom));

        RoomManager.getInstance().sortRooms(roomList);
        RoomManager.getInstance().ratingSantiyCheck(roomList);

        player.send(new FLASH_FLAT_RESULTS(roomList, -3, false));
    }
}
