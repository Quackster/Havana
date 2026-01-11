package org.alexdev.http.controller.housekeeping;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.moderation.actions.ModeratorBanUserAction;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.http.Routes;
import org.alexdev.http.util.RconUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping("/" + Routes.HOUSEKEEPING_PATH)
public class HousekeepingCommandsController {

    @GetMapping("/commands/ban")
    @ResponseBody
    public String ban(
            @RequestParam(value = "username", defaultValue = "") String username,
            HttpSession session) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "";
        }

        var playerDetails = PlayerDao.getDetails(username);

        if (playerDetails != null) {
            RconUtil.sendCommand(RconHeader.DISCONNECT_USER, new HashMap<>() {{
                put("userId", playerDetails.getId());
            }});

            PlayerDetails banningPlayerDetails = HousekeepingUtil.getPlayerDetails(session);

            return ModeratorBanUserAction.ban(banningPlayerDetails, "Banned for breaking the HabboWay", "", playerDetails.getName(), 999999999, true, true);
        }

        return "User doesn't exist";
    }
}
