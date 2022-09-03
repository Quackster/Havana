package org.alexdev.havana.messages.incoming.songs;

import org.alexdev.havana.dao.mysql.SongMachineDao;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.songs.SONG_PLAYLIST;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class GET_PLAY_LIST implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (room.getItemManager().getSoundMachine() == null) {
            return;
        }

        if (room.getItemManager().getSoundMachine().hasBehaviour(ItemBehaviour.SOUND_MACHINE) ||
            room.getItemManager().getSoundMachine().hasBehaviour(ItemBehaviour.JUKEBOX)) {
            player.send(new SONG_PLAYLIST(SongMachineDao.getSongPlaylist(room.getItemManager().getSoundMachine().getDatabaseId())));
        }
    }
}
