package org.alexdev.photorenderer;

import org.alexdev.photorenderer.utils.BorderEffect;
import org.alexdev.photorenderer.utils.DataUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.IndexColorModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class PhotoRenderer {
    private Color[] paletteData;
    private RenderOption option;

    public PhotoRenderer(Color[] paletteData, RenderOption option) {
        this.paletteData = paletteData;
        this.option = option;
    }

    public BufferedImage createImage(byte[] photoData) throws Exception {
        int CAST_PROPERTIES_OFFSET = 28;

        var bais = new ByteArrayInputStream(photoData);
        var bigEndianStream = new DataInputStream(bais);
        bigEndianStream.skip(CAST_PROPERTIES_OFFSET);

        int totalWidth = bigEndianStream.readShort() & 0x7FFF;

        int top = bigEndianStream.readShort();
        int left = bigEndianStream.readShort();
        int bottom = bigEndianStream.readShort();
        int right = bigEndianStream.readShort();

        Rectangle rectangle = new Rectangle(left, top, right - left, bottom - top);

        bigEndianStream.read();
        bigEndianStream.skip(7);
        bigEndianStream.readShort();
        bigEndianStream.readShort();
        bigEndianStream.read();

        int bitDepth = DataUtils.readLittleEndianByte(bigEndianStream);

        if (bitDepth != 8)
            throw new Exception("illegal");

        int palette = bigEndianStream.readInt() - 1; //Make sure that this one equals -3 = Grayscale

        if (palette != -3)
            throw new Exception("illegal");

        bigEndianStream.readInt(); // No idea! Lmao
        bigEndianStream.skip(4); // Reversed, should equal BITD

        int length =  DataUtils.readLittleEndianInt(bigEndianStream);
        int position = 0;

        /*var bytes = new byte[bais.available()];
        bais.read(bytes);

        Files.write(Path.of("habbo_photo.txt"), bytes);*/

        var data = new int[totalWidth * rectangle.height];

        while (bigEndianStream.available() > 0) {
            int marker = bigEndianStream.read();

            if (marker >= 128) {
                int fill = bigEndianStream.read();

                for (int i = 0; i < 257 - marker; i++) {
                    data[position] = fill;
                    position++;
                }

            } else {
                int[] buffer = new int[marker + 1];

                for (int i = 0; i < buffer.length; i++) {
                    data[position] = bigEndianStream.read();
                    position++;
                }
            }
        }

        var image = new BufferedImage(rectangle.width, rectangle.height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < rectangle.height; y++) {
            int[] row = Arrays.copyOfRange(data, y * totalWidth, (y * totalWidth) + totalWidth);

            if (row.length > 0) {
                for (int x = 0; x < rectangle.width; x++) {
                    var rgb = this.paletteData[row[x]];

                    int r = rgb.getRed();
                    int g = rgb.getGreen();
                    int b = rgb.getBlue();

                    Color color = new Color(r, g, b);
                    image.setRGB(x, y, color.getRGB());
                }
            }
        }

        bigEndianStream.close();

        if (option == RenderOption.SEPIA) {
            int[] palettes = {
                    0xffb85e2f,
                    0xffc06533,
                    0xfff08b46,
                    0xff681f10,
                    0xff88381c,
                    0xffc86b36,
                    0xffffd169,
                    0xffe07e3f,
                    0xffffb159,
                    0xffffde6f,
                    0xff702513,
                    0xffffea75,
                    0xffffd269
            };

            IndexColorModel colorModel = new IndexColorModel(8,         // bits per pixel
                    palettes.length,         // size of color component array
                    palettes,   // color map
                    0,         // offset in the map
                    false,      // has alpha
                    0,         // the pixel value that should be transparent
                    DataBuffer.TYPE_BYTE);

            BufferedImage img = new BufferedImage(
                    image.getWidth(), image.getHeight(), // match source
                    BufferedImage.TYPE_BYTE_BINARY, // required to work
                    colorModel); // TYPE_BYTE_BINARY color model (i.e. palette)

            Graphics2D g2 = img.createGraphics();
            g2.drawImage(image, 0, 0, null);
            g2.dispose();

            // Sneaky stuff to re-add back black border, else just use "return img;" here
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(img, "PNG", bos);
            byte[] imageBytes = bos.toByteArray();

            var borderEffect = new BorderEffect(1, Color.black);
            return borderEffect.apply(ImageIO.read(new ByteArrayInputStream(imageBytes)));
        }

        return image;
    }
}
