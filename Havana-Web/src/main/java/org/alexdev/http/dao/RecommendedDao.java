package org.alexdev.http.dao;

import org.alexdev.havana.dao.Storage;
import org.alexdev.havana.dao.mysql.GroupDao;
import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.groups.Group;
import org.alexdev.havana.game.room.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RecommendedDao {
    public static List<Group> getRecommendedGroups(boolean staffPick) {
        List<Group> groupList = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT groups_details.* FROM groups_details WHERE EXISTS(SELECT 1 FROM cms_recommended WHERE cms_recommended.recommended_id = groups_details.id AND cms_recommended.type = 'GROUP' AND cms_recommended.is_staff_pick = ?)", sqlConnection);
            preparedStatement.setInt(1, staffPick ? 1 : 0);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                groupList.add(GroupDao.fill(resultSet));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
            Storage.closeSilently(resultSet);
        }

        return groupList;
    }

    public static List<Room> getRecommendedRooms(boolean staffPick) {
        List<Room> roomList = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT rooms.*, users.username FROM rooms LEFT JOIN users ON rooms.owner_id = users.id WHERE EXISTS(SELECT 1 FROM cms_recommended WHERE cms_recommended.recommended_id = rooms.id AND cms_recommended.type = 'ROOM' AND cms_recommended.is_staff_pick = ?)", sqlConnection);
            preparedStatement.setInt(1, staffPick ? 1 : 0);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Room room = new Room();
                RoomDao.fill(room.getData(), resultSet);
                roomList.add(room);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
            Storage.closeSilently(resultSet);
        }

        return roomList;
    }

    public static void setRecommended(int recommendedId, String type, boolean staffPick, boolean enabled) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("DELETE FROM cms_recommended WHERE recommended_id = ? AND type = ? AND is_staff_pick = ?", sqlConnection);
            preparedStatement.setInt(1, recommendedId);
            preparedStatement.setString(2, type);
            preparedStatement.setInt(3, staffPick ? 1 : 0);
            preparedStatement.execute();
            Storage.closeSilently(preparedStatement);

            if (enabled) {
                preparedStatement = Storage.getStorage().prepare("INSERT INTO cms_recommended (recommended_id, type, is_staff_pick) VALUES (?, ?, ?)", sqlConnection);
                preparedStatement.setInt(1, recommendedId);
                preparedStatement.setString(2, type);
                preparedStatement.setInt(3, staffPick ? 1 : 0);
                preparedStatement.execute();
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }
}
