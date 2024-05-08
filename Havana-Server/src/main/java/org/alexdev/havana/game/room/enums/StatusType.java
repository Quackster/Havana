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

    DEAD("ded"),
    JUMP("jmp"),
    PET_SLEEP("slp"),
    EAT("eat"),
    SMILE("sml"),
    PLAY("pla"),
    SIGN("sign");

    private String statusCode;

    StatusType(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
