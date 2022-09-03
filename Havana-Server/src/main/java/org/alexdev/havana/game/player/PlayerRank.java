package org.alexdev.havana.game.player;

public enum PlayerRank {
    RANKLESS(0),
    NORMAL(1),
    GUIDE(2),
    HOBBA(3),
    SUPERHOBBA(4),
    MODERATOR(5),
    COMMUNITY_MANAGER(6),
    HOTEL_MANAGER(7),
    ADMINISTRATOR(8);

    private final int rankId;

    PlayerRank(int rankId) {
        this.rankId = rankId;
    }

    public String getName() {
        return this.name();
    }

    public int getRankId() {
        return this.rankId;
    }

    public static PlayerRank getRankForId(int rankId) {
        for (PlayerRank rank : PlayerRank.values()) {
            if (rank.getRankId() == rankId) {
                return rank;
            }
        }

        return null;
    }
}
