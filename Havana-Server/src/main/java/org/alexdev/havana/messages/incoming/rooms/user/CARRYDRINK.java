package org.alexdev.havana.messages.incoming.rooms.user;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.messages.outgoing.rooms.user.USER_DANCE;
import org.alexdev.havana.messages.outgoing.user.currencies.FILM;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class CARRYDRINK implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        int carryId = reader.readInt();

        if (carryId > 0 && !player.getRoomUser().canCarry(carryId)) {
            return;
        }

        // Use old carry status for camera else when user walks, the camera doesn't get used
        if (carryId == 20) {
            player.getRoomUser().stopCarrying();
            player.getRoomUser().setStatus(StatusType.CARRY_ITEM, "20");
            player.getRoomUser().setNeedsUpdate(true);

            player.send(new FILM(player.getDetails()));
            return;
        }

        if (carryId == 0 && player.getRoomUser().containsStatus(StatusType.CARRY_ITEM)) {
            // Remove carrying item status
            player.getRoomUser().removeStatus(StatusType.CARRY_ITEM);
            player.getRoomUser().setNeedsUpdate(true);

            if (player.getRoomUser().containsStatus(StatusType.SIT)) {
                player.getRoomUser().refreshUser();
            } else {
                player.getRoomUser().getRoom().send(new USER_DANCE(player.getRoomUser().getInstanceId(), 1));
                player.getRoomUser().getRoom().send(new USER_DANCE(player.getRoomUser().getInstanceId(), 0));
            }
        }

        if (carryId > 0) {
            player.getRoomUser().carryItem(carryId, null);
        } else {
            if (player.getRoomUser().isCarrying()) {
                player.getRoomUser().stopCarrying();
            }
        }

        player.getRoomUser().getTimerManager().resetRoomTimer();
    }
}
