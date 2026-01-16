package org.alexdev.havana.game.commands.registered;

import org.alexdev.havana.dao.mysql.GuideDao;
import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.game.player.guides.GuidingData;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.StringUtil;
import org.alexdev.havana.util.config.GameConfiguration;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;

public class GuideStatusCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.NORMAL);
    }

    @Override
    public void addArguments() { }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        var guiding = GuideDao.getGuidedBy(player.getDetails().getId());
        guiding.sort(Comparator.comparingLong(GuidingData::getLastOnline).reversed());

        StringBuilder alert = new StringBuilder();
        alert.append("You are guiding " + guiding.size() + " users. Remember a user needs an online time for at least " + TimeUnit.MINUTES.toDays(GameConfiguration.getInstance().getInteger("guide.completion.minutes")) + " days to be guided.<br><br>");

        alert.append(StringUtil.rightPad("Username", 20, " "));
        alert.append(StringUtil.rightPad("Time Online", 58, " "));
        alert.append(StringUtil.rightPad("Last Online", 15, " "));
        alert.append("<br>");

        for (var guideData : guiding) {
            alert.append(StringUtil.rightPad(guideData.getUsername(), 20, " "));
            alert.append(StringUtil.rightPad(DateUtil.getReadableSeconds(guideData.getTimeOnline()), 58, " "));
            alert.append(StringUtil.rightPad(DateUtil.getDate(guideData.getLastOnline(), DateUtil.SHORT_DATE), 15, " "));
            alert.append("<br>");
        }

        player.send(new ALERT(alert.toString()));
    }

    @Override
    public String getDescription() {
        return "View guide statistics";
    }
}