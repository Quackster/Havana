package org.alexdev.havana.messages.outgoing.rooms.user;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityState;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;
import org.alexdev.havana.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class USER_OBJECTS extends MessageComposer {
    private List<EntityState> states;

    public USER_OBJECTS(ConcurrentLinkedQueue<Entity> entities) {
        createEntityStates(new ArrayList<>(entities));
    }

    public USER_OBJECTS(List<Entity> users) {
        createEntityStates(users);
    }

    public USER_OBJECTS(Entity entity) {
        createEntityStates(List.of(entity));
    }

    private void createEntityStates(List<Entity> entities) {
        this.states = new ArrayList<>();

        for (Entity user : entities) {
            this.states.add(new EntityState(
                    user.getDetails().getId(),
                    user.getRoomUser().getInstanceId(),
                    user.getType(),
                    user.getDetails(),
                    user.getRoomUser().getRoom(),
                    user.getRoomUser().getPosition().copy(),
                    user.getRoomUser().getStatuses(),
                    user));
        }
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.states.size());

        for (EntityState states : states) {
            response.writeInt(states.getEntityId());
            response.writeString(states.getDetails().getName());
            response.writeString(states.getDetails().getMotto() + " ");
            response.writeString(states.getDetails().getFigure());
            response.writeInt(states.getInstanceId());
            response.writeInt(states.getPosition().getX());
            response.writeInt(states.getPosition().getY());
            response.writeString(StringUtil.format(states.getPosition().getZ()));
            response.writeInt(states.getPosition().getRotation());

            response.writeInt(states.getEntityType().getTypeId()); // TODO: Types

            if (states.getEntityType() == EntityType.PLAYER) {
                Player player = (Player) states.getEntity();
                int xp = player.getStatisticManager().getIntValue(PlayerStatistic.XP_EARNED_MONTH);

                response.writeString(states.getDetails().getSex().toUpperCase());
                response.writeInt(xp == 0 ? -1 : xp);

                if (states.getGroupMember() != null) {
                    response.writeInt(states.getGroupMember().getGroupId()); // Group id
                    response.writeInt(states.getGroupMember().getMemberRank().getClientRank()); // Group status
                } else {
                    response.writeInt(-1); // Group id
                    response.writeInt(-1); // Group status
                }

                if (states.getRoom() != null && (states.getRoom().getModel().getName().startsWith("pool_") || states.getRoom().getModel().getName().equals("md_a")) && states.getDetails().getPoolFigure().length() > 0) {
                    response.writeString(states.getDetails().getPoolFigure());
                } else {
                    response.writeString("");
                }
            }
        }

        /*for (EntityState states : states) {
            response.writeInt(states.getEntityId());
            response.writeString(states.getDetails().getName());
            response.writeString(states.getDetails().getFigure());
            response.writeString(states.getDetails().getName());
            response.writeString(states.getDetails().getMotto());
            response.writeString(states.getDetails().getFigure());
            response.writeInt(states.getInstanceId());
            response.writeInt(states.getPosition().getX());
            response.writeInt(states.getPosition().getY());
            response.writeString(StringUtil.format(states.getPosition().getZ()));
            response.writeInt(states.getPosition().getRotation());
            response.writeInt(1); // TODO: Types
            response.writeString(Character.toString(Character.toUpperCase(states.getDetails().getSex())));

            response.writeInt(0); // Group id
            response.writeInt(0); // Group status

            response.writeString("");

            /*response.writeKeyValue("l", states.getPosition().getX() + " " + states.getPosition().getY() + " " + Double.toString(StringUtil.format(states.getPosition().getZ())));

            if (states.getDetails().getMotto().length() > 0) {
                response.writeKeyValue("c", states.getDetails().getMotto());
            }

            if (states.getDetails().getShowBadge()) {
                response.writeKeyValue("b", states.getDetails().getCurrentBadge());
            }

            if (states.getRoom().getModel().getName().startsWith("pool_") ||
                    states.getRoom().getModel().getName().equals("md_a")) {

                if (states.getDetails().getPoolFigure().length() > 0) {
                    response.writeKeyValue("p", states.getDetails().getPoolFigure());
                }
            }
        }*/
    }

    @Override
    public short getHeader() {
        return 28; // "@\"
    }
}
