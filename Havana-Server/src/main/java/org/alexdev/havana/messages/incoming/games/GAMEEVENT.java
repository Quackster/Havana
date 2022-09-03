package org.alexdev.havana.messages.incoming.games;

import org.alexdev.havana.game.games.Game;
import org.alexdev.havana.game.games.battleball.BattleBallGame;
import org.alexdev.havana.game.games.battleball.BattleBallPowerUp;
import org.alexdev.havana.game.games.battleball.events.ActivatePowerUpEvent;
import org.alexdev.havana.game.games.enums.GameType;
import org.alexdev.havana.game.games.player.GamePlayer;
import org.alexdev.havana.game.games.snowstorm.SnowStormGame;
import org.alexdev.havana.game.games.snowstorm.messages.SnowStormMessageHandler;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class GAMEEVENT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (!player.getRoomUser().isWalkingAllowed()) {
            return;
        }

        GamePlayer gamePlayer = player.getRoomUser().getGamePlayer();

        if (gamePlayer == null) {
            return;
        }

        Game game = gamePlayer.getGame();
        int eventType = reader.readInt();

        if (game.getGameType() == GameType.SNOWSTORM) {
            SnowStormMessageHandler.getInstance().handleMessage(eventType, reader, (SnowStormGame)game, gamePlayer);
        } else {

            // Walk request
        /*if (eventType == 0) {
            if (!player.getRoomUser().isWalkingAllowed()) {
                return;
            }

            int X = reader.readInt();
            int Y = reader.readInt();

            SnowStormHandler.doWalk((SnowStormGame)game, gamePlayer, X, Y);
        }*/

            // Pickup Snowballs request
        /*if (eventType == 3) {
            SnowStormHandler.pickupSnowball((SnowStormGame)game, gamePlayer);
        }*/

            // Jump request
            if (eventType == 2) {
                if (!player.getRoomUser().isWalkingAllowed()) {
                    return;
                }

                int X = reader.readInt();
                int Y = reader.readInt();

                player.getRoomUser().walkTo(X, Y);
            }

            // Use power up request
            if (eventType == 4) {
                int powerId = reader.readInt();

                if (game instanceof BattleBallGame) {
                    BattleBallGame battleballGame = (BattleBallGame) game;

                    if (!battleballGame.getStoredPowers().containsKey(gamePlayer)) {
                        return;
                    }

                    var powerList = battleballGame.getStoredPowers().get(gamePlayer);

                    BattleBallPowerUp powerUp = null;

                    for (BattleBallPowerUp power : powerList) {
                        if (power.getId() == powerId) {
                            powerUp = power;
                            break;
                        }
                    }

                    if (powerUp != null) {
                        battleballGame.getEventsQueue().add(new ActivatePowerUpEvent(gamePlayer, powerUp));
                        powerList.remove(powerUp);

                        powerUp.usePower(gamePlayer, player.getRoomUser().getPosition());
                    }
                }
            }
        }
    }
}
