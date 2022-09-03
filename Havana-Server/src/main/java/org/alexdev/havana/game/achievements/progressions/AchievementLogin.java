package org.alexdev.havana.game.achievements.progressions;

import org.alexdev.havana.game.achievements.AchievementInfo;
import org.alexdev.havana.game.achievements.AchievementProgress;
import org.alexdev.havana.game.achievements.user.UserAchievement;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;
import org.alexdev.havana.util.DateUtil;

import java.util.concurrent.TimeUnit;

public class AchievementLogin implements AchievementProgress {
    @Override
    public boolean tryProgress(Player player, UserAchievement userAchievement, AchievementInfo achievementInfo) {
        int progress = 0;

        if (!player.getDetails().getPreviousRespectDay().equals(DateUtil.getCurrentDate(DateUtil.SHORT_DATE))) {
            String yesterday = DateUtil.getDate(DateUtil.getCurrentTimeSeconds() - TimeUnit.DAYS.toSeconds(1), DateUtil.SHORT_DATE);

            if (yesterday.equals(player.getDetails().getPreviousRespectDay())) {
                progress++;
            } else {
                player.getStatisticManager().setLongValue(PlayerStatistic.DAYS_LOGGED_IN_ROW, 0);
            }
        }

        /*PlayerStatisticsDao.getStatistic(player.getDetails().getId(), PlayerStatistic.DAYS_LOGGED_IN_ROW);
        if (TimeUnit.SECONDS.toDays(daysBtwLastLogin) > 1) {
            progress = 0;
        }
        else if (TimeUnit.SECONDS.toDays(daysBtwLastLogin) == 1) {
            progress++;
        }*/

        if (progress > 0) {
            player.getStatisticManager().incrementValue(PlayerStatistic.DAYS_LOGGED_IN_ROW, progress);
            progress = player.getStatisticManager().getIntValue(PlayerStatistic.DAYS_LOGGED_IN_ROW);
        }

        if (progress > achievementInfo.getProgressRequired()) {
            progress = achievementInfo.getProgressRequired();
        }

        if (progress > 0) {
            userAchievement.setProgress(progress);
            return true;
        }

        return false;
    }
}