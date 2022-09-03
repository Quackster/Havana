package org.alexdev.havana.game.item.interactors.types;

import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.item.interactors.enums.TotemColour;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.triggers.GenericTrigger;
import org.alexdev.havana.messages.outgoing.rooms.items.MOVE_FLOORITEM;

import java.util.List;

public class TotemLegTrigger extends GenericTrigger {
    @Override
    public void onInteract(Player player, Room room, Item item, int status) {
        Item itemAbove = item.getItemAbove();

        if (itemAbove != null &&
                (itemAbove.getDefinition().getSprite().equals("totem_head")
                || itemAbove.getDefinition().getSprite().equals("totem_planet"))) {
            return;
        }

        InteractionType.DEFAULT.getTrigger().onInteract(player, room, item, status);
    }

    @Override
    public void onItemMoved(Player player, Room room, Item item, boolean isRotation, Position oldPosition, Item itemBelow, Item itemAbove) {
        if (itemAbove == null || !itemAbove.getDefinition().getSprite().equals("totem_head")) {
            return;
        }

        if (TotemHeadTrigger.getHeadColour(itemAbove) != TotemColour.NONE) {
            itemAbove.setCustomData(String.valueOf(TotemHeadTrigger.convertHeadToColour(itemAbove, TotemColour.NONE)));
            itemAbove.updateStatus();
            ItemDao.updateItems(List.of(itemAbove));
        }
    }
    /*@Override
    public void onItemPlaced(Player player, Room room, Item item, boolean isRotation, Position oldPosition, Item itemBelow) {
        Item above = item.getItemAbove();

        if (isRotation) {
            // If we rotate the head and have legs underneath, rotate the legs
            if (above != null && above.getDefinition().getSprite().equals("totem_head")) {
                above.getPosition().setRotation(item.getPosition().getRotation());
                room.send(new MOVE_FLOORITEM(above));
                ItemDao.updateItem(above);
            }

            return;
        }
    }*/
}
