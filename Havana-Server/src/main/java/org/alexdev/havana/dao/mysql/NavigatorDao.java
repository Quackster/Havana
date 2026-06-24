package org.alexdev.havana.dao.mysql;

import org.alexdev.havana.dao.Storage;
import org.alexdev.havana.game.navigator.NavigatorCategory;
import org.alexdev.havana.game.navigator.NavigatorStyle;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.game.room.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class NavigatorDao {
    public record RoomCategoryAdmin(
            int id,
            int orderId,
            int parentId,
            boolean node,
            String name,
            boolean publicSpaces,
            boolean allowTrading,
            int minRoleAccess,
            int minRoleSetFlatCat,
            boolean clubOnly,
            boolean topPriority
    ) {}


    /**
     * Get all categories from the database.
     *
     * @return map of categories
     */
    public static HashMap<Integer, NavigatorCategory> getCategories() {
        HashMap<Integer, NavigatorCategory> categories = new HashMap<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet row = null;

        try {
            conn = Storage.getStorage().getConnection();
            stmt = Storage.getStorage().prepare("SELECT * FROM rooms_categories ORDER BY order_id ASC ", conn);
            row = stmt.executeQuery();

            while (row.next()) {
                NavigatorCategory category = new NavigatorCategory(
                        row.getInt("id"), row.getInt("parent_id"), row.getInt("order_id"),
                        row.getString("name"),
                        row.getBoolean("public_spaces"), row.getBoolean("allow_trading"),
                        PlayerRank.getRankForId(row.getInt("minrole_access")),
                        PlayerRank.getRankForId(row.getInt("minrole_setflatcat")),
                        row.getBoolean("isnode"), row.getBoolean("club_only"));

                categories.put(category.getId(), category);
            }
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(row);
            Storage.closeSilently(stmt);
            Storage.closeSilently(conn);
        }

        return categories;
    }

    public static List<RoomCategoryAdmin> getAdminCategories() {
        List<RoomCategoryAdmin> categories = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM rooms_categories ORDER BY parent_id ASC, order_id ASC, id ASC", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                categories.add(readAdminCategory(resultSet));
            }
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return categories;
    }

    public static RoomCategoryAdmin getAdminCategory(int id) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM rooms_categories WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return readAdminCategory(resultSet);
            }
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return null;
    }

    private static RoomCategoryAdmin readAdminCategory(ResultSet resultSet) throws SQLException {
        return new RoomCategoryAdmin(
                resultSet.getInt("id"),
                resultSet.getInt("order_id"),
                resultSet.getInt("parent_id"),
                resultSet.getBoolean("isnode"),
                resultSet.getString("name"),
                resultSet.getBoolean("public_spaces"),
                resultSet.getBoolean("allow_trading"),
                resultSet.getInt("minrole_access"),
                resultSet.getInt("minrole_setflatcat"),
                resultSet.getBoolean("club_only"),
                resultSet.getBoolean("is_top_priority")
        );
    }

    public static int saveAdminCategory(RoomCategoryAdmin category) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();

            if (category.id() > 0) {
                preparedStatement = Storage.getStorage().prepare("UPDATE rooms_categories SET order_id = ?, parent_id = ?, isnode = ?, name = ?, public_spaces = ?, allow_trading = ?, minrole_access = ?, minrole_setflatcat = ?, club_only = ?, is_top_priority = ? WHERE id = ?", sqlConnection);
            } else {
                preparedStatement = Storage.getStorage().prepare("INSERT INTO rooms_categories (order_id, parent_id, isnode, name, public_spaces, allow_trading, minrole_access, minrole_setflatcat, club_only, is_top_priority) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", sqlConnection);
            }

            preparedStatement.setInt(1, category.orderId());
            preparedStatement.setInt(2, category.parentId());
            preparedStatement.setBoolean(3, category.node());
            preparedStatement.setString(4, category.name());
            preparedStatement.setBoolean(5, category.publicSpaces());
            preparedStatement.setBoolean(6, category.allowTrading());
            preparedStatement.setInt(7, category.minRoleAccess());
            preparedStatement.setInt(8, category.minRoleSetFlatCat());
            preparedStatement.setBoolean(9, category.clubOnly());
            preparedStatement.setBoolean(10, category.topPriority());

            if (category.id() > 0) {
                preparedStatement.setInt(11, category.id());
            }

            preparedStatement.executeUpdate();

            if (category.id() > 0) {
                return category.id();
            }

            generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(generatedKeys);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return category.id();
    }

    public static void deleteAdminCategory(int id) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("DELETE FROM rooms_categories WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    /**
     * Get the styles for the public rooms
     *
     * @return the list of recent rooms
     */
    public static Map<Integer, NavigatorStyle> getNavigatorStyles() {
        Map<Integer, NavigatorStyle> style = new HashMap<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM navigator_styles", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                style.put(resultSet.getInt("room_id"), new NavigatorStyle(resultSet.getInt("room_id"), resultSet.getString("description"), resultSet.getString("thumbnail_url"), resultSet.getInt("thumbnail_layout")));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return style;
    }

    /**
     * Get the list of recent rooms from database set by limit and category id.
     *
     * @param limit      the maximum amount of usrs
     * @param categoryId the rooms to find under this category id
     * @return the list of recent rooms
     */
    public static List<Room> getRecentRooms(int limit, int categoryId) {
        List<Room> rooms = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM rooms LEFT JOIN users ON rooms.owner_id = users.id WHERE category = ? AND owner_id > 0 ORDER BY visitors_now DESC, rooms.rating DESC LIMIT ? ", sqlConnection);
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, limit);
            resultSet = preparedStatement.executeQuery();

            //public NavigatorCategory(int id, String name, boolean publicSpaces, boolean allowTrading, int minimumRoleAccess, int minimumRoleSetFlat) {
            while (resultSet.next()) {
                Room room = new Room();
                RoomDao.fill(room.getData(), resultSet);
                rooms.add(room);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return rooms;
    }

    /**
     * Count recent rooms by category id.
     *
     * @param limit the limit to count
     * @return the list of recent rooms
     */
    public static Map<Integer, Integer> getPopularCategories(int limit) {
        Map<Integer, Integer> categories = new LinkedHashMap<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT c.id AS id, c.name AS name, IFNULL((SELECT SUM(rooms.visitors_now) FROM rooms WHERE rooms.category = c.id), 0) AS room_visitors FROM rooms_categories c WHERE c.isnode = 0 AND c.public_spaces = 0 AND c.minrole_access = 1 AND id <> 2 ORDER BY room_visitors DESC LIMIT ?", sqlConnection);
            // preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(1, limit);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                categories.put(resultSet.getInt("id"), resultSet.getInt("room_visitors"));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return categories;
    }


    /**
     * Get the list of recent rooms by category id.
     *
     * @param categoryId the rooms to find under this category id
     * @return the list of recent rooms
     */
    public static List<Room> getRoomsByCategory(int categoryId) {
        List<Room> rooms = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM rooms LEFT JOIN users ON rooms.owner_id = users.id WHERE category = ? ORDER BY visitors_now DESC, rooms.rating DESC", sqlConnection);
            preparedStatement.setInt(1, categoryId);
            resultSet = preparedStatement.executeQuery();

            //public NavigatorCategory(int id, String name, boolean publicSpaces, boolean allowTrading, int minimumRoleAccess, int minimumRoleSetFlat) {
            while (resultSet.next()) {
                Room room = new Room();
                RoomDao.fill(room.getData(), resultSet);
                rooms.add(room);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return rooms;
    }

    /**
     * Get the list of most popular rooms from database set by limit
     *
     * @param limit the maximum amount of usrs
     * @return the list of recent rooms
     */
    public static List<Room> getRopularRooms(int limit, boolean includePublicRooms) {
        List<Room> rooms = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String excludePublicRooms = " owner_id > 0 AND";

        if (includePublicRooms) {
            excludePublicRooms = "";
        }

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM rooms LEFT JOIN users ON rooms.owner_id = users.id WHERE" + excludePublicRooms + " visitors_now > 0 ORDER BY visitors_now DESC, rooms.rating DESC LIMIT ? ", sqlConnection);

            preparedStatement.setInt(1, limit);
            resultSet = preparedStatement.executeQuery();

            //public NavigatorCategory(int id, String name, boolean publicSpaces, boolean allowTrading, int minimumRoleAccess, int minimumRoleSetFlat) {
            while (resultSet.next()) {
                Room room = new Room();
                RoomDao.fill(room.getData(), resultSet);
                rooms.add(room);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return rooms;
    }

    public static int createRoom(int ownerId, String roomName, String roomModel, boolean roomShowName, int accessType) throws SQLException {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int roomId = 0;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("INSERT INTO rooms (owner_id, name, description, model, showname, password, accesstype) VALUES (?,?,?,?,?, '', ?)", sqlConnection);
            preparedStatement.setInt(1, ownerId);
            preparedStatement.setString(2, roomName);
            preparedStatement.setString(3, "");
            preparedStatement.setString(4, roomModel);
            preparedStatement.setBoolean(5, roomShowName);
            preparedStatement.setInt(6, accessType);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                roomId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            Storage.logError(e);
            throw e;
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return roomId;
    }

    public static int getRoomCountByCategory(int categoryId) {
        int size = 0;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT count(*) FROM rooms WHERE category = ?", sqlConnection);
            preparedStatement.setInt(1, categoryId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                size = resultSet.getInt(1);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return size;
    }

    /**
     * Get the list of rooms owned by friends, sorted by visitors and then room score
     *
     * @param limit the maximum amount of rooms
     * @param friendList the list of user ids
     * @return the list of recent rooms
     */
    public static List<Room> getFriendRooms(int limit, List<String> friendList) {
        List<Room> rooms = new ArrayList<>();

        if (friendList.isEmpty()) {
            return rooms;
        }

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String friendIds = "(";
        friendIds += String.join(",", friendList);
        friendIds += ")";

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM rooms INNER JOIN users ON rooms.owner_id = users.id WHERE owner_id IN " + friendIds + " ORDER BY visitors_now DESC, rooms.rating DESC LIMIT ? ", sqlConnection);
            preparedStatement.setInt(1, limit);
            resultSet = preparedStatement.executeQuery();

            //public NavigatorCategory(int id, String name, boolean publicSpaces, boolean allowTrading, int minimumRoleAccess, int minimumRoleSetFlat) {
            while (resultSet.next()) {
                Room room = new Room();
                RoomDao.fill(room.getData(), resultSet);
                rooms.add(room);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return rooms;
    }

    public static List<Room> getRecentlyVisited(int limit, int userId) {
        List<Room> rooms = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;


        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT rooms.*,users.username AS username FROM room_visits " +
                    "INNER JOIN rooms ON rooms.id = room_visits.room_id " +
                    "INNER JOIN users ON rooms.owner_id = users.id " +
                    "WHERE user_id = ? " +
                    "AND owner_id > 0 " +
                    "ORDER BY visited_at DESC " +
                    "LIMIT ? ", sqlConnection);

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, limit);
            resultSet = preparedStatement.executeQuery();

            //public NavigatorCategory(int id, String name, boolean publicSpaces, boolean allowTrading, int minimumRoleAccess, int minimumRoleSetFlat) {
            while (resultSet.next()) {
                Room room = new Room();
                RoomDao.fill(room.getData(), resultSet);
                rooms.add(room);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return rooms;
    }
}
