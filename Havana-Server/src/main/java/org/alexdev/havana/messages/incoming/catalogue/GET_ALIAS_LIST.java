package org.alexdev.havana.messages.incoming.catalogue;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.catalogue.ALIAS_TOGGLE;
import org.alexdev.havana.messages.outgoing.catalogue.SPRITE_LIST;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class GET_ALIAS_LIST implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        player.send(new SPRITE_LIST());
        player.send(new ALIAS_TOGGLE());
    }
}
