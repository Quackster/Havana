package org.alexdev.havana.messages.incoming.trade;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.managers.RoomTradeManager;
import org.alexdev.havana.messages.outgoing.trade.TRADEACCEPT;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class TRADE_UNACCEPT implements MessageEvent {
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

        if (!player.getRoomUser().canConfirmTrade() && player.getRoomUser().hasAcceptedTrade()) {
            player.getRoomUser().setTradeAccept(false);

            player.send(new TRADEACCEPT(player.getDetails().getId(), player.getRoomUser().hasAcceptedTrade()));
            player.getRoomUser().getTradePartner().send(new TRADEACCEPT(player.getDetails().getId(), player.getRoomUser().hasAcceptedTrade()));
        } else {
            RoomTradeManager.close(player.getRoomUser(), false);
        }
    }
}
