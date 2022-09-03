package org.alexdev.havana.game.commands.registered.moderation;

import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.util.StringUtil;

public class CoordsCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.COMMUNITY_MANAGER);
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        player.send(new ALERT("Your coordinates:<br>" +
                "Room ID: " + player.getRoomUser().getRoom().getId() + "<br><br>" +
                "X: " + player.getRoomUser().getPosition().getX() + "<br>" +
                "Y: " + player.getRoomUser().getPosition().getY() + "<br>" +
                "Z: " + StringUtil.format(player.getRoomUser().getPosition().getZ()) + "<br><br>" +
                "Head rotation: " + player.getRoomUser().getPosition().getHeadRotation() + "<br>" +
                "Body rotation: " + player.getRoomUser().getPosition().getBodyRotation() + "<br>"));
    }

    @Override
    public String getDescription() {
        return "Shows the coordinates in the room";
    }
}
