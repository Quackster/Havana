package org.alexdev.havana.game.commands.registered;

import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE.ChatMessageType;
import org.alexdev.havana.util.config.GameConfiguration;

import java.time.Duration;

public class ShutdownCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.ADMINISTRATOR);
    }

    @Override
    public void addArguments() {
        /*this.arguments.add("minutes");*/
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        // Abort maintenance shutdown if provided argument is either cancel, off or stop (case insensitive)
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("cancel") || args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("stop")) {
                PlayerManager.getInstance().cancelMaintenance();
                player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "Cancelled shutdown", 0));
                return;
            }
        }

        long minutes;

        // Try parsing minutes argument, use default if failed
        try {
            if (args.length > 0) {
                minutes = Long.parseLong(args[0]);
            } else {
                minutes = GameConfiguration.getInstance().getLong("shutdown.minutes");
            }
        } catch (NumberFormatException e) {
            minutes = GameConfiguration.getInstance().getLong("shutdown.minutes");
            player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "Failed to parse minutes provided to shutdown command, defaulting to " + minutes + " minute(s)", 0));
        }

        // Enqueue maintenance shutdown
        PlayerManager.getInstance().planMaintenance(Duration.ofMinutes(minutes));

        // Let callee know Havana is shutting down in X minutes
        player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "Shutting down in " + minutes + " minute(s)", 0));
    }

    @Override
    public String getDescription() {
        return "<minutes> - Shutdown Havana";
    }
}