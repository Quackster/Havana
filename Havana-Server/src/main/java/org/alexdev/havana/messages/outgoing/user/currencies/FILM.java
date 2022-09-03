package org.alexdev.havana.messages.outgoing.user.currencies;

import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class FILM extends MessageComposer {
    private final int film;

    public FILM(PlayerDetails details) {
        this.film = details.getFilm();
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.film);
    }

    @Override
    public short getHeader() {
        return 4; // "@D"
    }
}
