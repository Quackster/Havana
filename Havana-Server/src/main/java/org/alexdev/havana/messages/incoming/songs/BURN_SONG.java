package org.alexdev.havana.messages.incoming.songs;

import org.alexdev.havana.dao.mysql.CurrencyDao;
import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.dao.mysql.JukeboxDao;
import org.alexdev.havana.dao.mysql.SongMachineDao;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.ItemManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.song.Song;
import org.alexdev.havana.messages.outgoing.user.currencies.CREDIT_BALANCE;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.Calendar;

public class BURN_SONG implements MessageEvent {
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

        if (player.getDetails().getCredits() <= 0) {
            return;
        }

        int songId = reader.readInt();

        Song song = SongMachineDao.getSong(songId);

        if (song == null) {
            return;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());

        Item item = new Item();
        item.setOwnerId(player.getDetails().getId());
        item.setDefinitionId(ItemManager.getInstance().getDefinitionBySprite("song_disk").getId());
        item.setCustomData(player.getDetails().getName() + (char)10 +
                cal.get(Calendar.DAY_OF_MONTH) + (char)10 +
                cal.get(Calendar.MONTH) + (char)10 +
                cal.get(Calendar.YEAR) + (char)10 +
                ItemManager.getInstance().calculateSongLength(song.getData()) + (char)10 +
                song.getTitle());

        ItemDao.newItem(item);

        player.getInventory().addItem(item);
        player.getInventory().getView("new");

        JukeboxDao.addDisk(item.getDatabaseId(), 0, songId);
        JukeboxDao.setBurned(songId, true);

        CurrencyDao.decreaseCredits(player.getDetails(), 1);
        player.send(new CREDIT_BALANCE(player.getDetails().getCredits()));
    }
}
