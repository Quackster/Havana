package org.alexdev.havana.messages.outgoing.openinghours;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.time.Duration;

public class INFO_HOTEL_CLOSING extends MessageComposer {
    private Duration minutesUntil;

    public INFO_HOTEL_CLOSING(Duration minutesUntil) {
        this.minutesUntil = minutesUntil;
    }


    @Override
    public void compose(NettyResponse response) {
        response.writeInt(Math.toIntExact(minutesUntil.toMinutes()));
    }

    @Override
    public short getHeader() {
        return 291; // "Dc"
    }
}