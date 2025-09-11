package org.alexdev.havana.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.oldskooler.simplelogger4j.SimpleLog;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

public class NettyServer  {
    final private static int BACK_LOG = 20;
    final private static int BUFFER_SIZE = 2048;
	final private static SimpleLog<NettyServer> log = SimpleLog.of(NettyServer.class);

    private final String ip;
    private final int port;

    private DefaultChannelGroup channels;
    private ServerBootstrap bootstrap;
    private AtomicInteger connectionIds;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public NettyServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        this.bootstrap = new ServerBootstrap();
        this.connectionIds = new AtomicInteger(0);
    }

    /**
     * Create the Netty sockets.
     */
    public void createSocket() {
        int threads = Runtime.getRuntime().availableProcessors();
        this.bossGroup = (Epoll.isAvailable()) ? new EpollEventLoopGroup(threads) : new NioEventLoopGroup(threads);
        this.workerGroup = (Epoll.isAvailable()) ? new EpollEventLoopGroup(threads) : new NioEventLoopGroup(threads);

        this.bootstrap.group(bossGroup, workerGroup)
                .channel((Epoll.isAvailable()) ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .childHandler(new NettyChannelInitializer(this))
                .option(ChannelOption.SO_BACKLOG, BACK_LOG)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.SO_RCVBUF, BUFFER_SIZE)
                .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(BUFFER_SIZE))
                .childOption(ChannelOption.ALLOCATOR, new PooledByteBufAllocator(true));
    }

    /**
     * Bind the server to its address that's been specified
     */
    public void bind() {
        int[] ports = new int[] { this.port, this.getFlashPort() /*, this.port + 4*/}; // R34 client: deprecated

        for (int gamePort : ports) {
            this.bootstrap.bind(new InetSocketAddress(this.getIp(), gamePort)).addListener(objectFuture -> {
                if (!objectFuture.isSuccess()) {
                    log.error("Failed to start server on address " + this.getIp() + ":" + gamePort);
                    log.error("Please double check there's no programs using the same game port, and you have set the correct IP address to listen on.");
                } else {
                    if (gamePort == this.port + 2) {
                        log.info("Flash game server is listening on: " + this.getIp() + ":" + gamePort);
                    /*} else if (gamePort == this.port + 4) {
                        log.info("Flash R34 game server is listening on {}:{}", this.getIp(), gamePort);*/
                    } else {
                        log.info("Shockwave game server is listening on: " + this.getIp() + ":" + gamePort);
                    }
                }
            });
        }
        /*this.bootstrap.bind(new InetSocketAddress(this.getIp(), this.getPort())).addListener(objectFuture -> {
            if (!objectFuture.isSuccess()) {
                SimpleLog.of(SnowStormGameTask.class).error("Failed to start server on address: {}:{}", this.getIp(), this.getPort());
                SimpleLog.of(SnowStormGameTask.class).error("Please double check there's no programs using the same gamePort, and you have set the correct IP address to listen on.", this.getIp(), this.getPort());
            } else {
                log.info("Shockwave game server is listening on {}:{}", this.getIp(), this.getPort());
            }
        });*/
    }

    public int getFlashPort() {
        return this.port + 2;
    }

    public int getBetaPort() {
        return this.port + 4;
    }

    /**
     * Dispose the server handler.
     *
     * @throws InterruptedException
     */
    public void dispose(boolean doSync) throws InterruptedException {
        // Shutdown gracefully
        if (doSync) {
            this.workerGroup.shutdownGracefully().sync();
            this.bossGroup.shutdownGracefully().sync();
        } else {
            this.workerGroup.shutdownGracefully();
            this.bossGroup.shutdownGracefully();
        }
    }

    /**
     * Get the IP of this server.
     *
     * @return the server ip
     */
    private String getIp() {
        return ip;
    }

    /**
     * Get the port of this server.
     *
     * @return the port
     */
    private Integer getPort() {
        return port;
    }

    /**
     * Get default channel group of channels
     * @return channels
     */
    public DefaultChannelGroup getChannels() {
        return channels;
    }

    /**
     * Get handler for connection ids.
     *
     * @return the atomic int instance
     */
    public AtomicInteger getConnectionIds() {
        return connectionIds;
    }
}
