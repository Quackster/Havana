package org.alexdev.havana.messages.incoming.rooms.settings;

import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.game.wordfilter.WordfilterManager;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.StringUtil;

public class SETFLATINFO implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        String contents = reader.contents();

        if (contents.startsWith("/")) {
            contents = contents.substring(1);
        }

        int roomId = reader.readInt();

        Room room = RoomManager.getInstance().getRoomById(roomId);

        if (room == null) {
            return;
        }

        if (!room.isOwner(player.getDetails().getId())) {
            return;
        }

        String description = WordfilterManager.filterSentence(reader.readString());
        String password = reader.readString();
        int allSuperUser = reader.readInt();

        room.getData().setDescription(StringUtil.filterInput(description, true));
        room.getData().setPassword(StringUtil.filterInput(password, true));
        room.getData().setSuperUsers(allSuperUser == 1);

        if (reader.remainingBytes().length > 0) {
            int maxVisitors = reader.readInt();

            if (maxVisitors < 10 || maxVisitors > 50) {
                maxVisitors = 25;
            }

            room.getData().setVisitorsMax(maxVisitors);
        }

        RoomDao.save(room);
    }
}
