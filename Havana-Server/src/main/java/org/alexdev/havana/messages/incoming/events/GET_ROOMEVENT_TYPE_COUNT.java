package org.alexdev.havana.messages.incoming.events;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.events.ROOMEVENT_TYPES;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.config.GameConfiguration;

public class GET_ROOMEVENT_TYPE_COUNT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        player.send(new ROOMEVENT_TYPES(GameConfiguration.getInstance().getInteger("events.category.count")));
    }
}
