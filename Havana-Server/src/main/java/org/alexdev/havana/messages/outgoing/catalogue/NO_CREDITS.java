package org.alexdev.havana.messages.outgoing.catalogue;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class NO_CREDITS extends MessageComposer {
    private boolean notEnoughCredits;
    private boolean notEnoughPixels;

    public NO_CREDITS(boolean notEnoughCredits, boolean notEnoughPixels) {
        this.notEnoughCredits = notEnoughCredits;
        this.notEnoughPixels = notEnoughPixels;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeBool(this.notEnoughCredits);
        response.writeBool(this.notEnoughPixels);
    }

    @Override
    public short getHeader() {
        return 68; // "AD"
    }
}
