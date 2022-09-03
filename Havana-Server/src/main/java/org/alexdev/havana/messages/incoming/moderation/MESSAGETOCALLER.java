package org.alexdev.havana.messages.incoming.moderation;

import org.alexdev.havana.game.moderation.cfh.CallForHelp;
import org.alexdev.havana.game.moderation.cfh.CallForHelpManager;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.messages.outgoing.moderation.CRY_REPLY;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class MESSAGETOCALLER implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (!player.hasFuse(Fuseright.RECEIVE_CALLS_FOR_HELP)) {
            return;
        }

        int callId = Integer.parseInt(reader.readString());
        String message = reader.readString();

        CallForHelp cfh = CallForHelpManager.getInstance().getCall(callId);

        if (cfh == null) {
            return;
        }

        Player caller = PlayerManager.getInstance().getPlayerById(cfh.getCaller());

        if (caller == null) {
            return;
        }

        caller.send(new CRY_REPLY(message));
        CallForHelpManager.getInstance().deleteCall(cfh);
    }
}
