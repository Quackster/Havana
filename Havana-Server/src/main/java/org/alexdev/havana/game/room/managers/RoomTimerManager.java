package org.alexdev.havana.game.room.managers;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.room.entities.RoomEntity;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.config.GameConfiguration;

public class RoomTimerManager {
    private Entity entity;
    private RoomEntity roomEntity;
    private int lookTimer;
    private long afkTimer;
    private long sleepTimer;
    private long chatBubbleTimer;

    public RoomTimerManager(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
        this.entity = roomEntity.getEntity();
    }

    /**
     * Reset all timers, used for first entry into room.
     */
    public void resetTimers() {
        this.resetRoomTimer();
        this.stopChatBubbleTimer();
        this.stopLookTimer();
    }

    /**
     * Set the room timer, make it 10 minutes by default
     */
    public void resetRoomTimer() {
        this.resetRoomTimer(GameConfiguration.getInstance().getInteger("afk.timer.seconds"));
    }

    /**
     * Set the room timer, but with an option to override it.
     *
     * @param afkTimer the timer to override
     */
    public void resetRoomTimer(long afkTimer) {
        /*if (this.entity.getRoomUser().getRoom() != null) {
            Room room = this.entity.getRoomUser().getRoom();

            if (room.getData().getAccessTypeId() > 0 && idleResetReason == IdleResetReason.ALL) {
                return;
            }
        }*/

        this.afkTimer = DateUtil.getCurrentTimeSeconds() + afkTimer;
        this.sleepTimer = DateUtil.getCurrentTimeSeconds() + GameConfiguration.getInstance().getInteger("sleep.timer.seconds");

        // If the user was sleeping, remove the sleep and tell the room cycle to update our character
        if (this.roomEntity.isSleeping()) {
            this.roomEntity.sleep(false);
        }
    }

    /**
     * Begin head look timer.
     */
    public void beginLookTimer() {
        this.beginLookTimer(6);
    }

    /**
     * Begin head look timer with custom time
     *
     * @param time the time in seconds before the head rotates
     */
    public void beginLookTimer(int time) {
        this.lookTimer = DateUtil.getCurrentTimeSeconds() + time;
    }

    /**
     * Stop head look timer.
     */
    public void stopLookTimer() {
        this.lookTimer = -1;
    }

    /**
     * Begin talk time out.
     */
    public void beginChatBubbleTimer() {
        int timeout = GameConfiguration.getInstance().getInteger("talk.bubble.timeout.seconds");

        if (timeout > 0) {
            this.chatBubbleTimer = DateUtil.getCurrentTimeSeconds() + timeout;
        }
    }

    /**
     * Stop talk time out.
     */
    public void stopChatBubbleTimer() {
        this.chatBubbleTimer = -1;
    }

    public Entity getEntity() {
        return entity;
    }

    public long getChatBubbleTimer() {
        return chatBubbleTimer;
    }

    public int getLookTimer() {
        return lookTimer;
    }

    public long getAfkTimer() {
        return afkTimer;
    }

    public long getSleepTimer() {
        return sleepTimer;
    }
}
