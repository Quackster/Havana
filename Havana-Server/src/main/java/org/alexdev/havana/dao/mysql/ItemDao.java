package org.alexdev.havana.dao.mysql;

import org.alexdev.havana.dao.Storage;
import org.alexdev.havana.game.ecotron.EcotronItem;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemDefinition;
import org.alexdev.havana.game.room.RoomData;
import org.alexdev.havana.util.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ItemDao {
    
    /**
     * Get the item definitions.
     *
     * @return the list of item definitions
     */
    public static Map<Integer, ItemDefinition> getItemDefinitions() {
        Map<Integer, ItemDefinition> definitions = new HashMap<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM items_definitions", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ItemDefinition definition = new ItemDefinition(resultSet.getInt("id"), resultSet.getString("sprite"),
                        resultSet.getString("name"), resultSet.getString("description"),
                        resultSet.getInt("sprite_id"), resultSet.getString("behaviour"), resultSet.getDouble("top_height"),
                        resultSet.getInt("length"), resultSet.getInt("width"), resultSet.getInt("max_status"),
                        resultSet.getString("interactor"), resultSet.getBoolean("is_tradable"),
                        resultSet.getBoolean("is_recyclable"), resultSet.getString("drink_ids"), resultSet.getInt("rental_time"),
                        resultSet.getString("allowed_rotations"), resultSet.getString("heights"));

                definitions.put(definition.getId(), definition);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return definitions;
    }

    public static Map<String, Integer> getItemVersions() {
        Map<String, Integer> definitions = new HashMap<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM furniture_versions", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                definitions.put(resultSet.getString("asset_name"), resultSet.getInt("version_id"));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return definitions;
    }

    public static List<EcotronItem> getEcotronItems() {
        List<EcotronItem> itemList = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM recycler_rewards ORDER BY order_id ASC", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                EcotronItem ecotronItem = new EcotronItem(resultSet.getString("sprite"), resultSet.getInt("chance"), resultSet.getInt("chance"));
                itemList.add(ecotronItem);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return itemList;
    }

    /**
     * Create new item entry with the definition id, user id and custom data. It will
     * override the current item id with its database id.
     *
     * @param item the item to create
     */
    public static void newItem(Item item) throws SQLException {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet row = null;

        long itemId = 0;

        try {

            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("INSERT INTO items (user_id, definition_id, custom_data, expire_time) VALUES (?,?,?,?)", sqlConnection);
            preparedStatement.setInt(1, item.getOwnerId());
            preparedStatement.setInt(2, item.getDefinition().getId());
            preparedStatement.setString(3, item.getCustomData());
            preparedStatement.setLong(4, item.getExpireTime());
            preparedStatement.executeUpdate();

            row = preparedStatement.getGeneratedKeys();

            if (row != null && row.next()) {
                itemId = row.getLong(1);
            }

        } catch (SQLException e) {
            Storage.logError(e);
            throw e;
        } finally {
            Storage.closeSilently(row);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        item.setDatabaseId(itemId);
        item.assignVirtualId();
    }

    /**
     * Get the inventory list of items.
     *
     * @param userId the id of the user to get the inventory for
     * @return the list of items
     */
    public static List<Item> getInventory(int userId) {
        List<Item> items = new CopyOnWriteArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM items WHERE user_id = ? AND room_id = 0 ORDER BY order_id ASC", sqlConnection);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Item item = new Item();
                fill(item, resultSet);
                items.add(item);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return items;
    }

    /**
     * Get the item by item id
     *
     * @param itemId the id of the item to to get
     * @return the item
     */
    public static Item getItem(long itemId) {
        Item item = null;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM items WHERE id = ?", sqlConnection);
            preparedStatement.setLong(1, itemId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                item = new Item();
                fill(item, resultSet);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return item;
    }

    /**
     * Get the room list of items.
     *
     * @return the list of items
     */
    public static List<Item> getRoomItems(RoomData roomData) {
        List<Item> items = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM items WHERE room_id = ?", sqlConnection);
            preparedStatement.setInt(1, roomData.getId());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Item item = new Item();
                item.assignVirtualId();
                fill(item, resultSet);
                items.add(item);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return items;
    }

    /**
     * Get expired items
     *
     * @return the list of expired items
     */
    public static List<Item> getExpiredItems() {
        List<Item> items = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM items WHERE expire_time <> -1 AND expire_time < ?", sqlConnection);
            preparedStatement.setLong(1, DateUtil.getCurrentTimeSeconds());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Item item = new Item();
                item.assignVirtualId();
                fill(item, resultSet);
                items.add(item);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return items;
    }


    /**
     * Redeem credit furniture atomicly
     *
     * @param amount credit amount to increase by
     * @param userID user ID
     */
    public static int redeemCreditItem(int amount, long itemID, int userID) {
        int updatedAmount = -1;
        Connection conn = null;
        PreparedStatement deleteQuery = null;
        PreparedStatement updateQuery = null;
        PreparedStatement fetchQuery = null;
        ResultSet row = null;

        try {
            conn = Storage.getStorage().getConnection();

            // We disable autocommit to make sure the following queries share the same atomic transaction
            conn.setAutoCommit(false);

            deleteQuery = Storage.getStorage().prepare("DELETE FROM items WHERE id = ?", conn);
            deleteQuery.setLong(1, itemID);
            deleteQuery.execute();

            // Increase credits
            updateQuery = Storage.getStorage().prepare("UPDATE users SET credits = credits + ? WHERE id = ?", conn);
            updateQuery.setInt(1, amount);
            updateQuery.setInt(2, userID);
            updateQuery.execute();

            // Fetch increased amount
            fetchQuery = Storage.getStorage().prepare("SELECT credits FROM users WHERE id = ?", conn);
            fetchQuery.setInt(1, userID);
            row = fetchQuery.executeQuery();

            // Commit these queries
            conn.commit();

            // Set amount
            if (row != null && row.next()) {
                updatedAmount = row.getInt("credits");
            }

        } catch (Exception e) {
            // Reset amount
            updatedAmount = -1;

            try {
                // Rollback these queries
                conn.rollback();
            } catch(SQLException re) {
                Storage.logError(re);
            }

            Storage.logError(e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ce) {
                Storage.logError(ce);
            }

            Storage.closeSilently(row);
            Storage.closeSilently(deleteQuery);
            Storage.closeSilently(updateQuery);
            Storage.closeSilently(fetchQuery);
            Storage.closeSilently(conn);
        }

        return updatedAmount;
    }

    /**
     * Update item by item instance.
     *
     * @param item the instance of the item to update it
     */
    public static void updateItem(Item item) {
        updateItems(List.of(item));
    }

    /**
     * Update an entire list of items at once.
     *
     * @param items the list of items
     */
    public static void updateItems(Collection<Item> items) {
        if (items.size() > 0) {
            Connection sqlConnection = null;
            PreparedStatement preparedStatement = null;

            try {
                sqlConnection = Storage.getStorage().getConnection();
                preparedStatement = Storage.getStorage().prepare("UPDATE items SET room_id = ?, definition_id = ?, x = ?, y = ?, z = ?, rotation = ?, wall_position = ?, custom_data = ?, order_id = ?, is_hidden = ? WHERE id = ?", sqlConnection);
                sqlConnection.setAutoCommit(false);

                for (Item item : items) {
                    preparedStatement.setInt(1, item.getRoomId());
                    preparedStatement.setInt(2, item.getDefinition().getId());
                    preparedStatement.setInt(3, item.getPosition().getX());
                    preparedStatement.setInt(4, item.getPosition().getY());
                    preparedStatement.setDouble(5, item.getPosition().getZ());
                    preparedStatement.setInt(6, item.getPosition().getRotation());
                    preparedStatement.setString(7, item.getWallPosition());
                    preparedStatement.setString(8, item.getCustomData());
                    preparedStatement.setInt(9, item.getOrderId());
                    preparedStatement.setInt(10, item.isHidden() ? 1 : 0);
                    preparedStatement.setLong(11, item.getDatabaseId());
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
    }

    /**
     * Update item by item instance.
     *
     * @param item the instance of the item to update it
     */
    public static void updateItemOwnership(Item item) {
        updateItemOwnership(List.of(item));
    }

    /**
     * Update an entire list of items at once.
     *
     * @param items the list of items
     */
    public static void updateItemOwnership(Collection<Item> items) {
        if (items.size() > 0) {
            Connection sqlConnection = null;
            PreparedStatement preparedStatement = null;

            try {
                sqlConnection = Storage.getStorage().getConnection();
                preparedStatement = Storage.getStorage().prepare("UPDATE items SET room_id = ?, user_id = ? WHERE id = ?", sqlConnection);
                sqlConnection.setAutoCommit(false);

                for (Item item : items) {
                    preparedStatement.setInt(1, item.getRoomId());
                    preparedStatement.setInt(2, item.getOwnerId());
                    preparedStatement.setLong(3, item.getDatabaseId());
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
    }

    /**
     * Update item by item instance.
     *
     * @param item the instance of the item to update it
     */
    public static void updateTradeState(Item item, boolean inTrade) {
        updateTradeStates(List.of(item), inTrade);
    }

    /**
     * Update an entire list of items at once.
     *
     * @param items the list of items
     */
    public static void updateTradeStates(Collection<Item> items, boolean inTrade) {
        if (items.size() > 0) {
            Connection sqlConnection = null;
            PreparedStatement preparedStatement = null;

            try {
                sqlConnection = Storage.getStorage().getConnection();
                preparedStatement = Storage.getStorage().prepare("UPDATE items SET is_trading = ? WHERE id = ?", sqlConnection);
                sqlConnection.setAutoCommit(false);

                for (Item item : items) {
                    item.setInTrade(inTrade);

                    preparedStatement.setBoolean(1, item.isInTrade());
                    preparedStatement.setLong(2, item.getDatabaseId());
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
    }

    public static void deleteItems(List<Long> items) {
        if (items.size() > 0) {
            Connection sqlConnection = null;
            PreparedStatement preparedStatement = null;

            try {
                sqlConnection = Storage.getStorage().getConnection();
                preparedStatement = Storage.getStorage().prepare("DELETE FROM items WHERE id = ?", sqlConnection);
                sqlConnection.setAutoCommit(false);

                for (Long itemId : items) {
                    preparedStatement.setLong(1, itemId);
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
    }


    public static void saveTotemExpire(int userId, long totemExpiration) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("UPDATE users SET totem_effect_expiry = ? WHERE id = ?", sqlConnection);
            preparedStatement.setLong(1, totemExpiration);
            preparedStatement.setInt(2, userId);
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void saveTradeBanExpire(int userId, long tradeBanExpiration) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("UPDATE users SET trade_ban_expiration = ? WHERE id = ?", sqlConnection);
            preparedStatement.setLong(1, tradeBanExpiration);
            preparedStatement.setInt(2, userId);
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }


    public static void deleteHandItems(int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("DELETE items WHERE is_hidden = 0 AND is_trading = 0 AND room_id = 0 AND user_id = ?", sqlConnection);
            preparedStatement.setInt(1, userId);
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }


    /**
     * Get the room list of items.
     *
     * @return the list of items
     */
    public static List<Item> getUserItemsByDefinition(int userId, ItemDefinition definition) {
        List<Item> items = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM items WHERE user_id = ? AND definition_id = ?", sqlConnection);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, definition.getId());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Item item = new Item();
                item.assignVirtualId();
                fill(item, resultSet);
                items.add(item);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return items;
    }

    public static void resetTradeStates() {
        try {
            Storage.getStorage().execute("UPDATE items SET is_trading = 0 WHERE is_trading > 0");
        } catch (SQLException e) {
            Storage.logError(e);
        }
    }

    /**
     * Fill item with data retrieved from the SQL query.
     *
     * @param item the item to fill data for
     * @param resultSet the result set returned with the data
     * @throws SQLException an exception if an error happened
     */
    public static void fill(Item item, ResultSet resultSet) throws SQLException {
        item.fill(resultSet.getLong("id"), resultSet.getInt("order_id"), resultSet.getInt("user_id"), resultSet.getInt("room_id"),
                resultSet.getInt("definition_id"), resultSet.getInt("x"), resultSet.getInt("y"),
                resultSet.getDouble("z"), resultSet.getInt("rotation"), resultSet.getString("wall_position"),
                resultSet.getString("custom_data"), resultSet.getBoolean("is_hidden"), resultSet.getBoolean("is_trading"),
                resultSet.getLong("expire_time"), resultSet.getTime("created_at").getTime() / 1000L);
    }
}
