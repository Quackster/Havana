package org.alexdev.havana.game.achievements.progressions;

import org.alexdev.havana.game.achievements.AchievementInfo;
import org.alexdev.havana.game.achievements.AchievementProgress;
import org.alexdev.havana.game.achievements.user.UserAchievement;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;

import java.util.concurrent.TimeUnit;

public class AchievementAllTimeHotelPresence implements AchievementProgress {
    @Override
    public boolean tryProgress(Player player, UserAchievement userAchievement, AchievementInfo achievementInfo) {
        int daysSince = (int) Math.floor(TimeUnit.SECONDS.toHours(player.getStatisticManager().getIntValue(PlayerStatistic.ONLINE_TIME)));//AchievementDao.getOnlineTime(player.getDetails().getId()))));

        if (daysSince >= achievementInfo.getProgressRequired()) {
            userAchievement.setProgress(achievementInfo.getProgressRequired());
            return true;
        }

        return false;
    }
}
