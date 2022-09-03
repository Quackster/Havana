package org.alexdev.http.controllers.habblet;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.dao.mysql.PlayerStatisticsDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;

public class RoomSelectionController {
    public static void confirm(WebConnection webConnection) {
        var template = webConnection.template("habblet/roomselectionConfirm");
        template.render();
    }

    public static void create(WebConnection webConnection) {
        if (!webConnection.session().getBoolean("authenticated") || !webConnection.post().contains("roomType")) {
            webConnection.send("");
            return;
        }

        PlayerDetails playerDetails = PlayerDao.getDetails(webConnection.session().getInt("user.id"));

        if (!playerDetails.canSelectRoom()) {
            webConnection.send("");
            return;
        }

        int roomType = Integer.parseInt(webConnection.post().getString("roomType"));

        if (roomType < 0 || roomType > 5) {
            webConnection.send("");
            return;
        }
        
        webConnection.send("");
    }

    public static void hide(WebConnection webConnection) {
        if (!webConnection.session().getBoolean("authenticated")) {
            webConnection.send("");
            return;
        }

        PlayerDetails playerDetails = PlayerDao.getDetails(webConnection.session().getInt("user.id"));

        if (!playerDetails.canSelectRoom()) {
            webConnection.send("");
            return;
        }

        playerDetails.setSelectedRoomId(-1);

        PlayerDao.saveSelectedRoom(playerDetails.getId(), -1);
        PlayerStatisticsDao.updateStatistic(playerDetails.getId(), PlayerStatistic.NEWBIE_ROOM_LAYOUT, -1);

        webConnection.send("");
    }
}
