package org.alexdev.havana.messages.incoming.club;

import org.alexdev.havana.game.club.ClubSubscription;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;
import org.alexdev.havana.log.Log;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.sql.SQLException;

public class SCR_GIFT_APPROVAL implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (ClubSubscription.isGiftDue(player)) {

            try {
                while (player.getStatisticManager().getIntValue(PlayerStatistic.GIFTS_DUE) > 0) {
                    ClubSubscription.tryNextGift(player);
                }

            } catch (SQLException e) {
                Log.getErrorLogger().error("Error trying to process club gift for user (" + player.getDetails().getName() + "): ", e);
            }
        }
    }
}
