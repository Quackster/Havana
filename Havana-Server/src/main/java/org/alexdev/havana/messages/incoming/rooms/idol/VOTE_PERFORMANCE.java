package org.alexdev.havana.messages.incoming.rooms.idol;

import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.stream.Collectors;

public class VOTE_PERFORMANCE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null)
            return;

        Item chair = player.getRoomUser().getCurrentItem();

        if (chair == null) {
            return;
        }

        if (chair.getDefinition().getInteractionType() != InteractionType.IDOL_VOTE_CHAIR) {
            return;
        }

        Item scoreboard = player.getRoomUser().getRoom().getItemManager().getIdolScoreboard();

        if (scoreboard == null) {
            return;
        }

        player.getRoomUser().getRoom().getIdolManager().vote(reader.readBoolean(), player);
    }
}
