package org.alexdev.havana.game.commands.registered.groups;

import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE;

public class RoomMuteCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.MODERATOR);
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

        player.getRoomUser().getRoom().getData().setRoomMuted(!player.getRoomUser().getRoom().getData().isRoomMuted());
        player.send(new CHAT_MESSAGE(CHAT_MESSAGE.ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "The room is now " + (player.getRoomUser().getRoom().getData().isRoomMuted() ? "muted" : "unmuted"), 0));
    }

    @Override
    public String getDescription() {
        return "Mutes/unmutes a room";
    }
}
