package org.alexdev.havana.messages.outgoing.jukebox;

import org.alexdev.havana.game.item.ItemManager;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.song.jukebox.BurnedDisk;
import org.alexdev.havana.game.song.Song;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.Map;

public class JUKEBOX_DISCS extends MessageComposer {
    private final Map<BurnedDisk, Song> disks;

    public JUKEBOX_DISCS(Map<BurnedDisk, Song> savedTracks) {
        this.disks = savedTracks;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(10);
        response.writeInt(this.disks.size());

        for (var kvp : this.disks.entrySet()) {
            BurnedDisk burnedDisk = kvp.getKey();
            Song song = kvp.getValue();

            response.writeInt(burnedDisk.getSlotId());
            response.writeInt(song.getId());
            response.writeInt(ItemManager.getInstance().calculateSongLength(song.getData()) / 2);

            response.writeString(song.getTitle());
            response.writeString(PlayerManager.getInstance().getPlayerData(song.getUserId()).getName());
        }
    }

    @Override
    public short getHeader() {
        return 334;
    }
}
