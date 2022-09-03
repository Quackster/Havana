package org.alexdev.photorenderer.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BorderEffect {
    private final int borderSize;
    private final Color color;

    public BorderEffect(int borderSize, Color color) {
        this.borderSize = borderSize;
        this.color = color;
    }

    public BufferedImage apply(BufferedImage bufferedImage) {
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setPaint(color);
        //Horizontal
        graphics2D.fillRect(0, 0, bufferedImage.getWidth(), borderSize);
        graphics2D.fillRect(0, bufferedImage.getHeight() - borderSize, bufferedImage.getWidth(), borderSize);
        //Vertical
        graphics2D.fillRect(0, 0, borderSize, bufferedImage.getHeight());
        graphics2D.fillRect(bufferedImage.getWidth() - borderSize, 0, borderSize, bufferedImage.getHeight());
        return bufferedImage;
    }
}
