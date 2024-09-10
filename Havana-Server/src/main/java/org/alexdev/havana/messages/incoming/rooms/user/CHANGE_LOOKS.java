package org.alexdev.havana.messages.incoming.rooms.user;

import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.misc.figure.FigureManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.StringUtil;

public class CHANGE_LOOKS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!player.getNetwork().isFlashConnection()) {
            return;
        }

        String gender = StringUtil.filterInput(reader.readString(), true);
        String figure = StringUtil.filterInput(reader.readString(), true);

        if (!gender.equals("M") && !gender.equals("F")) {
            return;
        }

        if (!FigureManager.getInstance().validateFigure(figure, gender, player.getDetails().hasClubSubscription())) {
            return;
        }

        player.getDetails().setFigure(figure);
        player.getDetails().setSex(gender);

        PlayerDao.saveDetails(player.getDetails().getId(),
                player.getDetails().getFigure(),
                player.getDetails().getPoolFigure(),
                player.getDetails().getSex());

        player.getRoomUser().refreshAppearance();
    }
}
