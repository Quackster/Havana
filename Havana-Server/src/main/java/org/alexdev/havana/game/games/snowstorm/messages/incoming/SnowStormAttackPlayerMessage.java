package org.alexdev.havana.game.games.snowstorm.messages.incoming;

import org.alexdev.havana.game.games.player.GamePlayer;
import org.alexdev.havana.game.games.snowstorm.SnowStormGame;
import org.alexdev.havana.game.games.snowstorm.events.SnowStormLaunchSnowballEvent;
import org.alexdev.havana.game.games.snowstorm.events.SnowStormThrowEvent;
import org.alexdev.havana.game.games.snowstorm.objects.SnowballObject;
import org.alexdev.havana.game.games.snowstorm.util.SnowStormMessage;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.pathfinder.Rotation;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.util.MalformedPacketException;

public class SnowStormAttackPlayerMessage implements SnowStormMessage {
    @Override
    public void handle(NettyRequest reader, SnowStormGame snowStormGame, GamePlayer gamePlayer) throws MalformedPacketException {
        if (!gamePlayer.getSnowStormAttributes().isWalkable()) {
            return;
        }

        if ((gamePlayer.getSnowStormAttributes().getLastThrow().get() + 300) > System.currentTimeMillis()) {
            return;
        }

        int userId = reader.readInt();
        int trajectory = reader.readInt();

        if (trajectory != 0 && trajectory != 2 && trajectory != 3) {
            return;
        }

        if (gamePlayer.getSnowStormAttributes().getSnowballs().get() <= 0) {
            return;
        }

        if (gamePlayer.getSnowStormAttributes().isWalking()) {
            gamePlayer.getSnowStormAttributes().setWalking(false);
        }

        var player = snowStormGame.getActivePlayers().stream().filter(p -> p.getObjectId() == userId).findFirst().orElse(null);

        if (player == null || !snowStormGame.isOppositionPlayer(gamePlayer, player)) {
            return;
        }

        //gamePlayer.getPlayer().getRoomUser().setWalkingAllowed(false);
        int objectId = snowStormGame.getObjectId().incrementAndGet();

        var snowball = new SnowballObject(objectId, snowStormGame, gamePlayer,
                gamePlayer.getSnowStormAttributes().getCurrentPosition().getX(),
                gamePlayer.getSnowStormAttributes().getCurrentPosition().getY(),
                player.getSnowStormAttributes().getCurrentPosition().getX(),
                player.getSnowStormAttributes().getCurrentPosition().getY(),
                trajectory,
                Rotation.calculateWalkDirection(gamePlayer.getSnowStormAttributes().getCurrentPosition(), player.getSnowStormAttributes().getCurrentPosition()));

        snowball.setTargetPlayer(player);
        gamePlayer.getSnowStormAttributes().getSnowballs().decrementAndGet();

        var visibilityPath = snowball.getPath();
        Position lastTilePosition = visibilityPath.size() > 0 ? visibilityPath.pollLast() : null;

        // Reconsider velocity/time to live and recalculate since it's blocked
        if (lastTilePosition != null && !lastTilePosition.equals(new Position(snowball.getTargetX(), snowball.getTargetY()))) {
            snowball.setTargetX(lastTilePosition.getX());
            snowball.setTargetY(lastTilePosition.getY());
            snowball.setBlocked(true);
        }

        snowStormGame.getUpdateTask().sendQueue(0, 1, new SnowStormThrowEvent(gamePlayer.getObjectId(), SnowStormGame.convertToWorldCoordinate(snowball.getTargetX()), SnowStormGame.convertToWorldCoordinate(snowball.getTargetY()), trajectory));
        snowStormGame.getUpdateTask().sendQueue(0, 1, new SnowStormLaunchSnowballEvent(objectId, gamePlayer.getObjectId(), SnowStormGame.convertToWorldCoordinate(snowball.getTargetX()), SnowStormGame.convertToWorldCoordinate(snowball.getTargetY()), trajectory));
        gamePlayer.getSnowStormAttributes().getLastThrow().set(System.currentTimeMillis());

        if (trajectory == 0 && visibilityPath.size() >= SnowStormGame.MAX_QUICK_THROW_DISTANCE) {
            return;
        }

        snowball.scheduleMovementTask();
        /*GameScheduler.getInstance().getService().schedule(() -> {
            try {
                snowStormGame.handleSnowballLanding(snowball);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, snowball.getTimeToLive() * 100, TimeUnit.MILLISECONDS);*/
    }
}
