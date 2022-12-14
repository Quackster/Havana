package org.alexdev.havana.messages.incoming.tutorial;

import org.alexdev.havana.game.achievements.AchievementManager;
import org.alexdev.havana.game.achievements.AchievementType;
import org.alexdev.havana.game.guides.GuideManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class MSG_REMOVE_ACCOUNT_HELP_TEXT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (!player.getGuideManager().hasTutorial()) {
            return;
        }

        if (player.getGuideManager().isBlockingTutorial()) {
            player.getGuideManager().setBlockingTutorial(false);
            player.getGuideManager().setCancelTutorial(true);
            return;
        }

        int id = reader.readInt();

        if (player.getGuideManager().isCancelTutorial()) {
            player.getGuideManager().setCancelTutorial(false);
            GuideManager.getInstance().tryClearTutorial(player);
            player.getStatisticManager().setLongValue(PlayerStatistic.IS_GUIDABLE, 0);
            AchievementManager.getInstance().tryProgress(AchievementType.ACHIEVEMENT_GRADUATE, player);
            return;
        }

        /*if (id == 1) {
            player.getDetails().getTutorialFlags().clear();
        } else {
            player.getDetails().getTutorialFlags().remove(Integer.valueOf(id));
        }*/

        //TutorialDao.updateTutorialFlags(player.getDetails().getId(), player.getDetails().getTutorialFlags());

        if (!player.getGuideManager().canUseTutorial())
            player.getGuideManager().setCanUseTutorial(true);

        /*if (player.getDetails().getTutorialFlags().isEmpty()) {
            if (!player.getGuideManager().isGuidable() && id == 2) {
                player.getDetails().getTutorialFlags().clear();
                player.getGuideManager().setGuidable(false);

                PlayerStatisticsDao.updateStatistic(player.getDetails().getId(), PlayerStatistic.IS_GUIDABLE, 0);
                TutorialDao.updateTutorialFlags(player.getDetails().getId(), player.getDetails().getTutorialFlags());
            } else if (id == 1 && !player.getDetails().getTutorialFlags().contains(1)) {
                AchievementManager.getInstance().tryProgress(AchievementType.ACHIEVEMENT_GRADUATE, player);

                player.getDetails().getTutorialFlags().addAll(List.of(1, 2, 3, 4, 5, 6, 7, 8));


                player.getGuideManager().setTutorialFinished(true);
                player.getGuideManager().setGuidable(true);
            }
        }*/
    }
}