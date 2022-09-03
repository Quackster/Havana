package org.alexdev.havana.messages.outgoing.rooms.user;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityState;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.messages.types.PlayerMessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;
import org.alexdev.havana.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class USER_STATUSES extends PlayerMessageComposer {
    private List<EntityState> states;

    public USER_STATUSES(List<Entity> users) {
        createEntityStates(users);
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
        var player = getPlayer();

        response.writeInt(this.states.size());

        for (EntityState states : states) {
            response.writeInt(states.getInstanceId());
            response.writeInt(states.getPosition().getX());
            response.writeInt(states.getPosition().getY());

            /*if (player.getRoomUser().getRoom().getModel().getName().equals("picnic")) {
                if (states.getPosition().getX() == 7 &&
                        states.getPosition().getY() == 24) {

                    if (player.getNetwork().isFlashConnected()) {
                        response.writeString(StringUtil.format(2.0));
                    } else {
                        response.writeString(StringUtil.format(4.0));
                    }
                } else if (states.getPosition().getX() == 8 &&
                        states.getPosition().getY() == 24) {
                    if (player.getNetwork().isFlashConnected()) {
                        response.writeString(StringUtil.format(4.0));
                    } else {
                        response.writeString(StringUtil.format(7.0));
                    }
                } else {
                    response.writeString(StringUtil.format(states.getPosition().getZ()));
                }
            }
            else {
                response.writeString(StringUtil.format(states.getPosition().getZ()));
            }*/

            //} else {
                response.writeString(StringUtil.format(states.getPosition().getZ()));
            //}

            response.writeInt(states.getPosition().getHeadRotation());
            response.writeInt(states.getPosition().getBodyRotation());
            response.write("/");

            for (var status : states.getStatuses().values()) {
                response.write(status.getKey().getStatusCode());

                if (status.getValue().length() > 0) {
                    response.write(" ");

                    if (status.getKey() == StatusType.SIT &&
                            player.getRoomUser().getRoom() != null &&
                            player.getRoomUser().getRoom().getModel().getName().equals("picnic")) {

                        if (states.getPosition().getX() == 8 &&
                                states.getPosition().getY() == 24) {
                            response.writeString(StringUtil.format(7.0));
                        } else {
                            response.write(status.getValue());
                        }
                    } else {
                        response.write(status.getValue());
                    }
                }

                response.write("/");
            }

            response.write((char)2);
        }
    }

    @Override
    public short getHeader() {
        return 34; // "@b"
    }
}
