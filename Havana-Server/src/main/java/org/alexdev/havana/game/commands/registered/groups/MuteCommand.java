package org.alexdev.havana.game.commands.registered.groups;

import org.alexdev.havana.dao.mysql.PlayerStatisticsDao;
import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.commands.CommandManager;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.util.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

public class MuteCommand extends Command {
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
        int minutes = StringUtils.isNumeric(args[1]) ? Integer.parseInt(args[1]) : 0;
        int seconds = 0;

        if (minutes > 0) {
            seconds = (int) TimeUnit.MINUTES.toSeconds(minutes);
        }

        PlayerDetails playerDetails = PlayerManager.getInstance().getPlayerData(name);

        if (playerDetails == null) {
            player.send(new ALERT("Could not find user: " + name));
            return;
        }

        if (CommandManager.getInstance().hasPermission(playerDetails, "mute")) {
            player.send(new ALERT("Cannot mute a user who has permission to mute"));
            return;
        }


        long expiration = DateUtil.getCurrentTimeSeconds() + seconds;
        PlayerStatisticsDao.updateStatistic(playerDetails.getId(), PlayerStatistic.MUTE_EXPIRES_AT, expiration);

        var online = PlayerManager.getInstance().getPlayerById(playerDetails.getId());

        if (online != null) {
            online.getStatisticManager().setLongValue(PlayerStatistic.MUTE_EXPIRES_AT, expiration);

        }

        player.send(new ALERT("Player (" + playerDetails.getName() + ") mute expiration date set to: " + DateUtil.getDate(expiration, DateUtil.LONG_DATE)));
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
        arguments.add("minutes");
    }

    @Override
    public String getDescription() {
        return "Remove user ability to speak";
    }
}
