package org.alexdev.photorenderer.palettes;

import java.awt.*;
import java.io.DataInputStream;
import java.io.FileInputStream;

public class PaletteUtils {
    public Color[] readPalette(String paletteFileName) throws Exception {
        var input = new DataInputStream(new FileInputStream(paletteFileName));
        new String(input.readNBytes(4));

        input.readInt();

        new String(input.readNBytes(4));
        new String(input.readNBytes(4));

        input.readInt();
        input.readShort();

        Color[] colors = new Color[input.readShort()];

        for (int i = 0; i < colors.length; i++) {
            int r = input.read();
            int g = input.read();
            int b = input.read();
            colors[i] = new Color(r, g, b);
            input.readByte();
            //System.out.println("new Color( " + r + ", " + g + ", " + b + "),");
        }

        return colors;
    }
}
