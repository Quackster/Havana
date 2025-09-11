package org.alexdev.havana.server.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.outgoing.messenger.MESSENGER_MSG;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.messages.types.PlayerMessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;
import org.alexdev.havana.util.StringUtil;
import org.alexdev.havana.util.config.ServerConfiguration;
import org.oldskooler.simplelogger4j.SimpleLog;

import java.util.List;

public class NetworkEncoder extends MessageToMessageEncoder<Object> {
    final private static SimpleLog<NetworkEncoder> log = SimpleLog.of(NetworkEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Object outgoing, List<Object> out) throws Exception {
        Player player = ctx.channel().attr(Player.PLAYER_KEY).get();
        ByteBuf buffer = Unpooled.buffer();

        if (outgoing instanceof MessageComposer) {
            var msg = (MessageComposer) outgoing;

            if (msg instanceof PlayerMessageComposer) {
                PlayerMessageComposer playerMessageComposer = (PlayerMessageComposer) msg;
                playerMessageComposer.setPlayer(player);
            }

            NettyResponse response = new NettyResponse(msg.getHeader(), buffer);

            try {
                if (msg instanceof ALERT) {
                    ALERT alert = (ALERT)msg;

                    if (player.getNetwork().isFlashConnection()) {
                        alert.setMessage(alert.getMessage().replace("<br>", "\r"));
                    }
                }

                // Fix "thumbs up" between Flash/Shockwave clients (ALT+7)
                if (msg instanceof MESSENGER_MSG) {
                    MESSENGER_MSG messengerMsg = (MESSENGER_MSG)msg;

                    if (player.getNetwork().isFlashConnection()) {
                        messengerMsg.getMessage().setMessage(messengerMsg.getMessage().getMessage().replace(Character.toString((char)149), Character.toString((char)8226)));
                    } else {
                        messengerMsg.getMessage().setMessage(messengerMsg.getMessage().getMessage().replace(Character.toString((char)8226), Character.toString((char)149)));
                    }
                }

                if (msg instanceof CHAT_MESSAGE) {
                    CHAT_MESSAGE chatMsg = (CHAT_MESSAGE)msg;

                    if (player.getNetwork().isFlashConnection()) {
                        chatMsg.setMessage(chatMsg.getMessage().replace(Character.toString((char)149), Character.toString((char)8226)));
                    } else {
                        chatMsg.setMessage(chatMsg.getMessage().replace(Character.toString((char)8226), Character.toString((char)149)));
                    }
                }

                msg.compose(response);
            } catch (Exception ex) {
                String name = "";

                if (player != null && player.isLoggedIn()) {
                    name = player.getDetails().getName();
                }

                SimpleLog.of(NetworkEncoder.class).error("Error occurred when composing (" + response.getHeader() + ") for user (" + name + "):", ex);
                return;
            }

            if (!response.isFinalised()) {
                buffer.writeByte(1);
                response.setFinalised(true);
            }

            if (ServerConfiguration.getBoolean("log.sent.packets")) {
                log.info("SENT: " + msg.getHeader() + " / " + response.getBodyString());
            }
        }

        if (outgoing instanceof String) {
            var msgString = (String) outgoing;
            buffer.writeBytes(msgString.getBytes(StringUtil.getCharset()));
        }

        out.add(buffer);
    }
}