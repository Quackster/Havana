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

public class CHAT implements MessageEvent {
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

        player.getRoomUser().talk(message, CHAT_MESSAGE.ChatMessageType.CHAT);
        // Make talk hard to read for long distance in public rooms
        /*if (room.isPublicRoom() && GameConfiguration.getInstance().getBoolean("talk.garbled.text") && !room.getModel().getName().contains("_arena_")) {
            int sourceX = player.getRoomUser().getPosition().getX();
            int sourceY = player.getRoomUser().getPosition().getY();

            for (Player roomPlayer : room.getEntityManager().getPlayers()) {
                int distX = Math.abs(sourceX - roomPlayer.getRoomUser().getPosition().getX()) - 1;
                int distY = Math.abs(sourceY - roomPlayer.getRoomUser().getPosition().getY()) - 1;

                if (distX < 9 && distY < 9) {// User can hear
                    if (distX <= 6 && distY <= 6) {// User can hear full message
                        roomPlayer.send(new CHAT_MESSAGE(ChatMessageType.CHAT, player.getRoomUser().getInstanceId(), message, gestureId));
                    } else {
                        int garbleIntensity = distX;

                        if (distY < distX) {
                            garbleIntensity = distY;
                        }

                        garbleIntensity -= 4;
                        char[] garbleMessage = message.toCharArray();

                        for (int pos = 0; pos < garbleMessage.length; pos++) {
                            int intensity = ThreadLocalRandom.current().nextInt(garbleIntensity, 6);

                            if (intensity > 3 &&
                                    garbleMessage[pos] != ' ' &&
                                    garbleMessage[pos] != ',' &&
                                    garbleMessage[pos] != '?' &&
                                    garbleMessage[pos] != '!') {
                                garbleMessage[pos] = '.';
                            }
                        }

                        roomPlayer.send(new CHAT_MESSAGE(ChatMessageType.CHAT, player.getRoomUser().getInstanceId(), new String(garbleMessage), gestureId));
                    }
                } else {
                    // Disappearing talk bubble
                    roomPlayer.send(new CHAT_MESSAGE(ChatMessageType.CHAT, player.getRoomUser().getInstanceId(), "",  gestureId));
                }
            }
        } else {
            var chatMsg = new CHAT_MESSAGE(ChatMessageType.CHAT, player.getRoomUser().getInstanceId(), message, gestureId);

            for (Player sessions : room.getEntityManager().getPlayers()) {
                if (sessions.getIgnoredList().contains(player.getDetails().getName())) {
                    continue;
                }

                sessions.send(chatMsg);
            }
        }*/
    }
}
