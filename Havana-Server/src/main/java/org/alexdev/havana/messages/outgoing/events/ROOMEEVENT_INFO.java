package org.alexdev.havana.messages.outgoing.events;

import org.alexdev.havana.game.events.Event;
import org.alexdev.havana.messages.types.PlayerMessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class ROOMEEVENT_INFO extends PlayerMessageComposer {
    private final Event event;

    public ROOMEEVENT_INFO(Event event) {
        this.event = event;
    }

    @Override
    public void compose(NettyResponse response) {
        if (this.event == null) {
            response.writeString(-1);
        } else {
            response.writeString(this.event.getEventHoster().getId());
            response.writeString(this.event.getEventHoster().getName());
            response.writeString(this.event.getRoomId());
            response.writeInt(this.event.getCategoryId());
            response.writeString(this.event.getName());
            response.writeString(this.event.getDescription());
            response.writeString(this.event.getStartedDate());
        }
    }

    @Override
    public short getHeader() {
        return 370;
    }
}
