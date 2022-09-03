package org.alexdev.havana.game.moderation.actions;

import org.alexdev.havana.dao.mysql.BanDao;
import org.alexdev.havana.game.ban.BanManager;
import org.alexdev.havana.game.ban.BanType;
import org.alexdev.havana.game.commands.CommandManager;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.moderation.ModerationAction;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.util.MalformedPacketException;
import org.alexdev.havana.util.DateUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ModeratorBanUserAction implements ModerationAction {
    @Override
    public void performAction(Player player, Room room, String alertMessage, String notes, NettyRequest reader) throws MalformedPacketException {
        if (!player.hasFuse(Fuseright.BAN)) {
            return;
        }

        String name = reader.readString();
        int banHours = reader.readInt();
        boolean banMachineId = reader.readBoolean();
        boolean banIp = reader.readBoolean();

        var response = ban(player.getDetails(), alertMessage, notes, name, TimeUnit.HOURS.toSeconds(banHours), banMachineId, banIp);
        player.send(new ALERT(response));
    }

    public static String ban(PlayerDetails banningPlayerDetails, String alertMessage, String notes, String name, long banSeconds, boolean banMachineId, boolean banIp) {
        Map<BanType, String> criteria = new HashMap<>();
        PlayerDetails playerDetails = PlayerManager.getInstance().getPlayerData(name);

        if (playerDetails == null) {
            return "Could not find user: " + name;
        }

        if (playerDetails.getId() == banningPlayerDetails.getId()) {
            return "Cannot ban yourself";
        }

        if (playerDetails.isBanned() != null) {
            return "User is already banned!";
        }

        if (CommandManager.getInstance().hasPermission(playerDetails, "ban"))
            return "Cannot ban a user who has permission to ban";

        long banTime = DateUtil.getCurrentTimeSeconds() + banSeconds;

        BanDao.addBan(BanType.USER_ID, String.valueOf(playerDetails.getId()), banTime, alertMessage, banningPlayerDetails.getId());
        criteria.put(BanType.USER_ID, String.valueOf(playerDetails.getId()));

        if (banMachineId && playerDetails.getMachineId() != null) {
            BanDao.addBan(BanType.MACHINE_ID, playerDetails.getMachineId(), banTime, alertMessage, banningPlayerDetails.getId());
            criteria.put(BanType.MACHINE_ID, playerDetails.getMachineId());
        }

        /*if (banIp) {
            var latestIp = PlayerDao.getLatestIp(playerDetails.getId());
            InetAddressValidator validator = InetAddressValidator.getInstance();

            // Validate an IPv4 address
            if (validator.isValidInet4Address(latestIp)) {
                BanDao.addBan(BanType.IP_ADDRESS, latestIp, banTime, alertMessage);
                criteria.put(BanType.IP_ADDRESS, latestIp);
            }
        }*/

        Player target = PlayerManager.getInstance().getPlayerById(playerDetails.getId());

        if (target != null) {
            target.getNetwork().disconnect();
        }

        BanManager.getInstance().disconnectBanAccounts(criteria);
        return "The user " + playerDetails.getName() + " has been banned.";
    }
}
