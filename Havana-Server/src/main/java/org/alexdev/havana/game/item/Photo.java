package org.alexdev.havana.game.item;

public class Photo {
    private long databaseId;
    private int checksum;
    private byte[] data;
    private long time;

    public Photo(long id, int checksum, byte[] data, long time) {
        this.databaseId = id;
        this.checksum = checksum;
        this.data = data;
        this.time = time;
    }

    public long getId() {
        return databaseId;
    }

    public int getChecksum() {
        return checksum;
    }

    public byte[] getData() {
        return data;
    }

    public long getTime() {
        return time;
    }
}
