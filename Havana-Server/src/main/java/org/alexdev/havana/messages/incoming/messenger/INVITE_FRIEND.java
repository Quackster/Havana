package org.alexdev.havana.messages.incoming.messenger;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.messenger.INSTANT_MESSAGE_INVITATION;
import org.alexdev.havana.messages.outgoing.messenger.INVITATION_ERROR;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class INVITE_FRIEND implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }


        int users = reader.readInt();

        List<Player> friends = new ArrayList<>();

        for (int i = 0; i < users; i++) {
            int userId = reader.readInt();

            if (!player.getMessenger().hasFriend(userId)) {
                player.send(new INVITATION_ERROR());
                break;
            }

            Player friend = PlayerManager.getInstance().getPlayerById(userId);

            if (friend == null) {
                player.send(new INVITATION_ERROR());
                continue;
            }

            friends.add(friend);
        }

        String message = StringUtil.filterInput(reader.readString(), false);

        for (Player friend : friends) {
            friend.send(new INSTANT_MESSAGE_INVITATION(player.getDetails().getId(), message));
        }
    }
}
