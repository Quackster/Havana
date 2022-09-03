package org.alexdev.havana.messages.outgoing.user.settings;

import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class ACCOUNT_PREFERENCES extends MessageComposer {
    private final PlayerDetails details;

    public ACCOUNT_PREFERENCES(PlayerDetails details) {
        this.details = details;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeBool(this.details.getSoundSetting());
    }

    @Override
    public short getHeader() {
        return 308; // "Dt": [[#tutorial_handler, #handleAccountPreferences]]
    }
}