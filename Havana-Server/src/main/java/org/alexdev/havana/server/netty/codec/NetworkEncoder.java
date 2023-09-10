package org.alexdev.havana.server.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.log.Log;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.outgoing.messenger.MESSENGER_MSG;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.messages.types.PlayerMessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;
import org.alexdev.havana.util.config.ServerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.Buffer;
import java.util.List;

public class NetworkEncoder extends MessageToMessageEncoder<MessageComposer> {
    final private static Logger log = LoggerFactory.getLogger(NetworkEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageComposer msg, List<Object> out) throws Exception {
        Player player = ctx.channel().attr(Player.PLAYER_KEY).get();
        ByteBuf buffer = Unpooled.buffer();

        if (msg instanceof PlayerMessageComposer) {
            PlayerMessageComposer playerMessageComposer = (PlayerMessageComposer) msg;
            playerMessageComposer.setPlayer(player);
        }

        NettyResponse response = new NettyResponse(msg.getHeader(), buffer);

        try {
            msg.compose(response);
        } catch (Exception ex) {
            Log.getErrorLogger().error("Error occurred when composing (" + response.getHeader() + "):", ex);
            return;
        }

        if (!response.isFinalised()) {
            buffer.writeByte(1);
            response.setFinalised(true);
        }

        if (ServerConfiguration.getBoolean("log.sent.packets")) {
            log.info("SENT 1: {} / {}", msg.getHeader(), response.getBodyString());
        }

        out.add(buffer);
    }
}