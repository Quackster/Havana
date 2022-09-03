package org.alexdev.havana.messages.incoming.rooms.user;

import org.alexdev.havana.game.commands.CommandManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.wordfilter.WordfilterManager;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE;
import org.alexdev.havana.messages.outgoing.rooms.user.TYPING_STATUS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.StringUtil;

public class SHOUT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        String originalMessage = reader.readString();
        String message = WordfilterManager.filterMandatorySentence(StringUtil.filterInput(originalMessage, true));

        if (message.isBlank()) {
            return;
        }

        if (WordfilterManager.hasBannableSentence(player, originalMessage)) {
            WordfilterManager.performBan(player);
            return;
        }

        if (CommandManager.getInstance().hasCommand(player, originalMessage)) {
            CommandManager.getInstance().invokeCommand(player, originalMessage);
            return;
        }

        if (player.isMuted()) {
            PlayerManager.getInstance().showMutedAlert(player);
            return;
        }

        player.getRoomUser().setTyping(false);
        room.send(new TYPING_STATUS(player.getRoomUser().getInstanceId(), player.getRoomUser().isTyping()));

        if (message.isEmpty()) {
            return;
        }

        player.getRoomUser().talk(message, CHAT_MESSAGE.ChatMessageType.SHOUT);
    }
}
