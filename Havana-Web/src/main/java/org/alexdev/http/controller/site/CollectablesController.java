package org.alexdev.http.controller.site;

import jakarta.servlet.http.HttpSession;
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
import org.alexdev.http.util.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CollectablesController {

    @GetMapping("/collectables")
    public String collectables(HttpSession session, Model model) {
        int pageId = GameConfiguration.getInstance().getInteger("collectables.page");
        CollectableData collectablesData = CollectablesManager.getInstance().getCollectableDataByPage(pageId);

        session.setAttribute("page", "credits");

        model.addAttribute("hasCollectable", (collectablesData != null));

        List<CollectableEntry> entries = new ArrayList<>();

        if (collectablesData != null) {
            collectablesData.checkCycle();

            model.addAttribute("collectableSprite", collectablesData.getActiveItem().getDefinition().getSprite());
            model.addAttribute("collectableName", collectablesData.getActiveItem().getDefinition().getName());
            model.addAttribute("collectableDescription", collectablesData.getActiveItem().getDefinition().getDescription());
            model.addAttribute("expireSeconds", collectablesData.getExpiry() - DateUtil.getCurrentTimeSeconds());

            for (String sprite : collectablesData.getSprites()) {
                ItemDefinition definition = ItemManager.getInstance().getDefinitionBySprite(sprite);
                entries.add(new CollectableEntry(sprite, definition.getName(), definition.getDescription()));
            }
        }

        model.addAttribute("collectablesShowroom", entries);
        return "collectables";
    }

    @GetMapping("/collectables/confirm")
    public String confirm(HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int pageId = GameConfiguration.getInstance().getInteger("collectables.page");
        CollectableData collectablesData = CollectablesManager.getInstance().getCollectableDataByPage(pageId);

        if (collectablesData == null) {
            return "redirect:/";
        }

        model.addAttribute("collectableName", collectablesData.getActiveItem().getDefinition().getName());
        model.addAttribute("collectableCost", collectablesData.getActiveItem().getPriceCoins());

        return "habblet/collectiblesConfirm";
    }

    @PostMapping("/collectables/purchase")
    public String purchase(HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        var pair = playerDetails.isBanned();

        if (pair != null) {
            return "redirect:/account/banned";
        }

        int pageId = GameConfiguration.getInstance().getInteger("collectables.page");
        CollectableData collectablesData = CollectablesManager.getInstance().getCollectableDataByPage(pageId);

        if (collectablesData == null) {
            return "redirect:/";
        }

        if (playerDetails.getCredits() >= collectablesData.getActiveItem().getPriceCoins() &&
                playerDetails.getPixels() >= collectablesData.getActiveItem().getPricePixels()) {

            if (collectablesData.getActiveItem().getPriceCoins() > 0) {
                CurrencyDao.decreaseCredits(playerDetails, collectablesData.getActiveItem().getPriceCoins());
            }

            if (collectablesData.getActiveItem().getPricePixels() > 0) {
                CurrencyDao.decreasePixels(playerDetails, collectablesData.getActiveItem().getPricePixels());
            }

            model.addAttribute("message", "You've successfully bought a " + collectablesData.getActiveItem().getDefinition().getName());

            try {
                var items = CatalogueManager.getInstance().purchase(playerDetails, collectablesData.getActiveItem(), "", "", DateUtil.getCurrentTimeSeconds());

                String transactionDescription = GRPC.getTransactionDescription(collectablesData.getActiveItem());

                if (transactionDescription != null) {
                    TransactionDao.createTransaction(playerDetails.getId(),
                            items.stream().map(e -> String.valueOf(e.getDatabaseId())).collect(Collectors.joining(",")),
                            collectablesData.getActiveItem().getId() + "",
                            collectablesData.getActiveItem().getAmount(),
                            "Collectable " + transactionDescription,
                            collectablesData.getActiveItem().getPriceCoins(), collectablesData.getActiveItem().getPricePixels(), true);
                }

            } catch (Exception ex) {
                model.addAttribute("message", "Purchasing the collectable failed due to system error");
            }

            RconUtil.sendCommand(RconHeader.REFRESH_HAND, new HashMap<>() {{
                put("userId", playerDetails.getId());
            }});

            RconUtil.sendCommand(RconHeader.REFRESH_CREDITS, new HashMap<>() {{
                put("userId", playerDetails.getId());
            }});
        } else {
            if (collectablesData.getActiveItem().getPricePixels() > playerDetails.getPixels()) {
                model.addAttribute("message", "Purchasing the collectable failed. You don't have enough pixels.");
            } else {
                model.addAttribute("message", "Purchasing the collectable failed. You don't have enough credits.");
            }
        }

        return "habblet/collectiblesPurchase";
    }
}
