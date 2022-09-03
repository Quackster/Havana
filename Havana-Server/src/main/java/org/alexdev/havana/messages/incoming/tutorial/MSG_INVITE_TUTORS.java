package org.alexdev.havana.messages.incoming.tutorial;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.tutorial.INVITATION_SENT;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.config.GameConfiguration;

import java.util.concurrent.TimeUnit;

public class MSG_INVITE_TUTORS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (!player.getGuideManager().isGuidable()) {
            return;
        }

        if (player.getGuideManager().isWaitingForGuide()) {
            return;
        }

        player.send(new INVITATION_SENT());

        player.getGuideManager().setStartedForWaitingGuidesTime((int) (DateUtil.getCurrentTimeSeconds() + TimeUnit.MINUTES.toSeconds(GameConfiguration.getInstance().getInteger("guide.search.timeout.minutes"))));
        player.getGuideManager().setWaitingForGuide(true);
    }
}
