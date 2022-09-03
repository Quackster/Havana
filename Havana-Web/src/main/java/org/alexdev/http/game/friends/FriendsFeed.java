package org.alexdev.http.game.friends;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;
import org.alexdev.havana.game.messenger.MessengerUser;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.dao.mysql.AlertsDao;

import java.util.stream.Collectors;

public class FriendsFeed {
    public static void createFriendsOnline(WebConnection webConnection, Template template) {
        if (!webConnection.session().getBoolean("authenticated")) {
            return;
        }

        PlayerDetails playerDetails = (PlayerDetails) template.get("playerDetails");

        if (playerDetails == null) {
            return;
        }

        var friends = AlertsDao.getOnlineFriends(playerDetails.getId());
        var requests = AlertsDao.countRequests(playerDetails.getId());

        template.set("feedFriendsOnline", friends.values().stream().filter(MessengerUser::isOnline).collect(Collectors.toList()));
        template.set("feedFriendRequests", requests);

        webConnection.session().delete("friendsOnlineRequest");
    }
}
