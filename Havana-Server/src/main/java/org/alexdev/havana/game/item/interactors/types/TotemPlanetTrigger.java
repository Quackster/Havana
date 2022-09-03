package org.alexdev.havana.game.item.interactors.types;

import org.alexdev.havana.dao.mysql.EffectDao;
import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.game.effects.Effect;
import org.alexdev.havana.game.effects.EffectsManager;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.item.interactors.enums.TotemEffect;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.triggers.GenericTrigger;
import org.alexdev.havana.messages.incoming.effects.ACTIVATE_AVATAR_EFFECT;
import org.alexdev.havana.messages.incoming.effects.USE_AVATAR_EFFECT;
import org.alexdev.havana.messages.outgoing.effects.AVATAR_EFFECTS;
import org.alexdev.havana.messages.outgoing.effects.AVATAR_EFFECT_ADDED;
import org.alexdev.havana.util.DateUtil;

import java.util.concurrent.TimeUnit;

public class TotemPlanetTrigger extends GenericTrigger {
    public void onInteract(Player player, Room room, Item item, int status) {
        Item itemBelow = item.getItemBelow();

        if (itemBelow != null &&
                (itemBelow.getDefinition().getSprite().equals("totem_head")
                || itemBelow.getDefinition().getSprite().equals("totem_leg"))) {

            checkEffect(item, player, room);
            return;
        }

        InteractionType.DEFAULT.getTrigger().onInteract(player, room, item, status);
    }

    private void checkEffect(Item item, Player player, Room room) {
        if (!room.isOwner(player.getDetails().getId()) && !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            return;
        }

        TotemEffect totemEffect = this.getTotemEffect(item);

        if (totemEffect == TotemEffect.NONE) {
            return;
        }

        for (Effect effect : player.getEffects()) {
            if (effect.getEffectId() == totemEffect.getEffectId()) {
                return;
            }
        }

        if (player.getDetails().getTotemEffectExpiry() > DateUtil.getCurrentTimeSeconds()) {
            return;
        }

        Effect effect = EffectDao.newEffect(player.getDetails().getId(), totemEffect.getEffectId(), -1, false);

        if (effect == null) {
            return;
        }

        player.getEffects().add(effect);

        player.send(new AVATAR_EFFECT_ADDED(effect));
        player.send(new AVATAR_EFFECTS(player.getEffects()));

        ACTIVATE_AVATAR_EFFECT.doAction(player, totemEffect.getEffectId());
        USE_AVATAR_EFFECT.doAction(player, totemEffect.getEffectId());

        ItemDao.saveTotemExpire(player.getDetails().getId(), DateUtil.getCurrentTimeSeconds() + TimeUnit.DAYS.toSeconds(1));
    }

    private TotemEffect getTotemEffect(Item totemPlanet) {
        if (totemPlanet.getItemBelow() == null || !totemPlanet.getItemBelow().getDefinition().getSprite().equals("totem_head")) {
            return TotemEffect.NONE;
        }

        Item totemHead = totemPlanet.getItemBelow();

        if (totemHead == null || totemHead.getItemBelow() == null || !totemHead.getItemBelow().getDefinition().getSprite().equals("totem_leg")) {
            return TotemEffect.NONE;
        }

        Item totemLegs = totemHead.getItemBelow();

        // FIRE
        /*
         * Status planet: 1
         * Status head: 12
         * Status legs: 8
         */

        // WAND
        /*
        Status planet: 2
        Status head: 9
        Status legs: 8
         */

        // RAIN
        /*
        Status planet: 0
        Status head: 10
        Status legs: 4
         */

        // LEVITATION
        /*
        Status planet: 2
        Status head: 5
        Status legs: 0
         */

        int planet = Integer.parseInt(totemPlanet.getCustomData());
        int head = Integer.parseInt(totemHead.getCustomData());
        int legs = Integer.parseInt(totemLegs.getCustomData());


        for (TotemEffect totemEffect : TotemEffect.values()) {
            if (totemEffect.getPlanet() == planet && totemEffect.getHead() == head && totemEffect.getLegs() == legs) {
                return totemEffect;
            }
        }


        return TotemEffect.NONE;
    }
}
