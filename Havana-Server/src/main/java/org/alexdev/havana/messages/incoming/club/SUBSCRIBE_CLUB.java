package org.alexdev.havana.messages.incoming.club;

import org.alexdev.havana.game.club.ClubSubscription;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.user.currencies.CREDIT_BALANCE;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class SUBSCRIBE_CLUB implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        reader.readString();
        int choice = reader.readInt();

        if (ClubSubscription.subscribeClub(player.getDetails(), choice)) {
            player.send(new CREDIT_BALANCE(player.getDetails().getCredits()));
            player.refreshClub();
        }
    }
}
