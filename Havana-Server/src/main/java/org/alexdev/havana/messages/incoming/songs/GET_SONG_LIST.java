package org.alexdev.havana.messages.incoming.songs;

import org.alexdev.havana.dao.mysql.SongMachineDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.song.Song;
import org.alexdev.havana.messages.outgoing.songs.SONG_LIST;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.List;

public class GET_SONG_LIST implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (room.getItemManager().getSoundMachine() == null) {
            return;
        }

        List<Song> songList = SongMachineDao.getSongList(room.getItemManager().getSoundMachine().getDatabaseId());

        player.send(new SONG_LIST(songList));
    }
}
