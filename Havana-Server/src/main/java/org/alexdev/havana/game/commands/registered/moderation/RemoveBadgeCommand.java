package org.alexdev.havana.game.commands.registered.moderation;

import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.badges.BadgeManager;
import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE.ChatMessageType;

public class RemoveBadgeCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.COMMUNITY_MANAGER);
    }

    @Override
    public void addArguments() {
        this.arguments.add("user");
        this.arguments.add("badge");
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
            player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "Badge code not provided", 0));
            return;
        }

        String badge = args[1];

        if (badge.startsWith("GL") || badge.startsWith("ACH_") || badge.equalsIgnoreCase("Z64")) {
            return;
        }

        var badgeManager = new BadgeManager(targetUser.getId());

        // Check if user already owns badge
        if (!badgeManager.hasBadge(badge)) {
            player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "User " + targetUser.getName() + " does not have this badge.", 0));
            return;
        }

        // Remove badge
        badgeManager.removeBadge(badge);
        player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "Badge " + badge + " removed from user " + targetUser.getName(), 0));
    }

    @Override
    public String getDescription() {
        return "Remove badge from user";
    }
}