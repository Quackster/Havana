package org.alexdev.havana.game.moderation.actions;

import org.alexdev.havana.dao.mysql.ModerationDao;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.moderation.ModerationAction;
import org.alexdev.havana.game.moderation.ModerationActionType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.texts.TextsManager;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.outgoing.moderation.MODERATOR_ALERT;
import org.alexdev.havana.messages.outgoing.rooms.user.HOTEL_VIEW;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.util.MalformedPacketException;

public class ModeratorKickUserAction implements ModerationAction {
    @Override
    public void performAction(Player player, Room room, String alertMessage, String notes, NettyRequest reader) throws MalformedPacketException {
        if (!player.hasFuse(Fuseright.KICK)) {
            return;
        }


        String alertUser = reader.readString();
        Player target = PlayerManager.getInstance().getPlayerByName(alertUser);

        if (target != null) {
            if (target.getDetails().getId() == player.getDetails().getId()) {
                return; // Can't kick yourself!
            }

            if (target.hasFuse(Fuseright.KICK)) {
                player.send(new ALERT(TextsManager.getInstance().getValue("modtool_rankerror")));
                return;
            }

            target.getRoomUser().kick(false, true);
            target.send(new HOTEL_VIEW());
            target.send(new MODERATOR_ALERT(alertMessage));

            ModerationDao.addLog(ModerationActionType.KICK_USER, player.getDetails().getId(), target.getDetails().getId(), alertMessage, notes);
        } else {
            player.send(new ALERT("Target user is not online."));
        }
    }
}
