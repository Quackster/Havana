package org.alexdev.havana.messages.incoming.rooms;

import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.games.player.GamePlayer;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.outgoing.navigator.CANTCONNECT;
import org.alexdev.havana.messages.outgoing.navigator.CANTCONNECT.QueueError;
import org.alexdev.havana.messages.outgoing.rooms.OPEN_CONNECTION;
import org.alexdev.havana.messages.outgoing.rooms.ROOM_URL;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class ROOM_DIRECTORY implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        reader.readInt();
        int roomId = reader.readInt();

        GamePlayer gamePlayer = player.getRoomUser().getGamePlayer();

        if (roomId == -1 && gamePlayer != null && gamePlayer.isEnteringGame()) {
            Room room = gamePlayer.getGame().getRoom();
            room.getEntityManager().enterRoom(player, gamePlayer.getSpawnPosition());
            return;
        }

        Room room = RoomManager.getInstance().getRoomById(roomId);

            if (room == null) {
                return;
            }

        if (room.isClubOnly() && !player.getDetails().hasClubSubscription()) {
            if (player.getRoomUser().getRoom() != null)
                player.getRoomUser().getRoom().getEntityManager().leaveRoom(player, false);

            player.send(new CANTCONNECT(QueueError.CLUB_ONLY));
            return;
        }

        /*if (room.isClubOnly()) {
            player.send(new ROOMQUEUEDATA(3));

            GameScheduler.getInstance().getService().schedule(()->{
                player.send(new ROOMQUEUEDATA(2));
            }, 3, TimeUnit.SECONDS);
            return;
        }*/

        if (room.isPublicRoom()) {
            if (room.getData().getTotalVisitorsNow() >= room.getData().getTotalVisitorsMax() && !player.hasFuse(Fuseright.ENTER_FULL_ROOMS)) {
                player.send(new CANTCONNECT(CANTCONNECT.ConnectError.ROOM_FULL));
                return;
            }
        }

        player.send(new OPEN_CONNECTION());
        player.send(new ROOM_URL());

        if (room.isPublicRoom()) {
            room.getEntityManager().enterRoom(player, null);
        }
    }
}