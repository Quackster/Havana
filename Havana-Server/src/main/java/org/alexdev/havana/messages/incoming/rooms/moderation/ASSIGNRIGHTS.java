package org.alexdev.havana.messages.incoming.rooms.moderation;

import org.alexdev.havana.dao.mysql.RoomRightsDao;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.util.MalformedPacketException;

import java.sql.SQLException;

public class ASSIGNRIGHTS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws SQLException, MalformedPacketException {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (!room.isOwner(player.getDetails().getId()) && !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            return;
        }

        Player target = PlayerManager.getInstance().getPlayerByName(reader.contents());

        if (target == null || target.getRoomUser().getRoom() == null || target.getRoomUser().getRoom().getId() != room.getId()) {
            return;
        }

        Integer userId = target.getDetails().getId();

        if (room.getRights().contains(userId)) {
            return;
        }

        room.getRights().add(userId);
        room.refreshRights(target, true);

        target.getRoomUser().setNeedsUpdate(true);
        RoomRightsDao.addRights(target.getDetails(), room.getData());
    }
}
