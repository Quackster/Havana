package org.alexdev.havana.messages.incoming.rooms.settings;

import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.navigator.NavigatorCategory;
import org.alexdev.havana.game.navigator.NavigatorManager;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class SETFLATCAT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int roomId = reader.readInt();
        int categoryId = reader.readInt();

        NavigatorCategory category = NavigatorManager.getInstance().getCategoryById(categoryId);

        if (category == null) {
            return;
        }

        if (category.getMinimumRoleSetFlat().getRankId() > player.getDetails().getRank().getRankId()) {
            return;
        }

        if (category.isNode() || category.isPublicSpaces()) {
            categoryId = 2;
        }

        Room room = RoomManager.getInstance().getRoomById(roomId);

        if (room == null) {
            return;
        }

        if (!room.isOwner(player.getDetails().getId())) {
            return;
        }

        room.getData().setCategoryId(categoryId);
        RoomDao.save(room);
    }
}
