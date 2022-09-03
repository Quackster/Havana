package org.alexdev.havana.game.achievements.progressions;

import org.alexdev.havana.dao.mysql.ReferredDao;
import org.alexdev.havana.dao.mysql.TagDao;
import org.alexdev.havana.game.achievements.AchievementInfo;
import org.alexdev.havana.game.achievements.AchievementProgress;
import org.alexdev.havana.game.achievements.user.UserAchievement;
import org.alexdev.havana.game.player.Player;

public class AchievementMGM implements AchievementProgress {
    @Override
    public boolean tryProgress(Player player, UserAchievement userAchievement, AchievementInfo achievementInfo) {
        int progress = ReferredDao.countReferred(player.getDetails().getId());

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
