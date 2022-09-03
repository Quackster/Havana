package org.alexdev.havana.messages.incoming.rooms.user;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.wordfilter.WordfilterManager;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE.ChatMessageType;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class WHISPER implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        String contents = reader.readString();

        String username = contents.substring(0, contents.indexOf(" "));
        String message = WordfilterManager.filterMandatorySentence(StringUtil.filterInput(contents.substring(username.length() + 1), true));

        if (message.isBlank()) {
            return;
        }
        
        if (WordfilterManager.hasBannableSentence(player, StringUtil.filterInput(contents.substring(username.length() + 1), true))) {
            WordfilterManager.performBan(player);
            return;
        }

        if (player.isMuted()) {
            PlayerManager.getInstance().showMutedAlert(player);
            return;
        }

        List<Player> receieveMessages = new ArrayList<>();
        receieveMessages.add(player);

        Player whisperUser = PlayerManager.getInstance().getPlayerByName(username);

        if (whisperUser != null) {
            if (!whisperUser.getIgnoredList().contains(player.getDetails().getName())) {
                receieveMessages.add(whisperUser);
            }
        }

        player.getRoomUser().talk(message, ChatMessageType.WHISPER, receieveMessages);
    }
}
