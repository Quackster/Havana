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
import java.util.List;

public class MSG_ROOMDIMMER_SET_PRESET implements MessageEvent {
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

        int presetId = reader.readInt();
        int backgroundState = reader.readInt();
        String presetColour = reader.readString();
        int presetStrength = reader.readInt();

        //if (!GameConfiguration.getInstance().getBoolean("roomdimmer.scripting.allowed")) {
        // Only check if roomdimmer scripting is allowed
        if (!(presetColour.equals("#74F5F5") ||
                        presetColour.equals("#0053F7") ||
                        presetColour.equals("#E759DE") ||
                        presetColour.equals("#EA4532") ||
                        presetColour.equals("#F2F851") ||
                        presetColour.equals("#82F349") ||
                        presetColour.equals("#000000"))) {
            return; // Nope, no scripting room dimmers allowed here!
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

        List<String> presets = presetData.getRight();

        presets.set(presetId - 1, backgroundState + "," + presetColour + "," + presetStrength);

        item.setCustomData("2," + presetId + "," + backgroundState + "," + presetColour + "," + presetStrength);
        item.updateStatus();
        item.save();

        MoodlightDao.updatePresets(item.getDatabaseId(), presetId, presets);
    }
}