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
    public record CataloguePageAdmin(
            int id,
            int oldId,
            int parentId,
            int orderId,
            int minRole,
            boolean navigatable,
            boolean clubOnly,
            String name,
            int icon,
            int colour,
            String layout,
            String images,
            String texts,
            String seasonalStart,
            int seasonalLength
    ) {}

    public record CatalogueItemAdmin(
            int id,
            String saleCode,
            String pageId,
            int orderId,
            int priceCoins,
            int pricePixels,
            int seasonalCoins,
            int seasonalPixels,
            boolean hidden,
            int amount,
            int definitionId,
            String itemSpecialSpriteId,
            boolean packageItem,
            String activeAt
    ) {}

    public record CataloguePackageAdmin(
            int id,
            String saleCode,
            int definitionId,
            String specialSpriteId,
            int amount
    ) {}

    public record CatalogueSaleBadgeAdmin(
            String saleCode,
            String badgeCode
    ) {}

    public record CatalogueCollectableAdmin(
            int id,
            int storePageId,
            int adminPageId,
            long expiry,
            long lifetime,
            int currentPosition,
            String classNames
    ) {}

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

    public static List<CataloguePageAdmin> getAdminPages() {
        List<CataloguePageAdmin> pages = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM catalogue_pages ORDER BY parent_id ASC, order_id ASC, id ASC", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                pages.add(readAdminPage(resultSet));
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

    public static CataloguePageAdmin getAdminPage(int id) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM catalogue_pages WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return readAdminPage(resultSet);
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

    private static CataloguePageAdmin readAdminPage(ResultSet resultSet) throws Exception {
        return new CataloguePageAdmin(
                resultSet.getInt("id"),
                resultSet.getInt("old_id"),
                resultSet.getInt("parent_id"),
                resultSet.getInt("order_id"),
                resultSet.getInt("min_role"),
                resultSet.getBoolean("is_navigatable"),
                resultSet.getBoolean("is_club_only"),
                resultSet.getString("name"),
                resultSet.getInt("icon"),
                resultSet.getInt("colour"),
                resultSet.getString("layout"),
                resultSet.getString("images"),
                resultSet.getString("texts"),
                resultSet.getString("seasonal_start"),
                resultSet.getInt("seasonal_length")
        );
    }

    public static int saveAdminPage(CataloguePageAdmin page) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();

            if (page.id() > 0) {
                preparedStatement = Storage.getStorage().prepare("UPDATE catalogue_pages SET old_id = ?, parent_id = ?, order_id = ?, min_role = ?, is_navigatable = ?, is_club_only = ?, name = ?, icon = ?, colour = ?, layout = ?, images = ?, texts = ?, seasonal_start = ?, seasonal_length = ? WHERE id = ?", sqlConnection);
            } else {
                preparedStatement = Storage.getStorage().prepare("INSERT INTO catalogue_pages (old_id, parent_id, order_id, min_role, is_navigatable, is_club_only, name, icon, colour, layout, images, texts, seasonal_start, seasonal_length) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", sqlConnection);
            }

            preparedStatement.setInt(1, page.oldId());
            preparedStatement.setInt(2, page.parentId());
            preparedStatement.setInt(3, page.orderId());
            preparedStatement.setInt(4, page.minRole());
            preparedStatement.setBoolean(5, page.navigatable());
            preparedStatement.setBoolean(6, page.clubOnly());
            preparedStatement.setString(7, page.name());
            preparedStatement.setInt(8, page.icon());
            preparedStatement.setInt(9, page.colour());
            preparedStatement.setString(10, page.layout());
            preparedStatement.setString(11, page.images());
            preparedStatement.setString(12, page.texts());
            preparedStatement.setString(13, blankToNull(page.seasonalStart()));
            preparedStatement.setInt(14, page.seasonalLength());

            if (page.id() > 0) {
                preparedStatement.setInt(15, page.id());
            }

            preparedStatement.executeUpdate();

            if (page.id() > 0) {
                return page.id();
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

        return page.id();
    }

    public static void deleteAdminPage(int id) {
        executeUpdate("DELETE FROM catalogue_pages WHERE id = ?", id);
    }

    public static List<CatalogueItemAdmin> getAdminItems() {
        List<CatalogueItemAdmin> items = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM catalogue_items ORDER BY page_id ASC, order_id ASC, id ASC", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                items.add(readAdminItem(resultSet));
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

    public static CatalogueItemAdmin getAdminItem(int id) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM catalogue_items WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return readAdminItem(resultSet);
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

    private static CatalogueItemAdmin readAdminItem(ResultSet resultSet) throws Exception {
        return new CatalogueItemAdmin(
                resultSet.getInt("id"),
                resultSet.getString("sale_code"),
                resultSet.getString("page_id"),
                resultSet.getInt("order_id"),
                resultSet.getInt("price_coins"),
                resultSet.getInt("price_pixels"),
                resultSet.getInt("seasonal_coins"),
                resultSet.getInt("seasonal_pixels"),
                resultSet.getBoolean("hidden"),
                resultSet.getInt("amount"),
                resultSet.getInt("definition_id"),
                resultSet.getString("item_specialspriteid"),
                resultSet.getBoolean("is_package"),
                resultSet.getString("active_at")
        );
    }

    public static int saveAdminItem(CatalogueItemAdmin item) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();

            if (item.id() > 0) {
                preparedStatement = Storage.getStorage().prepare("UPDATE catalogue_items SET sale_code = ?, page_id = ?, order_id = ?, price_coins = ?, price_pixels = ?, seasonal_coins = ?, seasonal_pixels = ?, hidden = ?, amount = ?, definition_id = ?, item_specialspriteid = ?, is_package = ?, active_at = ? WHERE id = ?", sqlConnection);
            } else {
                preparedStatement = Storage.getStorage().prepare("INSERT INTO catalogue_items (sale_code, page_id, order_id, price_coins, price_pixels, seasonal_coins, seasonal_pixels, hidden, amount, definition_id, item_specialspriteid, is_package, active_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", sqlConnection);
            }

            preparedStatement.setString(1, item.saleCode());
            preparedStatement.setString(2, item.pageId());
            preparedStatement.setInt(3, item.orderId());
            preparedStatement.setInt(4, item.priceCoins());
            preparedStatement.setInt(5, item.pricePixels());
            preparedStatement.setInt(6, item.seasonalCoins());
            preparedStatement.setInt(7, item.seasonalPixels());
            preparedStatement.setBoolean(8, item.hidden());
            preparedStatement.setInt(9, item.amount());
            preparedStatement.setInt(10, item.definitionId());
            preparedStatement.setString(11, item.itemSpecialSpriteId());
            preparedStatement.setBoolean(12, item.packageItem());
            preparedStatement.setString(13, blankToNull(item.activeAt()));

            if (item.id() > 0) {
                preparedStatement.setInt(14, item.id());
            }

            preparedStatement.executeUpdate();

            if (item.id() > 0) {
                return item.id();
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

        return item.id();
    }

    public static void deleteAdminItem(int id) {
        executeUpdate("DELETE FROM catalogue_items WHERE id = ?", id);
    }

    public static List<CataloguePackageAdmin> getAdminPackages() {
        List<CataloguePackageAdmin> packages = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM catalogue_packages ORDER BY salecode ASC, id ASC", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                packages.add(new CataloguePackageAdmin(resultSet.getInt("id"), resultSet.getString("salecode"), resultSet.getInt("definition_id"), resultSet.getString("special_sprite_id"), resultSet.getInt("amount")));
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

    public static CataloguePackageAdmin getAdminPackage(int id) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM catalogue_packages WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new CataloguePackageAdmin(resultSet.getInt("id"), resultSet.getString("salecode"), resultSet.getInt("definition_id"), resultSet.getString("special_sprite_id"), resultSet.getInt("amount"));
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

    public static int saveAdminPackage(CataloguePackageAdmin cataloguePackage) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();

            if (cataloguePackage.id() > 0) {
                preparedStatement = Storage.getStorage().prepare("UPDATE catalogue_packages SET salecode = ?, definition_id = ?, special_sprite_id = ?, amount = ? WHERE id = ?", sqlConnection);
            } else {
                preparedStatement = Storage.getStorage().prepare("INSERT INTO catalogue_packages (salecode, definition_id, special_sprite_id, amount) VALUES (?, ?, ?, ?)", sqlConnection);
            }

            preparedStatement.setString(1, cataloguePackage.saleCode());
            preparedStatement.setInt(2, cataloguePackage.definitionId());
            preparedStatement.setString(3, cataloguePackage.specialSpriteId());
            preparedStatement.setInt(4, cataloguePackage.amount());

            if (cataloguePackage.id() > 0) {
                preparedStatement.setInt(5, cataloguePackage.id());
            }

            preparedStatement.executeUpdate();

            if (cataloguePackage.id() > 0) {
                return cataloguePackage.id();
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

        return cataloguePackage.id();
    }

    public static void deleteAdminPackage(int id) {
        executeUpdate("DELETE FROM catalogue_packages WHERE id = ?", id);
    }

    public static List<CatalogueSaleBadgeAdmin> getAdminSaleBadges() {
        List<CatalogueSaleBadgeAdmin> saleBadges = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM catalogue_sale_badges ORDER BY sale_code ASC, badge_code ASC", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                saleBadges.add(new CatalogueSaleBadgeAdmin(resultSet.getString("sale_code"), resultSet.getString("badge_code")));
            }
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return saleBadges;
    }

    public static void createAdminSaleBadge(String saleCode, String badgeCode) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("INSERT INTO catalogue_sale_badges (sale_code, badge_code) VALUES (?, ?)", sqlConnection);
            preparedStatement.setString(1, saleCode);
            preparedStatement.setString(2, badgeCode);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void deleteAdminSaleBadge(String saleCode, String badgeCode) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("DELETE FROM catalogue_sale_badges WHERE sale_code = ? AND badge_code = ?", sqlConnection);
            preparedStatement.setString(1, saleCode);
            preparedStatement.setString(2, badgeCode);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static List<CatalogueCollectableAdmin> getAdminCollectables() {
        List<CatalogueCollectableAdmin> collectables = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM catalogue_collectables ORDER BY store_page ASC", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                collectables.add(readAdminCollectable(resultSet));
            }
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return collectables;
    }

    public static CatalogueCollectableAdmin getAdminCollectable(int id) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT * FROM catalogue_collectables WHERE store_page = ?", sqlConnection);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return readAdminCollectable(resultSet);
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

    private static CatalogueCollectableAdmin readAdminCollectable(ResultSet resultSet) throws Exception {
        return new CatalogueCollectableAdmin(
                resultSet.getInt("store_page"),
                resultSet.getInt("store_page"),
                resultSet.getInt("admin_page"),
                resultSet.getLong("expiry"),
                resultSet.getLong("lifetime"),
                resultSet.getInt("current_position"),
                resultSet.getString("class_names")
        );
    }

    public static int saveAdminCollectable(CatalogueCollectableAdmin collectable) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();

            if (collectable.id() > 0) {
                preparedStatement = Storage.getStorage().prepare("UPDATE catalogue_collectables SET store_page = ?, admin_page = ?, expiry = ?, lifetime = ?, current_position = ?, class_names = ? WHERE store_page = ?", sqlConnection);
            } else {
                preparedStatement = Storage.getStorage().prepare("INSERT INTO catalogue_collectables (store_page, admin_page, expiry, lifetime, current_position, class_names) VALUES (?, ?, ?, ?, ?, ?)", sqlConnection);
            }

            preparedStatement.setInt(1, collectable.storePageId());
            preparedStatement.setInt(2, collectable.adminPageId());
            preparedStatement.setLong(3, collectable.expiry());
            preparedStatement.setLong(4, collectable.lifetime());
            preparedStatement.setInt(5, collectable.currentPosition());
            preparedStatement.setString(6, collectable.classNames());

            if (collectable.id() > 0) {
                preparedStatement.setInt(7, collectable.id());
            }

            preparedStatement.executeUpdate();

            if (collectable.id() > 0) {
                return collectable.id();
            }

            return collectable.storePageId();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(generatedKeys);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return collectable.id();
    }

    public static void deleteAdminCollectable(int id) {
        executeUpdate("DELETE FROM catalogue_collectables WHERE store_page = ?", id);
    }

    private static String blankToNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value;
    }

    private static void executeUpdate(String query, int id) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare(query, sqlConnection);
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
