package org.alexdev.havana.messages.flash.incoming.navigator;

import org.alexdev.havana.game.messenger.MessengerUser;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.flash.outgoing.navigator.FLASH_FLAT_RESULTS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.ArrayList;
import java.util.List;

public class FLASH_FRIENDS_IN_ROOM implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getNetwork().isBetaConnected()) {
            return;
        }

        List<Room> friendsInRoom = new ArrayList<>();

        for (MessengerUser messengerUser : player.getMessenger().getFriends().values()) {
            if (!messengerUser.isOnline()) {
                continue;
            }

            var friend = PlayerManager.getInstance().getPlayerById(messengerUser.getUserId());
            var friendRoom = friend.getRoomUser().getRoom();

            if (friendRoom != null && !friendRoom.isPublicRoom()) {
                if (friendsInRoom.stream().noneMatch(room -> room.getId() == friendRoom.getId())) {
                    friendsInRoom.add(friendRoom);
                }
            }
        }

        RoomManager.getInstance().sortRooms(friendsInRoom);
        RoomManager.getInstance().ratingSantiyCheck(friendsInRoom);

        player.send(new FLASH_FLAT_RESULTS(friendsInRoom, -3, false));
    }
}
