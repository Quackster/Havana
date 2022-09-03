package org.alexdev.havana.game.games.snowstorm.messages.incoming;

import org.alexdev.havana.game.games.player.GamePlayer;
import org.alexdev.havana.game.games.snowstorm.SnowStormGame;
import org.alexdev.havana.game.games.snowstorm.util.SnowStormMessage;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.util.MalformedPacketException;

public class SnowStormWalkMessage implements SnowStormMessage {
    @Override
    public void handle(NettyRequest reader, SnowStormGame snowStormGame, GamePlayer gamePlayer) throws MalformedPacketException {
        if (!gamePlayer.getSnowStormAttributes().isWalkable() || !gamePlayer.getPlayer().getRoomUser().isWalkingAllowed()) {
            return;
        }

        int X = reader.readInt();
        int Y = reader.readInt();

        int newX = SnowStormGame.convertToGameCoordinate(X);
        int newY = SnowStormGame.convertToGameCoordinate(Y);

        // --System.out.println("Request: " + newX + ", " + newY);
        if (gamePlayer.getSnowStormAttributes().getCurrentPosition().equals(new Position(newX, newY))) {
            return;
        }

        gamePlayer.getSnowStormAttributes().setGoalWorldCoordinates(new int[] { X, Y });
        gamePlayer.getSnowStormAttributes().setWalking(true);
        gamePlayer.getSnowStormAttributes().setWalkGoal(new Position(newX, newY));

        //snowStormGame.getUpdateTask().sendQueue(0, 1,
        //        new SnowStormAvatarMoveEvent(gamePlayer.getObjectId(), X, Y));
    }
}
