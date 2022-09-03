package org.alexdev.havana.game.room.models.triggers;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.game.triggers.GenericTrigger;
import org.alexdev.havana.log.Log;
import org.alexdev.havana.messages.outgoing.rooms.items.SHOWPROGRAM;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class DivingDeckTrigger extends GenericTrigger {
    public static class PoolCamera implements Runnable {
        private final Room room;
        private Player player;
        private int cameraType;

        public PoolCamera(Room room) {
            this.room = room;
        }

        @Override
        public void run() {
            try {
                if (this.player == null) {
                    this.spectateNewPlayer();
                    this.newCameraMode(-1);
                    return;
                }

                int cameraType = ThreadLocalRandom.current().nextInt(0, 3);

                switch (cameraType) {
                    case 0: {
                        this.spectateNewPlayer();
                        break;
                    }
                    case 1: {
                        this.newCameraMode(1);
                        break;
                    }
                    case 2: {
                        this.newCameraMode(2);
                        break;
                    }
                }
            } catch (Exception ex) {
                Log.getErrorLogger().error("PoolCamera crashed: ", ex);
            }
        }

        /**
         * Finds a new player to spectate on the camera.
         */
        public void spectateNewPlayer() {
            try {
                List<Player> playerList = this.room.getEntityManager().getPlayers();

                if (playerList.isEmpty()) {
                    return;
                }

                this.player = null;

                if (playerList.size() > 1) {
                    this.player = playerList.get(ThreadLocalRandom.current().nextInt(playerList.size()));
                } else {
                    this.player = playerList.get(0);
                }

                if (this.player != null) {
                    this.room.send(new SHOWPROGRAM(new String[]{"cam1", "targetcamera", String.valueOf(this.player.getRoomUser().getInstanceId())}));
                }
            } catch (Exception ex) {
                Log.getErrorLogger().error("Error when trying to find player to spectate: ", ex);
            }
        }

        /**
         * Creates a new camera mode for the camera and sends it to all the users.
         */
        public void newCameraMode(int mode) {
            this.cameraType = mode > 0 ? mode : ThreadLocalRandom.current().nextInt(1, 3);
            this.room.send(new SHOWPROGRAM(new String[]{"cam1", "setcamera", String.valueOf(this.cameraType)}));
        }

        /**
         * Gets the current active player being spectated
         *
         * @return the player being spectated
         */
        public Player getPlayer() {
            return player;
        }

        /**
         * Get the camera type (zoomed in or zoomed out)
         *
         * @return the camera type
         */
        public int getCameraType() {
            return cameraType;
        }
    }

    @Override
    public void onRoomEntry(Entity entity, Room room, boolean firstEntry, Object... customArgs) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player)entity;

        if (room.getTaskManager().hasTask("DivingCamera")) {
            DivingDeckTrigger.PoolCamera task = (DivingDeckTrigger.PoolCamera) room.getTaskManager().getTask("DivingCamera");

            if (task.getPlayer() == null) {
                task.spectateNewPlayer();
            }

            if (task.getPlayer() != null) {
                player.send(new SHOWPROGRAM(new String[]{"cam1", "setcamera", String.valueOf(task.getCameraType())}));
            }
        } else {
            room.getTaskManager().scheduleTask("DivingCamera", new DivingDeckTrigger.PoolCamera(room), 3, 10, TimeUnit.SECONDS);
        }

        if (player.getRoomUser().getPosition().getZ() == 1.0) { // User entered room from the other pool
            player.getRoomUser().setStatus(StatusType.SWIM, "");
            player.getRoomUser().setNeedsUpdate(true);
        }
    }

    @Override
    public void onRoomLeave(Entity entity, Room room, Object... customArgs)  {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        if (room.getEntityManager().getPlayers().isEmpty()) {
            return;
        }

        Player player = (Player)entity;

        DivingDeckTrigger.PoolCamera task = (DivingDeckTrigger.PoolCamera) room.getTaskManager().getTask("DivingCamera");

        if (task.getPlayer() == player) {
            task.spectateNewPlayer();
        }
    }
}
