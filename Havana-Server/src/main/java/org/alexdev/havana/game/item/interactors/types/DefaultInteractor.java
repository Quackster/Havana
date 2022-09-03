package org.alexdev.havana.game.item.interactors.types;

import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.mapping.RoomTile;
import org.alexdev.havana.game.triggers.GenericTrigger;
import org.apache.commons.lang3.StringUtils;

public class DefaultInteractor extends GenericTrigger {
    public void onInteract(Player player, Room room, Item item, int status) {
        if (item.hasBehaviour(ItemBehaviour.GATE)) {
            if (item.isGateOpen()) {
                RoomTile tile = item.getTile();

                if (tile.getEntireEntities().size() > 0) {
                    // Can't close gate if there's a user on the tile
                    return;
                }
            }
        }

        if (item.hasBehaviour(ItemBehaviour.ROOMDIMMER)
                || item.hasBehaviour(ItemBehaviour.DICE)
                || item.hasBehaviour(ItemBehaviour.PRIZE_TROPHY)
                || item.hasBehaviour(ItemBehaviour.POST_IT)
                || item.hasBehaviour(ItemBehaviour.ROLLER)
                || item.hasBehaviour(ItemBehaviour.WHEEL_OF_FORTUNE)
                || item.hasBehaviour(ItemBehaviour.SOUND_MACHINE_SAMPLE_SET)) {
            return; // Prevent dice rigging, scripting trophies, post-its, etc.
        }

        if (item.getDefinition().getMaxStatus() > 0) {
            int currentMode = StringUtils.isNumeric(item.getCustomData()) ? Integer.valueOf(item.getCustomData()) : 0;
            int newMode = currentMode + 1;

            if (newMode >= item.getDefinition().getMaxStatus()) {
                newMode = 0;
            }


            item.setCustomData(String.valueOf(newMode));
            item.updateStatus();
            item.save();
        }
    }
}
