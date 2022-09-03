package org.alexdev.havana.game.achievements.progressions;

import org.alexdev.havana.dao.mysql.TagDao;
import org.alexdev.havana.game.achievements.AchievementInfo;
import org.alexdev.havana.game.achievements.AchievementProgress;
import org.alexdev.havana.game.achievements.user.UserAchievement;
import org.alexdev.havana.game.player.Player;

public class AchievementTags implements AchievementProgress {
    @Override
    public boolean tryProgress(Player player, UserAchievement userAchievement, AchievementInfo achievementInfo) {
        var tagList = TagDao.getUserTags(player.getDetails().getId());

        int progress = tagList.size();

        if (progress >= 5) {
            progress = achievementInfo.getProgressRequired();
        }

        if (progress >= achievementInfo.getProgressRequired()) {
            userAchievement.setProgress(progress);
            return true;
        }


        return false;
    }
}