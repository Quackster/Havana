package org.alexdev.havana.dao.mysql;

import org.alexdev.havana.dao.Storage;
import org.alexdev.havana.game.moderation.ChatMessage;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomData;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoomDao {
    public static final int FLASH_SEARCH_LIMIT = 100;
    public static final int SHOCKWAVE_SEARCH_LIMIT = 100;

    public record RoomAdmin(
            int id,
            int ownerId,
            String ownerName,
            int categoryId,
            String name,
            String description,
            String model,
            String ccts,
            int wallpaper,
            int floor,
            String landscape,
            boolean showOwnerName,
            boolean superUsers,
            int accessType,
            String password,
            int visitorsNow,
            int visitorsMax,
            int rating,
            String iconData,
            int groupId,
            boolean hidden,
            String createdAt,
            String updatedAt
    ) {}

    public record RoomRightAdmin(int userId, String username) {}
    public record RoomBanAdmin(int userId, String username, long expireAt) {}
    public record RoomEventAdmin(int userId, String username, int categoryId, String name, String description, long expireTime, String tags) {}

    public static void resetVisitors() {
        try {
            Storage.getStorage().execute("UPDATE rooms SET visitors_now = 0 WHERE visitors_now > 0");
        } catch (SQLException e) {
            Storage.logError(e);
        }
    }

    public static List<RoomAdmin> getAdminRooms(String query) {
        List<RoomAdmin> rooms = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String normalisedQuery = query == null ? "" : query.trim().toLowerCase();
            int roomId = -1;

            try {
                roomId = Integer.parseInt(normalisedQuery);
            } catch (NumberFormatException ignored) {

            }

            sqlConnection = Storage.getStorage().getConnection();

            if (normalisedQuery.isBlank()) {
                preparedStatement = Storage.getStorage().prepare("SELECT rooms.*, users.username AS username FROM rooms LEFT JOIN users ON rooms.owner_id = users.id ORDER BY rooms.id DESC LIMIT 100", sqlConnection);
            } else {
                preparedStatement = Storage.getStorage().prepare("SELECT rooms.*, users.username AS username FROM rooms LEFT JOIN users ON rooms.owner_id = users.id WHERE rooms.id = ? OR LOWER(rooms.name) LIKE ? OR LOWER(users.username) LIKE ? ORDER BY rooms.id DESC LIMIT 100", sqlConnection);
                preparedStatement.setInt(1, roomId);
                preparedStatement.setString(2, "%" + normalisedQuery + "%");
                preparedStatement.setString(3, "%" + normalisedQuery + "%");
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                rooms.add(readAdminRoom(resultSet));
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

    public static RoomAdmin getAdminRoom(int id) {
        RoomAdmin room = null;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT rooms.*, users.username AS username FROM rooms LEFT JOIN users ON rooms.owner_id = users.id WHERE rooms.id = ? LIMIT 1", sqlConnection);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                room = readAdminRoom(resultSet);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return room;
    }

    private static RoomAdmin readAdminRoom(ResultSet resultSet) throws SQLException {
        String ownerName = resultSet.getString("username");

        return new RoomAdmin(
                resultSet.getInt("id"),
                resultSet.getInt("owner_id"),
                ownerName == null ? "" : ownerName,
                resultSet.getInt("category"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("model"),
                resultSet.getString("ccts"),
                resultSet.getInt("wallpaper"),
                resultSet.getInt("floor"),
                resultSet.getString("landscape"),
                resultSet.getBoolean("showname"),
                resultSet.getBoolean("superusers"),
                resultSet.getInt("accesstype"),
                resultSet.getString("password"),
                resultSet.getInt("visitors_now"),
                resultSet.getInt("visitors_max"),
                resultSet.getInt("rating"),
                resultSet.getString("icon_data"),
                resultSet.getInt("group_id"),
                resultSet.getBoolean("is_hidden"),
                resultSet.getString("created_at"),
                resultSet.getString("updated_at")
        );
    }

    public static void saveAdminRoom(RoomAdmin room) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("UPDATE rooms SET owner_id = ?, category = ?, name = ?, description = ?, model = ?, ccts = ?, wallpaper = ?, floor = ?, landscape = ?, showname = ?, superusers = ?, accesstype = ?, password = ?, visitors_max = ?, rating = ?, icon_data = ?, group_id = ?, is_hidden = ? WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, room.ownerId());
            preparedStatement.setInt(2, room.categoryId());
            preparedStatement.setString(3, room.name());
            preparedStatement.setString(4, room.description());
            preparedStatement.setString(5, room.model());
            preparedStatement.setString(6, room.ccts());
            preparedStatement.setInt(7, room.wallpaper());
            preparedStatement.setInt(8, room.floor());
            preparedStatement.setString(9, room.landscape());
            preparedStatement.setBoolean(10, room.showOwnerName());
            preparedStatement.setBoolean(11, room.superUsers());
            preparedStatement.setInt(12, room.accessType());
            preparedStatement.setString(13, room.password());
            preparedStatement.setInt(14, room.visitorsMax());
            preparedStatement.setInt(15, room.rating());
            preparedStatement.setString(16, room.iconData());
            preparedStatement.setInt(17, room.groupId());
            preparedStatement.setBoolean(18, room.hidden());
            preparedStatement.setInt(19, room.id());
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void setAdminRoomHidden(int roomId, boolean hidden) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("UPDATE rooms SET is_hidden = ? WHERE id = ?", sqlConnection);
            preparedStatement.setBoolean(1, hidden);
            preparedStatement.setInt(2, roomId);
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static List<RoomRightAdmin> getAdminRoomRights(int roomId) {
        List<RoomRightAdmin> rights = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT rooms_rights.user_id, users.username FROM rooms_rights LEFT JOIN users ON rooms_rights.user_id = users.id WHERE rooms_rights.room_id = ? ORDER BY users.username ASC", sqlConnection);
            preparedStatement.setInt(1, roomId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                rights.add(new RoomRightAdmin(resultSet.getInt("user_id"), username == null ? "" : username));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return rights;
    }

    public static List<RoomBanAdmin> getAdminRoomBans(int roomId) {
        List<RoomBanAdmin> bans = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT rooms_bans.user_id, rooms_bans.expire_at, users.username FROM rooms_bans LEFT JOIN users ON rooms_bans.user_id = users.id WHERE rooms_bans.room_id = ? ORDER BY rooms_bans.expire_at DESC", sqlConnection);
            preparedStatement.setInt(1, roomId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                bans.add(new RoomBanAdmin(resultSet.getInt("user_id"), username == null ? "" : username, resultSet.getLong("expire_at")));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return bans;
    }

    public static List<RoomEventAdmin> getAdminRoomEvents(int roomId) {
        List<RoomEventAdmin> events = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT rooms_events.*, users.username FROM rooms_events LEFT JOIN users ON rooms_events.user_id = users.id WHERE rooms_events.room_id = ? ORDER BY rooms_events.expire_time DESC", sqlConnection);
            preparedStatement.setInt(1, roomId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                events.add(new RoomEventAdmin(
                        resultSet.getInt("user_id"),
                        username == null ? "" : username,
                        resultSet.getInt("category_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getLong("expire_time"),
                        resultSet.getString("tags")
                ));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return events;
    }

    public static List<String> getAdminRoomEntryBadges(int roomId) {
        List<String> badges = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT badge FROM rooms_entry_badges WHERE room_id = ? ORDER BY badge ASC", sqlConnection);
            preparedStatement.setInt(1, roomId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                badges.add(resultSet.getString("badge"));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return badges;
    }

    /**
     * Get a list of rooms by the owner id, use "0" for public rooms.
     *
     * @param userId the user id to get the rooms by
     * @return the list of rooms
     */
    public static List<Room> getRoomsByUserId(int userId) {
        List<Room> rooms = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM rooms LEFT JOIN users ON rooms.owner_id = users.id WHERE rooms.owner_id = ?", sqlConnection);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Room room = new Room();
                fill(room.getData(), resultSet);
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
     * Get a list of recommended rooms.
     *
     * @param limit the limit of rooms
     * @return the list of rooms
     */
    public static List<Room> getRecommendedRooms(int limit, int offset) {
        List<Room> roomList = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM rooms LEFT JOIN users ON rooms.owner_id = users.id WHERE owner_id > 0 AND accesstype = 0 ORDER BY visitors_now DESC, rating DESC LIMIT " + limit + " OFFSET " + offset, sqlConnection);
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

    /**
     * Get the list of the highest rated rooms
     *
     * @param limit the maximum amount of rooms
     * @return the list of highest rated rooms
     */
    public static List<Room> getHighestRatedRooms(int limit, int offset) {
        List<Room> rooms = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM rooms LEFT JOIN users ON rooms.owner_id = users.id WHERE owner_id > 0 ORDER BY rating DESC LIMIT " + limit + " OFFSET " + offset, sqlConnection);
            resultSet = preparedStatement.executeQuery();

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
     * Get the room id of a room by its model, used for walkways.
     *
     * @param model the model used to get the id for
     * @return the id, else -1
     */
    public static int getRoomIdByModel(String model) {
        int roomId = -1;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT id FROM rooms WHERE model = ?", sqlConnection);
            preparedStatement.setString(1, model);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                roomId = resultSet.getInt("id");
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return roomId;
    }


    /**
     * Search query for when people use the navigator search, will search either by username or room name similarities.
     *
     * @param searchQuery the query to use
     * @return the list of possible room matches
     */
    public static List<Room> searchRooms(String searchQuery, int roomOwner, int limit) {
        List<Room> rooms = new ArrayList<>();

        if (searchQuery.isBlank() && roomOwner == -1) {
            return rooms;
        }

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM rooms INNER JOIN users ON rooms.owner_id = users.id WHERE" + (roomOwner > 0 ? (" owner_id = " + roomOwner + " AND") : " LOWER(users.username) LIKE ? OR") + " LOWER(rooms.name) LIKE ? ORDER BY visitors_now DESC, rating DESC LIMIT ? ", sqlConnection);

            if (roomOwner > 0) {
                preparedStatement.setString(1, "%" + searchQuery + "%");
                preparedStatement.setInt(2, limit);
            } else {
                preparedStatement.setString(1, "%" + searchQuery + "%");
                preparedStatement.setString(2, "%" + searchQuery + "%");
                preparedStatement.setInt(3, limit);
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Room room = new Room();
                fill(room.getData(), resultSet);
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

    public static Room getRoomById(int roomId) {
        Room room = null;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM rooms LEFT JOIN users ON rooms.owner_id = users.id WHERE rooms.id = ?", sqlConnection);
            preparedStatement.setInt(1, roomId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                room = new Room();
                fill(room.getData(), resultSet);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return room;
    }

    /**
     * Save all room information.
     *
     * @param room the room to save
     */
    public static void saveDecorations(Room room) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("UPDATE rooms SET wallpaper = ?, floor = ?, landscape = ? WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, room.getData().getWallpaper());
            preparedStatement.setInt(2, room.getData().getFloor());
            preparedStatement.setString(3, room.getData().getLandscape());
            preparedStatement.setInt(4, room.getId());
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    /**
     * Save all room information.
     *
     * @param room the room to save
     */
    public static void save(Room room) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("UPDATE rooms SET category = ?, name = ?, description = ?, showname = ?, superusers = ?, accesstype = ?, password = ?, visitors_max = ? WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, room.getData().getCategoryId());
            preparedStatement.setString(2, room.getData().getName());
            preparedStatement.setString(3, room.getData().getDescription());
            preparedStatement.setBoolean(4, room.getData().showOwnerName());
            preparedStatement.setBoolean(5, room.getData().allowSuperUsers());
            preparedStatement.setInt(6, room.getData().getAccessTypeId());
            preparedStatement.setString(7, room.getData().getPassword());
            preparedStatement.setInt(8, room.getData().getVisitorsMax());
            preparedStatement.setInt(9, room.getId());
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }


    /**
     * Save visitor count of rooms
     *
     * @param id the id of room to save
     */
    public static void saveVisitors(int id, int size) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("UPDATE rooms SET visitors_now = ? WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    /**
     * Save rating for the room
     *
     * @param roomId the room to save
     * @param rating the new rating
     */
    public static void saveRating(int roomId, int rating) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("UPDATE rooms SET rating = ? WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, rating);
            preparedStatement.setInt(2, roomId);
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }


    /**
     * Save room icon data
     *
     * @param roomId the room id for the icon to save to
     * @param formattedIconData the formatted icon data to save
     */
    public static void saveIcon(int roomId, String formattedIconData) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("UPDATE rooms SET icon_data = ? WHERE id = ?", sqlConnection);
            preparedStatement.setString(1, formattedIconData);
            preparedStatement.setInt(2, roomId);
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    /**
     * Delete room.
     *
     * @param room the room to delete
     */
    public static void delete(Room room) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("DELETE FROM rooms WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, room.getId());
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    /**
     * Save visitor count of rooms
     *
     * @param roomId the room to save
     */
    public static void saveGroupId(int roomId, int groupId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("UPDATE rooms SET group_id = ? WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, roomId);
            preparedStatement.setInt(2, groupId);
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void saveChatLog(Collection<ChatMessage> chatMessages) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("INSERT INTO room_chatlogs (user_id, room_id, timestamp, chat_type, message) VALUES (?, ?, ?, ?, ?)", sqlConnection);
            sqlConnection.setAutoCommit(false);

            for (ChatMessage chatMessage : chatMessages) {
                preparedStatement.setInt(1, chatMessage.getPlayerId());
                preparedStatement.setInt(2, chatMessage.getRoomId());
                preparedStatement.setLong(3, chatMessage.getSentTime());

                switch (chatMessage.getChatMessageType()) {
                    case CHAT:
                        preparedStatement.setInt(4, 0);
                        break;
                    case SHOUT:
                        preparedStatement.setInt(4, 1);
                        break;
                    default:
                        preparedStatement.setInt(4, 2);
                        break;
                }

                preparedStatement.setString(5, chatMessage.getMessage());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            sqlConnection.setAutoCommit(true);

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static List<ChatMessage> getModChatlog(int roomId) {
        List<ChatMessage> chatHistoryList = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        //room_chatlogs (user_id, room_id, timestamp, chat_type, message)

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT rooms.owner_id AS owner_id, room_chatlogs.*,rooms.name AS room_name, users.username AS username FROM room_chatlogs " +
                    "INNER JOIN rooms ON room_chatlogs.room_id = rooms.id " +
                    "INNER JOIN users ON room_chatlogs.user_id = users.id " +
                    "WHERE room_id = ? " +
                    "ORDER BY timestamp DESC " +
                    "LIMIT 150", sqlConnection);
            preparedStatement.setInt(1, roomId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                CHAT_MESSAGE.ChatMessageType chatMessageType = CHAT_MESSAGE.ChatMessageType.CHAT;
                int chatType = resultSet.getInt("chat_type");

                if (chatType == 2) {
                    chatMessageType = CHAT_MESSAGE.ChatMessageType.WHISPER;
                }

                if (chatType == 1) {
                    chatMessageType = CHAT_MESSAGE.ChatMessageType.SHOUT;
                }

                chatHistoryList.add(new ChatMessage(resultSet.getInt("user_id"), resultSet.getString("username"),
                        resultSet.getString("message"), chatMessageType,
                        roomId, resultSet.getLong("timestamp")));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return chatHistoryList;
    }

    /**
     * Fill room data
     *
     * @param data the room data instance
     * @param row  the row
     * @throws SQLException the SQL exception
     */
    public static void fill(RoomData data, ResultSet row) throws SQLException {
        if (data == null) {
            return;
        }

        String ownerName = row.getString("username");

        data.fill(row.getInt("id"), row.getInt("owner_id"), ownerName != null ? ownerName : "", row.getInt("category"),
                row.getString("name"), row.getString("description"), row.getString("model"),
                row.getString("ccts"), row.getInt("wallpaper"), row.getInt("floor"), row.getString("landscape"),
                row.getBoolean("showname"), row.getBoolean("superusers"), row.getInt("accesstype"),
                row.getString("password"), row.getInt("visitors_now"), row.getInt("visitors_max"), row.getInt("rating"),
                row.getString("icon_data"), row.getInt("group_id"), row.getBoolean("is_hidden"));

    }
}
