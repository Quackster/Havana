package org.alexdev.havana.messages.incoming.trade;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.trade.TRADEACCEPT;
import org.alexdev.havana.messages.outgoing.trade.TRADECONFIRM;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class TRADE_ACCEPT implements MessageEvent {
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

        player.getRoomUser().setTradeAccept(true);

        player.send(new TRADEACCEPT(player.getDetails().getId(), player.getRoomUser().hasAcceptedTrade()));
        player.getRoomUser().getTradePartner().send(new TRADEACCEPT(player.getDetails().getId(), player.getRoomUser().hasAcceptedTrade()));
        //RoomTradeManager.refreshWindow(player);
        //RoomTradeManager.refreshWindow(player.getRoomUser().getTradePartner());

        if (player.getRoomUser().hasAcceptedTrade() &&
            player.getRoomUser().getTradePartner().getRoomUser().hasAcceptedTrade()) {

            /*RoomTradeManager.addItems(player, player.getRoomUser().getTradePartner());
            RoomTradeManager.addItems(player.getRoomUser().getTradePartner(), player);

            RoomTradeManager.close(player.getRoomUser());*/
            player.send(new TRADECONFIRM());
            player.getRoomUser().getTradePartner().send(new TRADECONFIRM());

            player.getRoomUser().setCanConfirmTrade(true);
            player.getRoomUser().getTradePartner().getRoomUser().setCanConfirmTrade(true);

            player.getRoomUser().setTradeAccept(false);
            player.getRoomUser().setTradeAccept(false);
        }
    }
}
