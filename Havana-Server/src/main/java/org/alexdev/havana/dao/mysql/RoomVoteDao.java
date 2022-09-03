package org.alexdev.havana.dao.mysql;

import org.alexdev.havana.dao.Storage;
import org.alexdev.havana.game.room.managers.VoteData;
import org.alexdev.havana.util.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RoomVoteDao {
    private static final long EXPIRE_SECONDS = TimeUnit.DAYS.toSeconds(30*2);

    /**
     * Vote for a room
     *
     * @param userId the user id who is voting
     * @param roomId the room id that the user is voting for
     * @param answer the value of the vote (1 or -1)
     */
    public static void vote(int userId, int roomId, int answer) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("INSERT INTO users_room_votes (user_id, room_id, vote, expire_time) VALUES (?, ?, ?, ?)", sqlConnection);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, roomId);
            preparedStatement.setInt(3, answer);
            preparedStatement.setLong(4, DateUtil.getCurrentTimeSeconds() + EXPIRE_SECONDS);
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    /**
     * Vote expired votes for a room
     */
    public static void removeExpiredVotes(int roomId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();

            /*preparedStatement = Storage.getStorage().prepare("SELECT room_id FROM users_room_votes WHERE expire_time < ?", sqlConnection);
            preparedStatement.setLong(1, DateUtil.getCurrentTimeSeconds());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                affected = true;
            }*/

            preparedStatement = Storage.getStorage().prepare("DELETE FROM users_room_votes WHERE room_id = ? AND expire_time < ?", sqlConnection);
            preparedStatement.setInt(1, roomId);
            preparedStatement.setLong(2, DateUtil.getCurrentTimeSeconds());
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    /**
     * Return a map of the room ratings
     *
     * @param roomId the id of the room id to save
     * @return Map containing key userId and value voteAnswer
     */
    public static List<VoteData> getRatings(int roomId) {
        List<VoteData> ratings = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT " +
                    "users_room_votes.user_id AS user_id, room_id, vote, CONVERT(GROUP_CONCAT(ip_address SEPARATOR ',') USING 'UTF8') AS ip_addresses, machine_id " +
                    "FROM " +
                    "users_room_votes " +
                    "INNER JOIN users_ip_logs ON users_ip_logs.user_id = users_room_votes.user_id " +
                    "INNER JOIN users ON users.id = users_room_votes.user_id " +
                    "WHERE " +
                    "users_room_votes.room_id = ? AND users_room_votes.expire_time > ? " +
                    "GROUP BY " +
                    "users_room_votes.user_id", sqlConnection);
            preparedStatement.setInt(1, roomId);
            preparedStatement.setLong(2, DateUtil.getCurrentTimeSeconds());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ratings.add(new VoteData(resultSet.getInt("user_id"), resultSet.getInt("vote"), resultSet.getString("ip_addresses"), resultSet.getString("machine_id")));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return ratings;
    }
}
