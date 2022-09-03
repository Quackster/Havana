package org.alexdev.havana.messages.incoming.moderation;

import org.alexdev.havana.game.moderation.cfh.CallForHelpManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.StringUtil;

public class SUBMIT_CFH implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        String message = StringUtil.filterInput(reader.readString(), false);

        if (message.length() == 0) {
            return;
        }

        if (CallForHelpManager.getInstance().hasPendingCall(player)) {
            return;
        }

        CallForHelpManager.getInstance().submitCall(player, message);
    }
}
