package org.alexdev.havana.messages.outgoing.songs;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.List;

public class USER_SOUND_PACKAGES extends MessageComposer {
    private final List<Integer> handSoundsets;

    public USER_SOUND_PACKAGES(List<Integer> handSoundsets) {
        this.handSoundsets = handSoundsets;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.handSoundsets.size());

        for (Integer songTrackId : this.handSoundsets) {
            response.writeInt(songTrackId);
        }
    }

    @Override
    public short getHeader() {
        return 302; // "Dn"
    }
}
