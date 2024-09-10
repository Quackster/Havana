package org.alexdev.havana.messages.flash.incoming.navigator;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.flash.outgoing.navigator.FLASH_FLAT_RESULTS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class FLASH_FAVOURITE_ROOMS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getNetwork().isBetaConnected()) {
            return;
        }

        var roomList = RoomManager.getInstance().getFavouriteRooms(player.getDetails().getId(), true);

        RoomManager.getInstance().sortRooms(roomList);
        RoomManager.getInstance().ratingSantiyCheck(roomList);

        player.send(new FLASH_FLAT_RESULTS(roomList, -3, false));
    }
}
