package org.alexdev.havana.game.item.interactors.types;

import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.triggers.GenericTrigger;

public class MultiHeightInteractor extends GenericTrigger {
    public void onInteract(Player player, Room room, Item item, int status) {
        if (item.getItemAbove() != null)
            return;

        InteractionType.DEFAULT.getTrigger().onInteract(player, room, item, status);

        room.getMapping().getTile(item.getPosition()).removeItem(item);
        room.getMapping().getTile(item.getPosition()).addItem(item);

        item.updateEntities(null);
    }
}
