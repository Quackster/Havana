package org.alexdev.havana.game.commands.registered.groups;

import org.alexdev.havana.dao.mysql.BanDao;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.ban.BanType;
import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.server.netty.NettyPlayerNetwork;
import org.apache.commons.validator.routines.InetAddressValidator;

public class UnbanCommand extends Command {
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

        Unban(player, args[0]);
    }

    private void Unban(Player player, String name) {
        PlayerDetails playerDetails = PlayerManager.getInstance().getPlayerData(name);

        if (playerDetails == null) {
            player.send(new ALERT("Could not find user: " + name));
            return;
        }

        if (playerDetails.getMachineId() != null && playerDetails.getMachineId().length() > 0) {
            BanDao.removeBan(BanType.MACHINE_ID, playerDetails.getMachineId());
        }

        String ip = null;
        Player targetUser = PlayerManager.getInstance().getPlayerByName(name);

        if (targetUser != null) {
            ip = NettyPlayerNetwork.getIpAddress(targetUser.getNetwork().getChannel());
        } else {
            ip = PlayerDao.getLatestIp(playerDetails.getId());
        }

        InetAddressValidator validator = InetAddressValidator.getInstance();

        // Validate an IPv4 address
        if (ip != null && !validator.isValid(ip)) {
            ip = null;
        }

        if (ip != null && ip.length() > 0) {
            BanDao.removeBan(BanType.IP_ADDRESS, ip);
        }

        BanDao.removeBan(BanType.USER_ID, String.valueOf(playerDetails.getId()));

        if (targetUser != null) {
            targetUser.getNetwork().getChannel().close();
        }

        player.send(new ALERT("Player " + playerDetails.getName() + " is successfully unbanned."));

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
        return "Removes a ban for the given user";
    }
}
