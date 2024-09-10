package org.alexdev.havana.messages.outgoing.catalogue;

import org.alexdev.havana.game.catalogue.CatalogueItem;
import org.alexdev.havana.game.catalogue.CataloguePackage;
import org.alexdev.havana.game.catalogue.CataloguePage;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.messages.types.PlayerMessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.ArrayList;
import java.util.List;

public class CATALOGUE_PAGE extends PlayerMessageComposer {
    private final CataloguePage page;
    private final List<CatalogueItem> catalogueItems;
    private final List<String> images;
    private final List<String> texts;

    public CATALOGUE_PAGE(CataloguePage cataloguePage, List<CatalogueItem> cataloguePageItems) {
        this.page = cataloguePage;
        this.catalogueItems = cataloguePageItems;
        this.images = cataloguePage.getImages();
        this.texts = cataloguePage.getTexts();
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.page.getId());
        response.writeString(this.page.getLayout());
        response.writeInt(this.images.size());

        for (String image : this.images) {
            response.writeString(image);
        }

        if (this.page.getLayout().equalsIgnoreCase("frontpage3") && this.getPlayer().getNetwork().isFlashConnection()) {
            List<String> texts = new ArrayList<>(this.texts);
            texts = texts.subList(0, texts.size() - 6);

            texts.add("1. Always ask permission from the bill payer first.\r\n" +
                    "2. Send HABBO in a UK SMS to 78881. You'll get an SMS back with a voucher code and will be charged Â£3 plus your standard UK SMS rate, normally 10p.\r\n" +
                    "3. Enter the code below to redeem 35 Credits.");
            texts.add("Redeem a Habbo Voucher code here:");
            texts.add("#FAF8CC");
            texts.add("#FAF8CC");


            response.writeInt(texts.size());

            for (String text : texts) {
                response.writeString(text);
            }
        } else {
            response.writeInt(this.texts.size());

            for (String text : this.texts) {
                response.writeString(text);
            }
        }

        response.writeInt(this.catalogueItems.size());

        for (CatalogueItem catalogueItem : this.catalogueItems) {
            response.writeInt(catalogueItem.getId());

            if (!catalogueItem.isPackage() &&
                    (catalogueItem.getDefinition().getSprite().equals("poster") ||
                            catalogueItem.getDefinition().getSprite().equals("landscape") ||
                            catalogueItem.getDefinition().getSprite().equals("wallpaper") ||
                            catalogueItem.getDefinition().getSprite().equals("floor"))) {
                response.writeString(catalogueItem.getDefinition().getSprite() + " " + catalogueItem.getItemSpecialId());
            } else {
                response.writeString(catalogueItem.getSaleCode());
            }

            response.writeInt(catalogueItem.getPriceCoins());
            response.writeInt(catalogueItem.getPricePixels());

            response.writeInt(catalogueItem.getPackages().size());

            for (CataloguePackage packageItem : catalogueItem.getPackages()) {
                if (packageItem.getDefinition().hasBehaviour(ItemBehaviour.EFFECT)) {
                    response.writeString("e");
                } else if (packageItem.getDefinition().hasBehaviour(ItemBehaviour.WALL_ITEM)) {
                    response.writeString("i");
                } else {
                    response.writeString("s");
                }

                if (packageItem.getDefinition().hasBehaviour(ItemBehaviour.EFFECT)) {
                    response.writeInt(Integer.parseInt(packageItem.getSpecialSpriteId()));
                } else {
                    response.writeInt(packageItem.getDefinition().getSpriteId());
                }

                response.writeString(packageItem.getSpecialSpriteId());
                response.writeInt(packageItem.getAmount());
                response.writeInt(packageItem.getDefinition().getRentalTimeAsMinutes());

            }
        }

    }
    
    @Override
    public short getHeader() {
        return 127; // "A"
    }
}
