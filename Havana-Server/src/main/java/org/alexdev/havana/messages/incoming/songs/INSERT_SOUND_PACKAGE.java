package org.alexdev.havana.messages.incoming.songs;

import org.alexdev.havana.dao.mysql.SongMachineDao;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.songs.USER_SOUND_PACKAGES;
import org.alexdev.havana.messages.outgoing.songs.SOUND_PACKAGES;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.util.MalformedPacketException;

import java.sql.SQLException;
import java.util.Map;

public class INSERT_SOUND_PACKAGE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws SQLException, MalformedPacketException {
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

        Map<Integer, Integer> tracks = SongMachineDao.getTracks(room.getItemManager().getSoundMachine().getDatabaseId());

        int soundSetId = reader.readInt();
        int slotId = 1;//reader.readInt() - 1;

        while (tracks.containsKey(slotId)) {
            slotId++;
        }

        if (tracks.containsKey(slotId) || slotId >= 5 || slotId < 0) {
            return;
        }

        int trackId = -1;
        Item trackItem = null;

        for (Item item : player.getInventory().getItems()) {
            if (item.hasBehaviour(ItemBehaviour.SOUND_MACHINE_SAMPLE_SET) && item.isVisible()) {
                int songId = Integer.parseInt(item.getDefinition().getSprite().split("_")[2]);

                if (songId == soundSetId) {
                    trackItem = item;
                    trackId = songId;
                    break;
                }
            }
        }

        if (trackId == -1) {
            return;
        }

        trackItem.setHidden(true);
        trackItem.save();

        player.getInventory().getView("new");
        SongMachineDao.addTrack(room.getItemManager().getSoundMachine().getDatabaseId(), soundSetId, slotId);

        player.send(new SOUND_PACKAGES(SongMachineDao.getTracks(room.getItemManager().getSoundMachine().getDatabaseId())));
        player.send(new USER_SOUND_PACKAGES(player.getInventory().getSoundsets()));
    }
}
