package org.alexdev.havana.messages.outgoing.moderation;

import org.alexdev.havana.game.moderation.cfh.CallForHelp;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class CFH_ACK extends MessageComposer {
    private final CallForHelp call;

    public CFH_ACK(CallForHelp call){
        this.call = call;
    }

    @Override
    public void compose(NettyResponse response) {
        // TODO: verify if structure and packet name is correct by looking at the lingo
        response.writeBool(call != null);

        if (call != null) {
            response.writeString(call.getCryId());
            response.writeString(call.getFormattedRequestTime());
            response.writeString(call.getMessage());
        }
    }

    @Override
    public short getHeader() {
        return 319; // "D"
    }
}
