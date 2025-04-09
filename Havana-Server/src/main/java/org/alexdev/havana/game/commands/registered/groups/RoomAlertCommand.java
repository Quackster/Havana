package org.alexdev.havana.game.commands.registered.groups;

import org.alexdev.havana.dao.mysql.ModerationDao;
import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.moderation.ModerationActionType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.outgoing.moderation.MODERATOR_ALERT;
import org.alexdev.havana.util.StringUtil;

import java.util.List;

public class RoomAlertCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.MODERATOR);
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        // Concatenate all arguments
        String alert = StringUtil.filterInput(String.join(" ", args), true);
        List<Player> players = entity.getRoomUser().getRoom().getEntityManager().getPlayers();

        for (Player target : players) {
            target.send(new MODERATOR_ALERT(alert));
        }

        ModerationDao.addLog(ModerationActionType.ROOM_ALERT, entity.getDetails().getId(), -1, alert, "");
    }

    @Override
    public String getDescription() {
        return "Sends an alert room-wide";
    }
}