package org.alexdev.havana.messages.outgoing.songs;

import org.alexdev.havana.game.song.Song;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class SONG_INFO extends MessageComposer {
    private final Song song;

    public SONG_INFO(Song song) {
        this.song = song;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.song.getId());
        response.writeString(this.song.getTitle());
        response.writeString(this.song.getData());
    }

    @Override
    public short getHeader() {
        return 300; // "Dl"
    }
}
