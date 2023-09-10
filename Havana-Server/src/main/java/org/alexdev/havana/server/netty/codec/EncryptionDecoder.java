package org.alexdev.havana.server.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.alexdev.havana.game.encryption.Cryptography;
import org.alexdev.havana.game.encryption.HugeInt15;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.server.netty.NettyPlayerNetwork;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.StringUtil;
import org.alexdev.havana.util.encoding.Base64Encoding;

import java.math.BigInteger;
import java.util.List;

public class EncryptionDecoder extends ByteToMessageDecoder {
    private Cryptography pHeaderDecoder;
    private Cryptography pDecoder;

    public EncryptionDecoder(BigInteger sharedKey) {
        this.pHeaderDecoder = new Cryptography(HugeInt15.getByteArray(sharedKey));
        this.pDecoder = new Cryptography(HugeInt15.getByteArray(sharedKey));
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        Player player = ctx.channel().attr(Player.PLAYER_KEY).get();

        String tHeader;
        String tBody;

        int pMsgSize;

        buffer.markReaderIndex();

        while (buffer.readableBytes() > 6) {
            byte[] tHeaderMsg = new byte[6];
            buffer.readBytes(tHeaderMsg);

            tHeader = new String(tHeaderMsg, StringUtil.getCharset());
            tHeader = this.pHeaderDecoder.kg4R6Jo5xjlqtFGs1klMrK4ZTzb3R(tHeader);

            int tByte1 = ((int) tHeader.charAt(3)) & 63;
            int tByte2 = ((int) tHeader.charAt(2)) & 63;
            int tByte3 = ((int) tHeader.charAt(1)) & 63;
            pMsgSize = (tByte2 * 64) | tByte1;
            pMsgSize = (tByte3 * 64 * 64) | pMsgSize;

            if (buffer.readableBytes() < pMsgSize) {
                buffer.resetReaderIndex();
                return;
            }

            player.getNetwork().setTx(
                    NettyPlayerNetwork.iterateRandom(player.getNetwork().getTx())
            );

            byte[] tBodyMsg = new byte[pMsgSize];
            buffer.readBytes(tBodyMsg);

            tBody = new String(tBodyMsg, StringUtil.getCharset());
            tBody = this.pDecoder.kg4R6Jo5xjlqtFGs1klMrK4ZTzb3R(tBody);
            tBody = NettyPlayerNetwork.removePadding(tBody, player.getNetwork().getTx() % 5);

            ByteBuf result = Unpooled.buffer();

            result.writeBytes(Base64Encoding.encode(tBody.length(), 3));
            result.writeBytes(tBody.getBytes(StringUtil.getCharset()));

            out.add(result);
        }
    }
}