package org.alexdev.havana.messages.incoming.rooms.user;

import org.alexdev.havana.game.GameScheduler;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.game.room.tasks.CameraTask;
import org.alexdev.havana.messages.outgoing.rooms.user.USER_STATUSES;
import org.alexdev.havana.messages.outgoing.rooms.user.USER_USE_OBJECT;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class USEITEM implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        if (player.getRoomUser().containsStatus(StatusType.CARRY_ITEM)) {
            String item = player.getRoomUser().getStatus(StatusType.CARRY_ITEM).getValue();

            player.getRoomUser().getStatuses().remove(StatusType.CARRY_ITEM.getStatusCode());
            player.getRoomUser().setStatus(StatusType.USE_ITEM, item);

            if (!player.getRoomUser().isWalking()) {
                player.getRoomUser().getRoom().send(new USER_STATUSES(List.of(player)));
            }

            GameScheduler.getInstance().getService().schedule(new CameraTask(player), 1, TimeUnit.SECONDS);
        } else {
            //player.getRoomUser().getRoom().send(new USER_USE_OBJECT(player.getRoomUser().getInstanceId(), player.getRoomUser().getCarryId()));
        }
    }
}
