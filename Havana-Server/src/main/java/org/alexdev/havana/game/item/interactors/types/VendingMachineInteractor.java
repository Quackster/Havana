package org.alexdev.havana.game.item.interactors.types;

import org.alexdev.havana.game.GameScheduler;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.pathfinder.Rotation;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.game.triggers.GenericTrigger;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class VendingMachineInteractor extends GenericTrigger {
    public void onInteract(Player player, Room room, Item item, int status) {
        /*if (player.getRoomUser().isUsingEffect()) {
            return;
        }*/

        // Can't getInteractor to the vending machine unless we're close
        Position front = item.getPosition().getSquareInFront();

        // Let user access fridge from the side, left or right.
        if (!front.touches(player.getRoomUser().getPosition()) &&
                !player.getRoomUser().getPosition().getSquareRight().equals(item.getPosition()) &&
                !player.getRoomUser().getPosition().getSquareLeft().equals(item.getPosition())) {
            player.getRoomUser().walkTo(front.getX(), front.getY());
            return;
        }

        // Only rotate user if they are in front
        if (!player.getRoomUser().containsStatus(StatusType.SIT) &&
            !player.getRoomUser().containsStatus(StatusType.LAY)) {
            int newRotation = Rotation.calculateWalkDirection(player.getRoomUser().getPosition(), item.getPosition());

            if (player.getRoomUser().getPosition().getRotation() != newRotation) {
                if (player.getRoomUser().isSittingOnChair()) { // Don't rotate user on chair when using vending machine
                    return;
                }

                player.getRoomUser().getPosition().setRotation(newRotation);
                player.getRoomUser().setNeedsUpdate(true);
            }
        }

        int randomDrinkId = item.getDefinition().getDrinkIds()[ThreadLocalRandom.current().nextInt(0, item.getDefinition().getDrinkIds().length)];

        item.setCustomData("1");
        item.updateStatus();

        GameScheduler.getInstance().getService().schedule(() -> {
            player.getRoomUser().carryItem(randomDrinkId, null);
        }, 1, TimeUnit.SECONDS);

        GameScheduler.getInstance().getService().schedule(() -> {
            item.setCustomData("0");
            item.updateStatus();
        }, 2, TimeUnit.SECONDS);
    }
}
