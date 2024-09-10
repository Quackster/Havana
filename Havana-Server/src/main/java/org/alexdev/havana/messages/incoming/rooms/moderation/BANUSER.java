package org.alexdev.havana.messages.incoming.rooms.moderation;

import org.alexdev.havana.dao.mysql.RoomBanDao;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.texts.TextsManager;
import org.alexdev.havana.messages.outgoing.navigator.CANTCONNECT;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.DateUtil;

import java.util.concurrent.TimeUnit;

public class BANUSER implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Player target = null;//PlayerManager.getInstance().getPlayerByName(playerName);
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!player.getNetwork().isFlashConnection()) {
            target = PlayerManager.getInstance().getPlayerByName(reader.contents());
        } else {
            target = PlayerManager.getInstance().getPlayerById(reader.readInt());
        }

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

        // Don't allow kicking if you aren't room owner or don't have fuse rights
        if (!room.isOwner(player.getDetails().getId()) && !player.hasFuse(Fuseright.KICK)) {
            player.send(new ALERT(TextsManager.getInstance().getValue("modtool_rankerror")));
            return;
        }

        target.send(new CANTCONNECT(CANTCONNECT.ConnectError.BANNED));
        target.getRoomUser().kick(false, true);

        RoomBanDao.addBan(target.getDetails().getId(), room.getId(), DateUtil.getCurrentTimeSeconds() + TimeUnit.HOURS.toSeconds(6));
    }
}
