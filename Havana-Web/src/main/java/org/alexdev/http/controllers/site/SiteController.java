package org.alexdev.http.controllers.site;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.http.util.XSSUtil;

public class SiteController {
    public static void pixels(WebConnection webConnection) {
        XSSUtil.clear(webConnection);

        if (!webConnection.session().contains("authenticated")) {
            return;
        }

        var template = webConnection.template("pixels");
        webConnection.session().set("page", "credits");
        template.render();
    }

}
