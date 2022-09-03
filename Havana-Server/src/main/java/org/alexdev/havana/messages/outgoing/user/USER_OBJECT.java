package org.alexdev.havana.messages.outgoing.user;

import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class USER_OBJECT extends MessageComposer {
    private final PlayerDetails details;

    public USER_OBJECT(PlayerDetails details) {
        this.details = details;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeString(this.details.getId());
        response.writeString(this.details.getName());
        response.writeString(this.details.getFigure());
        response.writeString(this.details.getSex());
        response.writeString(this.details.getMotto());
        response.writeInt(this.details.getTickets());
        response.writeString(this.details.getPoolFigure());
        response.writeInt(this.details.getFilm());
        response.writeBool(false); // directMail
        response.writeInt(this.details.getDailyRespectPoints() > 0 ? this.details.getRespectPoints() : 0);//this.details.getRespectPoints());
        response.writeInt(this.details.getDailyRespectPoints() > 0 ? this.details.getDailyRespectPoints() : 0);
    }

    @Override
    public short getHeader() {
        return 5; // "@E"
    }
}
