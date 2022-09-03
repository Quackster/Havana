package org.alexdev.havana.messages.incoming.songs;

import org.alexdev.havana.dao.mysql.SongMachineDao;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.song.Song;
import org.alexdev.havana.messages.outgoing.songs.SONG_LOCKED;
import org.alexdev.havana.messages.outgoing.songs.USER_SOUND_PACKAGES;
import org.alexdev.havana.messages.outgoing.songs.SONG_INFO;
import org.alexdev.havana.messages.outgoing.songs.SOUND_PACKAGES;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class EDIT_SONG implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (!room.isOwner(player.getDetails().getId()) && !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            return;
        }

        if (room.getItemManager().getSoundMachine() == null) {
            return;
        }

        // We don't want a user to get kicked when making cool beats
        player.getRoomUser().getTimerManager().resetRoomTimer();

        int songId = reader.readInt();
        var song = SongMachineDao.getSong(songId);

        if (song == null) {
            return;
        }

        if (song.getUserId() != player.getDetails().getId()) {
            return;
        }

        if (song.isBurnt()) {
            player.send(new SONG_LOCKED());
        }

        player.send(new SONG_INFO(song));
        player.send(new SOUND_PACKAGES(SongMachineDao.getTracks(room.getItemManager().getSoundMachine().getDatabaseId())));
        player.send(new USER_SOUND_PACKAGES(player.getInventory().getSoundsets()));
    }
}
