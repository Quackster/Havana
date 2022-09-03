package org.alexdev.havana.messages.incoming.rooms.user;

import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.handlers.walkways.WalkwaysManager;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class GOAWAY implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        if (!player.getRoomUser().isWalkingAllowed()) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (room.isPublicRoom()) {
            Position doorPosition = room.getModel().getDoorLocation();

            if (WalkwaysManager.getInstance().getWalkway(room, doorPosition) != null) {
                return;
            }
        }

        player.getRoomUser().kick(true, false);
    }
}
