package org.alexdev.havana.game.games.snowstorm.messages.incoming;

import org.alexdev.havana.game.games.player.GamePlayer;
import org.alexdev.havana.game.games.snowstorm.SnowStormGame;
import org.alexdev.havana.game.games.snowstorm.events.SnowStormCreateSnowballEvent;
import org.alexdev.havana.game.games.snowstorm.util.SnowStormActivityState;
import org.alexdev.havana.game.games.snowstorm.util.SnowStormMessage;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.util.MalformedPacketException;

public class SnowstormCreateSnowballMessage implements SnowStormMessage {
    @Override
    public void handle(NettyRequest request, SnowStormGame snowStormGame, GamePlayer gamePlayer) throws MalformedPacketException {
        if (!gamePlayer.getSnowStormAttributes().isWalkable()) {
            //System.out.println("Player " + gamePlayer.getPlayer().getDetails().getName() + " state " + gamePlayer.getSnowStormAttributes().getActivityState().name());
            return;
        }

        if (gamePlayer.getSnowStormAttributes().getSnowballs().get() >= 5) {
            //System.out.println("Player " + gamePlayer.getPlayer().getDetails().getName() + " has " + gamePlayer.getSnowStormAttributes().getSnowballs().get());
            return;
        }

        if (gamePlayer.getSnowStormAttributes().isWalking()) {
            gamePlayer.getSnowStormAttributes().setWalking(false);
        }

        snowStormGame.getUpdateTask().sendQueue(0, 1, new SnowStormCreateSnowballEvent(gamePlayer.getObjectId()));

        gamePlayer.getSnowStormAttributes().setActivityState(SnowStormActivityState.ACTIVITY_STATE_CREATING, ()-> {
            if (!gamePlayer.getSnowStormAttributes().isWalkable() || gamePlayer.getSnowStormAttributes().getHealth().get() == 0) {
                return;
            }

            gamePlayer.getSnowStormAttributes().getSnowballs().incrementAndGet();
        });
    }
}
