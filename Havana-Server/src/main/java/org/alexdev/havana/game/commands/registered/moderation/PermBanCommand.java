package org.alexdev.havana.game.commands.registered.moderation;

import org.alexdev.havana.dao.mysql.BanDao;
import org.alexdev.havana.game.ban.BanManager;
import org.alexdev.havana.game.ban.BanType;
import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.StringUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PermBanCommand extends Command {
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

        PlayerDetails playerDetails = PlayerManager.getInstance().getPlayerData(args[0]);

        if (playerDetails == null) {
            player.send(new ALERT("Could not find user: " + args[0]));
            return;
        }

        if (playerDetails.isBanned() != null) {
            player.send(new ALERT("User is already banned!"));
            return;
        }

        Map<BanType, String> criteria = new HashMap<>();
        long in20Years = DateUtil.getCurrentTimeSeconds() + (TimeUnit.DAYS.toSeconds(365) * 20);
        var reason = StringUtil.filterInput(Arrays.asList(args).stream().skip(2).collect(Collectors.joining(" ")), true);

        if (playerDetails.getMachineId() != null && playerDetails.getMachineId().length() > 0) {
            BanDao.addBan(BanType.MACHINE_ID, playerDetails.getMachineId(), in20Years, reason, playerDetails.getId());
            criteria.put(BanType.MACHINE_ID, playerDetails.getMachineId());
        }

        Player targetUser = PlayerManager.getInstance().getPlayerByName(args[0]);

        /*String ip = null;

        if (targetUser != null) {
            ip = NettyPlayerNetwork.getIpAddress(targetUser.getNetwork().getChannel());
        } else {
            ip = PlayerDao.getLatestIp(playerDetails.getId());
        }

        InetAddressValidator validator = InetAddressValidator.getInstance();

        // Validate an IPv4 address
        if (!validator.isValid(ip)) {
            ip = null;
        }

        if (ip != null && ip.length() > 0) {
            BanDao.addBan(BanType.IP_ADDRESS, ip, in20Years, "Banned for breaking the Habbo Way");
            criteria.put(BanType.IP_ADDRESS, ip);
        }*/

        BanDao.addBan(BanType.USER_ID, String.valueOf(playerDetails.getId()), in20Years, reason, playerDetails.getId());
        criteria.put(BanType.USER_ID, String.valueOf(playerDetails.getId()));

        if (targetUser != null) {
            targetUser.getNetwork().getChannel().close();
        }

        player.send(new ALERT("Player " + playerDetails.getName() + " is successfully banned."));
        BanManager.getInstance().disconnectBanAccounts(criteria);

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
    public String getDescription() {
        return "Permanently bans a given user";
    }
}
