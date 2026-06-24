package org.alexdev.havana.dao.mysql;

import org.alexdev.havana.dao.Storage;
import org.alexdev.havana.game.wordfilter.WordfilterWord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class WordfilterDao {
    public record WordfilterAdmin(int id, String word, boolean bannable, boolean filterable) {}

    public static List<WordfilterWord> getBadWords() {
        List<WordfilterWord> word = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM wordfilter", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                word.add(new WordfilterWord(resultSet.getString("word"), resultSet.getBoolean("is_bannable"), resultSet.getBoolean("is_filterable")));
            }
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return word;
    }

    public static List<WordfilterAdmin> getAdminWords() {
        List<WordfilterAdmin> words = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM wordfilter ORDER BY word ASC", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                words.add(new WordfilterAdmin(resultSet.getInt("id"), resultSet.getString("word"), resultSet.getBoolean("is_bannable"), resultSet.getBoolean("is_filterable")));
            }
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return words;
    }

    public static WordfilterAdmin getAdminWord(int id) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM wordfilter WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new WordfilterAdmin(resultSet.getInt("id"), resultSet.getString("word"), resultSet.getBoolean("is_bannable"), resultSet.getBoolean("is_filterable"));
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

    public static int saveAdminWord(WordfilterAdmin word) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();

            if (word.id() > 0) {
                preparedStatement = Storage.getStorage().prepare("UPDATE wordfilter SET word = ?, is_bannable = ?, is_filterable = ? WHERE id = ?", sqlConnection);
            } else {
                preparedStatement = Storage.getStorage().prepare("INSERT INTO wordfilter (word, is_bannable, is_filterable) VALUES (?, ?, ?)", sqlConnection);
            }

            preparedStatement.setString(1, word.word());
            preparedStatement.setBoolean(2, word.bannable());
            preparedStatement.setBoolean(3, word.filterable());

            if (word.id() > 0) {
                preparedStatement.setInt(4, word.id());
            }

            preparedStatement.executeUpdate();

            if (word.id() > 0) {
                return word.id();
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

        return word.id();
    }

    public static void deleteAdminWord(int id) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("DELETE FROM wordfilter WHERE id = ?", sqlConnection);
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
