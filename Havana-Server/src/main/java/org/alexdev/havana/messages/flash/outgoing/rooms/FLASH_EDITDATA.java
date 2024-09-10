package org.alexdev.havana.messages.flash.outgoing.rooms;

import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class FLASH_EDITDATA extends MessageComposer {
    private final Room room;

    public FLASH_EDITDATA(Room room) {
        this.room = room;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.room.getData().getId());
        response.writeString(this.room.getData().getName());
        response.writeString(this.room.getData().getDescription());
        response.writeInt(this.room.getData().getAccessTypeId());
        response.writeInt(this.room.getData().getCategoryId());
        response.writeInt(this.room.getData().getVisitorsMax());
        response.writeInt(this.room.getData().getVisitorsMax());

        response.writeBool(false);
        response.writeBool(false);
        response.writeBool(false);

        var tags = room.getData().getTags();
        response.writeInt(tags.size());

        for (String tag : tags) {
            response.writeString(tag);
        }


        response.writeInt(this.room.getRights().size());

        for (int userId : this.room.getRights()) {
            PlayerDetails playerDetails = PlayerManager.getInstance().getPlayerData(userId);

            response.writeInt(userId);
            response.writeString(playerDetails.getName());
        }

        response.writeInt(this.room.getRights().size());
    }

    @Override
    public short getHeader() {
        return 465;
    }
}
