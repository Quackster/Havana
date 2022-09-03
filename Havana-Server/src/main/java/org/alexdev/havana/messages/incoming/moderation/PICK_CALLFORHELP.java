package org.alexdev.havana.messages.incoming.moderation;

import org.alexdev.havana.game.moderation.cfh.CallForHelp;
import org.alexdev.havana.game.moderation.cfh.CallForHelpManager;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class PICK_CALLFORHELP implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (!player.hasFuse(Fuseright.RECEIVE_CALLS_FOR_HELP)) {
            return;
        }

        int callId = Integer.parseInt(reader.readString());
        boolean blockCfh = reader.readBoolean();

        CallForHelp cfh = CallForHelpManager.getInstance().getCall(callId);

        if (cfh ==  null) {
            return;
        }

        CallForHelpManager.getInstance().pickUp(cfh, player);

        if (blockCfh) {
            CallForHelpManager.getInstance().deleteCall(cfh);
        }
    }
}
