package org.alexdev.havana.messages.outgoing.messenger;

import org.alexdev.havana.game.messenger.MessengerUser;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.List;

public class FRIENDLIST extends MessageComposer {
    private final Player player;
    private final List<MessengerUser> friends;

    public FRIENDLIST(Player player, List<MessengerUser> friends) {
        this.player = player;
        this.friends = friends;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.friends.size());

        for (MessengerUser friend : this.friends) {
            friend.serialise(player, response);
        }
    }

    @Override
    public short getHeader() {
        return 263; // "DG"
    }
}