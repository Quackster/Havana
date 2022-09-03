package org.alexdev.havana.messages.incoming.user.badges;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.guides.INIT_TUTOR_SERVICE_STATUS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class GETAVAILABLEBADGES implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (!player.isLoggedIn()) {
            return;
        }

        if (player.isProcessLoginSteps()) {
            player.getBadgeManager().refreshBadges();
            player.getAchievementManager().processAchievements(player, true);
            player.setProcessLoginSteps(false);
        }

        if (player.getGuideManager().isGuide()) {
            player.send(new INIT_TUTOR_SERVICE_STATUS(1));
        }
    }
}
