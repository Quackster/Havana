package org.alexdev.havana.game.item.interactors.types;

import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.game.GameScheduler;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.pathfinder.Rotation;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.game.triggers.GenericTrigger;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class FortuneInteractor extends GenericTrigger {
    public static int FORTUNE_OFF = 8;
    public static int FORTUNE_NO_STATE = -1;

    public void onInteract(Player player, Room room, Item item, int status) {
        if (item.getRequiresUpdate()) {
            return;
        }

        int currentMode = StringUtils.isNumeric(item.getCustomData()) ? Integer.valueOf(item.getCustomData()) : FORTUNE_OFF;
        int newMode;

        // Turn on
        if (currentMode == FORTUNE_OFF) {
            newMode = FORTUNE_NO_STATE;
        } else {
            newMode = FORTUNE_OFF;
        }

        item.setCustomData(String.valueOf(newMode));
        item.updateStatus();
        item.save();

        int rotation = Rotation.calculateWalkDirection(player.getRoomUser().getPosition(), item.getPosition());

        // When sitting calculate even rotation
        if (player.getRoomUser().containsStatus(StatusType.SIT)) {
            var current = player.getRoomUser().getPosition(); // And now rotate their head for all sitting people.
            player.getRoomUser().getPosition().setHeadRotation(Rotation.getHeadRotation(current.getRotation(), current, item.getPosition()));

        } else {
            player.getRoomUser().getPosition().setRotation(rotation);
        }

        player.getRoomUser().setNeedsUpdate(true);

        if (newMode == FORTUNE_NO_STATE) {
            item.setRequiresUpdate(true);

            GameScheduler.getInstance().getService().schedule(() -> {
                int randomNumber = ThreadLocalRandom.current().nextInt(0, 8); // 0-7

                item.setCustomData(String.valueOf(randomNumber));
                item.updateStatus();

                item.setRequiresUpdate(false);
                item.save();

            }, 1500, TimeUnit.MILLISECONDS);
        }
    }
}
