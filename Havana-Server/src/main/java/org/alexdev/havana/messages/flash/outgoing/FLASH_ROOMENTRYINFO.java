package org.alexdev.havana.messages.flash.outgoing;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.messages.types.PlayerMessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class FLASH_ROOMENTRYINFO extends PlayerMessageComposer {
    private final Player player;
    private final Room room;

    public FLASH_ROOMENTRYINFO(Player player, Room room) {
        this.player = player;
        this.room = room;
    }

    @Override
    public void compose(NettyResponse response) {
        if (this.player.getNetwork().isBetaConnected()) {
            response.writeInt(this.room.getId());
            response.writeBool(this.room.isOwner(player.getDetails().getId()));
            response.writeBool(false);
            response.writeBool(false);
        } else {
            response.writeBool(!this.room.isPublicRoom());

            if (this.room.isPublicRoom()) {
                response.writeString(this.room.getData().getDescription());
                response.writeBool(false);
            } else {
                response.writeInt(this.room.getId());

                if (this.room.isOwner(player.getDetails().getId())) {
                    response.writeBool(true);
                } else {
                    response.writeBool(false);
                }
            }
        }
    }

    @Override
    public short getHeader() {
        return 471;
    }
}
