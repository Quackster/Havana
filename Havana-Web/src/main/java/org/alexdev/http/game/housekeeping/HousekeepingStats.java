package org.alexdev.http.game.housekeeping;

public class HousekeepingStats {
    private final int userCount;
    private final int inventoryItemsCount;
    private final int roomItemCount;
    private final int groupCount;
    private final int petCount;
    private final int photoCount;

    public HousekeepingStats(int userCount, int inventoryItemsCount, int roomItemCount, int groupCount, int petCount, int photoCount) {
        this.userCount = userCount;
        this.inventoryItemsCount = inventoryItemsCount;
        this.roomItemCount = roomItemCount;
        this.groupCount = groupCount;
        this.petCount = petCount;
        this.photoCount = photoCount;
    }
}
