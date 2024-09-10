package org.alexdev.havana.messages.incoming.rooms.moderation;

import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.dao.mysql.RoomRightsDao;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.flash.outgoing.rooms.FLASH_EDITDATA;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.ArrayList;
import java.util.List;

public class REMOVERIGHTS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (!room.isOwner(player.getDetails().getId()) && !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            return;
        }


        List<Integer> targets = null;

        if (!player.getNetwork().isFlashConnection()) {
            targets = List.of(PlayerDao.getId(reader.contents()));
        } else {
            targets = new ArrayList<>();// = PlayerManager.getInstance().getPlayerById(reader.readInt());

            int remove = reader.readInt();
            for (int i = 0; i < remove; i++) {
                targets.add(reader.readInt());
            }
        }

        for (int targetId : targets) {
            if (!room.getRights().contains(targetId)) {
                continue;
            }

            var target = PlayerManager.getInstance().getPlayerById(targetId);

            if (target != null) {
                if (target == null || target.getRoomUser().getRoom() == null || target.getRoomUser().getRoom().getId() != room.getId()) {
                    continue;
                }

                room.refreshRights(target, true);
            }

            room.getRights().remove(Integer.valueOf(targetId));
            RoomRightsDao.removeRights(targetId, room.getData());
        }

        if (player.getNetwork().isFlashConnection()) {
            player.send(new FLASH_EDITDATA(room));
        }
    }
}