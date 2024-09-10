package org.alexdev.havana.messages.flash.incoming.modtool;

import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.flash.outgoing.modtool.FLASH_MODTOOL_CHATLOG;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class FLASH_MODTOOL_ROOM_CHATLOG implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null || player.getDetails().getRank().getRankId() < PlayerRank.MODERATOR.getRankId()) {
            return;
        }

        player.send(new FLASH_MODTOOL_CHATLOG(room, RoomDao.getModChatlog(room.getId())));
    }
}
