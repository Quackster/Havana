package org.alexdev.havana.messages.incoming.jukebox;

import org.alexdev.havana.dao.mysql.JukeboxDao;
import org.alexdev.havana.dao.mysql.SongMachineDao;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.song.Song;
import org.alexdev.havana.game.song.jukebox.JukeboxManager;
import org.alexdev.havana.messages.outgoing.jukebox.JUKEBOX_DISCS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class ADD_JUKEBOX_DISC implements MessageEvent {
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

        int itemId = reader.readInt();
        int slotId = reader.readInt();

        Item songDisk = null;

        for (Item item : player.getInventory().getItems()) {
            if (item.getVirtualId() == itemId && item.isVisible()) {
                songDisk = item;
                break;
            }
        }

        if (songDisk == null) {
            return;
        }

        songDisk.setHidden(true);
        songDisk.save();

        player.getInventory().getView("new"); // Refresh hand

        int songId = JukeboxDao.getSongIdByItem(songDisk.getDatabaseId());
        Song song = SongMachineDao.getSong(songId);

        if (song == null) {
            return;
        }

        if (slotId < 1 || slotId > 10) {
            return;
        }

        JukeboxDao.editDisk(songDisk.getDatabaseId(), room.getItemManager().getSoundMachine().getDatabaseId(), slotId);

        room.send(new JUKEBOX_DISCS(JukeboxManager.getInstance().getDisks(room.getItemManager().getSoundMachine().getDatabaseId())));
        new GET_USER_SONG_DISCS().handle(player, null);
    }
}
