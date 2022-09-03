package org.alexdev.http.controllers.site;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.http.util.XSSUtil;

public class FaqController {
    public static void faq(WebConnection webConnection) {
        XSSUtil.clear(webConnection);

        var template = webConnection.template("faq");
        template.render();
    }
}
