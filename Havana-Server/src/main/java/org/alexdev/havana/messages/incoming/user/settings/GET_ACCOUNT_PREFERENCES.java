package org.alexdev.havana.messages.incoming.user.settings;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.user.settings.ACCOUNT_PREFERENCES;
import org.alexdev.havana.messages.outgoing.user.settings.HELP_ITEMS;
import org.alexdev.havana.messages.outgoing.user.settings.SOUND_SETTING;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class GET_ACCOUNT_PREFERENCES implements MessageEvent {

    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        player.send(new SOUND_SETTING(player.getDetails()));
        //player.send(new HELP_ITEMS());
    }
}