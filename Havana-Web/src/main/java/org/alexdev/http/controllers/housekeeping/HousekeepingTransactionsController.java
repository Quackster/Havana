package org.alexdev.http.controllers.housekeeping;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;
import org.alexdev.havana.dao.mysql.TransactionDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.http.Routes;
import org.alexdev.http.game.housekeeping.HousekeepingManager;
import org.alexdev.http.util.SessionUtil;
import org.apache.commons.lang3.StringUtils;

public class HousekeepingTransactionsController {

    /**
     * Handle the /housekeeping/users/search URI request
     *
     * @param client the connection
     */
    public static void search(WebConnection client) {
        if (!client.session().getBoolean(SessionUtil.LOGGED_IN_HOUSKEEPING)) {
            client.redirect("/" + Routes.HOUSEKEEPING_PATH);
            return;
        }

        Template tpl = client.template("housekeeping/transaction_lookup");
        tpl.set("housekeepingManager", HousekeepingManager.getInstance());

        PlayerDetails playerDetails = (PlayerDetails) tpl.get("playerDetails");

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "transaction/lookup")) {
            client.redirect("/" + Routes.HOUSEKEEPING_PATH);
            return;
        }

        try {
            if (client.post().getValues().size() > 0) {
                var transactions = TransactionDao.getTransactionsPastMonth(client.post().getString("searchQuery"), true);
                tpl.set("transactions", transactions);
            }

            if (client.get().getValues().size() > 0) {
                var transactions = TransactionDao.getTransactionsPastMonth(client.get().getString("searchQuery"), true);
                tpl.set("transactions", transactions);
            }
        } catch (Exception ex) {

        }

        tpl.set("pageName", "Transaction Lookup");
        tpl.render();

        // Delete alert after it's been rendered
        client.session().delete("alertMessage");
    }

    public static void item_lookup(WebConnection client) {
        if (!client.session().getBoolean(SessionUtil.LOGGED_IN_HOUSKEEPING)) {
            client.redirect("/" + Routes.HOUSEKEEPING_PATH);
            return;
        }

        Template tpl = client.template("housekeeping/transaction_item_lookup");
        tpl.set("housekeepingManager", HousekeepingManager.getInstance());

        PlayerDetails playerDetails = (PlayerDetails) tpl.get("playerDetails");

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "transaction/lookup")) {
            client.redirect("/" + Routes.HOUSEKEEPING_PATH);
            return;
        }

            var transactions = TransactionDao.getTransactionByItem(StringUtils.isNumeric(client.get().getString("id")) ? client.get().getInt("id") : 0);
            tpl.set("transactions", transactions);

        tpl.set("pageName", "Transaction Lookup");
        tpl.render();

        // Delete alert after it's been rendered
        client.session().delete("alertMessage");
    }
}
