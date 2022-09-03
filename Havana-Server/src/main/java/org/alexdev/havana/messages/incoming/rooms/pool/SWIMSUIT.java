package org.alexdev.havana.messages.incoming.rooms.pool;

import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.rooms.user.USER_OBJECTS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.StringUtil;

public class SWIMSUIT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.getData().getModel().equals("pool_a") &&
            !room.getData().getModel().equals("md_a")) {
            return;
        }

        String swimsuit = StringUtil.filterInput(reader.contents(), true);
        player.getDetails().setPoolFigure(swimsuit);

        room.send(new USER_OBJECTS(player));

        PlayerDao.saveDetails(
                player.getDetails().getId(),
                player.getDetails().getFigure(),
                player.getDetails().getPoolFigure(),
                player.getDetails().getSex());
    }
}
