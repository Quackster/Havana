package org.alexdev.http.controller.api;

import com.google.gson.GsonBuilder;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.util.config.GameConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class TicketController {

    @GetMapping("/ticket")
    public ResponseEntity<String> getTicket(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password) {

        if (username == null || password == null) {
            return ResponseEntity.ok("");
        }

        PlayerDetails details = new PlayerDetails();
        boolean hasError;

        if (username.isBlank() || password.isBlank()) {
            hasError = true;
        } else {
            hasError = !PlayerDao.login(details, username, password);
        }

        if (hasError) {
            return ResponseEntity.ok("");
        }

        String ssoTicket = details.getSsoTicket();

        // Update sso ticket
        if (GameConfiguration.getInstance().getBoolean("reset.sso.after.login") || ssoTicket == null || ssoTicket.isBlank()) {
            ssoTicket = UUID.randomUUID().toString();
            PlayerDao.setTicket(details.getId(), ssoTicket);
        }

        JsonDetails jsonDetails = new JsonDetails();
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
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(gson.toJson(jsonDetails));
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password) {

        if (username == null || password == null) {
            return ResponseEntity.ok("ERROR No username or password supplied");
        }

        PlayerDetails details = new PlayerDetails();
        boolean hasError;

        if (username.isBlank() || password.isBlank()) {
            hasError = true;
        } else {
            hasError = !PlayerDao.login(details, username, password);
        }

        if (hasError) {
            return ResponseEntity.ok("ERROR Login invalid");
        }

        String ssoTicket = details.getSsoTicket();

        // Update sso ticket
        if (GameConfiguration.getInstance().getBoolean("reset.sso.after.login") || ssoTicket == null || ssoTicket.isBlank()) {
            ssoTicket = UUID.randomUUID().toString();
            PlayerDao.setTicket(details.getId(), ssoTicket);
        }

        return ResponseEntity.ok(ssoTicket);
    }

    private static class JsonDetails {
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
