package org.alexdev.havana.messages.incoming.rooms;

import org.alexdev.havana.dao.mysql.RoomBanDao;
import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.outgoing.alerts.LOCALISED_ERROR;
import org.alexdev.havana.messages.outgoing.navigator.CANTCONNECT;
import org.alexdev.havana.messages.outgoing.rooms.DOORBELL_WAIT;
import org.alexdev.havana.messages.outgoing.rooms.FLATNOTALLOWEDTOENTER;
import org.alexdev.havana.messages.outgoing.rooms.FLAT_LETIN;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class TRYFLAT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int roomId = reader.remainingBytes().length > 0 ? reader.readInt() : player.getRoomUser().getRoom().getId();
        String password = "";

        if (reader.remainingBytes().length > 0) {
            password = reader.readString();
        }

        Room room = RoomManager.getInstance().getRoomById(roomId);

        if (room == null) {
            room = RoomDao.getRoomById(roomId);
        }

        if (room == null) {
                return;

        }

        /*
        if (room == null) {
            if (player.getDetails().getTemporaryRoom() != null) {
                room = player.getDetails().getTemporaryRoom();
            } else {
                return;
            }
        }

         */

        if (room.getData().getTotalVisitorsNow() >= room.getData().getTotalVisitorsMax() && !player.hasFuse(Fuseright.ENTER_FULL_ROOMS)) {
            player.send(new CANTCONNECT(CANTCONNECT.ConnectError.ROOM_FULL));
            return;
        }

        if (!player.hasFuse(Fuseright.ENTER_LOCKED_ROOMS)) {
            if (RoomBanDao.hasBan(player.getDetails().getId(), roomId)) {
                player.send(new CANTCONNECT(CANTCONNECT.ConnectError.BANNED));
                return;
            }

            if (player.getRoomUser().getAuthenticateId() != roomId){
                if (room.getData().getAccessTypeId() == 1 && !room.hasRights(player.getDetails().getId(), false) && !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
                    if (rangDoorbell(room, player)) {
                        player.send(new DOORBELL_WAIT());
                    } else {
                        player.send(new FLATNOTALLOWEDTOENTER());
                    }

                    return;
                }

                if (room.getData().getAccessTypeId() == 2 && !room.isOwner(player.getDetails().getId()) && !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
                    if (!password.equals(room.getData().getPassword())) {
                        player.send(new LOCALISED_ERROR(-100002));
                        return;
                    }
                }
            }
        }

        // User switched rooms, cancel teleportation
        if (player.getRoomUser().getAuthenticateTelporterRoomId() != roomId) {
            player.getRoomUser().setAuthenticateTelporterId(-1);
            player.getRoomUser().setAuthenticateTelporterRoomId(-1);
        }

        player.getRoomUser().setAuthenticateId(roomId);
        player.send(new FLAT_LETIN());
    }

    private boolean rangDoorbell(Room room, Player player) {
        boolean sentWithRights = false;

        for (Player user : room.getEntityManager().getPlayers()) {
            if (!room.hasRights(user.getDetails().getId()) && !player.hasFuse(Fuseright.MOD)) {
                continue;
            }

            user.send(new DOORBELL_WAIT(player.getDetails().getName()));
            sentWithRights = true;
        }

        return sentWithRights;
    }
}
