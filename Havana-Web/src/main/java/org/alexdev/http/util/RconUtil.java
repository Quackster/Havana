package org.alexdev.http.util;

import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.http.HavanaWeb;
import org.alexdev.http.util.rcon.RconTask;

import java.util.Map;

public class RconUtil {
    public static void sendCommand(RconHeader header, Map<String, Object> parameters) {
        RconTask rconTask = new RconTask(header, parameters);
        HavanaWeb.getExecutor().execute(rconTask);
    }
}
