package org.alexdev.havana.messages.incoming.rooms.user;

import org.alexdev.havana.dao.mysql.TagDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.rooms.user.TAG_LIST;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class GET_USER_TAGS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        Player p = PlayerManager.getInstance().getPlayerById(reader.readInt());

        if (p == null) {
            return;
        }

        player.send(new TAG_LIST(p.getDetails().getId(), TagDao.getUserTags(p.getDetails().getId())));
    }
}
