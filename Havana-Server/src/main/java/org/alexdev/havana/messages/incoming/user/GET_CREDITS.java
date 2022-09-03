package org.alexdev.havana.messages.incoming.user;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.user.currencies.ActivityPointNotification;
import org.alexdev.havana.messages.outgoing.user.currencies.CREDIT_BALANCE;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class GET_CREDITS implements MessageEvent {

    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        player.send(new CREDIT_BALANCE(player.getDetails().getCredits()));
        player.send(new ActivityPointNotification(player.getDetails().getPixels(), ActivityPointNotification.ActivityPointAlertType.NO_SOUND));

        /*if (DateUtil.getCurrentTimeSeconds() > (player.getDetails().getLastPixelsTime() + TimeUnit.MINUTES.toSeconds(15))) {
            player.getDetails().setLastPixelsTime(DateUtil.getCurrentTimeSeconds() + TimeUnit.MINUTES.toSeconds(15));

            CurrencyDao.increasePixels(player.getDetails(), 15);
            PlayerDao.saveLastPixelHandout(player.getDetails());

            player.send(new ActivityPointNotification(player.getDetails().getPixels(), ActivityPointNotification.ActivityPointAlertType.PIXELS_RECEIVED));
        } else {
            player.send(new ActivityPointNotification(player.getDetails().getPixels(), ActivityPointNotification.ActivityPointAlertType.NO_SOUND));
        }*/
    }
}
