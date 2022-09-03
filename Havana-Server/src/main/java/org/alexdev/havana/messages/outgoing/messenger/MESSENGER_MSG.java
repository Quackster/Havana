package org.alexdev.havana.messages.outgoing.messenger;

import org.alexdev.havana.game.messenger.MessengerMessage;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class MESSENGER_MSG extends MessageComposer {
    private final MessengerMessage message;

    public MESSENGER_MSG(MessengerMessage message) {
        this.message = message;
    }

    @Override
    public void compose(NettyResponse response) {
        //response.writeInt(this.message.getVirtualId());
        response.writeInt(this.message.getFromId());
        //response.writeString(DateUtil.getDateAsString(this.message.getTimeSet()));
        response.writeString(this.message.getMessage());
    }

    public MessengerMessage getMessage() {
        return message;
    }

    @Override
    public short getHeader() {
        return 134; // "BF"
    }
}
