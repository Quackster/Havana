package org.alexdev.havana.game.commands.registered;

import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.item.ItemManager;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.rooms.items.PLACE_FLOORITEM;
import org.alexdev.havana.util.StringUtil;

public class TalkCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.ADMINISTRATOR);
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        String talkMessage;

        if (args.length > 0) {
            talkMessage = StringUtil.filterInput(String.join(" ", args), true);
        } else {
            talkMessage = "";
        }

        TalkCommand.createVoiceSpeakMessage(player.getRoomUser().getRoom(), talkMessage);
    }

    public static void createVoiceSpeakMessage(Room room, String text) {
        // 'Speaker'
        Item pItem = new Item();
        pItem.setVirtualId(999);
        pItem.setPosition(new Position(255, 255, -1f));
        pItem.setCustomData("voiceSpeak(\"" + text + "\")");
        pItem.setDefinitionId(ItemManager.getInstance().getDefinitionBySprite("spotlight").getId());
        room.send(new PLACE_FLOORITEM(pItem));
    }

    @Override
    public String getDescription() {
        return "Voice to text command";
    }
}
