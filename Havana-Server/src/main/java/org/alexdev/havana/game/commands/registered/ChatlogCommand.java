package org.alexdev.havana.game.commands.registered;

import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.moderation.ChatMessage;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class ChatlogCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.NORMAL);
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        var roomChatLogs = RoomDao.getModChatlog(entity.getRoomUser().getRoom().getId());

        StringBuilder viewableLogs = new StringBuilder();

        var sortedChatLogs = roomChatLogs.stream()
                .filter(msg -> msg.getSentTime() > entity.getRoomUser().getEnteredRoomAt())
                .sorted(Comparator.comparing(ChatMessage::getSentTime))
                .toList();

        for (var commandSet : sortedChatLogs) {
            LocalDateTime dateTime = LocalDateTime.ofEpochSecond(commandSet.getSentTime(), 0, java.time.ZoneOffset.UTC);
            String formattedTime = dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            viewableLogs.append("[").append(formattedTime).append("] ");
            viewableLogs.append(commandSet.getPlayerName());
            viewableLogs.append(": ");
            viewableLogs.append(commandSet.getMessage());
            viewableLogs.append("<br>");
        }

        if (entity.getType() == EntityType.PLAYER) {
            Player player = (Player) entity;
            player.send(new ALERT(viewableLogs.toString()));
        }
    }

    @Override
    public String getDescription() {
        return "Display chat log for current room.";
    }
}


