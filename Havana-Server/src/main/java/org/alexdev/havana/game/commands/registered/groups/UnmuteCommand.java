package org.alexdev.havana.game.commands.registered.groups;

import org.alexdev.havana.dao.mysql.PlayerStatisticsDao;
import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;

public class UnmuteCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.MODERATOR);
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

        String name = args[0];

        PlayerDetails playerDetails = PlayerManager.getInstance().getPlayerData(name);

        if (playerDetails == null) {
            player.send(new ALERT("Could not find user: " + name));
            return;
        }

        /*if (CommandManager.getInstance().hasPermission(playerDetails, "unmute")) {
            player.send(new ALERT("Cannot unmute a user who has permission to unmute"));
            return;
        }*/

        long expiration = 0;//DateUtil.getCurrentTimeSeconds() + seconds;
        PlayerStatisticsDao.updateStatistic(playerDetails.getId(), PlayerStatistic.MUTE_EXPIRES_AT, expiration);

        var online = PlayerManager.getInstance().getPlayerById(playerDetails.getId());

        if (online != null) {
            online.getStatisticManager().setLongValue(PlayerStatistic.MUTE_EXPIRES_AT, expiration);

        }

        player.send(new ALERT("Player (" + playerDetails.getName() + ") unmute expiration date removed"));
        /*
            if (playerDetails.getIpAddress() != null && playerDetails.getIpAddress().length() > 0) {
                BanDao.addBan(BanType.IP_ADDRESS, playerDetails.getIpAddress(), in20Years);
            }
        } else {
            if (playerDetails.getIpAddress() != null && playerDetails.getIpAddress().length() > 0) {
                BanDao.addBan(BanType.IP_ADDRESS, playerDetails.getIpAddress(), in20Years);
            }
        }*/
    }

    @Override
    public void addArguments() {
        arguments.add("user");
    }

    @Override
    public String getDescription() {
        return "Lets user speak again";
    }
}
