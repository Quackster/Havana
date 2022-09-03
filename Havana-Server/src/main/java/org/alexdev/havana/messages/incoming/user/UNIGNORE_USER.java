package org.alexdev.havana.messages.incoming.user;

import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.dao.mysql.UsersMutesDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.user.IGNORE_USER_RESULT;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class UNIGNORE_USER implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        String username = reader.readString();

        if (!player.getIgnoredList().contains(username)) {
            return;
        }

        int userId = PlayerDao.getId(username);
        UsersMutesDao.removeMuted(player.getDetails().getId(), userId);

        player.getIgnoredList().remove(username);
        player.send(new IGNORE_USER_RESULT(3));
    }
}
