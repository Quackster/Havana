package org.alexdev.havana.messages.incoming.tutorial;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class RESET_TUTORIAL implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (!player.isLoggedIn()) {
            return;
        }

        if (player.getGuideManager().isGuide()) {
            player.send(new ALERT("You cannot restart the tutorial while as a guide."));
            return;
        }

        player.getStatisticManager().setLongValue(PlayerStatistic.HAS_TUTORIAL, 1);

        if (!player.getBadgeManager().hasBadge("ACH_Student1")) {
            player.getStatisticManager().setLongValue(PlayerStatistic.IS_GUIDABLE, 1);
        }

        player.send(new ALERT("You may now do the tutorial again, please relog for it to take effect."));
    }
}
