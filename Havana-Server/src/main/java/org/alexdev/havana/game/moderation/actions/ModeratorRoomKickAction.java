package org.alexdev.havana.game.moderation.actions;

import org.alexdev.havana.dao.mysql.ModerationDao;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.moderation.ModerationAction;
import org.alexdev.havana.game.moderation.ModerationActionType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.moderation.MODERATOR_ALERT;
import org.alexdev.havana.messages.outgoing.rooms.user.HOTEL_VIEW;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.List;

public class ModeratorRoomKickAction implements ModerationAction {
    @Override
    public void performAction(Player player, Room room, String alertMessage, String notes, NettyRequest reader) {
        if (!player.hasFuse(Fuseright.ROOM_KICK)) {
            return;
        }

        List<Player> players = player.getRoomUser().getRoom().getEntityManager().getPlayers();

        for (Player target : players) {
            // Don't kick other moderators
            if (target.hasFuse(Fuseright.ROOM_KICK)) {
                continue;
            }

            target.getRoomUser().kick(false, true);

            target.send(new HOTEL_VIEW());
            target.send(new MODERATOR_ALERT(alertMessage));
        }


        ModerationDao.addLog(ModerationActionType.ROOM_KICK, player.getDetails().getId(), -1, alertMessage, notes);
    }
}
