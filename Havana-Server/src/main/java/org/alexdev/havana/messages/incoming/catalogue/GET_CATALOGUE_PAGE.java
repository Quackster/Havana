package org.alexdev.havana.messages.incoming.catalogue;

import org.alexdev.havana.game.catalogue.CatalogueItem;
import org.alexdev.havana.game.catalogue.CatalogueManager;
import org.alexdev.havana.game.catalogue.CataloguePage;
import org.alexdev.havana.game.catalogue.collectables.CollectablesManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.catalogue.CATALOGUE_PAGE;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.config.GameConfiguration;

import java.util.List;

public class GET_CATALOGUE_PAGE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        CataloguePage cataloguePage = CatalogueManager.getInstance().getCataloguePage(reader.readInt());

        if (cataloguePage == null) {
            return;
        }

        if (!cataloguePage.isNavigatable()) {
            return;
        }

        if (cataloguePage.isClubOnly() && !player.getDetails().hasClubSubscription()) {
            return;
        }

        if (!cataloguePage.isValidSeasonal()) {
            return;
        }

        if (player.getDetails().getRank().getRankId() >= cataloguePage.getMinRole().getRankId()) {
            if (GameConfiguration.getInstance().getInteger("rare.cycle.page.id") == cataloguePage.getId()) {
                if (GameConfiguration.getInstance().getBoolean("rare.cycle.pixels.only")) {
                    cataloguePage.setLayout("pixelrent");
                } else {
                    cataloguePage.setLayout("cars");
                }
            }

            if (CollectablesManager.getInstance().getCollectableDataByPage(cataloguePage.getId()) != null) {
                var item = CollectablesManager.getInstance().getCollectableDataByPage(cataloguePage.getId()).getActiveItem();

                if (item != null) {
                    cataloguePage = cataloguePage.copy();

                    if (item.getPricePixels() > 0 && item.getPriceCoins() > 0) {
                        cataloguePage.setLayout("cars");
                    } else if (item.getPriceCoins() > 0 && item.getPricePixels() <= 0) {
                        cataloguePage.setLayout("default_3x3");
                    } else if (item.getPricePixels() > 0 && item.getPriceCoins() <= 0) {
                        cataloguePage.setLayout("pixelrent");
                    }
                }
            }

            List<CatalogueItem> catalogueItemList = CatalogueManager.getInstance().getCataloguePageItems(cataloguePage.getId(), false);
            player.send(new CATALOGUE_PAGE(cataloguePage, catalogueItemList));
        }
    }
}
