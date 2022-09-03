package org.alexdev.havana.game.commands.registered.moderation;

import org.alexdev.havana.game.GameScheduler;
import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.pathfinder.Pathfinder;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.pathfinder.Rotation;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.outgoing.rooms.user.USER_STATUSES;
import org.alexdev.havana.util.schedule.FutureRunnable;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HeadRotateCommand extends Command {
    public static final Position[] HEAD_ROTATION = new Position[]{
            new Position(1, 0, 0),//
            new Position(1, 1, 0),//
            new Position(0, 1, 0),//
            new Position(-1, 1, 0),//
            new Position(-1, 0, 0),//
            new Position(-1, -1, 0),//
            new Position(0, -1, 0),
            new Position(1, -1, 0)
    };

    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.MODERATOR);
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        doHeadRotate(entity);
    }

    public static void doHeadRotate(Entity entity) {
        var current = entity.getRoomUser().getPosition().copy();

        int currentRotation = current.getHeadRotation();
        int startPosition = 0;

        for (var pos : HEAD_ROTATION) {
            var tmp = current.add(pos);
            int rotation = Rotation.calculateWalkDirection(current, tmp);

            if (rotation == currentRotation) {
                break;
            }

            startPosition++;
        }

        final AtomicInteger turns = new AtomicInteger(8);
        final AtomicInteger nextMove = new AtomicInteger(startPosition + 1);

        var headTurnRunnable = new FutureRunnable() {
            public void run() {
                try {
                    if (turns.get() == 1) {
                        this.cancelFuture();
                        return;
                    }

                    if (entity.getRoomUser().isWalking()) {
                        entity.getRoomUser().getPosition().setHeadRotation(entity.getRoomUser().getPosition().getBodyRotation());
                        this.cancelFuture();
                        return;
                    }

                    if (nextMove.get() + 1 >= HEAD_ROTATION.length) {
                        nextMove.set(0);
                    }

                    var tmp = current.add(HEAD_ROTATION[nextMove.getAndIncrement()]);

                    entity.getRoomUser().getPosition().setHeadRotation(Rotation.calculateWalkDirection(current, tmp));
                    entity.getRoomUser().getRoom().send(new USER_STATUSES(List.of(entity)));

                    turns.decrementAndGet();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        var future = GameScheduler.getInstance().getService().scheduleAtFixedRate(headTurnRunnable, 0, 200, TimeUnit.MILLISECONDS);
        headTurnRunnable.setFuture(future);
    }

    @Override
    public String getDescription() {
        return "Rotates the users head";
    }
}