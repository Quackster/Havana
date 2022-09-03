package org.alexdev.havana.messages.outgoing.user.currencies;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class CREDIT_BALANCE extends MessageComposer {
    private final int credits;

    public CREDIT_BALANCE(int credits) {
        this.credits = credits;
    }


    @Override
    public void compose(NettyResponse response) {
        response.write(this.credits);
    }

    @Override
    public short getHeader() {
        return 6; // "@F
    }
}
