package org.alexdev.havana.game.achievements;

import org.alexdev.havana.game.achievements.user.UserAchievement;
import org.alexdev.havana.game.player.Player;

public interface AchievementProgress {
    boolean tryProgress(Player player, UserAchievement userAchievement, AchievementInfo achievementInfo);
}
