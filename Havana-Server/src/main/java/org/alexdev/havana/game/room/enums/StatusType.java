package org.alexdev.havana.game.room.enums;

public enum StatusType {
    MOVE("mv"),
    SIT("sit"),
    LAY("lay"), 
    FLAT_CONTROL("flatctrl"),
    SWIM("swim"),
    TALK("talk"),

    CARRY_ITEM("cri"),
    CARRY_DRINK("carryd"),

    USE_ITEM("usei"),
    USE_DRINK("drink"),

    JOIN_GAME("joingame"),

    TRADE("trd"),
    DANCE("dance"),

    SIGN("sign"),
    DEAD("ded"),
    JUMP("jmp"),
    SLEEP("slp"),
    EAT("eat");

    private String statusCode;

    StatusType(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
