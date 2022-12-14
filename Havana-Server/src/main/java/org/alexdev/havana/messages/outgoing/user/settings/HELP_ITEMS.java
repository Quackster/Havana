package org.alexdev.havana.messages.outgoing.user.settings;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.List;

public class HELP_ITEMS extends MessageComposer {
    private final List<Integer> tutorialFlags;

    public HELP_ITEMS(List<Integer> tutorialFlags) {
        this.tutorialFlags = tutorialFlags;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.tutorialFlags.size());

        for (int flag : this.tutorialFlags) {
            response.writeInt(flag);
        }

    }

    @Override
    public short getHeader() {
        return 352; // "E`"
    }
}
