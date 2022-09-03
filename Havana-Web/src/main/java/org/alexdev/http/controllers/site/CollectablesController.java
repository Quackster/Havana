package org.alexdev.http.controllers.site;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.havana.dao.mysql.CurrencyDao;
import org.alexdev.havana.dao.mysql.TransactionDao;
import org.alexdev.havana.game.catalogue.CatalogueManager;
import org.alexdev.havana.game.catalogue.collectables.CollectableData;
import org.alexdev.havana.game.catalogue.collectables.CollectablesManager;
import org.alexdev.havana.game.item.ItemManager;
import org.alexdev.havana.game.item.base.ItemDefinition;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.messages.incoming.catalogue.GRPC;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.game.collectables.CollectableEntry;
import org.alexdev.http.util.RconUtil;
import org.alexdev.http.util.XSSUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CollectablesController {
    public static void collectables(WebConnection webConnection) {
        XSSUtil.clear(webConnection);

        int pageId = GameConfiguration.getInstance().getInteger("collectables.page");
        CollectableData collectablesData = CollectablesManager.getInstance().getCollectableDataByPage(pageId);

        webConnection.session().set("page", "credits");

        var template = webConnection.template("collectables");
        template.set("hasCollectable", (collectablesData != null));

        List<CollectableEntry> entries = new ArrayList<>();

        if (collectablesData != null) {
            collectablesData.checkCycle();

            template.set("collectableSprite", collectablesData.getActiveItem().getDefinition().getSprite());
            template.set("collectableName", collectablesData.getActiveItem().getDefinition().getName());
            template.set("collectableDescription", collectablesData.getActiveItem().getDefinition().getDescription());
            template.set("expireSeconds", collectablesData.getExpiry() - DateUtil.getCurrentTimeSeconds());


            for (String sprite : collectablesData.getSprites()) {
                ItemDefinition definition = ItemManager.getInstance().getDefinitionBySprite(sprite);
                entries.add(new CollectableEntry(sprite, definition.getName(), definition.getDescription()));
            }
        }

        template.set("collectablesShowroom", entries);
        template.render();
    }

    public static void confirm(WebConnection webConnection) {
        if (!webConnection.session().getBoolean("authenticated")) {
            webConnection.redirect("/");
            return;
        }

        int pageId = GameConfiguration.getInstance().getInteger("collectables.page");
        CollectableData collectablesData = CollectablesManager.getInstance().getCollectableDataByPage(pageId);

        if (collectablesData == null) {
            webConnection.redirect("/");
            return;
        }

        var template = webConnection.template("habblet/collectiblesConfirm");

        template.set("collectableName", collectablesData.getActiveItem().getDefinition().getName());
        template.set("collectableCost", collectablesData.getActiveItem().getPriceCoins());

        template.render();
    }

    public static void purchase(WebConnection webConnection) {
        if (!webConnection.session().getBoolean("authenticated")) {
            webConnection.redirect("/");
            return;
        }

        var template = webConnection.template("habblet/collectiblesPurchase");
        PlayerDetails playerDetails = (PlayerDetails) template.get("playerDetails");

        var pair = playerDetails.isBanned();

        if (pair != null) {
            webConnection.redirect("/account/banned");
            return;
        }

        int pageId = GameConfiguration.getInstance().getInteger("collectables.page");
        CollectableData collectablesData = CollectablesManager.getInstance().getCollectableDataByPage(pageId);

        if (collectablesData == null) {
            webConnection.redirect("/");
            return;
        }


        if (playerDetails.getCredits() >= collectablesData.getActiveItem().getPriceCoins() &&
            playerDetails.getPixels() >= collectablesData.getActiveItem().getPricePixels()) {

            if (collectablesData.getActiveItem().getPriceCoins() > 0) {
                CurrencyDao.decreaseCredits(playerDetails, collectablesData.getActiveItem().getPriceCoins());
            }

            if (collectablesData.getActiveItem().getPricePixels() > 0) {
                CurrencyDao.decreasePixels(playerDetails, collectablesData.getActiveItem().getPricePixels());
            }

            template.set("message", "You've successfully bought a " + collectablesData.getActiveItem().getDefinition().getName());


            try {
                var items = CatalogueManager.getInstance().purchase(playerDetails, collectablesData.getActiveItem(), "", "", DateUtil.getCurrentTimeSeconds());

                String transactionDscription = GRPC.getTransactionDescription(collectablesData.getActiveItem());

                if (transactionDscription != null) {
                    TransactionDao.createTransaction(playerDetails.getId(),
                            items.stream().map(e -> String.valueOf(e.getDatabaseId())).collect(Collectors.joining(",")),
                            collectablesData.getActiveItem().getId() + "",
                            collectablesData.getActiveItem().getAmount(),
                            "Collectable " + transactionDscription,
                            collectablesData.getActiveItem().getPriceCoins(), collectablesData.getActiveItem().getPricePixels(), true);
                }

            } catch (Exception ex) {
                template.set("message", "Purchasing the collectable failed due to system error");
            }

            RconUtil.sendCommand(RconHeader.REFRESH_HAND, new HashMap<>() {{
                put("userId", playerDetails.getId());
            }});

            RconUtil.sendCommand(RconHeader.REFRESH_CREDITS, new HashMap<>() {{
                put("userId", playerDetails.getId());
            }});
        } else {
            if (collectablesData.getActiveItem().getPricePixels() > playerDetails.getPixels()) {
                template.set("message", "Purchasing the collectable failed. You don't have enough pixels.");
            } else {
                template.set("message", "Purchasing the collectable failed. You don't have enough credits.");
            }
        }

        template.render();
    }
}
