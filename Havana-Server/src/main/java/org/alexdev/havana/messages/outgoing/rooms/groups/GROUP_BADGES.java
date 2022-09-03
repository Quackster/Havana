package org.alexdev.havana.messages.outgoing.rooms.groups;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.HashMap;

public class GROUP_BADGES extends MessageComposer {
    private final HashMap<Integer, String> groupBadges;

    public GROUP_BADGES(HashMap<Integer, String> groupBadges) {
        this.groupBadges = groupBadges;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.groupBadges.size());

        for (var entry : this.groupBadges.entrySet()) {
            response.writeInt(entry.getKey());
            response.writeString(entry.getValue());
        }
    }

    @Override
    public short getHeader() {
        return 309;
    }
}
