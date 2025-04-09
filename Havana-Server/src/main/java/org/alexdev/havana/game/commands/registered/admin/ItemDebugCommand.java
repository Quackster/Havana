package org.alexdev.havana.game.commands.registered.admin;

import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE.ChatMessageType;

public class ItemDebugCommand extends Command {
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

        if (player.getRoomUser().getRoom() == null) {
            return;
        }


        player.send(new CHAT_MESSAGE(ChatMessageType.CHAT, player.getRoomUser().getInstanceId(), "Item debug has been set to " + (!player.getRoomUser().hasItemDebug()), 0));
        player.getRoomUser().setHasItemDebug(!player.getRoomUser().hasItemDebug());
    }

    @Override
    public String getDescription() {
        return "Enable/disable item debug";
    }
}
