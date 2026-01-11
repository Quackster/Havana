package org.alexdev.http.controller.habblet;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.wordfilter.WordfilterManager;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.util.HtmlUtil;
import org.alexdev.http.util.RconUtil;
import org.alexdev.http.util.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class UpdateMottoController {

    @PostMapping("/myhabbo/updatemotto")
    @ResponseBody
    public String updateMotto(
            @RequestParam(value = "motto", defaultValue = "") String mottoInput,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);
        PlayerDetails playerDetails = PlayerDao.getDetails(userId);

        Long lastUpdateDate = (Long) session.getAttribute("lastMottoUpdate");
        if (lastUpdateDate != null) {
            if (DateUtil.getCurrentTimeSeconds() < lastUpdateDate) {
                return "<script>" +
                        "document.getElementById(\"habbo-plate\").innerHTML = \"<img src='" + GameConfiguration.getInstance().getString("site.path") + "/web-gallery/images/sticker_croco.gif' style='margin-top: 57px'>\";" +
                        "</script>" + HtmlUtil.escape(playerDetails.getMotto());
            }
        }

        session.setAttribute("lastMottoUpdate", DateUtil.getCurrentTimeSeconds());

        String responseMotto = "";
        String motto = WordfilterManager.filterSentence(HtmlUtil.removeHtmlTags(mottoInput));

        if (motto.length() > 100) {
            motto = motto.substring(0, 100);
        }

        if (motto.replace(" ", "").isEmpty()) {
            responseMotto = "Click to enter your motto/ status";
            motto = "";
        } else if (motto.toLowerCase().equals("crikey")) {
            responseMotto = "<script>" +
                    "document.getElementById(\"habbo-plate\").innerHTML = \"<img src='" + GameConfiguration.getInstance().getString("site.path") + "/web-gallery/images/sticker_croco.gif' style='margin-top: 57px'>\";" +
                    "</script>";
        }

        if (!playerDetails.getMotto().equals(motto)) {
            PlayerDao.saveMotto(userId, motto);

            if (playerDetails.isOnline()) {
                final int finalUserId = userId;
                RconUtil.sendCommand(RconHeader.REFRESH_LOOKS, new HashMap<>() {{
                    put("userId", finalUserId);
                }});
            }
        }

        return responseMotto + HtmlUtil.escape(motto);
    }
}
