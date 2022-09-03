package org.alexdev.http.controllers.habblet;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.havana.dao.mysql.EventsDao;
import org.alexdev.http.server.Watchdog;

import java.util.stream.Collectors;

public class EventController {
    public static void loadEvents(WebConnection webConnection) {
        if (!webConnection.post().contains("eventTypeId")) {
            webConnection.send("");
            return;
        }

        int filterId = webConnection.post().getInt("eventTypeId");

        var tpl = webConnection.template("habblet/load_events");
        tpl.set("events", Watchdog.EVENTS.stream().filter(event -> event.getCategoryId() == filterId).collect(Collectors.toList()));
        tpl.render();
    }
}
