package org.alexdev.havana.messages.outgoing.rooms.items;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.roller.RollingData;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;
import org.alexdev.havana.util.StringUtil;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SLIDEOBJECTBUNDLE extends MessageComposer {
    private Item roller;
    private List<RollingData> rollingItems;
    private RollingData rollingEntity;

    public SLIDEOBJECTBUNDLE(Item roller, List<RollingData> rollingItems, RollingData rollingEntity) {
        this.roller = roller;
        this.rollingItems = rollingItems;
        this.rollingEntity = rollingEntity;
    }


    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.roller.getPosition().getX());
        response.writeInt(this.roller.getPosition().getY());
        response.writeInt(this.roller.getPosition().getSquareInFront().getX());
        response.writeInt(this.roller.getPosition().getSquareInFront().getY());
        response.writeInt(this.rollingItems.size());

        for (RollingData item : this.rollingItems) {
            response.writeInt(item.getItem().getVirtualId());
            response.writeString(StringUtil.format(item.getFromPosition().getZ()));
            response.writeString(StringUtil.format(item.getNextPosition().getZ()));
        }

        boolean hasEntity = this.rollingEntity != null && this.rollingEntity.getEntity().getRoomUser().getRoom() != null;

        response.writeInt(this.roller.getVirtualId());
        response.writeInt(hasEntity ? 2 : 0);

        if (hasEntity) {
            response.writeInt(this.rollingEntity.getEntity().getRoomUser().getInstanceId());
            response.writeString(StringUtil.format(this.rollingEntity.getFromPosition().getZ()));
            response.writeString(StringUtil.format(this.rollingEntity.getDisplayHeight()));
        }
    }

    @Override
    public short getHeader() {
        return 230; // "Cf"
    }
}
