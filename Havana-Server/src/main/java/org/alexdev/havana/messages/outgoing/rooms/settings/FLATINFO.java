package org.alexdev.havana.messages.outgoing.rooms.settings;

import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class FLATINFO extends MessageComposer {
    private final boolean overrideLock;
    private Player player;
    private Room room;

    public FLATINFO(Player player, Room room, boolean overrideLock) {
        this.player = player;
        this.room = room;
        this.overrideLock = overrideLock;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeBool(this.room.getData().allowSuperUsers());
        response.writeInt(this.overrideLock ? 0 : this.room.getData().getAccessTypeId());
        response.writeInt(this.room.getId());

        if (this.room.isOwner(player.getDetails().getId())|| this.room.getData().showOwnerName() || this.player.hasFuse(Fuseright.SEE_ALL_ROOMOWNERS)) {
            response.writeString(this.room.getData().getOwnerName());
        } else {
            response.writeString("-");
        }

        response.writeString(this.room.getModel().getName()); // Is called "marker" in Lingo code
        response.writeString(this.room.getData().getName());
        response.writeString(this.room.getData().getDescription());
        response.writeBool(this.room.getData().showOwnerName());
        response.writeBool(this.room.getCategory().hasAllowTrading()); // Allow trading
        response.writeInt(this.room.getData().getVisitorsNow());
        response.writeInt(this.room.getData().getVisitorsMax());
    }

    @Override
    public short getHeader() {
        return 54; // "@v"
    }
}

