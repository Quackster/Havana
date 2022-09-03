package org.alexdev.havana.messages.incoming.rooms.settings;

import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.game.wordfilter.WordfilterManager;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.StringUtil;

public class UPDATEFLAT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int roomId = reader.readInt();

        Room room = RoomManager.getInstance().getRoomById(roomId);

        if (room == null) {
            return;
        }

        if (!room.isOwner(player.getDetails().getId())) {
            return;
        }

        String roomName = WordfilterManager.filterSentence(StringUtil.filterInput(reader.readString(), true));
        String accessType = reader.readString();
        boolean showOwner = reader.readBoolean();

        int accessTypeId = 0;

        if (accessType.equals("closed")) {
            accessTypeId = 1;
        }

        if (accessType.equals("password")) {
            accessTypeId = 2;
        }

        room.getData().setName(roomName);
        room.getData().setAccessType(accessTypeId);
        room.getData().setShowOwnerName(showOwner);
        RoomDao.save(room);
    }
}
