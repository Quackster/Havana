package org.alexdev.havana.messages.incoming.rooms.settings;

import org.alexdev.havana.dao.mysql.GroupDao;
import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.dao.mysql.TagDao;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.games.snowstorm.tasks.SnowStormGameTask;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.flash.incoming.navigator.FLASH_USERFLATS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.oldskooler.simplelogger4j.SimpleLog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DELETEFLAT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int roomId = reader.readInt();
        delete(roomId, player.getDetails().getId());

        if (player.getNetwork().isFlashConnection()) {
            new FLASH_USERFLATS().handle(player, null);
        }
    }

    public static void delete(int roomId, int userId) throws SQLException {
        Room room = RoomManager.getInstance().getRoomById(roomId);

        if (room == null) {
            return;
        }

        if (!room.isOwner(userId)) {
            return;
        }

        for (var item : room.getItems()) {
            item.delete();
        }

        List<Entity> entities = new ArrayList<>(room.getEntities());

        for (Entity entity : entities) {
            room.getEntityManager().leaveRoom(entity, true);
        }

        if (!room.tryDispose()) {
            SimpleLog.of(DELETEFLAT.class).error("Room " + roomId + " did not want to get disposed by player id " + userId);
        }

        TagDao.removeTags(0, roomId, 0);
        GroupDao.deleteHomeRoom(roomId);
        RoomDao.delete(room);
    }
}
