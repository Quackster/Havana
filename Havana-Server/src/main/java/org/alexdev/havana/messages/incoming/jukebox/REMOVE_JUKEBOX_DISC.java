package org.alexdev.havana.messages.incoming.jukebox;

import org.alexdev.havana.dao.mysql.JukeboxDao;
import org.alexdev.havana.dao.mysql.SongMachineDao;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.song.jukebox.BurnedDisk;
import org.alexdev.havana.game.song.jukebox.JukeboxManager;
import org.alexdev.havana.messages.outgoing.jukebox.JUKEBOX_DISCS;
import org.alexdev.havana.messages.outgoing.songs.SONG_PLAYLIST;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class REMOVE_JUKEBOX_DISC implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (room.getItemManager().getSoundMachine() == null) {
            return;
        }

        if (!room.hasRights(player.getDetails().getId()) && !player.hasFuse(Fuseright.MOD)) {
            return;
        }

        int slotId = reader.readInt();
        BurnedDisk burnedDisk = JukeboxDao.getDisk(room.getItemManager().getSoundMachine().getDatabaseId(), slotId);

        if (burnedDisk == null) {
            return;
        }

        Item songDisk = null;

        for (Item item : player.getInventory().getItems()) {
            if ((burnedDisk.getItemId() == item.getDatabaseId()) && !item.isVisible()) {
                songDisk = item;
                break;
            }
        }

        if (songDisk == null) {
            return;
        }

        songDisk.setHidden(false);
        player.getInventory().addItem(songDisk); // Re-add at start.

        songDisk.save();

        SongMachineDao.removePlaylistSong(burnedDisk.getSongId(), room.getItemManager().getSoundMachine().getDatabaseId());
        JukeboxDao.editDisk(songDisk.getDatabaseId(), 0, 0);

        player.getInventory().getView("new"); // Refresh hand
        new GET_USER_SONG_DISCS().handle(player, null);

        room.send(new SONG_PLAYLIST(SongMachineDao.getSongPlaylist(room.getItemManager().getSoundMachine().getDatabaseId())));
        room.send(new JUKEBOX_DISCS(JukeboxManager.getInstance().getDisks(room.getItemManager().getSoundMachine().getDatabaseId())));
    }
}
