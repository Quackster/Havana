package org.alexdev.havana.game.song;

public class SongPlaylist {
    private long itemId;
    private Song song;
    private int slotId;

    public SongPlaylist(long itemId, Song song, int slotId) {
        this.itemId = itemId;
        this.song = song;
        this.slotId = slotId;
    }

    public long getItemId() {
        return itemId;
    }

    public Song getSong() {
        return song;
    }

    public int getSlotId() {
        return slotId;
    }
}
