package org.alexdev.havana.messages.incoming.navigator;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.outgoing.navigator.NODESPACEUSERS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.ArrayList;
import java.util.List;

public class GETSPACENODEUSERS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = RoomManager.getInstance().getRoomById(reader.readInt() - RoomManager.PUBLIC_ROOM_OFFSET);

        if (room == null) {
            return;
        }

        var players = room.getEntityManager().getPlayers();

        List<Room> childRooms;

        if (room.isPublicRoom()) {
            childRooms = RoomManager.getInstance().getChildRooms(room);
        } else {
            childRooms = new ArrayList<>();
        }

        if (childRooms.size() > 0) {
            for (Room childRoom : childRooms) {
                players.addAll(childRoom.getEntityManager().getPlayers());
            }
        }

        player.send(new NODESPACEUSERS(players));
    }
}
