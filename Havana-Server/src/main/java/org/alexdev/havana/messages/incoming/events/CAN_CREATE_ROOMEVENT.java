package org.alexdev.havana.messages.incoming.events;

import org.alexdev.havana.game.events.EventsManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.events.ROOMEVENT_PERMISSION;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class CAN_CREATE_ROOMEVENT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        boolean canCreateEvent = EventsManager.getInstance().canCreateEvent(player);
        player.send(new ROOMEVENT_PERMISSION(canCreateEvent));
    }
}
