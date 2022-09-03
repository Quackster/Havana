package org.alexdev.havana.messages.outgoing.trade;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class TRADEOPEN extends MessageComposer {
    private final int userId;
    private final boolean userCanTrade;
    private final int partnerId;
    private final boolean partnerCanTrade;

    public TRADEOPEN(int userId, boolean userCanTrade, int partnerId, boolean partnerCanTrade) {
        this.userId = userId;
        this.userCanTrade = userCanTrade;
        this.partnerId = partnerId;
        this.partnerCanTrade = partnerCanTrade;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.userId);
        response.writeBool(this.userCanTrade);
        response.writeInt(this.partnerId);
        response.writeBool(this.partnerCanTrade); // User can trade. TODO: Honour user settings
    }

    @Override
    public short getHeader() {
        return 104; // "Ah"
    }
}
