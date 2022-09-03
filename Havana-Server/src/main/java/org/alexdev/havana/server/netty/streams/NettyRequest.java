package org.alexdev.havana.server.netty.streams;

import io.netty.buffer.ByteBuf;
import org.alexdev.havana.server.util.MalformedPacketException;
import org.alexdev.havana.util.StringUtil;
import org.alexdev.havana.util.encoding.Base64Encoding;
import org.alexdev.havana.util.encoding.VL64Encoding;

import java.nio.charset.Charset;

public class NettyRequest {
    final private int headerId;
    final private String header;
    final private ByteBuf buffer;
    private boolean isDisposed;

    public NettyRequest(ByteBuf buffer) {
        this.buffer = buffer;
        this.header = new String(new byte[] { buffer.readByte(), buffer.readByte() });
        this.headerId = Base64Encoding.decode(header.getBytes());
    }


    public NettyRequest(int headerId, ByteBuf buffer) {
        this.buffer = buffer;
        this.headerId = headerId;
        this.header = new String(Base64Encoding.encode(headerId, 2), StringUtil.getCharset());
    }

    public Integer readInt() throws MalformedPacketException {
        try {
            byte[] remaining = this.remainingBytes();

            int length = remaining[0] >> 3 & 7;
            int value = VL64Encoding.decode(remaining);
            readBytes(length);

            return value;
        } catch (Exception ex) {
            throw new MalformedPacketException("The packet sent to the server was malformed.");
        }
    }

    public int readBase64() throws MalformedPacketException {
        try {
            return Base64Encoding.decode(new byte[] {
                    this.buffer.readByte(),
                    this.buffer.readByte()
            });
        } catch (Exception ex) {
            throw new MalformedPacketException("The packet sent to the server was malformed.");
        }
    }

    public boolean readBoolean() throws MalformedPacketException {
        try {
            return this.readInt() == 1;
        } catch (Exception ex) {
            throw new MalformedPacketException("The packet sent to the server was malformed.");
        }

    }

    public String readString() throws MalformedPacketException {
        try {
            int length = this.readBase64();
            byte[] data = this.readBytes(length);

            return new String(data, StringUtil.getCharset());
        } catch (Exception ex) {
            throw new MalformedPacketException("The packet sent to the server was malformed.");
        }
    }

    public String readClientString() throws MalformedPacketException {
        try {
            byte[] data = remainingBytes();

            int position = 0;

            for (int i = 0; i < data.length; i++) {
                if (data[i] == (byte) 2) {
                    break;
                }

                position = i;
            }

            String readData = new String(this.readBytes(position + 1), StringUtil.getCharset());
            this.readBytes(1);
            return readData;

        } catch (Exception ex) {
            throw new MalformedPacketException("The packet sent to the server was malformed.");
        }
    }

    public byte[] readBytes(int len) throws MalformedPacketException {
        try {
            byte[] payload = new byte[len];
            this.buffer.readBytes(payload);
            return payload;
        } catch (Exception ex) {
            throw new MalformedPacketException("The packet sent to the server was malformed.");
        }
    }

    public byte[] remainingBytes() throws MalformedPacketException {
        try {
            this.buffer.markReaderIndex();

            byte[] bytes = new byte[this.buffer.readableBytes()];
            buffer.readBytes(bytes);

            this.buffer.resetReaderIndex();
            return bytes;
        } catch (Exception ex) {
            throw new MalformedPacketException("The packet sent to the server was malformed.");
        }
    }

    public String contents() throws MalformedPacketException {
        try {
            byte[] remiainingBytes = this.remainingBytes();

            if (remiainingBytes != null) {
                return new String(remiainingBytes);
            }

            return null;
        } catch (Exception ex) {
            throw new MalformedPacketException("The packet sent to the server was malformed.");
        }
    }

    public String getMessageBody() {
        String consoleText = this.buffer.toString(Charset.defaultCharset());

        for (int i = 0; i < 14; i++) {
            consoleText = consoleText.replace(Character.toString((char)i), "{" + i + "}");
        }

        return this.header + consoleText;
    }

    public String getHeader() {
        return header;
    }

    public int getHeaderId() {
        return headerId;
    }

    public void dispose() {
        if (this.isDisposed) {
            return;
        }

        this.isDisposed = true;
        this.buffer.release();
    }
}