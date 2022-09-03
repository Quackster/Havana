package org.alexdev.havana.messages.incoming.songs;

import org.alexdev.havana.dao.mysql.SongMachineDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.songs.SONG_INFO;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class GET_SONG_INFO implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (room.getItemManager().getSoundMachine() == null) {
            return;
        }

        int songId = reader.readInt();

        player.send(new SONG_INFO(SongMachineDao.getSong(songId)));
    }
}
