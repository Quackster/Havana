package org.alexdev.havana.messages.incoming.rooms.items;

import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.dao.mysql.TransactionDao;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.outgoing.user.currencies.CREDIT_BALANCE;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class CONVERT_FURNI_TO_CREDITS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.isOwner(player.getDetails().getId()) && !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            return;
        }

        int itemId = reader.readInt();

        if (itemId < 0) {
            return;
        }

        Item item = room.getItemManager().getById(itemId);

        if (item == null || !item.hasBehaviour(ItemBehaviour.REDEEMABLE) || item.isDeleted()) {
            return;
        }

        // Sprite is of format CF_50_goldbar. This retrieves the 50 part
        Integer amount = Integer.parseInt(item.getDefinition().getSprite().split("_")[1]);

        // Delete item and update credits amount in one atomic operation
        player.getInventory().getItems().forEach(x -> {
            if (x.getDatabaseId() == item.getDatabaseId()) {
                x.setDeleted(true);
            }
        });

        room.getItems().forEach(x -> {
            if (x.getDatabaseId() == item.getDatabaseId()) {
                x.setDeleted(true);
            }
        });

        int currentAmount = ItemDao.redeemCreditItem(amount, item.getDatabaseId(), player.getDetails().getId());

        // Couldn't redeem item (database error)
        if (currentAmount == -1) {
            // TODO: find real composer for this. Maybe use error composer?
            player.send(new ALERT("Unable to redeem furniture! Contact staff or support team."));
            return;
        }

        TransactionDao.createTransaction(player.getDetails().getId(),
                String.valueOf(item.getDatabaseId()), "", 1,
                "Exchanged " + item.getDefinition().getName() + " into " + amount + " credits",
                amount, 0, false);

        // Notify room of item removal and set credits of player
        room.getMapping().removeItem(player, item);
        player.getDetails().setCredits(currentAmount);

        // Send new credit amount
        player.send(new CREDIT_BALANCE(player.getDetails().getCredits()));
    }
}