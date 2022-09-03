package org.alexdev.http.controllers.api;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.havana.game.item.Photo;
import org.alexdev.http.dao.CommunityDao;
import org.alexdev.http.util.HtmlUtil;
import org.alexdev.photorenderer.PhotoRenderer;
import org.alexdev.photorenderer.RenderOption;
import org.alexdev.photorenderer.palettes.GreyscalePalette;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PhotosController {
    public static void viewphotos(WebConnection webConnection) throws Exception {
        if (!webConnection.session().contains("user.id")) {
            webConnection.send("Please sign in");
            return;
        }

        PhotoRenderer photoViewer = null;
        var photos = CommunityDao.getPhotos(webConnection.session().getInt("user.id"));

        StringBuilder stringBuilder = new StringBuilder();

        var renderOption = RenderOption.GREYSCALE;

        if (webConnection.get().contains("greyscale")) {
            renderOption = RenderOption.GREYSCALE;
        }

        if (webConnection.get().contains("sepia")) {
            renderOption = RenderOption.SEPIA;
        }

        if (renderOption != RenderOption.GREYSCALE) {
            stringBuilder.append("<p>View images as greyscale? <a href=\"?greyscale\"\">View Greyscale</a></p>");
        } else {
            stringBuilder.append("<p>View images as original Sepia? <a href=\"?sepia\"\">View as Sepia</a></p>");
        }

        photoViewer = new PhotoRenderer(GreyscalePalette.getPalette(), renderOption);

        //int i = 1;
        for (Photo photo : photos) {
            var src = photoViewer.createImage(photo.getData());
            stringBuilder.append("<img src=\"data:image/png;base64," + HtmlUtil.encodeToString(src, "PNG") + "\"> ");

            /*if (i % 6 == 0) {
                stringBuilder.append("<br>");
            }

            i++;*/
        }

        /*i = 1;
        for (Photo photo : photos) {
            PhotoRenderer photoViewer = new PhotoRenderer();
            var src = photoViewer.createImage(photo.getData(), photoViewer.getCachedPalette(), PhotoRenderOption.GREYSCALE);
            stringBuilder.append("<img src=\"data:image/png;base64," + encodeToString(src, "PNG") + "\"> ");

            if (i % 6 == 0) {
                stringBuilder.append("<br>");
            }

            i++;
        }*/

        stringBuilder.append("<p><i>Made by Alex</i></p>");
        webConnection.send(stringBuilder.toString());
    }


}
