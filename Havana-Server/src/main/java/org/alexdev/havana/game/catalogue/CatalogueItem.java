package org.alexdev.havana.game.catalogue;

import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.item.base.ItemDefinition;
import org.alexdev.havana.game.item.ItemManager;
import org.alexdev.havana.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class CatalogueItem {
    private int id;
    private String saleCode;
    private int[] pages;
    private int orderId;
    private int priceCoins;
    private int pricePixels;
    private int seasonalCoins;
    private int seasonalPixels;
    private int amount;
    private boolean hidden;
    private ItemDefinition definition;
    private String itemSpecialId;
    private boolean isPackage;
    private List<CataloguePackage> packages;
    private int definitionId;
    private String activeAt;

    public CatalogueItem(int id, String saleCode, String pageId, int orderId, int price, int pricePixels, int seasonalCoins, int seasonalPixels, int amount, boolean hidden, int definitionId, String itemSpecialId, boolean isPackage, String activeAt) {
        int[] pages = new int[pageId.split(",").length];

        int i = 0;
        if (pageId.length() > 0) {
            for (String data : pageId.split(",")) {
                pages[i++] = Integer.parseInt(data);
            }
        }

        this.setPageData(id, saleCode, pages, orderId, price, pricePixels, seasonalCoins, seasonalPixels, amount, hidden, definitionId, itemSpecialId, isPackage, activeAt);
    }


    public CatalogueItem(int id, String saleCode, int[] pages, int orderId, int price, int pricePixels, int seasonalCoins, int seasonalPixels, int amount, boolean hidden, int definitionId, String itemSpecialId, boolean isPackage, String activeAt) {
        this.setPageData(id, saleCode, pages, orderId, price, pricePixels, seasonalCoins, seasonalPixels, amount, hidden, definitionId, itemSpecialId, isPackage, activeAt);
    }

    private void setPageData(int id, String saleCode, int[] pages, int orderId, int price, int pricePixels, int seasonalCoins, int seasonalPixels, int amount, boolean hidden, int definitionId, String itemSpecialId, boolean isPackage, String activeAt) {
        this.id = id;
        this.saleCode = saleCode;
        this.orderId = orderId;
        this.priceCoins = price;
        this.pricePixels = pricePixels;
        this.seasonalCoins = seasonalCoins;
        this.seasonalPixels = seasonalPixels;
        this.amount = amount;
        this.hidden = hidden;
        this.definitionId = definitionId;
        this.definition = ItemManager.getInstance().getDefinition(definitionId);
        this.itemSpecialId = itemSpecialId;
        this.isPackage = isPackage;
        this.packages = new ArrayList<>();
        this.pages = pages;
        this.activeAt = activeAt;

        if (this.definition == null && !this.isPackage) {
            System.out.println("Item " + this.id + " (" + this.saleCode + ") has an invalid definition id: " + definitionId);
        }
    }

    public int getId() {
        return id;
    }

    public String getType() {
        if (this.isPackage) {
            return "d";
        } else {
            if (this.definition.hasBehaviour(ItemBehaviour.WALL_ITEM)) {
                return "i";
            } else if (this.definition.hasBehaviour(ItemBehaviour.EFFECT)) {
                return "e";
            } else {
                return "s";
            }
        }
    }

    public String getSaleCode() {
        return saleCode;
    }

    public int[] getPages() {
        return pages;
    }

    public boolean hasPage(int pageId) {
        for (int page : this.pages) {
            if (page == pageId) {
                return true;
            }
        }

        return false;
    }

    public int getOrderId() {
        return orderId;
    }

    public ItemDefinition getDefinition() {
        return definition;
    }

    public int getPriceCoins() {
        return priceCoins;
    }

    public int getPricePixels() {
        return pricePixels;
    }

    public int getAmount() {
        return amount;
    }

    public void setPriceCoins(int priceCoins) {
        this.priceCoins = priceCoins;
    }

    public void setPricePixels(int pricePixels) {
        this.pricePixels = pricePixels;
    }

    public int getSeasonalCoins() {
        return seasonalCoins;
    }

    public void setSeasonalCoins(int seasonalCoins) {
        this.seasonalCoins = seasonalCoins;
    }

    public int getSeasonalPixels() {
        return seasonalPixels;
    }

    public void setSeasonalPixels(int seasonalPixels) {
        this.seasonalPixels = seasonalPixels;
    }

    public String getItemSpecialId() {
        return itemSpecialId;
    }

    public boolean isPackage() {
        return isPackage;
    }

    public List<CataloguePackage> getPackages() {
        if (this.isPackage) {
            return packages;
        }

        List<CataloguePackage> items = new ArrayList<>();
        items.add(new CataloguePackage(this.saleCode, this.definition.getId(), this.itemSpecialId, this.amount));

        return items;

    }

    public String getActiveAt() {
        return activeAt;
    }

    public boolean isActive() {
        if (activeAt == null) {
            return false;
        }

        return this.activeAt.equalsIgnoreCase(DateUtil.getDate(DateUtil.getCurrentTimeSeconds(), "MM-dd"));
    }

    /**
     * Copy the catalogue item instance so we can set prices that won't affect the main instance.
     *
     * @return the new catalogue item instance
     */
    public CatalogueItem copy() {
        return new CatalogueItem(this.id, this.saleCode, this.pages, this.orderId, this.priceCoins, this.pricePixels, this.seasonalCoins, this.seasonalPixels, this.amount, this.hidden, this.definition.getId(), this.itemSpecialId, this.isPackage, this.activeAt);
    }

    public boolean isHidden() {
        return hidden;
    }

    public int getDefinitionId() {
        return definitionId;
    }
}
