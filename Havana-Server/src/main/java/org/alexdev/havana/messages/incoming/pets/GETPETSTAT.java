package org.alexdev.havana.messages.incoming.pets;

import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.pets.Pet;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.pets.PETSTAT;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class GETPETSTAT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        int petId = reader.readInt();
        String petName = reader.readString();

        Pet pet = (Pet) room.getEntityManager().getById(petId, EntityType.PET);

        if (pet == null) {
            return;
        }

        player.send(new PETSTAT(pet));
    }
}
