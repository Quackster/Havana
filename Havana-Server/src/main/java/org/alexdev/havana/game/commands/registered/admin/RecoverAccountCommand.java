package org.alexdev.havana.game.commands.registered.admin;

import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;

public class RecoverAccountCommand extends Command {
    @Override
    public void setPlayerRank() {
        this.setPlayerRank(PlayerRank.ADMINISTRATOR);
    }

    @Override
    public void addArguments() {
        this.arguments.add("user");
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) throws Exception {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        PlayerDetails targetUser = PlayerDao.getDetails(args[0]);

        if (targetUser == null) {
            player.send(new ALERT("User not found"));
            return;
        }

        player.send(new ALERT(targetUser.getName() + "'s password has been reset to: changeme123"));
        PlayerDao.setPassword(targetUser.getId(), PlayerManager.getInstance().createPassword("changeme123"));
    }

    @Override
    public String getDescription() {
        return "Resets the player's account";
    }
}
