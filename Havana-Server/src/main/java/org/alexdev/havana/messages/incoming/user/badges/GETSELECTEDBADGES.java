package org.alexdev.havana.messages.incoming.user.badges;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.messages.outgoing.user.badges.USERBADGE;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class GETSELECTEDBADGES implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (reader.contents().isEmpty()) {
            return;
        }

        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        //if (player.getRoomUser().getLastBadgeRequest() > DateUtil.getCurrentTimeSeconds()) {
        //    return;
        //}

        int userId = reader.readInt();

        Player badgePlayer = PlayerManager.getInstance().getPlayerById(userId);

        if (badgePlayer == null) {
            return;
        }

        player.send(new USERBADGE(userId, badgePlayer.getBadgeManager().getEquippedBadges()));
        //player.getRoomUser().setLastBadgeRequest(DateUtil.getCurrentTimeSeconds() + 5);
    }
}
