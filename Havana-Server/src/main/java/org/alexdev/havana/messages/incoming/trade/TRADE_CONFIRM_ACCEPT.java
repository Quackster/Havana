package org.alexdev.havana.messages.incoming.trade;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.managers.RoomTradeManager;
import org.alexdev.havana.messages.outgoing.trade.TRADE_COMPLETED;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class TRADE_CONFIRM_ACCEPT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (player.getRoomUser().getTradePartner() == null) {
            return;
        }

        if (!player.getDetails().isTradeEnabled() || !player.getRoomUser().getTradePartner().getDetails().isTradeEnabled()) {
            return;
        }

        if (!player.getRoomUser().canConfirmTrade() || !player.getRoomUser().getTradePartner().getRoomUser().canConfirmTrade()) {
            return;
        }

        player.getRoomUser().setTradeConfirmed(true);

        if (player.getRoomUser().isTradeConfirmed() &&
                player.getRoomUser().getTradePartner().getRoomUser().isTradeConfirmed()) {

            /*
            if (RoomTradeManager.isTradeBannable(player)) {
                RoomTradeManager.close(player.getRoomUser(), true);
                return;
            }
            */

            player.send(new TRADE_COMPLETED());
            player.getRoomUser().getTradePartner().send(new TRADE_COMPLETED());

            RoomTradeManager.addItems(player, player.getRoomUser().getTradePartner());
            RoomTradeManager.addItems(player.getRoomUser().getTradePartner(), player);

            RoomTradeManager.finish(player.getRoomUser());
        }
    }
}
