package org.alexdev.havana.game.effects;

import org.alexdev.havana.dao.mysql.EffectDao;

import java.util.Map;

public class EffectsManager {
    private static EffectsManager instance;
    private Map<Integer, Integer> effectTimes;

    public EffectsManager() {
        this.effectTimes = EffectDao.getEffectTimes();
    }

    /**
     * Get the time for this effect.
     *
     * @param effectId the effect
     * @return the time in seconds
     */
    public int getEffectTime(int effectId) {
        if (this.effectTimes.containsKey(effectId)) {
            return this.effectTimes.get(effectId);
        }

        return 7200; // 2 hours default
    }

    /**
     * Get the {@link EffectsManager} instance
     *
     * @return the catalogue manager instance
     */
    public static EffectsManager getInstance() {
        if (instance == null) {
            instance = new EffectsManager();
        }

        return instance;
    }
}
