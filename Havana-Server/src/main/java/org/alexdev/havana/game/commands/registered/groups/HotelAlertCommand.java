package org.alexdev.havana.game.commands.registered.groups;

import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.util.StringUtil;

public class HotelAlertCommand extends Command {
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
        alert += "<br><br>- " + entity.getDetails().getName();

        // Send all players an alert
        PlayerManager.getInstance().sendAll(new ALERT(alert));
    }

    @Override
    public String getDescription() {
        return "Sends an alert hotel-wide";
    }
}