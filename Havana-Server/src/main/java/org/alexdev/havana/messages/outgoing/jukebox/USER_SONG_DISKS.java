package org.alexdev.havana.messages.outgoing.jukebox;

import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.List;
import java.util.Map;

public class USER_SONG_DISKS extends MessageComposer {
    private final Map<Item, Integer> userDisks;

    public USER_SONG_DISKS(Map<Item, Integer> userDisks) {
        this.userDisks = userDisks;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.userDisks.size());

        for (var kvp : this.userDisks.entrySet()) {
            response.writeInt(kvp.getValue());
            response.writeString(kvp.getKey().getCustomData().split(Character.toString((char)10))[5]);
        }
    }

    @Override
    public short getHeader() {
        return 333;
    }
}
