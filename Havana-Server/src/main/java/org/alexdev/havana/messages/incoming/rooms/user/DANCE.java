package org.alexdev.havana.messages.incoming.rooms.user;


import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class DANCE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (player.getRoomUser().containsStatus(StatusType.SWIM) || player.getRoomUser().containsStatus(StatusType.SIT) || player.getRoomUser().containsStatus(StatusType.LAY)) {
            return; // Don't allow dancing if they're sitting or laying.
        }

        int danceId = reader.readInt();

        if (danceId == 0) {
            player.getRoomUser().stopDancing();
        } else if (danceId == 1) {
            player.getRoomUser().dance(danceId);
        } else if (danceId > 1 && danceId < 5) {
            if (!player.hasFuse(Fuseright.USE_CLUB_DANCE)) {
                return;
            }

            player.getRoomUser().dance(danceId);
        }

        player.getRoomUser().stopCarrying();
        player.getRoomUser().getTimerManager().resetRoomTimer();
    }
}
