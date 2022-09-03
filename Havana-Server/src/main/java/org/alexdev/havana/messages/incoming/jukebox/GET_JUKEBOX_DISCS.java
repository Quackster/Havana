package org.alexdev.havana.messages.incoming.jukebox;

import org.alexdev.havana.dao.mysql.JukeboxDao;
import org.alexdev.havana.dao.mysql.SongMachineDao;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.song.jukebox.BurnedDisk;
import org.alexdev.havana.game.song.Song;
import org.alexdev.havana.game.song.jukebox.JukeboxManager;
import org.alexdev.havana.messages.outgoing.jukebox.JUKEBOX_DISCS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.HashMap;
import java.util.Map;

public class GET_JUKEBOX_DISCS implements MessageEvent {
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


        player.send(new JUKEBOX_DISCS(JukeboxManager.getInstance().getDisks(room.getItemManager().getSoundMachine().getDatabaseId())));
    }
}
