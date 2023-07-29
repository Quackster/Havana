package org.alexdev.havana.server.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.alexdev.havana.game.encryption.Cryptography;
import org.alexdev.havana.game.encryption.HugeInt15;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.server.netty.NettyPlayerNetwork;
import org.alexdev.havana.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EncryptionEncoder extends MessageToMessageEncoder<ByteBuf> {
    private static final Logger log = LoggerFactory.getLogger(EncryptionEncoder.class);

    private Cryptography pHeaderEncoder;
    private Cryptography pEncoder;

    public EncryptionEncoder(BigInteger sharedKey) {
        this.pHeaderEncoder = new Cryptography(HugeInt15.getByteArray(sharedKey));
        this.pEncoder = new Cryptography(HugeInt15.getByteArray(sharedKey));
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        Player player = ctx.channel().attr(Player.PLAYER_KEY).get();

        player.getNetwork().setRx(NettyPlayerNetwork.iterateRandom(
            player.getNetwork().getRx()
        ));

        String tOriginalMsg = buffer.toString(StringUtil.getCharset());

        String tHeader;
        String tMsg;

        tMsg = NettyPlayerNetwork.addPad(tOriginalMsg,  player.getNetwork().getRx() % 5);
        tMsg = this.pEncoder.AkwGx8bHG2kc1xGG4xbdHPCV0fqvK(tMsg);

        var tLength = tMsg.length();
        var tL1 = Character.toString((tLength & 63) | 64);
        var tL2 = Character.toString(((tLength / 64) & 63) | 64);
        var tL3 = Character.toString(((tLength / 4096) & 63) | 64);

        tHeader = (char) (ThreadLocalRandom.current().nextInt(127) + 1) + (tL3 + tL2 + tL1);
        tHeader = this.pHeaderEncoder.AkwGx8bHG2kc1xGG4xbdHPCV0fqvK(tHeader);

        var tEncryptedMsg = Unpooled.buffer();

        tEncryptedMsg.writeBytes(tHeader.getBytes(StringUtil.getCharset()));
        tEncryptedMsg.writeBytes(tMsg.getBytes(StringUtil.getCharset()));

        out.add(tEncryptedMsg);
    }
}