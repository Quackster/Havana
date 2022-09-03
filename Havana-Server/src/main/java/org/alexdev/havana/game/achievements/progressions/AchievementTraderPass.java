package org.alexdev.havana.game.achievements.progressions;

import org.alexdev.havana.game.achievements.AchievementInfo;
import org.alexdev.havana.game.achievements.AchievementProgress;
import org.alexdev.havana.game.achievements.user.UserAchievement;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.util.config.GameConfiguration;

public class AchievementTraderPass implements AchievementProgress {
    @Override
    public boolean tryProgress(Player player, UserAchievement userAchievement, AchievementInfo achievementInfo) {
        /*var canUseTrade = true;TimeUnit.SECONDS.toDays(DateUtil.getCurrentTimeSeconds() - player.getDetails().getJoinDate()) >= 3 &&
                player.getStatisticManager().getIntValue(PlayerStatistic.ONLINE_TIME) >= TimeUnit.MINUTES.toHours(60) && player.getDetails().isTradeEnabled();*/

        if (player.getDetails().isTradeEnabled()/* && isActivated(player.getStatisticManager().getValue(PlayerStatistic.ACTIVATION_CODE))*/) {
            userAchievement.setProgress(achievementInfo.getProgressRequired());
            return true;
        }

        return false;
    }

    public static boolean isActivated(String activationCode) {
        if (!GameConfiguration.getInstance().getBoolean("email.smtp.enable")) {
            return true;
        }

        return activationCode == null;
    }

}
