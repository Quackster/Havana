package org.alexdev.havana.game.item.roller;

import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.room.Room;

public interface RollingAnalysis<T> {
    public Position canRoll(T rollingType, Item roller, Room room);
    public void doRoll(T rollingType, Item roller, Room room, Position fromPosition, Position nextPosition);
}
