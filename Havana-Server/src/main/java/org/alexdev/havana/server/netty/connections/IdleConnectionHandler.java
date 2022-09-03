package org.alexdev.havana.server.netty.connections;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.user.PING;
import org.alexdev.havana.util.config.ServerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdleConnectionHandler extends ChannelDuplexHandler {
    private static Logger logger = LoggerFactory.getLogger(IdleConnectionHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object event) {
        if (event instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) event;

            if (e.state() == IdleState.READER_IDLE) {
                Player player = ctx.channel().attr(Player.PLAYER_KEY).get();

                if (player.isPingOK()) {
                    player.setPingOK(false);
                    player.send(new PING());
                } else {
                    if (ServerConfiguration.getBoolean("log.connections")) {
                        if (player.isLoggedIn()) {
                            logger.info("Player {} has timed out", player.getDetails().getName());
                        } else {
                            logger.info("Connection {} has timed out", player.getNetwork().getConnectionId());
                        }
                    }

                    player.kickFromServer();
                }
            }
        }
    }
}
