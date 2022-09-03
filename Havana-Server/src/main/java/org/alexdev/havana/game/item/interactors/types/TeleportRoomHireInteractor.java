package org.alexdev.havana.game.item.interactors.types;

import org.alexdev.havana.game.GameScheduler;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.navigator.NavigatorManager;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.entities.RoomPlayer;
import org.alexdev.havana.game.room.models.RoomModelManager;
import org.alexdev.havana.game.triggers.GenericTrigger;
import org.alexdev.havana.messages.outgoing.rooms.user.LOGOUT;

import java.util.concurrent.TimeUnit;

public class TeleportRoomHireInteractor extends GenericTrigger {
    public static final String TELEPORTER_CLOSE = "0";
    public static final String TELEPORTER_OPEN = "1";
    public static final String TELEPORTER_EFFECTS = "2";

    public void onInteract(Player player, Room room, Item item, int status) {
        RoomPlayer roomUser = player.getRoomUser();

        if (player.getRoomUser().getAuthenticateTelporterId() != -1) {
            return;
        }

        Position front = item.getPosition().getSquareInFront();

        if (!front.equals(roomUser.getPosition()) && !item.getPosition().equals(roomUser.getPosition())) {
            roomUser.walkTo(front.getX(), front.getY());
            return;
        }

        item.setCustomData(TELEPORTER_OPEN);
        item.updateStatus();

        roomUser.walkTo(item.getPosition().getX(), item.getPosition().getY());
        roomUser.setWalkingAllowed(false);

        // Resume normal teleportation
        GameScheduler.getInstance().getService().schedule(() -> {
            item.setCustomData(TELEPORTER_EFFECTS);
            item.updateStatus();
        }, 1000, TimeUnit.MILLISECONDS);

        GameScheduler.getInstance().getService().schedule(() -> {
            room.send(new LOGOUT(player.getRoomUser().getInstanceId()));
            item.setCustomData(TELEPORTER_CLOSE);
            item.updateStatus();

            tryCreateRoom(player, room, item);
        }, 1500, TimeUnit.MILLISECONDS);
    }

    private void tryCreateRoom(Player player, Room sourceRoom, Item item) {
        var room = item.getTemporaryRoom();

        if (room == null) {
            room = new Room();

            room.getData().fill(0, "Hire Room", "This is hosted by the Hire-A-Room furniture");
            room.getData().setCustomRoom(true);
            room.getData().setOwnerId(sourceRoom.getData().getOwnerId());
            room.getData().setCategoryId(NavigatorManager.getInstance().getCategories().values().stream().filter(x -> !x.isPublicSpaces()).findFirst().orElse(null).getId());
            room.getData().setVisitorsMax(50);
            
            room.setRoomModel(RoomModelManager.getInstance().getModel("model_a"));
            item.setTemporaryRoom(room);
        }

        //        player.getDetails().setTemporaryRoom(room);
        room.forward(player, false);
    }
}
