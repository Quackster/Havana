package org.alexdev.havana.messages.incoming.messenger;

import org.alexdev.havana.dao.mysql.MessengerDao;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.messenger.*;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.messages.outgoing.messenger.ADD_BUDDY;
import org.alexdev.havana.messages.outgoing.messenger.BUDDY_REQUEST_RESULT;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.util.MalformedPacketException;

import java.util.ArrayList;
import java.util.List;

public class MESSENGER_ACCEPTBUDDY implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        List<MessengerError> errors = new ArrayList<>();

        int amount = reader.readInt();

        for (int i = 0; i < amount; i++) {
            int userId = reader.readInt();

            MessengerUser newBuddy = player.getMessenger().getRequest(userId);

            if (newBuddy == null) {
                MessengerError error = new MessengerError(MessengerErrorType.FRIEND_REQUEST_NOT_FOUND);
                error.setCauser("");

                errors.add(error);
                continue;
            }

            Messenger newBuddyData = MessengerManager.getInstance().getMessengerData(userId);

            if (newBuddyData == null) {
                // log warning
                continue;
            }

            if (player.getMessenger().isFriendsLimitReached()) {
                MessengerError error = new MessengerError(MessengerErrorType.FRIENDLIST_FULL);
                error.setCauser(newBuddy.getUsername());

                errors.add(error);
                continue;
            }

            if (newBuddyData.isFriendsLimitReached()) {
                MessengerError error = new MessengerError(MessengerErrorType.TARGET_FRIEND_LIST_FULL);
                error.setCauser(newBuddy.getUsername());

                errors.add(error);
                continue;
            }

            /*if (!newBuddyData.allowsFriendRequests()) {
                MessengerError error = new MessengerError(MessengerErrorType.TARGET_DOES_NOT_ACCEPT);
                error.setCauser(newBuddy.getUsername());

                errors.add(error);
                continue;
            }*/

            player.getMessenger().addFriend(newBuddy);
        }

        player.send(new BUDDY_REQUEST_RESULT(errors));
    }
}
