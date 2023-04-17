package org.alexdev.havana.game.commands;

import org.alexdev.havana.game.commands.registered.*;
import org.alexdev.havana.game.commands.registered.admin.*;
import org.alexdev.havana.game.commands.registered.groups.*;
import org.alexdev.havana.game.commands.registered.moderation.*;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.texts.TextsManager;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.util.config.GameConfiguration;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandManager {
    private Map<String[], Command> commands;
    
    private static final Logger log = LoggerFactory.getLogger(CommandManager.class);
    private static CommandManager instance;

    public CommandManager() {
        this.commands = new LinkedHashMap<>();

        for (var set : getCommands()) {
            addCommand(set.getKey(), set.getValue());
        }

        log.info("Loaded {} commands", commands.size());
    }

    /**
     * Get a fresh list of command instances.
     *
     * @return the list of commands
     */
    public static List<Pair<String[], Command>> getCommands() {
        var tempCommands = new LinkedHashMap<String[], Command>();
        tempCommands.put(new String[] { "help", "commands" }, new HelpCommand());
        tempCommands.put(new String[] { "about", "info" }, new AboutCommand());
        tempCommands.put(new String[] { "giveitem", "givedrink" }, new GiveDrinkCommand());
        tempCommands.put(new String[] { "dropitem", "dropdrink" }, new DropDrinkCommand());
        tempCommands.put(new String[] { "sit" }, new SitCommand());
        tempCommands.put(new String[] { "uptime", "status" }, new UptimeCommand());
        tempCommands.put(new String[] { "coords" }, new CoordsCommand());
        tempCommands.put(new String[] { "pickall" }, new PickAllCommand());
        tempCommands.put(new String[] { "usersonline", "whosonline" }, new UsersOnlineCommand());
        tempCommands.put(new String[] { "rgb", "rainbow" }, new RainbowDimmerCommand());
        tempCommands.put(new String[] { "afk", "idle" }, new AfkCommand());
        tempCommands.put(new String[] { "guidestatus" }, new GuideStatusCommand());
        tempCommands.put(new String[] {"cl", "chatlog", "wys"}, new ChatlogCommand());

        // Staff commands
        tempCommands.put(new String[] { "copyroom" }, new CopyRoomCommand());
        tempCommands.put(new String[] { "givebadge" }, new GiveBadgeCommand());
        tempCommands.put(new String[] { "deletebadge", "removebadge" }, new RemoveBadgeCommand());
        tempCommands.put(new String[] { "packet" }, new PacketTestCommand());
        tempCommands.put(new String[] { "reload" }, new ReloadCommand());
        tempCommands.put(new String[] { "shutdown" }, new ShutdownCommand());
        tempCommands.put(new String[] { "setconfig" }, new SetConfigCommand());
        tempCommands.put(new String[] { "itemdebug" }, new ItemDebugCommand());
        tempCommands.put(new String[] { "talk" }, new TalkCommand());
        tempCommands.put(new String[] { "bot" }, new BotCommand());
        tempCommands.put(new String[] { "headrotate", "hr" }, new HeadRotateCommand());
        tempCommands.put(new String[] { "teleport", "tele", "telep", "tp" }, new TeleportCommand());
        tempCommands.put(new String[] { "addcredits", "givecredits" }, new GiveCreditsCommand());
        tempCommands.put(new String[] { "removecredits", "delcredits" }, new RemoveCreditsCommand());
        tempCommands.put(new String[] { "checkbalance", "balance" }, new CheckCreditsCommand());
        tempCommands.put(new String[] { "resetpw" }, new RecoverAccountCommand());
        tempCommands.put(new String[] { "unacceptable" }, new UnacceptableCommand());
        tempCommands.put(new String[] { "giftroom" }, new GiftRoomCommand());

        // Moderation
        tempCommands.put(new String[] { "dc", "disconnect" }, new DisconnectUserCommand());

        // Group perms
        tempCommands.put(new String[] { "roommute", "eventmute" }, new RoomMuteCommand());
        tempCommands.put(new String[] { "hotelalert", "ha" }, new HotelAlertCommand());
        tempCommands.put(new String[] { "roomalert", "ra" }, new RoomAlertCommand());
        tempCommands.put(new String[] { "ban", "userban" }, new BanCommand());
        tempCommands.put(new String[] { "unban" }, new UnbanCommand());
        tempCommands.put(new String[] { "tradeban" }, new TradeBanCommand());
        tempCommands.put(new String[] { "mute" }, new MuteCommand());
        tempCommands.put(new String[] { "unmute" }, new UnmuteCommand());

        List<Pair<String[], Command>> commandList = new ArrayList<>();

        for (var set : tempCommands.entrySet()) {
            commandList.add(Pair.of(set.getKey(), set.getValue()));
        }

        return commandList;
    }

    /**
     * Add the command
     * @param aliases the aliases
     * @param command the command instance
     */
    private void addCommand(String[] aliases, Command command) {
        this.commands.put(aliases, command);

        if (GameConfiguration.getInstance().getString("groups.ids.permission." + aliases[0], "").length() > 0) {
            command.addGroup(Stream.of(GameConfiguration.getInstance().getString("groups.ids.permission." + aliases[0]).split(","))
                    .map(Integer::parseInt).collect(Collectors.toList()));
        }
    }

    /**
     * Gets the command.
     *
     * @param commandName the command name
     * @return the command
     */
    private Command getCommand(String commandName) {
        for (Entry<String[], Command> entrySet : commands.entrySet()) {
            for (String name : entrySet.getKey()) {

                if (commandName.equalsIgnoreCase(name)) {
                    return entrySet.getValue();
                }
            }
        }

        return null;
    }

    /**
     * Checks for command.
     *
     * @param entity the player
     * @param message the message
     * @return true, if successful
     */
    public boolean hasCommand(Entity entity, String message) {
        if (message.startsWith(":") && message.length() > 1) {
            var parts = message.split(":");

            if (parts.length > 1) {
                String commandName = message.split(":")[1];
                commandName = commandName.split(" ").length > 0 ? commandName.split(" ")[0] : "";
                Command cmd = this.getCommand(commandName);

                if (cmd != null) {
                    return this.hasPermission(entity.getDetails(), commandName);
                }
            }
        }

        return false;
    }

    /**
     * Checks for command permission.
     *
     * @param playerDetails the player details
     * @param commandName the command
     * @return true, if successful
     */
    public boolean hasPermission(PlayerDetails playerDetails, String commandName) {
        var cmd = getCommand(commandName);

        if (cmd == null)
            return false;

        boolean hasRank = playerDetails.getRank().getRankId() >= cmd.getPlayerRank().getRankId();

        if (hasRank)
            return true;

        var player = PlayerManager.getInstance().getPlayerById(playerDetails.getId());

        if (player != null) {
            for (int groupId : cmd.getGroupPermission()) {
                if (player.getJoinedGroup(groupId) != null)
                    return true;
            }
        }

        return false;
    }

    /**
     * Invoke command.
     *
     * @param entity the player
     * @param message the message
     */
    public void invokeCommand(Entity entity, String message) throws Exception {
        String commandName = message.split(":")[1].split(" ")[0];
        Command cmd = this.getCommand(commandName);

        String[] args = new String[0];

        if (message.length() > (commandName.length() + 2)) {
            args = message.replace(":" + commandName + " ", "").split(" ");
        }

        if (cmd != null) {
            if (args.length < cmd.getArguments().length) {
                if (entity.getType() == EntityType.PLAYER) {
                    Player player = (Player)entity;
                    player.send(new ALERT(TextsManager.getInstance().getValue("player_commands_no_args")));
                } else {
                    System.out.println(TextsManager.getInstance().getValue("player_commands_no_args"));
                }
                return;
            }
            
            cmd.handleCommand(entity, message, args);
        }
    }

    /**
     * Gets the commands.
     *
     * @return the commands
     */
    /*public List<Pair<String[], Command>> getCommands() {
        List<Pair<String[], Command>> commandList = new ArrayList<>();

        for (var set : this.commands.entrySet()) {
            commandList.add(Pair.of(set.getKey(), set.getValue()));
        }

        return commandList;
    }*/

    /**
     * Gets the instance
     *
     * @return the instance
     */
    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }

        return instance;
    }

    /**
     * Reset the instance
     *
     * @return the instance
     */
    public static void reset() {
        instance = null;
        getInstance();
    }
}