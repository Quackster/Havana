package org.alexdev.havana.game.commands.registered.moderation;

import org.alexdev.havana.dao.mysql.CurrencyDao;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE.ChatMessageType;
import org.alexdev.havana.messages.outgoing.user.currencies.CREDIT_BALANCE;
import org.apache.commons.lang3.StringUtils;

public class GiveCreditsCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.COMMUNITY_MANAGER);
    }

    @Override
    public void addArguments() {
        this.arguments.add("user");
        this.arguments.add("credits");
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

        PlayerDetails targetUser = PlayerDao.getDetails(args[0]);

        if (targetUser == null) {
            player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "Could not find user: " + args[0], 0));
            return;
        }

        if (args.length == 1) {
            player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "Credit amount not provided", 0));
            return;
        }

        if (!StringUtils.isNumeric(args[1])) {
            player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "Credit amount not provided", 0));
            return;
        }

        int amount = Integer.parseInt(args[1]);

        CurrencyDao.increaseCredits(targetUser, amount);
        var user = PlayerManager.getInstance().getPlayerById(targetUser.getId());

        if (user != null) {
            user.send(new CREDIT_BALANCE(user.getDetails().getCredits()));
        }

        player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), amount + " credits added to user " + targetUser.getName(), 0));
    }

    @Override
    public String getDescription() {
        return "Add credits to user";
    }
}