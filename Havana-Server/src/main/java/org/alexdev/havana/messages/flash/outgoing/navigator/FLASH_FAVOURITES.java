package org.alexdev.havana.messages.flash.outgoing.navigator;

import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.List;

public class FLASH_FAVOURITES extends MessageComposer {
    private final int limit;
    private final List<Room> favouriteRooms;

    public FLASH_FAVOURITES(int limit, List<Room> favouriteRooms) {
        this.limit = limit;
        this.favouriteRooms = favouriteRooms;
    }


    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.limit);
        response.writeInt(this.favouriteRooms.size());

        for (Room room : this.favouriteRooms) {
            response.writeInt(room.getId());
        }
    }

    @Override
    public short getHeader() {
        return 458;
    }
}
