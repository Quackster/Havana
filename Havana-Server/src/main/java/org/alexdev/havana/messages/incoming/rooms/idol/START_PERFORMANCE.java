package org.alexdev.havana.messages.incoming.rooms.idol;

import org.alexdev.havana.dao.mysql.JukeboxDao;
import org.alexdev.havana.dao.mysql.SongMachineDao;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.song.Song;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class START_PERFORMANCE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;

        }

        if (player.getRoomUser().getRoom().getIdolManager().getPerformer() != player) {
            return;
        }

        int itemId = reader.readInt();
        Item songDisk = null;

        for (Item item : player.getInventory().getItems()) {
            if (item.getVirtualId() == itemId) {
                songDisk = item;
                break;
            }
        }

        Song song = null;

        if (songDisk != null) {
            int songId = JukeboxDao.getSongIdByItem(songDisk.getDatabaseId());
            song = SongMachineDao.getSong(songId);
        }

        player.getRoomUser().getRoom().getIdolManager().startPerformance(song);
    }
}
