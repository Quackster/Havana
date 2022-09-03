package org.alexdev.havana.messages.outgoing.rooms;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class ROOMAD extends MessageComposer {
    private final String image;
    private final String url;

    public ROOMAD(String image, String url) {
        this.image = image;
        this.url = url;

    }

    @Override
    public void compose(NettyResponse response) {
        if (this.image != null) {
            response.writeString(this.image);
            response.writeString(this.url);
        } else {
            response.writeString("");
        }
    }

    @Override
    public short getHeader() {
        return 208; // "CP"
    }
}
