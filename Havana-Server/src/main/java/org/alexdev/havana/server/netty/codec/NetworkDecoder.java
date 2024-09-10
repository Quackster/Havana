package org.alexdev.havana.server.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.encoding.Base64Encoding;

import java.nio.charset.Charset;
import java.util.List;

public class NetworkDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {
        if (buffer.readableBytes() < 4) {
            // If the incoming data is less than 5 bytes, it's junk.
            return;
        }

        Player player = ctx.channel().attr(Player.PLAYER_KEY).get();

        if (player.getNetwork().isFlashConnection()) {
            buffer.markReaderIndex();

            if (buffer.readByte() != 64) {
                ctx.channel().writeAndFlush("<?xml version=\"1.0\"?>\r\n" +
                        "<!DOCTYPE cross-domain-policy SYSTEM \"/xml/dtds/cross-domain-policy.dtd\">\r\n" +
                        "<cross-domain-policy>\r\n" +
                        "<allow-access-from domain=\"*\" to-ports=\"1-31111\" />\r\n" +
                        "</cross-domain-policy>\0");
            }

            buffer.resetReaderIndex();
        }

        buffer.markReaderIndex();

        int length = Base64Encoding.decode(new byte[]{buffer.readByte(), buffer.readByte(), buffer.readByte()});

        if (buffer.readableBytes() < length) {
            buffer.resetReaderIndex();
            return;
        }

        if (length < 0) {
            return;
        }

        out.add(new NettyRequest(buffer.readBytes(length)));
    }
}