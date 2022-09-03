package org.alexdev.havana.messages.incoming.songs;

import org.alexdev.havana.dao.mysql.SongMachineDao;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.item.ItemManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.song.Song;
import org.alexdev.havana.messages.outgoing.songs.*;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.StringUtil;

public class SAVE_SONG_EDIT implements MessageEvent {
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
        String title = StringUtil.filterInput(reader.readString(), true);
        String data = StringUtil.filterInput(reader.readString(), true);

        Song song = SongMachineDao.getSong(songId);

        if (song == null) {
            return;
        }

        if (song.getUserId() != player.getDetails().getId()) {
            return;
        }

        var songList = SongMachineDao.getSongList(room.getItemManager().getSoundMachine().getDatabaseId());

        // Don't allow overriding burnt song, alert user
        if (song.isBurnt()) {
            for (Song s : songList) {
                if (s.getId() == songId && s.getTitle().equals(title)) {
                    player.send(new SONG_LOCKED());
                    return;
                }
            }

            SongMachineDao.addSong(player.getDetails().getId(), room.getItemManager().getSoundMachine().getDatabaseId(), title, ItemManager.getInstance().calculateSongLength(data), data);
            songList = SongMachineDao.getSongList(room.getItemManager().getSoundMachine().getDatabaseId());
        } else {
            SongMachineDao.saveSong(songId, title, ItemManager.getInstance().calculateSongLength(data), data);
        }

        player.send(new SONG_INFO(SongMachineDao.getSong(songId)));
        player.send(new SONG_UPDATE());
        player.send(new SONG_LIST(songList));

        room.send(new SONG_PLAYLIST(SongMachineDao.getSongPlaylist(room.getItemManager().getSoundMachine().getDatabaseId())));
    }
}
