package org.alexdev.havana.messages.incoming.pets;

import org.alexdev.havana.game.pets.PetManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.pets.NAMEAPPROVED;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class APPROVE_PET_NAME implements MessageEvent {

    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        String name = reader.readString();
        int approveStatus = PetManager.getInstance().isValidName(player.getDetails().getName(), name);
        player.send(new NAMEAPPROVED(approveStatus));
    }
}
