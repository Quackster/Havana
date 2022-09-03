package org.alexdev.havana.game.commands.registered.moderation;

import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;

public class UnacceptableCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.MODERATOR);
    }

    @Override
    public void addArguments() {
        this.arguments.add("name/desc/description/both");
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

        var room = player.getRoomUser().getRoom();
        var unacceptableValue = "Unacceptable to hotel management";

        if (args[0].equalsIgnoreCase("name")) {
            room.getData().setName(unacceptableValue);
            RoomDao.save(room);
            player.send(new ALERT("Set the room name to unacceptable"));
            return;
        }

        if (args[0].equalsIgnoreCase("desc") || args[0].equalsIgnoreCase("description")) {
            room.getData().setDescription(unacceptableValue);
            RoomDao.save(room);
            player.send(new ALERT("Set the room description to unacceptable"));
            return;
        }

        if (args[0].equalsIgnoreCase("both")) {
            room.getData().setName(unacceptableValue);
            room.getData().setDescription(unacceptableValue);
            RoomDao.save(room);
            player.send(new ALERT("Set both the room name and description to unacceptable"));
            return;
        }
    }

    @Override
    public String getDescription() {
        return "Makes a room set to 'Unacceptable to hotel management'";
    }
}
