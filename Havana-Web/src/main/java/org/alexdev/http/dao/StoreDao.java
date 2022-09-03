package org.alexdev.http.dao;

import org.alexdev.havana.dao.Storage;
import org.alexdev.http.game.stickers.StickerCategory;
import org.alexdev.http.game.stickers.StickerProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreDao {
    public static List<StickerCategory> getCategories() {
        List<StickerCategory> categoryList = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM cms_stickers_categories", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                categoryList.add(new StickerCategory(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("min_rank"),
                        resultSet.getInt("category_type")));
            }
        } catch (SQLException e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return categoryList;
    }

    public static List<StickerProduct> getCatalogue() {
        List<StickerProduct> productList = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM cms_stickers_catalogue", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                productList.add(new StickerProduct(
                        resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description"), resultSet.getInt("min_rank"),
                        resultSet.getString("data"), resultSet.getInt("price"), resultSet.getInt("amount"),
                        resultSet.getInt("category_id"), resultSet.getInt("widget_type"), resultSet.getInt("type")));
            }
        } catch (SQLException e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return productList;
    }
}
