package org.alexdev.photorenderer.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DataUtils {
    public static short readLittleEndianShort(InputStream inputStream) throws IOException {
        ByteBuffer bb = ByteBuffer.wrap(readBytes(inputStream, 2));
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.getShort();
    }

    public static int readLittleEndianInt(InputStream inputStream) throws IOException {
        ByteBuffer bb = ByteBuffer.wrap(readBytes(inputStream, 4));
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.getInt();
    }

    public static int readLittleEndianByte(InputStream inputStream) throws IOException {
        ByteBuffer bb = ByteBuffer.wrap(readBytes(inputStream, 1));
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.get();
    }

    private static byte[] readBytes(InputStream inputStream, int length) throws IOException {
        byte[] buffer = new byte[length];
        inputStream.read(buffer);
        return buffer;
    }
}
