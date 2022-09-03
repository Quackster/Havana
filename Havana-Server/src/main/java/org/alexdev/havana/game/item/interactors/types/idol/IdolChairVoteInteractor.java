package org.alexdev.havana.game.item.interactors.types.idol;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.entities.RoomEntity;
import org.alexdev.havana.game.room.tasks.EntityTask;
import org.alexdev.havana.game.triggers.GenericTrigger;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.messages.outgoing.effects.USER_AVATAR_EFFECT;
import org.alexdev.havana.messages.outgoing.rooms.items.JUDGE_GUI_STATUS;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;
import org.alexdev.havana.util.StringUtil;

public class IdolChairVoteInteractor extends GenericTrigger {
    @Override
    public void onEntityStop(Entity entity, RoomEntity roomEntity, Item item, boolean isRotation) {
        InteractionType.CHAIR.getTrigger().onEntityStop(entity, roomEntity, item, isRotation);

        if (isRotation) {
            return;
        }

        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        roomEntity.getRoom().getIdolManager().updatePerformer();

        Player player = (Player) entity;

        if (roomEntity.getRoom().getIdolManager().getPerformer() == null || !item.getCustomData().equals("0") || roomEntity.getRoom().getIdolManager().getVoted().contains(player)) {
            player.send(new JUDGE_GUI_STATUS(1, -1));
        } else {
            player.send(new JUDGE_GUI_STATUS(2, roomEntity.getRoom().getIdolManager().getPerformer().getDetails().getId()));
        }

    }

    @Override
    public void onEntityLeave(Entity entity, RoomEntity roomEntity, Item item, Object... customArgs) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;
        player.send(new JUDGE_GUI_STATUS(0, -1));
    }
}
