package org.alexdev.havana.dao.mysql;

import org.alexdev.havana.dao.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class LogDao {
    // DELETE FROM users_ip_logs WHERE UNIX_TIMESTAMP(created_at) < (UNIX_TIMESTAMP() - 10);
    // DELETE FROM users_ip_logs WHERE UNIX_TIMESTAMP(created_at) < (UNIX_TIMESTAMP() - 10);

    // Delete rows more than 1 month old
    // DELETE FROM users_transactions WHERE UNIX_TIMESTAMP(created_at) < (UNIX_TIMESTAMP() - 2678400)

    public static void deleteTradeLogs(int interval) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {

            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("DELETE FROM users_transactions WHERE UNIX_TIMESTAMP(created_at) < (UNIX_TIMESTAMP() - ?)", sqlConnection);
            preparedStatement.setInt(1, interval);
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void deleteIpAddressLogs(int interval) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {

            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("DELETE FROM users_ip_logs WHERE UNIX_TIMESTAMP(created_at) < (UNIX_TIMESTAMP() - ?)", sqlConnection);
            preparedStatement.setInt(1, interval);
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void deleteChatLogs(int interval) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {

            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("DELETE FROM room_chatlogs WHERE timestamp < (UNIX_TIMESTAMP() - ?)", sqlConnection);
            preparedStatement.setInt(1, interval);
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

}
