package org.alexdev.havana.game.commands.registered.groups;

import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.moderation.actions.ModeratorBanUserAction;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class BanCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.MODERATOR);
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        String name = args[0];

        int minutes = StringUtils.isNumeric(args[1]) ? Integer.parseInt(args[1]) : 0;
        var reason = StringUtil.filterInput(Arrays.asList(args).stream().skip(2).collect(Collectors.joining(" ")), true);

        var response = ModeratorBanUserAction.ban(player.getDetails(), reason, "", name, TimeUnit.MINUTES.toSeconds(minutes), true, true);
        player.send(new ALERT(response));
    }

    @Override
    public void addArguments() {
        arguments.add("user");
        arguments.add("minutes");
        arguments.add("reason");
    }

    @Override
    public String getDescription() {
        return "Temporarily bans a given user";
    }
}
