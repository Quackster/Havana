package org.alexdev.havana.messages.flash.outgoing.modtool;

import org.alexdev.havana.game.events.EventsManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class FLASH_MODTOOL_ROOMDATA extends MessageComposer {
    private final Room room;

    public FLASH_MODTOOL_ROOMDATA(Room room) {
        this.room = room;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.room.getId());
        response.writeInt(this.room.getData().getTotalVisitorsNow()); // user count
        response.writeBool(this.room.getEntityManager().getPlayers().stream().anyMatch(user -> user.getDetails().getId() == room.getData().getOwnerId()));
        response.writeInt(this.room.getData().getOwnerId());
        response.writeString(this.room.getData().getOwnerName());
        response.writeInt(this.room.getId());
        response.writeString(this.room.getData().getName());
        response.writeString(this.room.getData().getDescription());
        response.writeInt(this.room.getData().getTags().size());

        for (String tag : this.room.getData().getTags()) {
            response.writeString(tag);
        }

        var event = EventsManager.getInstance().getEventByRoomId(this.room.getId());
        response.writeBool(event != null);

        if (event != null) {
            response.writeString(event.getName());
            response.writeString(event.getDescription());
            response.writeInt(event.getTags().size());

            for (String tag : event.getTags()) {
                response.writeString(tag);
            }
        }
    }

    @Override
    public short getHeader() {
        return 538;
    }
}
