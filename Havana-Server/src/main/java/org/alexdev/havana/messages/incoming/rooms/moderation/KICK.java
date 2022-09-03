package org.alexdev.havana.messages.incoming.rooms.moderation;

import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.texts.TextsManager;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.outgoing.rooms.user.HOTEL_VIEW;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class KICK implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        Player target = PlayerManager.getInstance().getPlayerByName(reader.contents());

        if (target == null || target.getRoomUser().getRoom() == null || target.getRoomUser().getRoom().getId() != room.getId()) {
            return;
        }

        if (target.getDetails().getId() == player.getDetails().getId()) {
            return; // Can't kick yourself!
        }

        // Don't allow kicking if they are room moderators / room owners
        if (target.hasFuse(Fuseright.KICK) || room.isOwner(target.getDetails().getId())) {
            player.send(new ALERT(TextsManager.getInstance().getValue("modtool_rankerror")));
            return;
        }

        // Don't allow kicking if you don't have room rights and don't have fuse rights
        if (!room.hasRights(player.getDetails().getId()) && !player.hasFuse(Fuseright.KICK)) {
            player.send(new ALERT(TextsManager.getInstance().getValue("modtool_rankerror")));
            return;
        }

        target.send(new HOTEL_VIEW());
        target.getRoomUser().kick(false, true);
    }
}
