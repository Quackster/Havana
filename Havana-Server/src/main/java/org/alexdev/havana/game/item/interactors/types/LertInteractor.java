package org.alexdev.havana.game.item.interactors.types;

import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.game.GameScheduler;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.triggers.GenericTrigger;

public class LertInteractor extends GenericTrigger {
    public void onInteract(Player player, Room room, Item item, int status) {
        item.setCustomData("0");
        item.updateStatus();

        item.setCustomData("1");
        item.updateStatus();

        item.setCustomData("0");
        item.updateStatus();
        item.save();
    }
}
