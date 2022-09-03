package org.alexdev.havana.game.commands.registered.moderation;

import org.alexdev.havana.dao.mysql.BadgeDao;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.badges.BadgeManager;
import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE.ChatMessageType;
import org.alexdev.havana.messages.outgoing.rooms.user.FIGURE_CHANGE;

import java.util.List;

public class GiveBadgeCommand extends Command {
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

        if (args.length == 1) {
            player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "Badge code not provided", 0));
            return;
        }

        PlayerDetails targetUserDetails = PlayerDao.getDetails(args[0]);

        if (targetUserDetails == null) {
            player.send(new ALERT("Could not find user: " + args[0]));
            return;
        }

        String badge = args[1];

        if (badge.startsWith("GL") || badge.startsWith("ACH_") || badge.equalsIgnoreCase("Z64")) {
            return;
        }

        Player targetUser = PlayerManager.getInstance().getPlayerByName(args[0]);

        if (targetUser == null) {
            var badgeManager = new BadgeManager(targetUserDetails.getId());

            // Check if user already owns badge
            if (badgeManager.hasBadge(badge)) {
                player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "User " + targetUserDetails.getName() + " already owns this badge.", 0));
                return;
            }

            List<String> rankBadges = BadgeDao.getRankBadges();

            // Check if badge code is a rank badge
            if (rankBadges.contains(badge)) {
                player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "This badge belongs to a certain rank. If you would like to give " + targetUserDetails.getName() + " this badge, increase their rank.", 0));
                return;
            }

            // Add badge
            badgeManager.tryAddBadge(badge, null);
            player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "Badge " + badge + " added to user " + targetUserDetails.getName(), 0));
        } else {
            // Check if user already owns badge
            if (targetUser.getBadgeManager().hasBadge(badge)) {
                player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "User " + targetUserDetails.getName() + " already owns this badge.", 0));
                return;
            }

            List<String> rankBadges = BadgeDao.getRankBadges();

            // Check if badge code is a rank badge
            if (rankBadges.contains(badge)) {
                player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "This badge belongs to a certain rank. If you would like to give " + targetUserDetails.getName() + " this badge, increase their rank.", 0));
                return;
            }

            // Add badge
            targetUser.getBadgeManager().tryAddBadge(badge, null, 0);

            Room targetRoom = targetUser.getRoomUser().getRoom();

            // Let other room users know something changed if targetUser is inside a room
            if (targetRoom != null) {
                targetRoom.send(new FIGURE_CHANGE(targetUser.getRoomUser().getInstanceId(), targetUserDetails));
            }

            player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "Badge " + badge + " added to user " + targetUserDetails.getName(), 0));
        }
    }

    @Override
    public String getDescription() {
        return "Add badge to user";
    }
}