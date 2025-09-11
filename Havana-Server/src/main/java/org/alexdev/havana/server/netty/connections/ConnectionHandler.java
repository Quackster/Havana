package org.alexdev.havana.server.netty.connections;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.alexdev.havana.Havana;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.MessageHandler;
import org.alexdev.havana.messages.outgoing.handshake.HELLO;
import org.alexdev.havana.server.netty.NettyPlayerNetwork;
import org.alexdev.havana.server.netty.NettyServer;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.havana.util.config.ServerConfiguration;
import org.oldskooler.simplelogger4j.SimpleLog;

import java.io.IOException;

public class ConnectionHandler extends SimpleChannelInboundHandler<NettyRequest> {
    final private static SimpleLog<ConnectionHandler> log = SimpleLog.of(ConnectionHandler.class);
    private static final int MAX_CONNECTIONS = 1000;
    final private NettyServer server;

    public ConnectionHandler(NettyServer server) {
        this.server = server;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        int maxConnectionsPerIp = GameConfiguration.getInstance().getInteger("max.connections.per.ip");
        String ipAddress = NettyPlayerNetwork.getIpAddress(ctx.channel());

        // TODO: IP ban checking

        if (maxConnectionsPerIp > 0) {
            int count = 0;

            for (Channel channel : this.server.getChannels()) {
                String connectedIpAddress = NettyPlayerNetwork.getIpAddress(channel);

                if (connectedIpAddress.equals(ipAddress)) {
                    count++;
                }
            }

            if (count >= maxConnectionsPerIp) {
                //log.debug("Kicking off connection from " + ipAddress + " due to max connections reached");
                ctx.channel().close();
                return;
            }
        }

        Player player = new Player(new NettyPlayerNetwork(ctx.channel(), this.server.getConnectionIds().getAndIncrement()));
        ctx.channel().attr(Player.PLAYER_KEY).set(player);

        if (!this.server.getChannels().add(ctx.channel()) || Havana.isShuttingdown()) {
            ctx.close();
            return;
        }

        if (!player.getNetwork().isFlashConnection()) {
            player.send(new HELLO());
        }

        if (ServerConfiguration.getBoolean("log.connections")) {
            log.info("[" + player.getNetwork().getConnectionId() + "] Connection from " + NettyPlayerNetwork.getIpAddress(ctx.channel()));
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        if (this.server.getConnectionIds().get() > 0)
            this.server.getConnectionIds().getAndDecrement(); // Decrement because we don't want it to reach Integer.MAX_VALUE

        if (this.server.getChannels().contains(ctx.channel())) {
            this.server.getChannels().remove(ctx.channel());
        }

        Player player = ctx.channel().attr(Player.PLAYER_KEY).get();

        if (player != null) {
            player.dispose();

            if (ServerConfiguration.getBoolean("log.connections")) {
                log.info("[" + player.getNetwork().getConnectionId() + "] Disconnection from " + NettyPlayerNetwork.getIpAddress(ctx.channel()));
            }
        }
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, NettyRequest message) {
        Player player = ctx.channel().attr(Player.PLAYER_KEY).get();

        if (player == null) {
            SimpleLog.of(ConnectionHandler.class).error(String.format("Player was null from %s", ctx.channel().remoteAddress().toString().replace("/", "").split(":")[0]));
            return;
        }

        if (message == null) {
            SimpleLog.of(ConnectionHandler.class).error(String.format("Receiving message was null from {}", ctx.channel().remoteAddress().toString().replace("/", "").split(":")[0]));
            return;
        }

        MessageHandler.getInstance().handleRequest(player, message);

        try {
            message.dispose();
        } catch (Exception ex) {
            SimpleLog.of(ConnectionHandler.class).error("Error when disposing message:", ex);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //if (cause instanceof Exception) {
        if (!(cause instanceof IOException)) {
            SimpleLog.of(ConnectionHandler.class).error("Netty error occurred: ", cause); //ctx.close();
        }
        //}
    }
}