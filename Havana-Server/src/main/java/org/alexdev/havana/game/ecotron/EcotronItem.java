package org.alexdev.havana.game.ecotron;

public class EcotronItem {
    private String spriteName;
    private int orderId;
    private int chance;

    public EcotronItem(String spriteName, int orderId, int chance) {
        this.spriteName = spriteName;
        this.orderId = orderId;
        this.chance = chance;
    }

    public String getSpriteName() {
        return spriteName;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getChance() {
        return chance;
    }
}
