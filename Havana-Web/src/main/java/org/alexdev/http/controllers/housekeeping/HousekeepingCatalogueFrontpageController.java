package org.alexdev.http.controllers.housekeeping;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.Routes;
import org.alexdev.http.dao.NewsDao;
import org.alexdev.http.game.housekeeping.HousekeepingManager;
import org.alexdev.http.util.RconUtil;
import org.alexdev.http.util.SessionUtil;

import java.util.HashMap;
import java.util.List;

public class HousekeepingCatalogueFrontpageController {
    public static void edit(WebConnection client) {
        if (!client.session().getBoolean(SessionUtil.LOGGED_IN_HOUSKEEPING)) {
            client.redirect("/" + Routes.HOUSEKEEPING_PATH);
            return;
        }

        PlayerDetails session = PlayerDao.getDetails(client.session().getInt("user.id"));

        if (!HousekeepingManager.getInstance().hasPermission(session.getRank(), "catalogue/edit_frontpage")) {
            client.redirect("/" + Routes.HOUSEKEEPING_PATH);
            return;
        }

        if (client.post().getValues().size() > 0) {
            if (client.post().getString("header").isBlank()) {
                client.session().set("alertColour", "danger");
                client.session().set("alertMessage", "Header cannot be blank");
            } else if (client.post().getString("subtext").isBlank()) {
                client.session().set("alertColour", "danger");
                client.session().set("alertMessage", "The subtext cannot be blank");
            } else {
                client.session().set("alertColour", "success");
                client.session().set("alertMessage", "The frontpage has been successfully saved");
            }

            GameConfiguration.getInstance().updateSetting("catalogue.frontpage.input.1",  client.post().getString("image"));
            GameConfiguration.getInstance().updateSetting("catalogue.frontpage.input.2",  client.post().getString("header"));
            GameConfiguration.getInstance().updateSetting("catalogue.frontpage.input.3",  client.post().getString("subtext"));
            GameConfiguration.getInstance().updateSetting("catalogue.frontpage.input.4",  client.post().getString("link"));

            RconUtil.sendCommand(RconHeader.REFRESH_CATALOGUE_FRONTPAGE, new HashMap<>());
        }

        List<String> images = NewsDao.getTopStoryImages();

        Template tpl = client.template("housekeeping/catalogue_frontpage");
        tpl.set("housekeepingManager", HousekeepingManager.getInstance());
        tpl.set("pageName", "Edit Catalogue Frontpage");
        tpl.set("images", images);
        tpl.set("frontpageText1", GameConfiguration.getInstance().getString("catalogue.frontpage.input.1"));
        tpl.set("frontpageText2", GameConfiguration.getInstance().getString("catalogue.frontpage.input.2"));
        tpl.set("frontpageText3", GameConfiguration.getInstance().getString("catalogue.frontpage.input.3"));
        tpl.set("frontpageText4", GameConfiguration.getInstance().getString("catalogue.frontpage.input.4"));
        tpl.render();

        // Delete alert after it's been rendered
        client.session().delete("alertMessage");
    }
}
