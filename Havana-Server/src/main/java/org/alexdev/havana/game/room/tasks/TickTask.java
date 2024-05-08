package org.alexdev.havana.game.room.tasks;

import org.alexdev.havana.util.DateUtil;

public abstract class TickTask {
    private long timeUntilNextTick;

    public abstract void tick();

    public void setTimeUntilNextTick(int secs) {
        this.timeUntilNextTick = DateUtil.getCurrentTimeSeconds() + secs;
    }

    public boolean isTickRunnable() {
        return DateUtil.getCurrentTimeSeconds() > timeUntilNextTick;
    }
}
