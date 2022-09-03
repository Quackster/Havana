package org.alexdev.havana.messages.outgoing.alerts;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class ALERT extends MessageComposer {
    private String message;

    public ALERT(String message) {
        this.message = message;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeString(this.message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public short getHeader() {
        return 139; // "BK"
    }
}
