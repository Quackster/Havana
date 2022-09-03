package org.alexdev.havana.messages.incoming.moderation;

import org.alexdev.havana.game.moderation.cfh.CallForHelp;
import org.alexdev.havana.game.moderation.cfh.CallForHelpManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.moderation.CFH_ACK;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class DELETE_CRY implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        CallForHelp cfh = CallForHelpManager.getInstance().getPendingCall(player.getDetails().getId());

        if (cfh == null) {
            return;
        }

        CallForHelpManager.getInstance().deleteCall(cfh);
        player.send(new CFH_ACK(null));
    }
}
