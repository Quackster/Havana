package org.alexdev.http.controllers.habblet;

import io.netty.handler.codec.http.FullHttpResponse;
import org.alexdev.duckhttpd.response.ResponseBuilder;
import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.wordfilter.WordfilterManager;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.util.HtmlUtil;
import org.alexdev.http.util.RconUtil;

import java.util.HashMap;

public class UpdateMottoController {
    public static void updatemotto(WebConnection webConnection) {
        int userId = webConnection.session().getInt("user.id");

        if (userId < 1) {
            webConnection.send("");
            return;
        }

        PlayerDetails playerDetails = PlayerDao.getDetails(webConnection.session().getInt("user.id"));

        if (webConnection.session().contains("lastMottoUpdate")) {
            long lastUpdateDate = webConnection.session().getLongOrElse("lastMottoUpdate",  DateUtil.getCurrentTimeSeconds());

            if (DateUtil.getCurrentTimeSeconds() < lastUpdateDate) {
                FullHttpResponse httpResponse = ResponseBuilder.create("<script>" +
                            "document.getElementById(\"habbo-plate\").innerHTML = \"<img src='" + GameConfiguration.getInstance().getString("site.path") + "/web-gallery/images/sticker_croco.gif' style='margin-top: 57px'>\";" +
                            "</script>" + HtmlUtil.escape(playerDetails.getMotto()));
                webConnection.send(httpResponse);
                return;
            }
        }

        webConnection.session().set("lastMottoUpdate", String.valueOf(DateUtil.getCurrentTimeSeconds()));

        String responseMotto = "";
        String motto = WordfilterManager.filterSentence(HtmlUtil.removeHtmlTags(webConnection.post().getString("motto")));

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
           /* else {
                // Check previous motto and send back figure
                if (playerDetails.getMotto().toLowerCase().equals("crikey")) {
                    responseMotto = "<script>" +
                            "document.getElementById(\"habbo-plate\").innerHTML = \"<img alt='{{ playerDetails.username }}' src='https://www.habbo.com/habbo-imaging/avatarimage?figure=" + playerDetails.getFigure() + "&size=b&direction=3&head_direction=3&crr=0&gesture=sml&frame=1' width='64' height='110' />\";" +
                            "</script>" + motto;
                }
            }*/
        } else {

        }

        if (!playerDetails.getMotto().equals(motto)) {
            PlayerDao.saveMotto(userId, motto);

            if (playerDetails.isOnline()) {
                RconUtil.sendCommand(RconHeader.REFRESH_LOOKS, new HashMap<>() {{
                    put("userId", userId);
                }});
            }
        }

        FullHttpResponse httpResponse = ResponseBuilder.create(responseMotto + HtmlUtil.escape(motto));
        webConnection.send(httpResponse);
    }
}
