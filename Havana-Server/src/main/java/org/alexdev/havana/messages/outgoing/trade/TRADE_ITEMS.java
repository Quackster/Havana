package org.alexdev.havana.messages.outgoing.trade;

import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.List;

public class TRADE_ITEMS extends MessageComposer {
    private final Player player;
    private final List<Item> ownItems;
    private final Player tradePartner;
    private final List<Item> partnerItems;

    public TRADE_ITEMS(Player player, List<Item> ownItems, Player tradePartner, List<Item> partnerItems) {
        this.player = player;
        this.ownItems = ownItems;
        this.tradePartner = tradePartner;
        this.partnerItems = partnerItems;
    }

    @Override
    public void compose(NettyResponse response) {
        /*response.write(this.player.getDetails().getName(), (char) 9);
        response.write(this.playerAcceptedTrade ? "true" : "false", (char) 9);

        for (int i = 0; i < this.ownItems.size(); i++) {
            Item item = this.ownItems.get(i);
            Inventory.serialise(response, item, i);
        }

        response.write((char) 13);

        response.write(this.tradePartner.getDetails().getName(), (char) 9);
        response.write(this.partnerAcceptedTrade ? "true" : "false", (char) 9);

        for (int i = 0; i < this.partnerItems.size(); i++) {
            Item item = this.partnerItems.get(i);
            Inventory.serialise(response, item, i);
        }*/

        response.writeInt(this.player.getDetails().getId());
        response.writeInt(this.ownItems.size());

        int j = 0;
        for (Item item : this.ownItems) {
            this.serialiseItem(response, item);
            //Inventory.serialise(response, item, j++);
        }

        response.writeInt(this.tradePartner.getDetails().getId());
        response.writeInt(this.partnerItems.size());

        int i = 0;
        for (Item item : this.partnerItems) {
            this.serialiseItem(response, item);
            //Inventory.serialise(response, item, i++);
        }
    }

    private void serialiseItem(NettyResponse response, Item item) {
        response.writeInt(item.getVirtualId());
        response.writeString(item.hasBehaviour(ItemBehaviour.WALL_ITEM) ? "i" : "s");
        response.writeInt(item.getVirtualId());
        response.writeInt(item.getDefinition().getSpriteId());
        response.writeInt(-1);
        response.writeInt(-1);
        response.writeString(item.getCustomData());
        response.writeInt(-1);
        response.writeInt(-1);
        response.writeInt(-1);

        if (!item.hasBehaviour(ItemBehaviour.WALL_ITEM)) {
            response.writeInt(-1);
        }
    }

    @Override
    public short getHeader() {
        return 108; // "Al"
    }
}
