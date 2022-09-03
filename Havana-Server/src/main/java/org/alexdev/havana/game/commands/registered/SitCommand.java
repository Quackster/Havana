package org.alexdev.havana.game.commands.registered;

import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.game.room.tasks.EntityTask;
import org.alexdev.havana.messages.outgoing.effects.USER_AVATAR_EFFECT;
import org.alexdev.havana.util.StringUtil;

public class SitCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.NORMAL);
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        if (player.getRoomUser().containsStatus(StatusType.SIT)) {
            return;
        }

        if (player.getRoomUser().containsStatus(StatusType.SWIM)) {
            return;
        }

        double height = 0.5;

        if (player.getRoomUser().getRoom().isPublicRoom()) {
            if (player.getRoomUser().getRoom().getModel().getName().startsWith("pool_")) {
                height = 0.0;
            }
        }

        int rotation = player.getRoomUser().getPosition().getRotation() / 2 * 2;
        Item item = player.getRoomUser().getCurrentItem();

        if (item != null) {
            if (item.getDefinition().getInteractionType() == InteractionType.WS_JOIN_QUEUE ||
                    item.getDefinition().getInteractionType() == InteractionType.WS_QUEUE_TILE ||
                    item.getDefinition().getInteractionType() == InteractionType.WS_TILE_START) {
                return; // Don't process :sit command on furniture that the user is already on.
            }

            if (item.hasBehaviour(ItemBehaviour.CAN_SIT_ON_TOP) || item.hasBehaviour(ItemBehaviour.CAN_LAY_ON_TOP)) {
                return; // Don't process :sit command on furniture that the user is already on.
            }

            if (!item.hasBehaviour(ItemBehaviour.ROLLER)) {
                height += item.getDefinition().getTopHeight();
            }
        }

        player.getRoomUser().getPosition().setRotation(rotation);
        player.getRoomUser().setStatus(StatusType.SIT, StringUtil.format(height));
        player.getRoomUser().stopDancing();
        player.getRoomUser().setNeedsUpdate(true);

        if (player.getRoomUser().isUsingEffect()) {
            if (!player.getRoomUser().getRoom().getTaskManager().hasTask("EntityTask")) {
                return;
            }

            EntityTask entityTask = (EntityTask) player.getRoomUser().getRoom().getTaskManager().getTask("EntityTask");
            entityTask.getQueueAfterLoop().add(new USER_AVATAR_EFFECT(player.getRoomUser().getInstanceId(), player.getRoomUser().getEffectId()));
        }
    }

    @Override
    public String getDescription() {
        return "Parks your arse on the floor";
    }
}
