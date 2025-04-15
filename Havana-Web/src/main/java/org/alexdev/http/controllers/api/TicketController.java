package org.alexdev.http.controllers.api;

import com.google.gson.GsonBuilder;
import org.alexdev.duckhttpd.response.ResponseBuilder;
import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.util.config.GameConfiguration;

import java.util.UUID;

public class TicketController {
    public static void get(WebConnection webConnection) {
        String ssoTicket = "";

        if (webConnection.get().contains("username") && webConnection.get().contains("password")) {
            var username = webConnection.get().getString("username");
            var password = webConnection.get().getString("password");

            PlayerDetails details = new PlayerDetails();
            boolean hasError;

            if (username.isBlank() || password.isBlank()) {
                hasError = true;
            } else {
                hasError = !PlayerDao.login(details, username, password);
            }

            if (!hasError) {
                ssoTicket = details.getSsoTicket();

                // Update sso ticket
                if (GameConfiguration.getInstance().getBoolean("reset.sso.after.login") || ssoTicket == null || ssoTicket.isBlank()) {
                    ssoTicket = UUID.randomUUID().toString();
                    PlayerDao.setTicket(details.getId(), ssoTicket);
                }

                var jsonDetails = new JsonDetails();

                jsonDetails.host = GameConfiguration.getInstance().getString("loader.game.ip");
                jsonDetails.site = GameConfiguration.getInstance().getString("site.path");
                jsonDetails.shockwavePort = GameConfiguration.getInstance().getString("loader.game.port");
                jsonDetails.musPort = GameConfiguration.getInstance().getString("loader.mus.port");
                jsonDetails.flashPort = GameConfiguration.getInstance().getString("loader.flash.port");

                jsonDetails.shockwaveDcr = GameConfiguration.getInstance().getString("loader.dcr.http");
                jsonDetails.shockwaveVariables = GameConfiguration.getInstance().getString("loader.external.variables").replace("https://", "http://");
                jsonDetails.shockwaveTexts = GameConfiguration.getInstance().getString("loader.external.texts").replace("https://", "http://");

                jsonDetails.flashBase = GameConfiguration.getInstance().getString("loader.flash.base");
                jsonDetails.flashSwf = GameConfiguration.getInstance().getString("loader.flash.swf");
                jsonDetails.flashTexts = GameConfiguration.getInstance().getString("loader.flash.external.texts");
                jsonDetails.flashVariables = GameConfiguration.getInstance().getString("loader.flash.external.variables");

                jsonDetails.ssoTicket = ssoTicket;

                var gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
                var response = ResponseBuilder.create("text/json", gson.toJson(jsonDetails));
                webConnection.send(response);
            }
        }
    }
    public static void login(WebConnection webConnection) {
        String ssoTicket = "";

        if (webConnection.get().contains("username") && webConnection.get().contains("password")) {
            var username = webConnection.get().getString("username");
            var password = webConnection.get().getString("password");

            PlayerDetails details = new PlayerDetails();
            boolean hasError;

            if (username.isBlank() || password.isBlank()) {
                hasError = true;
            } else {
                hasError = !PlayerDao.login(details, username, password);
            }

            if (!hasError) {
                ssoTicket = details.getSsoTicket();

                // Update sso ticket
                if (GameConfiguration.getInstance().getBoolean("reset.sso.after.login") || ssoTicket == null || ssoTicket.isBlank()) {
                    ssoTicket = UUID.randomUUID().toString();
                    PlayerDao.setTicket(details.getId(), ssoTicket);
                }

                var response = ResponseBuilder.create(ssoTicket);
                webConnection.send(response);
            }
            else {
                var response = ResponseBuilder.create("ERROR Login invalid");
                webConnection.send(response);
            }
        } else {
            var response = ResponseBuilder.create("ERROR No username or password supplied");
            webConnection.send(response);
        }
    }

    private static class JsonDetails
    {
        public String host;
        public String site;
        public String musPort;
        public String shockwavePort;
        public String shockwaveDcr;
        public String shockwaveVariables;
        public String shockwaveTexts;
        public String flashPort;
        public String flashBase;
        public String flashSwf;
        public String flashVariables;
        public String flashTexts;
        public String ssoTicket;
    }
}
