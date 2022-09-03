package org.alexdev.havana.game.catalogue;

import org.alexdev.havana.game.item.base.ItemDefinition;
import org.alexdev.havana.game.item.ItemManager;

public class CataloguePackage {
    private String saleCode;
    private int definitionId;
    private String  specialSpriteId;
    private int amount;

    public CataloguePackage(String saleCode, int definitionId, String specialSpriteId, int amount) {
        this.saleCode = saleCode;
        this.definitionId = definitionId;
        this.specialSpriteId = specialSpriteId;
        this.amount = amount;
    }

    public String getSaleCode() {
        return saleCode;
    }

    public int getDefinitionId() {
        return definitionId;
    }

    public ItemDefinition getDefinition() {
        return ItemManager.getInstance().getDefinition(this.definitionId);
    }

    public String  getSpecialSpriteId() {
        return specialSpriteId;
    }

    public int getAmount() {
        return amount;
    }
}
