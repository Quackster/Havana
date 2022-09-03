package org.alexdev.havana.game.commands.registered;

import org.alexdev.havana.Havana;
import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.games.GameManager;
import org.alexdev.havana.game.games.enums.GameState;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.config.GameConfiguration;

public class UptimeCommand extends Command {
    private static final int UPTIME_COMMAND_INTERVAL_SECONDS = 5;
    private static long UPTIME_COMMAND_EXPIRY = 0L;

    private static final int CPU_NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final String CPU_ARCHITECTURE = System.getProperty("os.arch");
    private static final String JVM_NAME = System.getProperty("java.vm.name");
    private static final String OPERATING_SYSTEM_NAME = System.getProperty("os.name");

    private static int MEMORY_USAGE = 0;
    private static int ACTIVE_PLAYERS = 0;
    private static int AUTHENTICATED_PLAYERS = 0;
    private static int ACTIVE_GAMES = 0;

    public UptimeCommand(){
        UPTIME_COMMAND_EXPIRY = DateUtil.getCurrentTimeSeconds();
    }

    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.NORMAL);
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        if (DateUtil.getCurrentTimeSeconds() > UPTIME_COMMAND_EXPIRY) {
            AUTHENTICATED_PLAYERS = PlayerManager.getInstance().getPlayers().size();
            ACTIVE_PLAYERS = PlayerManager.getInstance().getActivePlayers().size();

            Runtime runtime = Runtime.getRuntime();
            MEMORY_USAGE = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024);

            UPTIME_COMMAND_EXPIRY = DateUtil.getCurrentTimeSeconds() + UPTIME_COMMAND_INTERVAL_SECONDS;
        }


        StringBuilder msg = new StringBuilder();
msg.append("Server uptime is " + DateUtil.getReadableSeconds(DateUtil.getCurrentTimeSeconds() - Havana.getStartupTime()) + "<br>");

        if (!GameConfiguration.getInstance().getBoolean("show.inactive.players")) {
            msg.append("There are " + AUTHENTICATED_PLAYERS + " online players<br>");
        } else {
            msg.append("There are " + ACTIVE_PLAYERS + " active players out of " + AUTHENTICATED_PLAYERS + " online players<br>");
        }

        msg.append("Daily player peak: " + PlayerManager.getInstance().getDailyPlayerPeak() + "<br>");
        msg.append("All time player peak: " + PlayerManager.getInstance().getAllTimePlayerPeak() + "<br>");
        msg.append("Active games: " + GameManager.getInstance().getGames().stream().filter(game -> game.getGameState() == GameState.STARTED).count() + " (" + GameManager.getInstance().getFinishedGameCounter().get() + " games played since server boot)<br>");
        msg.append("<br>");
        msg.append("SYSTEM<br>");
        msg.append("CPU architecture: " + CPU_ARCHITECTURE + "<br>");
        msg.append("CPU cores: " + CPU_NUM_THREADS + "<br>");
        msg.append("memory usage: " + MEMORY_USAGE + " MB<br>");
        msg.append("JVM: " + JVM_NAME + "<br>");
        msg.append("OS: " + OPERATING_SYSTEM_NAME);

        player.send(new ALERT(msg.toString()));
    }

    @Override
    public String getDescription() {
        return "Get the uptime and status of the server";
    }
}
