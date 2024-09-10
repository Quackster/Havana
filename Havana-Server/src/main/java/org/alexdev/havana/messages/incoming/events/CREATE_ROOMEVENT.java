package org.alexdev.havana.messages.incoming.events;

import org.alexdev.havana.game.events.Event;
import org.alexdev.havana.game.events.EventsManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.wordfilter.WordfilterManager;
import org.alexdev.havana.messages.outgoing.events.ROOMEEVENT_INFO;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class CREATE_ROOMEVENT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (!EventsManager.getInstance().canCreateEvent(player)) {
            return;
        }

        int category = reader.readInt();

        if (category < 1 || category > 11) {
            return;
        }

        String name = WordfilterManager.filterSentence(StringUtil.filterInput(reader.readString(), true));
        String description = WordfilterManager.filterSentence(StringUtil.filterInput(reader.readString(), true));

        List<String> tags = new ArrayList<>();

        if (player.getNetwork().isFlashConnection()) {
            int amount = reader.readInt();

            for (int i = 0; i < amount; i++) {
                if (i > 1) {
                    break;
                }

                tags.add(StringUtil.filterInput(reader.readString(), true).replace(",", " "));
            }
        }

        Event event = EventsManager.getInstance().createEvent(player, category, name, description, tags);
        player.getRoomUser().getRoom().send(new ROOMEEVENT_INFO(event));
    }
}
