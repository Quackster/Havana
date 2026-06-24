package org.alexdev.http.controllers.housekeeping;

import com.google.gson.reflect.TypeToken;
import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;
import org.alexdev.havana.dao.mysql.CatalogueDao;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.catalogue.CatalogueManager;
import org.alexdev.havana.game.catalogue.collectables.CollectablesManager;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.havana.util.StringUtil;
import org.alexdev.http.Routes;
import org.alexdev.http.game.housekeeping.HousekeepingManager;
import org.alexdev.http.util.RconUtil;
import org.alexdev.http.util.SessionUtil;

import java.util.HashMap;
import java.util.List;

public class HousekeepingCatalogueController {
    private static final String PERMISSION = "catalogue/manage";

    public static void pages(WebConnection client) {
        if (!ensurePermission(client)) {
            return;
        }

        Template tpl = catalogueTemplate(client, "housekeeping/catalogue_pages", "Catalogue Pages");
        tpl.set("pages", CatalogueDao.getAdminPages());
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void editPage(WebConnection client) {
        if (!ensurePermission(client)) {
            return;
        }

        int id = client.get().contains("id") ? client.get().getInt("id") : 0;
        CatalogueDao.CataloguePageAdmin page = id > 0 ? CatalogueDao.getAdminPage(id) : null;

        if (id > 0 && page == null) {
            setAlert(client, "danger", "Catalogue page does not exist");
            client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/catalogue/pages");
            return;
        }

        if (client.post().queries().size() > 0) {
            String name = client.post().getString("name").trim();
            String images = normaliseJsonList(client.post().getString("images"));
            String texts = normaliseJsonList(client.post().getString("texts"));

            if (name.isBlank()) {
                setAlert(client, "danger", "Page name cannot be blank");
            } else if (images == null || texts == null) {
                setAlert(client, "danger", "Images and texts must be valid JSON arrays");
            } else {
                int savedId = CatalogueDao.saveAdminPage(new CatalogueDao.CataloguePageAdmin(
                        id,
                        client.post().getInt("old_id"),
                        client.post().getInt("parent_id"),
                        client.post().getInt("order_id"),
                        client.post().getInt("min_role"),
                        client.post().contains("is_navigatable"),
                        client.post().contains("is_club_only"),
                        name,
                        client.post().getInt("icon"),
                        client.post().getInt("colour"),
                        client.post().getString("layout").trim(),
                        images,
                        texts,
                        client.post().getString("seasonal_start").trim(),
                        client.post().getInt("seasonal_length")
                ));

                refreshCatalogue();
                setAlert(client, "success", "Catalogue page saved successfully");
                client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/catalogue/pages/edit?id=" + savedId);
                return;
            }
        }

        Template tpl = catalogueTemplate(client, "housekeeping/catalogue_page_edit", id > 0 ? "Edit Catalogue Page" : "Create Catalogue Page");
        tpl.set("page", page);
        tpl.set("pages", CatalogueDao.getAdminPages());
        tpl.set("ranks", PlayerRank.values());
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void deletePage(WebConnection client) {
        if (!ensurePermission(client)) {
            return;
        }

        if (client.get().contains("id")) {
            CatalogueDao.deleteAdminPage(client.get().getInt("id"));
            refreshCatalogue();
            setAlert(client, "success", "Catalogue page deleted successfully");
        }

        client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/catalogue/pages");
    }

    public static void items(WebConnection client) {
        if (!ensurePermission(client)) {
            return;
        }

        Template tpl = catalogueTemplate(client, "housekeeping/catalogue_items", "Catalogue Items");
        tpl.set("items", CatalogueDao.getAdminItems());
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void editItem(WebConnection client) {
        if (!ensurePermission(client)) {
            return;
        }

        int id = client.get().contains("id") ? client.get().getInt("id") : 0;
        CatalogueDao.CatalogueItemAdmin item = id > 0 ? CatalogueDao.getAdminItem(id) : null;

        if (id > 0 && item == null) {
            setAlert(client, "danger", "Catalogue item does not exist");
            client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/catalogue/items");
            return;
        }

        if (client.post().queries().size() > 0) {
            String saleCode = client.post().getString("sale_code").trim();
            String pageId = client.post().getString("page_id").trim();

            if (saleCode.isBlank()) {
                setAlert(client, "danger", "Sale code cannot be blank");
            } else if (!pageId.matches("[0-9,]+")) {
                setAlert(client, "danger", "Page assignment must be a comma-separated list of page IDs");
            } else {
                int savedId = CatalogueDao.saveAdminItem(new CatalogueDao.CatalogueItemAdmin(
                        id,
                        saleCode,
                        pageId,
                        client.post().getInt("order_id"),
                        client.post().getInt("price_coins"),
                        client.post().getInt("price_pixels"),
                        client.post().getInt("seasonal_coins"),
                        client.post().getInt("seasonal_pixels"),
                        client.post().contains("hidden"),
                        client.post().getInt("amount"),
                        client.post().getInt("definition_id"),
                        client.post().getString("item_specialspriteid").trim(),
                        client.post().contains("is_package"),
                        client.post().getString("active_at").trim()
                ));

                refreshCatalogue();
                setAlert(client, "success", "Catalogue item saved successfully");
                client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/catalogue/items/edit?id=" + savedId);
                return;
            }
        }

        Template tpl = catalogueTemplate(client, "housekeeping/catalogue_item_edit", id > 0 ? "Edit Catalogue Item" : "Create Catalogue Item");
        tpl.set("item", item);
        tpl.set("pages", CatalogueDao.getAdminPages());
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void deleteItem(WebConnection client) {
        if (!ensurePermission(client)) {
            return;
        }

        if (client.get().contains("id")) {
            CatalogueDao.deleteAdminItem(client.get().getInt("id"));
            refreshCatalogue();
            setAlert(client, "success", "Catalogue item deleted successfully");
        }

        client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/catalogue/items");
    }

    public static void packages(WebConnection client) {
        if (!ensurePermission(client)) {
            return;
        }

        Template tpl = catalogueTemplate(client, "housekeeping/catalogue_packages", "Catalogue Packages");
        tpl.set("packages", CatalogueDao.getAdminPackages());
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void editPackage(WebConnection client) {
        if (!ensurePermission(client)) {
            return;
        }

        int id = client.get().contains("id") ? client.get().getInt("id") : 0;
        CatalogueDao.CataloguePackageAdmin cataloguePackage = id > 0 ? CatalogueDao.getAdminPackage(id) : null;

        if (id > 0 && cataloguePackage == null) {
            setAlert(client, "danger", "Catalogue package row does not exist");
            client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/catalogue/packages");
            return;
        }

        if (client.post().queries().size() > 0) {
            String saleCode = client.post().getString("salecode").trim();

            if (saleCode.isBlank()) {
                setAlert(client, "danger", "Sale code cannot be blank");
            } else {
                int savedId = CatalogueDao.saveAdminPackage(new CatalogueDao.CataloguePackageAdmin(
                        id,
                        saleCode,
                        client.post().getInt("definition_id"),
                        client.post().getString("special_sprite_id").trim(),
                        client.post().getInt("amount")
                ));

                refreshCatalogue();
                setAlert(client, "success", "Catalogue package row saved successfully");
                client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/catalogue/packages/edit?id=" + savedId);
                return;
            }
        }

        Template tpl = catalogueTemplate(client, "housekeeping/catalogue_package_edit", id > 0 ? "Edit Catalogue Package Row" : "Create Catalogue Package Row");
        tpl.set("cataloguePackage", cataloguePackage);
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void deletePackage(WebConnection client) {
        if (!ensurePermission(client)) {
            return;
        }

        if (client.get().contains("id")) {
            CatalogueDao.deleteAdminPackage(client.get().getInt("id"));
            refreshCatalogue();
            setAlert(client, "success", "Catalogue package row deleted successfully");
        }

        client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/catalogue/packages");
    }

    public static void saleBadges(WebConnection client) {
        if (!ensurePermission(client)) {
            return;
        }

        if (client.post().queries().size() > 0) {
            String saleCode = client.post().getString("sale_code").trim();
            String badgeCode = client.post().getString("badge_code").trim();

            if (saleCode.isBlank() || badgeCode.isBlank()) {
                setAlert(client, "danger", "Sale code and badge code are required");
            } else {
                CatalogueDao.createAdminSaleBadge(saleCode, badgeCode);
                refreshCatalogue();
                setAlert(client, "success", "Catalogue badge reward saved successfully");
                client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/catalogue/sale_badges");
                return;
            }
        }

        Template tpl = catalogueTemplate(client, "housekeeping/catalogue_sale_badges", "Catalogue Sale Badges");
        tpl.set("saleBadges", CatalogueDao.getAdminSaleBadges());
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void deleteSaleBadge(WebConnection client) {
        if (!ensurePermission(client)) {
            return;
        }

        if (client.get().contains("sale_code") && client.get().contains("badge_code")) {
            CatalogueDao.deleteAdminSaleBadge(client.get().getString("sale_code"), client.get().getString("badge_code"));
            refreshCatalogue();
            setAlert(client, "success", "Catalogue badge reward deleted successfully");
        }

        client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/catalogue/sale_badges");
    }

    public static void collectables(WebConnection client) {
        if (!ensurePermission(client)) {
            return;
        }

        Template tpl = catalogueTemplate(client, "housekeeping/catalogue_collectables", "Catalogue Collectables");
        tpl.set("collectables", CatalogueDao.getAdminCollectables());
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void editCollectable(WebConnection client) {
        if (!ensurePermission(client)) {
            return;
        }

        int id = client.get().contains("id") ? client.get().getInt("id") : 0;
        CatalogueDao.CatalogueCollectableAdmin collectable = id > 0 ? CatalogueDao.getAdminCollectable(id) : null;

        if (id > 0 && collectable == null) {
            setAlert(client, "danger", "Collectable cycle does not exist");
            client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/catalogue/collectables");
            return;
        }

        if (client.post().queries().size() > 0) {
            String classNames = client.post().getString("class_names").trim();

            if (classNames.isBlank()) {
                setAlert(client, "danger", "Class names cannot be blank");
            } else {
                int savedId = CatalogueDao.saveAdminCollectable(new CatalogueDao.CatalogueCollectableAdmin(
                        id,
                        client.post().getInt("store_page"),
                        client.post().getInt("admin_page"),
                        client.post().getLong("expiry"),
                        client.post().getLong("lifetime"),
                        client.post().getInt("current_position"),
                        classNames
                ));

                refreshCatalogue();
                setAlert(client, "success", "Collectable cycle saved successfully");
                client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/catalogue/collectables/edit?id=" + savedId);
                return;
            }
        }

        Template tpl = catalogueTemplate(client, "housekeeping/catalogue_collectable_edit", id > 0 ? "Edit Catalogue Collectable" : "Create Catalogue Collectable");
        tpl.set("collectable", collectable);
        tpl.render();
        client.session().delete("alertMessage");
    }

    public static void deleteCollectable(WebConnection client) {
        if (!ensurePermission(client)) {
            return;
        }

        if (client.get().contains("id")) {
            CatalogueDao.deleteAdminCollectable(client.get().getInt("id"));
            refreshCatalogue();
            setAlert(client, "success", "Collectable cycle deleted successfully");
        }

        client.redirect("/" + Routes.HOUSEKEEPING_PATH + "/catalogue/collectables");
    }

    private static Template catalogueTemplate(WebConnection client, String template, String pageName) {
        Template tpl = client.template(template);
        tpl.set("housekeepingManager", HousekeepingManager.getInstance());
        tpl.set("pageName", pageName);
        return tpl;
    }

    private static boolean ensurePermission(WebConnection client) {
        if (!client.session().getBoolean(SessionUtil.LOGGED_IN_HOUSKEEPING)) {
            client.redirect("/" + Routes.HOUSEKEEPING_PATH);
            return false;
        }

        PlayerDetails playerDetails = PlayerDao.getDetails(client.session().getInt("user.id"));

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), PERMISSION)) {
            client.redirect("/" + Routes.HOUSEKEEPING_PATH);
            return false;
        }

        return true;
    }

    private static void refreshCatalogue() {
        CatalogueManager.reset();
        CollectablesManager.reset();
        RconUtil.sendCommand(RconHeader.REFRESH_CATALOGUE, new HashMap<>());
    }

    private static void setAlert(WebConnection client, String colour, String message) {
        client.session().set("alertColour", colour);
        client.session().set("alertMessage", message);
    }

    private static String normaliseJsonList(String value) {
        try {
            List<String> list = StringUtil.GSON.fromJson(value, new TypeToken<List<String>>(){}.getType());

            if (list == null) {
                return null;
            }

            return StringUtil.GSON.toJson(list);
        } catch (Exception ex) {
            return null;
        }
    }
}
