package org.alexdev.havana.messages.incoming.effects;

import org.alexdev.havana.dao.mysql.EffectDao;
import org.alexdev.havana.game.effects.Effect;
import org.alexdev.havana.game.effects.EffectsManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.effects.AVATAR_EFFECTS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.DateUtil;

public class ACTIVATE_AVATAR_EFFECT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int effectId = reader.readInt();
        doAction(player, effectId);
    }

    public static void doAction(Player player, int effectId) {
        Effect toActivate = null;
        var activatedEffectCheck = player.getEffects().stream().filter(effect -> effect.getEffectId() == effectId && !effect.isActivated()).findFirst();

        if (activatedEffectCheck.isPresent()) {
            toActivate = activatedEffectCheck.get();
        }

        if (toActivate == null) {
            return;
        }

        int effectTime = EffectsManager.getInstance().getEffectTime(effectId);
        long expireTime = (DateUtil.getCurrentTimeSeconds() + effectTime);

        toActivate.setActivated(true);
        toActivate.setExpiryDate(expireTime);

        player.send(new AVATAR_EFFECTS(player.getEffects()));
        EffectDao.saveEffect(toActivate);
    }
}
