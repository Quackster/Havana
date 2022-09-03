package org.alexdev.havana.messages.outgoing.user.currencies;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class TICKET_BALANCE extends MessageComposer {
    private final int tickets;

    public TICKET_BALANCE(int tickets) {
        this.tickets = tickets;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.tickets);
    }

    @Override
    public short getHeader() {
        return 124; // "A|"
    }
}
