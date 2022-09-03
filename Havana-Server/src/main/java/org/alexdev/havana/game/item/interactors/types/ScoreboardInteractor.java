package org.alexdev.havana.game.item.interactors.types;

import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.game.GameScheduler;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.triggers.GenericTrigger;
import org.apache.commons.lang3.StringUtils;

public class ScoreboardInteractor extends GenericTrigger {
    public void onInteract(Player player, Room room, Item item, int status) {
        if (!room.hasRights(player.getDetails().getId()) && !player.hasFuse(Fuseright.MOD)) {
            return;
        }

        if (status == 1) {
            if (item.getDefinition().getMaxStatus() > 0) {
                int currentMode = StringUtils.isNumeric(item.getCustomData()) ? Integer.valueOf(item.getCustomData()) : 0;
                int newMode = currentMode - 1;

                if (newMode < 0) {
                    newMode = 99;
                }

                item.setCustomData(String.valueOf(newMode));
                item.updateStatus();
            }
        }

        if (status == 2) {
            InteractionType.DEFAULT.getTrigger().onInteract(player, room, item, status);
        }

        if (status == 0) {
            if (StringUtils.isNumeric(item.getCustomData())) {
                item.setCustomData("x");
                item.updateStatus();
            } else {
                item.setCustomData("0");
                item.updateStatus();
            }
        }

        item.save();
    }
}
