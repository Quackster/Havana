package org.alexdev.havana.messages.outgoing.rooms;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class INTERSITIALDATA extends MessageComposer {
    private final String image;
    private final String url;

    public INTERSITIALDATA(String image, String url) {
        this.image = image;
        this.url = url;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeString(this.image);
        response.writeString(this.url);
    }

    @Override
    public short getHeader() {
        return 258;
    }
}
