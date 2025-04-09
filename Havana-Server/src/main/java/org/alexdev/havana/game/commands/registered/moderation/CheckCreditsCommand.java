package org.alexdev.havana.game.commands.registered.moderation;

import org.alexdev.havana.dao.mysql.CurrencyDao;
import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE.ChatMessageType;
import org.alexdev.havana.messages.outgoing.user.currencies.CREDIT_BALANCE;
import org.apache.commons.lang3.StringUtils;

public class CheckCreditsCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.MODERATOR);
    }

    @Override
    public void addArguments() {
        this.arguments.add("user");
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        // :givebadge Alex NL1

        // should refuse to give badges that belong to ranks
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Player targetUser = PlayerManager.getInstance().getPlayerByName(args[0]);

        if (targetUser == null) {
            player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "Could not find user: " + args[0], 0));
            return;
        }

        player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), targetUser.getDetails().getName() + " has " + targetUser.getDetails().getCredits() + " credits", 0));
    }

    @Override
    public String getDescription() {
        return "Checks the credit amount for an user";
    }
}