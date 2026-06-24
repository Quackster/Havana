package org.alexdev.havana.dao.mysql;

import org.alexdev.havana.dao.Storage;
import org.alexdev.havana.game.room.models.RoomModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RoomModelDao {
    public record RoomModelAdmin(int id, String modelId, String modelName, int doorX, int doorY, double doorZ, int doorDir, String heightmap, String triggerClass) {}

    public static ConcurrentHashMap<String, RoomModel> getModels() {
        ConcurrentHashMap<String, RoomModel> roomModels = new ConcurrentHashMap<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM rooms_models", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                RoomModel roomModel = new RoomModel(resultSet.getString("model_id"), resultSet.getString("model_name"),
                        resultSet.getInt("door_x"), resultSet.getInt("door_y"), resultSet.getDouble("door_z"),
                        resultSet.getInt("door_dir"), resultSet.getString("heightmap"), resultSet.getString("trigger_class"));

                roomModels.put(roomModel.getId(), roomModel);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return roomModels;
    }

    public static List<RoomModelAdmin> getAdminModels() {
        List<RoomModelAdmin> models = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM rooms_models ORDER BY id ASC", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                models.add(readAdminModel(resultSet));
            }
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return models;
    }

    public static RoomModelAdmin getAdminModel(int id) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM rooms_models WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return readAdminModel(resultSet);
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

    private static RoomModelAdmin readAdminModel(ResultSet resultSet) throws SQLException {
        return new RoomModelAdmin(
                resultSet.getInt("id"),
                resultSet.getString("model_id"),
                resultSet.getString("model_name"),
                resultSet.getInt("door_x"),
                resultSet.getInt("door_y"),
                resultSet.getDouble("door_z"),
                resultSet.getInt("door_dir"),
                resultSet.getString("heightmap"),
                resultSet.getString("trigger_class")
        );
    }

    public static int saveAdminModel(RoomModelAdmin model) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();

            if (model.id() > 0) {
                preparedStatement = Storage.getStorage().prepare("UPDATE rooms_models SET model_id = ?, model_name = ?, door_x = ?, door_y = ?, door_z = ?, door_dir = ?, heightmap = ?, trigger_class = ? WHERE id = ?", sqlConnection);
            } else {
                preparedStatement = Storage.getStorage().prepare("INSERT INTO rooms_models (model_id, model_name, door_x, door_y, door_z, door_dir, heightmap, trigger_class) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", sqlConnection);
            }

            preparedStatement.setString(1, model.modelId());
            preparedStatement.setString(2, model.modelName());
            preparedStatement.setInt(3, model.doorX());
            preparedStatement.setInt(4, model.doorY());
            preparedStatement.setDouble(5, model.doorZ());
            preparedStatement.setInt(6, model.doorDir());
            preparedStatement.setString(7, model.heightmap());
            preparedStatement.setString(8, model.triggerClass());

            if (model.id() > 0) {
                preparedStatement.setInt(9, model.id());
            }

            preparedStatement.executeUpdate();

            if (model.id() > 0) {
                return model.id();
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

        return model.id();
    }

    public static void deleteAdminModel(int id) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("DELETE FROM rooms_models WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

}
