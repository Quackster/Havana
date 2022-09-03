package org.alexdev.havana.messages.outgoing.alerts;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class LOCALISED_ERROR extends MessageComposer {
    private final String externalTextEntry;
    private final int errorCode;

    public LOCALISED_ERROR(String externalTextEntry) {
        this.externalTextEntry = externalTextEntry;
        this.errorCode = -1;
    }

    public LOCALISED_ERROR(int errorCode) {
        this.externalTextEntry = null;
        this.errorCode = errorCode;
    }

    @Override
    public void compose(NettyResponse response) {
        if (this.externalTextEntry != null) {
            response.write(this.externalTextEntry);
        } else {
            response.writeInt(this.errorCode);
        }
    }

    @Override
    public short getHeader() {
        return 33; // "@a"
    }
}
