package org.alexdev.havana.game.item.interactors.enums;

public enum TotemEffect {
    NONE(-1, -1, -1, 0),
    FIRE(1, 12, 8, 25),
    WAND(2, 9, 8, 26),
    RAIN(0, 10, 4, 24),
    LEVITATION(2, 5, 0, 23);

    int planet;
    int head;
    int legs;
    int effectId;

    TotemEffect(int planet, int levitation, int legs, int effectId) {
        this.planet = planet;
        this.head = levitation;
        this.legs = legs;
        this.effectId = effectId;
    }

    public int getPlanet() {
        return planet;
    }

    public int getHead() {
        return head;
    }

    public int getLegs() {
        return legs;
    }

    public int getEffectId() {
        return effectId;
    }
}
