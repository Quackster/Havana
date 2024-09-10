package org.alexdev.havana.messages.flash.outgoing.rooms;

import org.alexdev.havana.game.player.Wardrobe;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.List;

public class WARDROBE extends MessageComposer {
    private final List<Wardrobe> wardrobe;

    public WARDROBE(List<Wardrobe> wardrobe) {
        this.wardrobe = wardrobe;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeBool(false);
        response.writeInt(this.wardrobe.size());

        for (var wardrobeItem : this.wardrobe) {
            response.writeInt(wardrobeItem.getSlotId());
            response.writeString(wardrobeItem.getFigure());
            response.writeString(wardrobeItem.getSex());
        }
    }

    @Override
    public short getHeader() {
        return 267;
    }
}
