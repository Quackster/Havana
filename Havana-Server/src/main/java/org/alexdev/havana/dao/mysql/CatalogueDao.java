package org.alexdev.havana.dao.mysql;

import com.google.gson.reflect.TypeToken;
import org.alexdev.havana.dao.Storage;
import org.alexdev.havana.game.catalogue.CatalogueItem;
import org.alexdev.havana.game.catalogue.CataloguePackage;
import org.alexdev.havana.game.catalogue.CataloguePage;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CatalogueDao {

    public static Map<String, List<String>> getBadgeRewards()  {
        Map<String, List<String>> badges = new ConcurrentHashMap<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM catalogue_sale_badges", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String saleCode = resultSet.getString("sale_code");
                String badgeCode = resultSet.getString("badge_code");

                if (!badges.containsKey(saleCode)) {
                    badges.put(saleCode, new ArrayList<>());
                }

                badges.get(saleCode).add(badgeCode);
            }
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return badges;
    }

    /**
     * Get the catalogue pages.
     *
     * @return the list of catalogue pages
     */
    public static List<CataloguePage> getPages() {
        List<CataloguePage> pages = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet row = null;

        try {
            conn = Storage.getStorage().getConnection();
            stmt = Storage.getStorage().prepare("SELECT * FROM catalogue_pages ORDER BY order_id ASC", conn);
            row = stmt.executeQuery();

            while (row.next()) {
                CataloguePage page = new CataloguePage(row.getInt("id"), row.getInt("parent_id"), PlayerRank.getRankForId(row.getInt("min_role")),
                        row.getBoolean("is_navigatable"), row.getBoolean("is_club_only"), row.getString("name"), row.getInt("icon"), row.getInt("colour"),
                        row.getString("layout"),
                        StringUtil.GSON.fromJson(row.getString("images"), new TypeToken<List<String>>(){}.getType()),
                        StringUtil.GSON.fromJson(row.getString("texts"), new TypeToken<List<String>>(){}.getType()),
                        row.getString("seasonal_start"), row.getInt("seasonal_length"));

                pages.add(page);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(row);
            Storage.closeSilently(stmt);
            Storage.closeSilently(conn);
        }

        return pages;
    }

    /**
     * Get the catalogue items.
     *
     * @return the list of catalogue items
     */
    public static List<CatalogueItem> getItems() {
        List<CatalogueItem> pages = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM catalogue_items ORDER BY order_id ASC", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                CatalogueItem item = new CatalogueItem(resultSet.getInt("id"), resultSet.getString("sale_code"), resultSet.getString("page_id"),
                        resultSet.getInt("order_id"), resultSet.getInt("price_coins"), resultSet.getInt("price_pixels"),
                        resultSet.getInt("seasonal_coins"), resultSet.getInt("seasonal_pixels"), resultSet.getInt("amount"),
                        resultSet.getBoolean("hidden"), resultSet.getInt("definition_id"), resultSet.getString("item_specialspriteid"), resultSet.getBoolean("is_package"),
                        resultSet.getString("active_at"));

                pages.add(item);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return pages;
    }

    /**
     * Get the catalogue packages.
     *
     * @return the list of catalogue packages
     */
    public static List<CataloguePackage> getPackages() {
        List<CataloguePackage> packages = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM catalogue_packages", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                CataloguePackage cataloguePackage = new CataloguePackage(resultSet.getString("salecode"), resultSet.getInt("definition_id"),
                        resultSet.getString("special_sprite_id"), resultSet.getInt("amount"));

                packages.add(cataloguePackage);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return packages;
    }

    /**
     * Save catalogue item price.
     */
    public static void setPrice(String saleCode, int price) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("UPDATE catalogue_items SET price = ? WHERE sale_code = ?", sqlConnection);
            preparedStatement.setInt(1, price);
            preparedStatement.setString(2, saleCode);
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }
}
