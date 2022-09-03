package org.alexdev.http.controllers.habblet;

import org.alexdev.duckhttpd.server.connection.WebConnection;

public class NavigationComponent {
    public static void navigation(WebConnection webConnection) {
        webConnection.send("");
    }
}
