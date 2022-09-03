package org.alexdev.havana.game.commands.registered;

import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.commands.CommandManager;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class HelpCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.NORMAL);
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        var availableCommands = CommandManager.getCommands().stream().filter(command -> CommandManager.getInstance().hasPermission(entity.getDetails(), command.getKey()[0])).collect(Collectors.toList());

        int pageId = 1;
        String parsePageId = args.length > 0 ? args[0] : "1";
        String commandFilter = null;

        if (!StringUtils.isNumeric(parsePageId))
        {
            commandFilter = args[0];
            parsePageId = args.length > 1 ? args[1] : "1";
        }

        if (StringUtils.isNumeric(parsePageId)) {
            pageId = Integer.parseInt(parsePageId);
        }

        if (commandFilter != null) {
            final String finalCommandFilter = commandFilter;
            availableCommands = availableCommands.stream().filter(x -> Arrays.asList(x.getKey()).stream().anyMatch(commandAlias -> commandAlias.contains(finalCommandFilter))).collect(Collectors.toList());
        }

        availableCommands.sort(Comparator.comparing(x -> x.getKey()[0]));
        var commands = StringUtil.paginate(availableCommands, 10, true);

        if (!commands.containsKey(pageId - 1)) {
            pageId = 1;
        }

        var commandList = commands.get(pageId - 1);

        StringBuilder about = new StringBuilder();
        about.append("Commands ('<' and '>' are optional parameters):<br>").append("<br>");

        for (var commandSet : commandList) {
            String[] commandAlias = commandSet.getKey();
            Command command = commandSet.getValue();
            
            about.append(":").append(String.join("/", commandAlias));

            if (command.getArguments().length > 0) {
                if (command.getArguments().length > 1) {
                    about.append(" [").append(String.join("] [", command.getArguments())).append("]");
                } else {
                    about.append(" [").append(command.getArguments()[0]).append("]");
                }
            }

            about.append(" - ").append(command.getDescription()).append("<br>");
        }

        about.append("<br>")
                .append("Page ")
                .append(pageId)
                .append(" out of ")
                .append(commands.size());

        if (entity.getType() == EntityType.PLAYER) {
            Player player = (Player) entity;
            player.send(new ALERT(about.toString()));
        }
    }

    @Override
    public String getDescription() {
        return "<page> - List available commands";
    }
}
