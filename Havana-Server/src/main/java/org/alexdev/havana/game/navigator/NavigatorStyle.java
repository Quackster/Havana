package org.alexdev.havana.game.navigator;

public class NavigatorStyle {
    private int roomId;
    private String description;
    private String thumbnailUrl;
    private int thumbnailLayout;

    public NavigatorStyle(int roomId, String description, String thumbnailUrl, int thumbnailLayout) {
        this.roomId = roomId;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.thumbnailLayout = thumbnailLayout;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public int getThumbnailLayout() {
        return thumbnailLayout;
    }

    public String getDescription() {
        return description;
    }
}
