package org.alexdev.havana.game.achievements.progressions;

import org.alexdev.havana.game.achievements.AchievementInfo;
import org.alexdev.havana.game.achievements.AchievementProgress;
import org.alexdev.havana.game.achievements.user.UserAchievement;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.util.config.GameConfiguration;

public class AchievementGraduate implements AchievementProgress {
    @Override
    public boolean tryProgress(Player player, UserAchievement userAchievement, AchievementInfo achievementInfo) {
        if (!GameConfiguration.getInstance().getBoolean("tutorial.enabled")) {
            return false;
        }

        //if (!player.getDetails().getTutorialFlags().contains(1)) {
            userAchievement.setProgress(achievementInfo.getProgressRequired());
            return true;
        //}

        //return false;
    }
}
