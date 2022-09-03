package org.alexdev.havana.messages.outgoing.navigator;

import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.List;

public class SEARCH_FLAT_RESULTS extends MessageComposer {
    private final List<Room> roomList;
    private final Player player;

    public SEARCH_FLAT_RESULTS(List<Room> roomList, Player player) {
        this.roomList = roomList;
        this.player = player;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.roomList.size());

        for (Room room : this.roomList) {
            response.writeInt(room.getId());
            response.writeString(room.getData().getName());

            if (room.isOwner(this.player.getDetails().getId()) || room.getData().showOwnerName() || this.player.hasFuse(Fuseright.SEE_ALL_ROOMOWNERS)) {
                response.writeString(room.getData().getOwnerName());
            } else {
                response.writeString("-");
            }

            response.writeString(room.getData().getAccessType());
            response.writeInt(room.getData().getVisitorsNow());
            response.writeInt(room.getData().getVisitorsMax());
            response.writeString(room.getData().getDescription());
        }
    }

    @Override
    public short getHeader() {
        return 55; // "@w"
    }
}
