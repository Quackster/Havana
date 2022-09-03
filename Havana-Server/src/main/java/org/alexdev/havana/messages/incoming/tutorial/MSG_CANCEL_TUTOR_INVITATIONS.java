package org.alexdev.havana.messages.incoming.tutorial;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class MSG_CANCEL_TUTOR_INVITATIONS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (!player.getGuideManager().isGuidable()) {
            return;
        }

        if (!player.getGuideManager().isWaitingForGuide()) {
            return;
        }

        player.getGuideManager().setWaitingForGuide(false);
        player.getGuideManager().setGuidable(false);
        player.getGuideManager().getInvited().clear();
    }
}
