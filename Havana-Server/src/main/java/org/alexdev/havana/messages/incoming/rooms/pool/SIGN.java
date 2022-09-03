package org.alexdev.havana.messages.incoming.rooms.pool;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.apache.commons.lang3.StringUtils;

public class SIGN implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        String contents = reader.contents();

        if (!StringUtils.isNumeric(contents)) {
            return;
        }

        int vote = Integer.parseInt(contents);

        if (vote < 0) {
            return;
        }

        if (vote <= 7) {
            player.getRoomUser().setLidoVote(vote + 3);
        }

        player.getRoomUser().setStatus(StatusType.SIGN, contents, 5, null, -1, -1);
        player.getRoomUser().setNeedsUpdate(true);

        player.getRoomUser().getTimerManager().resetRoomTimer();
    }
}