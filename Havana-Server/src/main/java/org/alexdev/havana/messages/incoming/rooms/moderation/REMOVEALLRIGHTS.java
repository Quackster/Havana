package org.alexdev.havana.messages.incoming.rooms.moderation;

import org.alexdev.havana.dao.mysql.RoomRightsDao;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class REMOVEALLRIGHTS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int roomId = reader.readInt();

        Room room = RoomManager.getInstance().getRoomById(roomId);

        if (room == null) {
            return;
        }

        if (!room.isOwner(player.getDetails().getId()) && !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            return;
        }

        room.getRights().clear();

        for (Player roomPlayer : room.getEntityManager().getPlayers()) {
            room.refreshRights(roomPlayer, true);
        }

        RoomRightsDao.deleteRoomRights(room.getData());
    }
}
