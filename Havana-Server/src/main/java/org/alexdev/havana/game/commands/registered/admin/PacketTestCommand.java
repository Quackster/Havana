package org.alexdev.havana.game.commands.registered.admin;

import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerRank;

public class PacketTestCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.ADMINISTRATOR);
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        String packet = String.join(" ", args);

        for (int i = 0; i < 14; i++) {
            packet = packet.replace("{" + i + "}", Character.toString((char)i));
        }

        // Add ending packet suffix
        packet += Character.toString((char)1);

        player.sendObject(packet);
    }

    @Override
    public String getDescription() {
        return "Tests a Habbo client-sided packet";
    }
}
