package org.alexdev.havana.messages.incoming.rooms.moderation;

import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.rooms.FLATNOTALLOWEDTOENTER;
import org.alexdev.havana.messages.outgoing.rooms.FLAT_LETIN;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class LETUSERIN implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.hasRights(player.getDetails().getId())&& !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            return;
        }

        String username = reader.readString();
        boolean canEnter = reader.readBoolean();

        Player enteringPlayer = PlayerManager.getInstance().getPlayerByName(username);

        if (enteringPlayer == null || enteringPlayer.getRoomUser().getAuthenticateId() == room.getId()) {
            return;
        }

        if (canEnter) {
            enteringPlayer.getRoomUser().setAuthenticateId(room.getId());
            enteringPlayer.send(new FLAT_LETIN());
        } else {
            enteringPlayer.send(new FLATNOTALLOWEDTOENTER());
        }
    }
}
