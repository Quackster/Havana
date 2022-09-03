package org.alexdev.havana.messages.outgoing.rooms.items;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class JUDGE_GUI_STATUS extends MessageComposer {
    private final int status;
    private final int userId;

    public JUDGE_GUI_STATUS(int status, int userId) {
        this.status = status;
        this.userId = userId;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.status);

        if (this.status == 2)
            response.writeInt(this.userId);
    }

    @Override
    public short getHeader() {
        return 490;
    }
}
