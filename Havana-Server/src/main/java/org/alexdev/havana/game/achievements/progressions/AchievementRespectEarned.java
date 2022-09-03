package org.alexdev.havana.game.achievements.progressions;

import org.alexdev.havana.dao.mysql.AchievementDao;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.achievements.AchievementInfo;
import org.alexdev.havana.game.achievements.AchievementManager;
import org.alexdev.havana.game.achievements.AchievementProgress;
import org.alexdev.havana.game.achievements.AchievementType;
import org.alexdev.havana.game.achievements.user.UserAchievement;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.rooms.user.RESPECT_NOTIFICATION;

import java.util.stream.Collectors;

public class AchievementRespectEarned implements AchievementProgress {
    public static void convertOldPoints(Player player) {
        if (!(player.getDetails().getRespectPoints() > 0)) {
            return;
        }

        for (int i = 1; i < 11; i++) {
            if (!(player.getDetails().getRespectPoints() > 0)) {
                continue;
            }

            AchievementInfo achievementInfo = AchievementManager.getInstance().locateAchievement(AchievementType.ACHIEVEMENT_RESPECT_EARNED, i);

            if (player.getDetails().getRespectPoints() >= achievementInfo.getProgressRequired()) {
                player.getDetails().setRespectPoints(player.getDetails().getRespectPoints() - achievementInfo.getProgressRequired());

                var achievement = player.getAchievementManager().locateAchievement(AchievementType.ACHIEVEMENT_RESPECT_EARNED);
                achievement.setProgress(achievementInfo.getProgressRequired() - 1);
                AchievementDao.saveUserAchievement(player.getDetails().getId(), achievement);
            } else {
                int currentPoints = player.getDetails().getRespectPoints();
                player.getDetails().setRespectPoints(0);

                var achievement = player.getAchievementManager().locateAchievement(AchievementType.ACHIEVEMENT_RESPECT_EARNED);
                achievement.setProgress(currentPoints - 1);
                AchievementDao.saveUserAchievement(player.getDetails().getId(), achievement);
            }

            AchievementManager.getInstance().tryProgress(AchievementType.ACHIEVEMENT_RESPECT_EARNED, player);
        }

        if (player.getDetails().getRespectPoints() > 0) {
            player.getDetails().setRespectPoints(0);
        }

        PlayerDao.saveRespect(player.getDetails().getId(), player.getDetails().getDailyRespectPoints(), player.getDetails().getRespectPoints(), player.getDetails().getRespectDay(), player.getDetails().getRespectGiven());
    }
    @Override
    public boolean tryProgress(Player player, UserAchievement userAchievement, AchievementInfo achievementInfo) {
        int progress = userAchievement.getProgress() + 1;

        if (progress > achievementInfo.getProgressRequired()) {
            progress = achievementInfo.getProgressRequired();
        }

        if (progress != userAchievement.getProgress()) {
            userAchievement.setProgress(progress);
            return true;
        }

        return false;
    }
}
