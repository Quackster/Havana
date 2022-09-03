package org.alexdev.havana.game.room.models.triggers;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.games.GameManager;
import org.alexdev.havana.game.games.enums.GameType;
import org.alexdev.havana.game.games.player.GamePlayer;
import org.alexdev.havana.game.games.snowstorm.SnowStormGame;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.triggers.GameLobbyTrigger;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.outgoing.games.GAMEPLAYERINFO;
import org.alexdev.havana.messages.outgoing.games.LOUNGEINFO;
import org.alexdev.havana.util.config.GameConfiguration;

import java.util.Map;

public class SnowStormLobbyTrigger extends GameLobbyTrigger {
    @Override
    public void onRoomEntry(Entity entity, Room room, boolean firstEntry, Object... customArgs) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        // Don't show panel and lounge info if create game is disabled
        if (!GameConfiguration.getInstance().getBoolean(this.getGameType().name().toLowerCase() + ".create.game.enabled")) {
            return;
        }

        Player player = (Player) entity;

        player.send(new LOUNGEINFO());
        player.send(new GAMEPLAYERINFO(this.getGameType(), room.getEntityManager().getPlayers()));

        if (GameConfiguration.getInstance().getBoolean("snowstorm.alpha.alert")) {
            player.send(new ALERT("Welcome to SnowStorm testing!<br><br>The game is almost complete but may have a few quirks to figure out. Please report any issues (aside from desync) to Alex.<br><br>Thanks to the following for making it possible:<br>  - Sefhriloff<br>  - Ascii"));
        }
    }

    @Override
    public void onRoomLeave(Entity entity, Room room, Object... customArgs) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        if (player.getRoomUser().getObservingGameId() != -1) {
            player.getRoomUser().stopObservingGame();
        }
    }

    /* new GameParameter("fieldType", true, "1", 1, 5),
                    new GameParameter("numTeams", true, "2", 2, 4),
                    new GameParameter("gameLengthChoice", true, "1", 1, 3),
                    new GameParameter("name", true, "")
            };*/

    @Override
    public void createGame(Player gameCreator, Map<String, Object> gameParameters) {
        int mapId = (int) gameParameters.get("fieldType");

        if (mapId < 1 || mapId > 7) {
            return;
        }

        int teams = (int) gameParameters.get("numTeams");

        if (teams < 1 || teams > 4) {
            return;
        }

        String name = (String) gameParameters.get("name");

        if (name.isEmpty()) {
            return;
        }

        int lengthChoice = (int) gameParameters.get("gameLengthChoice");

        SnowStormGame game = new SnowStormGame(GameManager.getInstance().createId(), mapId, name, teams, gameCreator, lengthChoice, false);

        GamePlayer gamePlayer = new GamePlayer(gameCreator);
        gamePlayer.setGameId(game.getId());
        gamePlayer.setTeamId(0);

        gameCreator.getRoomUser().setGamePlayer(gamePlayer);
        game.movePlayer(gamePlayer, -1, 0);

        GameManager.getInstance().getGames().add(game);
    }

    @Override
    public GameType getGameType() {
        return GameType.SNOWSTORM;
    }
}
