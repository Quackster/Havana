package org.alexdev.havana.messages.incoming.messenger;

import org.alexdev.havana.dao.mysql.MessengerDao;
import org.alexdev.havana.game.messenger.MessengerMessage;
import org.alexdev.havana.game.messenger.MessengerUser;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.wordfilter.WordfilterManager;
import org.alexdev.havana.messages.outgoing.messenger.INSTANT_MESSAGE_ERROR;
import org.alexdev.havana.messages.outgoing.messenger.MESSENGER_MSG;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.StringUtil;

public class MESSENGER_SENDMSG implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        //int amount = reader.readInt();

        /*List<Integer> friends = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            int friend_id = reader.readInt();
            friends.add(friend_id);
        }*/

        int userId = reader.readInt();

        String originalMessage = reader.readString();
        String message = WordfilterManager.filterMandatorySentence(StringUtil.filterInput(originalMessage, false));

        if (message.isBlank()) {
            return;
        }

        if (WordfilterManager.hasBannableSentence(player, originalMessage)) {
            WordfilterManager.performBan(player);
            return;
        }

        if (player.isMuted()) {
            PlayerManager.getInstance().showMutedAlert(player);
            return;
        }

        MessengerUser friend = player.getMessenger().getFriend(userId);

        if (friend == null) {
            player.send(new INSTANT_MESSAGE_ERROR(6, userId));
            return;
        }

        Player friendPlayer = PlayerManager.getInstance().getPlayerById(userId);

        if (friendPlayer == null) {
            player.send(new INSTANT_MESSAGE_ERROR(5, userId));
            return;
        }

        String chatMessage = friendPlayer.getDetails().isWordFilterEnabled() ? WordfilterManager.filterSentence(message) : message;
        int messageId = MessengerDao.newMessage(player.getDetails().getId(), userId, originalMessage);

        MessengerMessage msg = new MessengerMessage(
                messageId, userId, player.getDetails().getId(), DateUtil.getCurrentTimeSeconds(), chatMessage);

        friendPlayer.send(new MESSENGER_MSG(msg));
    }
}
