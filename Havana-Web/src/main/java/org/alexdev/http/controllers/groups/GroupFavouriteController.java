package org.alexdev.http.controllers.groups;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.havana.dao.mysql.GroupDao;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.groups.Group;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.http.util.RconUtil;

import java.util.HashMap;

public class GroupFavouriteController {
    public static void confirmselectfavourite(WebConnection webConnection) {
        if (!webConnection.session().getBoolean("authenticated")) {
            webConnection.redirect("/");
            return;
        }

        int groupId = webConnection.post().getInt("groupId");
        String groupName = GroupDao.getGroupName(groupId);

        if (groupName == null) {
            webConnection.send("");
            return;
        }

        var template = webConnection.template("groups/favourite/confirm_select_favourite");
        template.set("groupName", groupName);
        template.render();
    }

    public static void selectfavourite(WebConnection webConnection) {
        if (!webConnection.session().getBoolean("authenticated")) {
            webConnection.redirect("/");
            return;
        }

        int groupId = webConnection.post().getInt("groupId");
        int userId = webConnection.session().getInt("user.id");

        Group group = GroupDao.getGroup(groupId);

        if (group == null || !group.isMember(userId)) {
            webConnection.send("");
            return;
        }

        PlayerDao.saveFavouriteGroup(userId, groupId);
        RconUtil.sendCommand(RconHeader.REFRESH_GROUP_PERMS, new HashMap<>() {{
            put("userId", String.valueOf(userId));
        }});

        webConnection.send("OK");
    }

    public static void confirmdeselectfavourite(WebConnection webConnection) {
        if (!webConnection.session().getBoolean("authenticated")) {
            webConnection.redirect("/");
            return;
        }

        var template = webConnection.template("groups/favourite/confirm_deselect_favourite");
        template.render();
    }

    public static void deselectfavourite(WebConnection webConnection) {
        if (!webConnection.session().getBoolean("authenticated")) {
            webConnection.redirect("/");
            return;
        }

        int groupId = webConnection.post().getInt("groupId");
        int userId = webConnection.session().getInt("user.id");

        Group group = GroupDao.getGroup(groupId);

        if (group == null || !group.isMember(userId)) {
            webConnection.send("");
            return;
        }

        PlayerDao.saveFavouriteGroup(userId, 0);

        RconUtil.sendCommand(RconHeader.REFRESH_GROUP_PERMS, new HashMap<>() {{
            put("userId", String.valueOf(userId));
        }});

        webConnection.send("OK");
    }
}
