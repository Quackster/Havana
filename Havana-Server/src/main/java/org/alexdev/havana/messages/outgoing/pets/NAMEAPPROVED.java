package org.alexdev.havana.messages.outgoing.pets;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class NAMEAPPROVED extends MessageComposer {
    private final int approveStatus;

    public NAMEAPPROVED(int approveStatus) {
        this.approveStatus = approveStatus;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.approveStatus);
    }

    @Override
    public short getHeader() {
        return 36;
    }
}
