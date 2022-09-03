package org.alexdev.havana.messages.incoming.moderation;

import org.alexdev.havana.game.moderation.cfh.CallForHelp;
import org.alexdev.havana.game.moderation.cfh.CallForHelpManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.moderation.CFH_ACK;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class REQUEST_CFH implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        // Retrieve open calls from the current user
        CallForHelp call = CallForHelpManager.getInstance().getPendingCall(player.getDetails().getId());

        // Send details
        player.send(new CFH_ACK(call));
    }
}
