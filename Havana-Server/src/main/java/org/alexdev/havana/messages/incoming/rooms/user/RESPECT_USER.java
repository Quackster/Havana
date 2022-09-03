package org.alexdev.havana.messages.incoming.rooms.user;

import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.achievements.AchievementManager;
import org.alexdev.havana.game.achievements.AchievementType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.rooms.user.RESPECT_NOTIFICATION;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class RESPECT_USER implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!(player.getDetails().getDailyRespectPoints() > 0)) {
            return;
        }

        int userId = reader.readInt();

        Player target = PlayerManager.getInstance().getPlayerById(userId);

        if (target == null) {
            return;
        }

        if (target.getDetails().getId() == player.getDetails().getId()) {
            return;
        }

        if (target.getRoomUser().getRoom() == null || target.getRoomUser().getRoom().getId() != room.getId()) {
            return;
        }

        // Add respect given
        player.getDetails().setRespectGiven(player.getDetails().getRespectGiven() + 1);
        AchievementManager.getInstance().tryProgress(AchievementType.ACHIEVEMENT_RESPECT_GIVEN, player);

        // Increase respect points for target
       // target.getDetails().setRespectPoints(target.getDetails().getRespectPoints() + 1);
        room.send(new RESPECT_NOTIFICATION(target.getDetails().getId(), 1));
        AchievementManager.getInstance().tryProgress(AchievementType.ACHIEVEMENT_RESPECT_EARNED, target);

        // Lower daily respect points for user
        player.getDetails().setDailyRespectPoints(player.getDetails().getDailyRespectPoints() - 1);

        // Save both users
        PlayerDao.saveRespect(target.getDetails().getId(), target.getDetails().getDailyRespectPoints(), target.getDetails().getRespectPoints(), target.getDetails().getRespectDay(), target.getDetails().getRespectGiven());
        PlayerDao.saveRespect(player.getDetails().getId(), player.getDetails().getDailyRespectPoints(), player.getDetails().getRespectPoints(), player.getDetails().getRespectDay(), player.getDetails().getRespectGiven());
    }
}
