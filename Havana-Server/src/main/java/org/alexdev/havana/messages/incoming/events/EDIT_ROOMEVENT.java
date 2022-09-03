package org.alexdev.havana.messages.incoming.events;

import org.alexdev.havana.dao.mysql.EventsDao;
import org.alexdev.havana.game.events.Event;
import org.alexdev.havana.game.events.EventsManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.wordfilter.WordfilterManager;
import org.alexdev.havana.messages.outgoing.events.ROOMEEVENT_INFO;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class EDIT_ROOMEVENT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.isOwner(player.getDetails().getId())) {
            return;
        }

        if (!EventsManager.getInstance().hasEvent(room.getId())) {
            return;
        }

        Event event = EventsManager.getInstance().getEventByRoomId(room.getId());

        if (event == null) {
            return;
        }

        int category = reader.readInt();

        if (category < 1 || category > 11) {
            return;
        }

        String name = WordfilterManager.filterSentence(StringUtil.filterInput(reader.readString(), true));
        String description = WordfilterManager.filterSentence(StringUtil.filterInput(reader.readString(), true));

        event.setCategoryId(category);
        event.setName(name);
        event.setDescription(description);

        room.send(new ROOMEEVENT_INFO(event));
        EventsDao.save(event);
    }
}
