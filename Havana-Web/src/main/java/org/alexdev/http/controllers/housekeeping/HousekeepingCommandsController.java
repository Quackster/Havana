package org.alexdev.http.controllers.housekeeping;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.moderation.actions.ModeratorBanUserAction;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.http.util.RconUtil;
import org.alexdev.http.util.SessionUtil;

import java.util.HashMap;

public class HousekeepingCommandsController {

    /**
     * Handle the /housekeeping URI request
     *
     * @param client the connection
     */
    public static void ban(WebConnection client) {
        // If they are logged in, send them to the /me page
        if (!client.session().getBoolean(SessionUtil.LOGGED_IN_HOUSKEEPING)) {
            client.send("");
        }

        var playerDetails = PlayerDao.getDetails(client.get().getString("username"));

        if (playerDetails != null) {
            RconUtil.sendCommand(RconHeader.DISCONNECT_USER, new HashMap<>() {{
                put("userId", playerDetails.getId());
            }});

            int banningId = client.session().getInt("user.id");
            var banningPlayerDetails = PlayerDao.getDetails(banningId);

            //ModerationDao.addLog(ModerationActionType.ALERT_USER, player.getDetails().getId(), playerDetails.getId(), "Banned for breaking the HabboWay", "");
            client.send(ModeratorBanUserAction.ban(banningPlayerDetails, "Banned for breaking the HabboWay", "", playerDetails.getName(), 999999999, true, true));
            return;
        }

        client.send("User doesn't exist");
    }
}