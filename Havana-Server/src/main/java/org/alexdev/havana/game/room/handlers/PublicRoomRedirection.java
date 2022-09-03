package org.alexdev.havana.game.room.handlers;

import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.entities.RoomEntity;

public class PublicRoomRedirection {
    public static boolean isRedirected(RoomEntity roomEntity, int targetX, int targetY) {
        if (roomEntity == null || roomEntity.getPosition() == null) {
            return false;
        }

        Room room = roomEntity.getRoom();

        if (room.getModel().getName().equals("sun_terrace")) {
            double currentZ = roomEntity.getPosition().getZ();
            double goalZ = room.getMapping().getTile(targetX, targetY).getTileHeight();

            if (!(currentZ >= 8) && goalZ >= 8 && roomEntity.getPosition().getX() != 4 && roomEntity.getPosition().getY() != 18) {
                return true;
            }

            return targetX == 4 && targetY == 18 && roomEntity.getPosition().getX() != 6 && roomEntity.getPosition().getY() != 21;
        }

        if (room.getModel().getName().equals("star_lounge")) {
            if (targetX == 36 && targetY == 27) {
                roomEntity.walkTo(37, 28);
                return true;
            }
        }

        return false;
    }
}
