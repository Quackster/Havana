package org.alexdev.havana.game.effects;

import org.alexdev.havana.util.DateUtil;

public class Effect {
    private int id;
    private int userId;
    private int effectId;
    private long expiryDate;
    private boolean activated;

    public Effect(int id, int userId, int effectId, long expiryDate, boolean activated) {
        this.id = id;
        this.userId = userId;
        this.effectId = effectId;
        this.expiryDate = expiryDate;
        this.activated = activated;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getEffectId() {
        return effectId;
    }

    public int getTimeLeft() {
        if (this.expiryDate >= DateUtil.getCurrentTimeSeconds()) {
            return (int) (this.expiryDate - DateUtil.getCurrentTimeSeconds());
        }

        return 0;
    }

    public long getExpireDate() {
        return expiryDate;
    }

    public void setExpiryDate(long expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Integer getTimeDuration() {
        return EffectsManager.getInstance().getEffectTime(this.effectId);
    }
}
