package org.alexdev.havana.game.commands.registered;

import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.util.StringUtil;

import java.util.List;
import java.util.Map;

public class UsersOnlineCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.NORMAL);
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        int maxPlayersPerLine = 5;

        List<Player> players = PlayerManager.getInstance().getPlayers();
        Map<Integer, List<Player>> paginatedPlayers = StringUtil.paginate(players, maxPlayersPerLine);

        Player session = (Player) entity;

        StringBuilder sb = new StringBuilder()
                .append("Users online: ").append(players.size()).append("<br>")
                .append("Daily player peak count: ").append(PlayerManager.getInstance().getDailyPlayerPeak()).append("<br>")
                .append("List of users online: ").append("<br><br>");

        for (List<Player> playerList : paginatedPlayers.values()) {
            int i = 0;
            int length = playerList.size();
            for (Player player : playerList) {
                if (!player.getDetails().isOnlineStatusVisible()) {
                    continue;
                }

                sb.append(player.getDetails().getName());

                i++;

                if (i < length) {
                    sb.append(", ");
                }
            }

            sb.append("<br>");
        }

        session.send(new ALERT(sb.toString()));
    }

    @Override
    public String getDescription() {
        return "Get the list of players currently online";
    }
}
