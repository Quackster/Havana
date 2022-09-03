package org.alexdev.havana.messages.incoming.rooms.user;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class RATEFLAT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null || room.isPublicRoom()) {
            return;
        }

        // Room owner is not allowed to vote on his own room
        if (room.getData().getOwnerId() == player.getDetails().getId()) {
            return;
        }

        int answer = reader.readInt();

        // It's either negative or positive
        if (answer != 1 && answer != -1) {
            return;
        }

        if (room.hasVoted(player)) {
            return;
        }

        room.addVote(answer, player);
    }
}
