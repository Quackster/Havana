package org.alexdev.havana.messages.incoming.tutorial;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.messages.outgoing.tutorial.INVITE_CANCELLED;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.apache.commons.lang3.StringUtils;

public class MSG_REJECT_TUTOR_INVITATION implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (!player.getGuideManager().isGuide()) {
            return;
        }

        if (player.getGuideManager().getInvites().isEmpty()) {
            return;
        }

        String data = reader.readString();

        if (!StringUtils.isNumeric(data)) {
            return;
        }

        int userId = Integer.parseInt(data);

        if (!player.getGuideManager().getInvites().contains(userId)) {
            return;
        }

        Player newb = PlayerManager.getInstance().getPlayerById(userId);

        if (newb == null || newb.getRoomUser().getRoom() == null) {
            return;
        }

        // TODO: Error checking
        player.getGuideManager().removeInvite(userId);
        player.send(new INVITE_CANCELLED());
    }
}
