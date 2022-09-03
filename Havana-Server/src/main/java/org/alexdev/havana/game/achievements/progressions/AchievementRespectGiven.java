package org.alexdev.havana.game.achievements.progressions;

import org.alexdev.havana.game.achievements.AchievementInfo;
import org.alexdev.havana.game.achievements.AchievementProgress;
import org.alexdev.havana.game.achievements.user.UserAchievement;
import org.alexdev.havana.game.player.Player;

public class AchievementRespectGiven implements AchievementProgress {
    @Override
    public boolean tryProgress(Player player, UserAchievement userAchievement, AchievementInfo achievementInfo) {
        userAchievement.setProgress(userAchievement.getProgress() + 1);
        return true;
    }
}
