package org.alexdev.havana.messages.incoming.rooms.user;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.messages.outgoing.rooms.user.USER_DANCE;
import org.alexdev.havana.messages.outgoing.user.currencies.FILM;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class CARRYITEM implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        int carryId = reader.readInt();

        if (carryId > 0 && !player.getRoomUser().canCarry(carryId)) {
            return;
        }

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

            // Hacky fix to remove "Carrying: camera"
            player.getRoomUser().getRoom().send(new USER_DANCE(player.getRoomUser().getInstanceId(), 1));
            player.getRoomUser().getRoom().send(new USER_DANCE(player.getRoomUser().getInstanceId(), 0));
        }


        /*if (StringUtils.isNumeric(contents)) {
            player.getRoomUser().carryItem(Integer.parseInt(contents), null);
        } else {
            player.getRoomUser().carryItem(-1, contents);
        }*/

        player.getRoomUser().carryItem(carryId, null);
        player.getRoomUser().getTimerManager().resetRoomTimer();
    }
}
