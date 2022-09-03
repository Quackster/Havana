package org.alexdev.havana.messages.outgoing.moderation;

import org.alexdev.havana.game.moderation.cfh.CallForHelp;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class PICKED_CRY extends MessageComposer {

    private CallForHelp cfh;

    public PICKED_CRY(CallForHelp cfh){
        this.cfh = cfh;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeString(cfh.getCryId());
        response.writeString(cfh.getPickedUpBy());
    }

    @Override
    public short getHeader() {
        return 149; // "BU"
    }
}
