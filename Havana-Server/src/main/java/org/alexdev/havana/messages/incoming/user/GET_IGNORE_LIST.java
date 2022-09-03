package org.alexdev.havana.messages.incoming.user;

import org.alexdev.havana.dao.mysql.UsersMutesDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.messages.outgoing.user.IGNORED_LIST;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.List;

public class GET_IGNORE_LIST implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getIgnoredList().size() > 0) {
            return;
        }

        List<Integer> ignoreList = UsersMutesDao.getMutedUsers(player.getDetails().getId());

        for (int userId : ignoreList) {
            var playerData = PlayerManager.getInstance().getPlayerData(userId);

            if (playerData == null) {
                continue;
            }

            player.getIgnoredList().add(PlayerManager.getInstance().getPlayerData(userId).getName());
        }

        player.send(new IGNORED_LIST(player.getIgnoredList()));
    }
}
