package org.alexdev.havana.messages.incoming.rooms.dimmer;

import org.alexdev.havana.dao.mysql.MoodlightDao;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.rooms.dimmer.MOODLIGHT_PRESETS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

public class MSG_ROOMDIMMER_GET_PRESETS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();
        Item item = room.getItemManager().getMoodlight();

        if (item == null) {
            return;
        }

        if (room.isOwner(player.getDetails().getId()) || player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            if (!MoodlightDao.containsPreset(item.getDatabaseId())) {
                MoodlightDao.createPresets(item.getDatabaseId());
            }
        }

        Pair<Integer, ArrayList<String>> presetData = MoodlightDao.getPresets(item.getDatabaseId());

        if (presetData == null) {
            return;
        }

        int currentPreset = presetData.getLeft();
        ArrayList<String> presets = presetData.getRight();

        player.send(new MOODLIGHT_PRESETS(currentPreset, presets));
    }
}