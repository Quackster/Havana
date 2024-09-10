package org.alexdev.havana.messages.outgoing.rooms;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class ROOMQUEUEDATA extends MessageComposer {
    private int position;

    public ROOMQUEUEDATA(int position) {
        this.position = position;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(1);

        response.writeString("d"); // general_loader_text /
        response.writeInt(2); // queue_current_ / queue_other_

        response.writeInt(1);

        response.writeString("d"); // queue_set.d.info=There are %d% Habbos in front of you in the queue.
        response.writeInt(this.position);
    }

    @Override
    public short getHeader() {
        return 259;
    }
}
