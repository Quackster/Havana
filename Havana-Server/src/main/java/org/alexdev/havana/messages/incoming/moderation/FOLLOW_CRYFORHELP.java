package org.alexdev.havana.messages.incoming.moderation;

import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.moderation.cfh.CallForHelp;
import org.alexdev.havana.game.moderation.cfh.CallForHelpManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class FOLLOW_CRYFORHELP implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (!player.hasFuse(Fuseright.RECEIVE_CALLS_FOR_HELP)) {
            return;
        }

        int callId = Integer.parseInt(reader.readString());
        CallForHelp cfh = CallForHelpManager.getInstance().getCall(callId);

        if (cfh == null) {
            return;
        }

        if (cfh.getRoom() == null) {
            return;
        }

        cfh.getRoom().forward(player, false);
        CallForHelpManager.getInstance().deleteCall(cfh);
    }
}
