package org.alexdev.havana.messages.incoming.events;

import org.alexdev.havana.game.events.Event;
import org.alexdev.havana.game.events.EventsManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.events.ROOMEVENT_LIST;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.List;

public class GET_ROOMEVENTS_BY_TYPE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int categoryId = reader.readInt();

        if (categoryId < 0 || categoryId > 11) {
            return;
        }

        List<Event> eventList = EventsManager.getInstance().getEvents(categoryId);
        player.send(new ROOMEVENT_LIST(categoryId, eventList));
    }
}
