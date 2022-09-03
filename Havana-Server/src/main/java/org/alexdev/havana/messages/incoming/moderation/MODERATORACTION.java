package org.alexdev.havana.messages.incoming.moderation;

import org.alexdev.havana.dao.mysql.ModerationDao;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.moderation.ModerationActionType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.texts.TextsManager;
import org.alexdev.havana.messages.outgoing.rooms.user.HOTEL_VIEW;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.outgoing.moderation.MODERATOR_ALERT;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.List;

public class MODERATORACTION implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int targetType = reader.readInt();
        int actionType = reader.readInt();

        String alertMessage = reader.readString();
        String notes = reader.readString();

        for (ModerationActionType moderationActionType : ModerationActionType.values()) {
            if (moderationActionType.getTargetType() == targetType &&
                moderationActionType.getActionType() == actionType) {
                moderationActionType.getModerationAction().performAction(player, player.getRoomUser().getRoom(), alertMessage, notes, reader);
            }
        }

        // TODO: refactor this if-else mess in something more syntactically pleasing
        /*if (commandCat == 0) {
            // User Command
            if (commandId == 0 && player.hasFuse(Fuseright.ROOM_ALERT)) {
                String alertUser = reader.readString();

                Player target = PlayerManager.getInstance().getPlayerByName(alertUser);

                if (target != null) {
                    target.send(new MODERATOR_ALERT(alertMessage));
                    ModerationDao.addLog(ModerationActionType.ALERT_USER, player.getDetails().getId(), target.getDetails().getId(), alertMessage, notes);
                } else {
                    player.send(new ALERT("Target user is not online."));
                }
            } else if (commandId == 1 && player.hasFuse(Fuseright.KICK)) {
                // Kick
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

                    target.getRoomUser().kick(false);
                    target.send(new HOTEL_VIEW());
                    target.send(new MODERATOR_ALERT(alertMessage));

                    ModerationDao.addLog(ModerationActionType.KICK_USER, player.getDetails().getId(), target.getDetails().getId(), alertMessage, notes);
                } else {
                    player.send(new ALERT("Target user is not online."));
                }
            } else if (commandId == 2 && player.hasFuse(Fuseright.BAN)) {
                //Ban
                // TODO: Banning
            }
        } else if (commandCat == 1) {
            // Room Command
            if (commandId == 0 && player.hasFuse(Fuseright.ROOM_ALERT)) {
                List<Player> players = player.getRoomUser().getRoom().getEntityManager().getPlayers();

                for (Player target : players) {
                    target.send(new MODERATOR_ALERT(alertMessage));
                }

                ModerationDao.addLog(ModerationActionType.ROOM_ALERT, player.getDetails().getId(), -1, alertMessage, notes);
            } else if (commandId == 1 && player.hasFuse(Fuseright.ROOM_KICK)) {
                // Room Kick
                List<Player> players = player.getRoomUser().getRoom().getEntityManager().getPlayers();

                for (Player target : players) {
                    // Don't kick other moderators
                    if (target.hasFuse(Fuseright.ROOM_KICK)) {
                        continue;
                    }

                    target.getRoomUser().kick(false);

                    target.send(new HOTEL_VIEW());
                    target.send(new MODERATOR_ALERT(alertMessage));

                    ModerationDao.addLog(ModerationActionType.ROOM_KICK, player.getDetails().getId(), -1, alertMessage, notes);
                }
            }
        }*/
    }
}
