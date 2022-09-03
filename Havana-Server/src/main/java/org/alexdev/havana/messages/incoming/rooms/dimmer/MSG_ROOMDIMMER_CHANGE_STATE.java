package org.alexdev.havana.messages.incoming.rooms.dimmer;

import org.alexdev.havana.dao.mysql.MoodlightDao;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE.ChatMessageType;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

public class MSG_ROOMDIMMER_CHANGE_STATE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (!room.isOwner(player.getDetails().getId()) && !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            return;
        }

        Item item = room.getItemManager().getMoodlight();

        if (item == null) {
            return;
        }

        if (!item.hasBehaviour(ItemBehaviour.ROOMDIMMER)) {
            return;
        }

        if (room.isOwner(player.getDetails().getId()) || player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            if (!MoodlightDao.containsPreset(item.getDatabaseId())) {
                MoodlightDao.createPresets(item.getDatabaseId());
            }
        }

        // Cancel RainbowTask because the operator decided to use their own moodlight settings.
        if (room.getTaskManager().hasTask("RainbowTask")) {
            room.getTaskManager().cancelTask("RainbowTask");
            player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "Rainbow room dimmer cycle has stopped", 0));
        }

        Pair<Integer, ArrayList<String>> presetData = MoodlightDao.getPresets(item.getDatabaseId());

        if (presetData == null) {
            return;
        }

        int currentPreset = presetData.getLeft();
        ArrayList<String> presets = presetData.getRight();

        boolean isEnabled = !(item.getCustomData().charAt(0) == '2');

        item.setCustomData((isEnabled ? "2" : "1") + "," + currentPreset + "," + presets.get(currentPreset - 1));
        item.updateStatus();
        item.save();
    }
}