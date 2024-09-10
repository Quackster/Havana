package org.alexdev.havana.messages.flash.outgoing.navigator;

import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.List;

public class FLASH_SEARCHRESULTS extends MessageComposer {
    private final List<Room> roomList;
    private final String searchQuery;

    public FLASH_SEARCHRESULTS(List<Room> roomList, String searchQuery) {
        this.roomList = roomList;
        this.searchQuery = searchQuery;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(1);
        response.writeInt(9);
        response.writeString(this.searchQuery);

        response.writeInt(this.roomList.size());

        for (Room room : this.roomList) {
            response.writeInt(room.getData().getId());
            response.writeBool(false);
            response.writeString(room.getData().getName());
            response.writeString(room.getData().getOwnerName());
            response.writeInt(room.getData().getAccessTypeId()); // room state
            response.writeInt(room.getData().getVisitorsNow());
            response.writeInt(room.getData().getVisitorsMax());
            response.writeString(room.getData().getDescription());
            response.writeInt(1);
            response.writeBool(room.getCategory().hasAllowTrading()); // can trade?
            response.writeInt(room.getData().getRating());
            response.writeInt(0);
            response.writeString("");

            var tags = room.getData().getTags();
            response.writeInt(tags.size());

            for (String tag : tags) {
                response.writeString(tag);
            }

            response.writeInt(0);
            response.writeInt(0);
            response.writeInt(0);
        }
    }

    @Override
    public short getHeader() {
        return 451;
    }
}
