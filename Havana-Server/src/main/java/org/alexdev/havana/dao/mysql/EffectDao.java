package org.alexdev.havana.dao.mysql;

import org.alexdev.havana.dao.Storage;
import org.alexdev.havana.game.effects.Effect;
import org.alexdev.havana.util.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class EffectDao {
    public static Map<Integer, Integer> getEffectTimes() {
        Map<Integer, Integer> effectTimes = new HashMap<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM settings_effects", sqlConnection);
            resultSet =  preparedStatement.executeQuery();

            while (resultSet.next()) {
                effectTimes.put(resultSet.getInt("effect_id"), resultSet.getInt("duration_seconds"));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
            Storage.closeSilently(resultSet);
        }

        return effectTimes;
    }

    public static CopyOnWriteArrayList<Effect> getEffects(int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        CopyOnWriteArrayList<Effect> effects = new CopyOnWriteArrayList<>();

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM users_effects WHERE user_id = ?", sqlConnection);
            preparedStatement.setInt(1, userId);
            resultSet =  preparedStatement.executeQuery();

            while (resultSet.next()) {
                effects.add(new Effect(resultSet.getInt("id"), resultSet.getInt("user_id"), resultSet.getInt("effect_id"),
                        resultSet.getLong("expiry_date"), resultSet.getBoolean("activated")));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
            Storage.closeSilently(resultSet);
        }

        return effects;
    }

    public static void removeEffects(int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("DELETE FROM users_effects WHERE user_id = ? AND expiry_date < ? AND activated = 1", sqlConnection);
            preparedStatement.setInt(1, userId);
            preparedStatement.setLong(2, DateUtil.getCurrentTimeSeconds());
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static Effect newEffect(int userId, int effectId, long expiryDate, boolean activated) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Effect effect = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("INSERT INTO users_effects (user_id, effect_id, expiry_date, activated) VALUES (?,?,?,?)", sqlConnection);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, effectId);
            preparedStatement.setLong(3, expiryDate);
            preparedStatement.setBoolean(4, activated);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                effect = new Effect(resultSet.getInt("id"), userId, effectId, expiryDate, activated);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
            Storage.closeSilently(resultSet);
        }

        return effect;
    }

    public static void saveEffect(Effect effect) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("UPDATE users_effects SET activated = ?, expiry_date = ? WHERE id = ?", sqlConnection);
            preparedStatement.setBoolean(1, effect.isActivated());
            preparedStatement.setLong(2, effect.getExpireDate());
            preparedStatement.setInt(3, effect.getId());
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

    }

    public static void deleteEffect(int id) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("DELETE FROM users_effects WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }
}
