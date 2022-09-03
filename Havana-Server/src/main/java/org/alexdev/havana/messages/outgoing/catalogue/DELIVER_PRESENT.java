package org.alexdev.havana.messages.outgoing.catalogue;

import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class DELIVER_PRESENT extends MessageComposer {
    private final Item present;

    public DELIVER_PRESENT(Item present) {
        this.present = present;
    }

    @Override
    public void compose(NettyResponse response) {
        if (this.present.getDefinition().hasBehaviour(ItemBehaviour.WALL_ITEM)) {
            response.writeString("i");
        } else {
            response.writeString("s");
        }

        response.writeInt(this.present.getDefinition().getSpriteId());

        if (this.present.getDefinition().getSprite().equals("poster")) {
            response.writeString(this.present.getDefinition().getSprite() + " " + this.present.getCustomData());
        } else {
            response.writeString(this.present.getDefinition().getSprite());
        }
        /*response.write(this.present.getDefinition().getSprite(), (char)13);
        response.write(this.present.getDefinition().getSprite());

        if (this.present.getDefinition().hasBehaviour(ItemBehaviour.WALL_ITEM)) {
            response.write(" ");
            response.writeString(this.present.getDefinition().getColour());
        } else {
            response.write((char)13);
            response.write(this.present.getDefinition().getLength(), (char)30);
            response.write(this.present.getDefinition().getWidth(), (char)30);
            response.write(this.present.getDefinition().getColour());
        }*/
    }

    @Override
    public short getHeader() {
        return 129; // "BA"
    }
}
