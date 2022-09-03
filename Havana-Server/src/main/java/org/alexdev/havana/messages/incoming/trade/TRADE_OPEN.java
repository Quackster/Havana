package org.alexdev.havana.messages.incoming.trade;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.game.room.managers.RoomTradeManager;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.outgoing.openinghours.INFO_HOTEL_CLOSING;
import org.alexdev.havana.messages.outgoing.trade.TRADEOPEN;
import org.alexdev.havana.messages.outgoing.trade.TRADE_ALREADY_OPEN;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class TRADE_OPEN implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.getCategory().hasAllowTrading()) {
            return;
        }

        if (player.getRoomUser().getTradePartner() != null) {
            return;
        }

        if (player.getDetails().isTradeBanned()) {
            player.send(new ALERT(RoomTradeManager.showTradeBanAlert(player)));
            return;
        }

        int instanceId = reader.readInt();
        Entity targetPartner = room.getEntityManager().getByInstanceId(instanceId);

        if (targetPartner == null) {
            return;
        }

        if (targetPartner.getType() != EntityType.PLAYER) {
            return;
        }

        Player tradePartner = (Player) targetPartner;

        if (tradePartner.getDetails().isTradeBanned()) {
            player.send(new ALERT("You cannot trade with this user"));
            return;
        }

        if (tradePartner.getRoomUser().getTradePartner() != null) {
            player.send(new TRADE_ALREADY_OPEN());
            return;
        }

         if (PlayerManager.getInstance().isMaintenance()) {
            player.send(new INFO_HOTEL_CLOSING(PlayerManager.getInstance().getMaintenanceAt()));
            return;
        }

        RoomTradeManager.close(player.getRoomUser(), false);
        RoomTradeManager.close(tradePartner.getRoomUser(), false);

        player.send(new TRADEOPEN(player.getDetails().getId(), player.getDetails().isTradeEnabled(), tradePartner.getDetails().getId(), tradePartner.getDetails().isTradeEnabled()));
        tradePartner.send(new TRADEOPEN(tradePartner.getDetails().getId(), tradePartner.getDetails().isTradeEnabled(), player.getDetails().getId(), player.getDetails().isTradeEnabled()));

        if (!player.getDetails().isTradeEnabled() && !tradePartner.getDetails().isTradeEnabled()) {
            return;
        }

        player.getRoomUser().setStatus(StatusType.TRADE, "");
        player.getRoomUser().setNeedsUpdate(true);
        player.getRoomUser().setTradePartner(tradePartner);

        tradePartner.getRoomUser().setStatus(StatusType.TRADE, "");
        tradePartner.getRoomUser().setNeedsUpdate(true);
        tradePartner.getRoomUser().setTradePartner(player);

        //RoomTradeManager.refreshWindow(player);
        //RoomTradeManager.refreshWindow(tradePartner);
    }
}
