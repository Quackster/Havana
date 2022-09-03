package org.alexdev.havana.game.achievements.progressions;

import org.alexdev.havana.game.achievements.AchievementInfo;
import org.alexdev.havana.game.achievements.AchievementProgress;
import org.alexdev.havana.game.achievements.user.UserAchievement;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;

public class AchievementGamePlayed implements AchievementProgress {
    @Override
    public boolean tryProgress(Player player, UserAchievement userAchievement, AchievementInfo achievementInfo) {
        int progress = player.getStatisticManager().getIntValue(PlayerStatistic.BATTLEBALL_GAMES_WON) + player.getStatisticManager().getIntValue(PlayerStatistic.SNOWSTORM_GAMES_WON);

        if (progress >= userAchievement.getProgress()) {
            if (progress > achievementInfo.getProgressRequired()) {
                progress = achievementInfo.getProgressRequired();
            }

            userAchievement.setProgress(progress);
            return true;
        }

        return false;
    }
}
