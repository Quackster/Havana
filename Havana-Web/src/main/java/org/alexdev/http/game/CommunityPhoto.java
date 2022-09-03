package org.alexdev.http.game;

import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.Photo;
import org.alexdev.http.util.HtmlUtil;
import org.alexdev.photorenderer.PhotoRenderer;

public class CommunityPhoto {
    private final Photo photo;
    private final PhotoRenderer photoViewer;
    private final Item item;
    private final long id;

    public CommunityPhoto(Photo photo, PhotoRenderer photoViewer, Item item) {
        this.id = photo.getId();
        this.photo = photo;
        this.photoViewer = photoViewer;
        this.item = item;
    }

    public long getId() {
        return id;
    }

    public String getDate() {
        var customData = this.item.getCustomData();

        if (customData.contains("\r")) {
            return customData.substring(0, customData.indexOf("\r") - 3);
        }

        return null;
    }

    public String getDescription() {
        var customData = this.item.getCustomData();

        if (customData.contains("\r")) {
            return customData.substring(customData.indexOf("\r") + 1);
        }

        return null;
    }

    public Photo getPhoto() {
        return photo;
    }

    public String renderPhoto() {
        try {
            var src = this.photoViewer.createImage(photo.getData());
            return HtmlUtil.encodeToString(src, "PNG");
        } catch (Exception e) {

        }

        return null;
    }

    public PhotoRenderer getPhotoViewer() {
        return photoViewer;
    }
}
