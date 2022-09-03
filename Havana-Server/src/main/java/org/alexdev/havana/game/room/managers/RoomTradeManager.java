package org.alexdev.havana.game.room.managers;

import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.dao.mysql.TransactionDao;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.entities.RoomPlayer;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.outgoing.trade.TRADE_CLOSE;
import org.alexdev.havana.messages.outgoing.trade.TRADE_ITEMS;
import org.alexdev.havana.server.netty.NettyPlayerNetwork;
import org.alexdev.havana.util.DateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RoomTradeManager {
    public static final int TRADE_BAN_IP_HISTORY_LIMIT = 20;

    /**
     * Close trade window, called when user leaves room, or closes
     * the trade window. Will close the partners trade window too.
     *
     * @param roomEntity the room user to close the trade window for
     */
    public static void close(RoomPlayer roomEntity, boolean serverClosed) {
        Player player = (Player) roomEntity.getEntity();

        if (roomEntity.getTradePartner() != null) {
            player.send(new TRADE_CLOSE(serverClosed ? player.getDetails().getId() : player.getDetails().getId()));
            player.getInventory().getView("new");

            roomEntity.getTradePartner().send(new TRADE_CLOSE(serverClosed ? player.getRoomUser().getTradePartner().getDetails().getId() : player.getDetails().getId()));
            roomEntity.getTradePartner().getInventory().getView("new");

            reset(roomEntity.getTradePartner().getRoomUser());
        }

        reset(roomEntity);
    }

    /**
     * Finishes trade window with user.
     *
     * @param roomEntity the room user to close the trade window for
     */
    public static void finish(RoomPlayer roomEntity) {
        Player player1 = (Player) roomEntity.getEntity();
        Player player2 = null;

        if (roomEntity.getTradePartner() != null) {
            player2 = roomEntity.getTradePartner();
            reset(player2.getRoomUser());
        }

        reset(roomEntity);

        if (player1 != null) {
            player1.getInventory().getView("new");
        }

        if (player2 != null) {
            player2.getInventory().getView("new");
        }
    }

    /**
     * Resets all trade variables.
     *
     * @param roomEntity the room user to reset the trade variables for
     */
    private static void reset(RoomPlayer roomEntity) {
        ItemDao.updateTradeStates(roomEntity.getTradeItems(), false);

        roomEntity.getTradeItems().clear();
        roomEntity.setTradeConfirmed(false);
        roomEntity.setTradeAccept(false);
        roomEntity.setTradePartner(null);
        roomEntity.setCanConfirmTrade(false);

        roomEntity.removeStatus(StatusType.TRADE);
        roomEntity.setNeedsUpdate(true);
    }

    /**
     * Refresh the trade window, called when a user agrees/unagrees or adds
     * an item to the trade window. Will be ignored if they have no trade
     * partner.
     *
     * @param player the player to refresh the trade window for
     */
    public static void refreshWindow(Player player) {
        if (player.getRoomUser().getTradePartner() == null) {
            return;
        }

        Player tradePartner = player.getRoomUser().getTradePartner();

        player.send(new TRADE_ITEMS(
                player,
                new ArrayList<>(player.getRoomUser().getTradeItems()),
                tradePartner,
                new ArrayList<>(tradePartner.getRoomUser().getTradeItems())
        ));
    }

    /**
     * Adds an item from the trade partners offered items into the first parameter
     * players' inventory.
     *
     * @param player the player to add the items into
     * @param tradePartner the player to get the items offered from
     */
    public static void addItems(Player player, Player tradePartner) {
        List<Item> itemsToUpdate = new ArrayList<>();

        for (Item item : tradePartner.getRoomUser().getTradeItems()) {
            tradePartner.getInventory().getItems().remove(item);
            player.getInventory().addItem(item);

            try {
                TransactionDao.createTransaction(player.getDetails().getId(),
                        String.valueOf(item.getDatabaseId()), String.valueOf(item.getDefinition().getId()), 1,
                        "Traded " + item.getDefinition().getName() + " from " + tradePartner.getDetails().getName(),
                        0, tradePartner.getDetails().getId(), false);

                TransactionDao.createTransaction(tradePartner.getDetails().getId(),
                        String.valueOf(item.getDatabaseId()), String.valueOf(item.getDefinition().getId()), 1,
                        "Traded " + item.getDefinition().getName() + " to " + player.getDetails().getName(),
                        0, player.getDetails().getId(), false);
            } catch (Exception ex) {

            }

            item.setOwnerId(player.getDetails().getId());
            item.setRoomId(0);

            itemsToUpdate.add(item);
        }

        ItemDao.updateItemOwnership(itemsToUpdate);
    }

    /**
     * Do trade ban.
     *
     * @param tradeBanned the person that is trade banned.
     */
    public static void addTradeBan(Player tradeBanned) {
        tradeBanned.send(new ALERT("You have been trade banned for 7 days for suspicious activity. Do not give credits to other users.<br>Read for more info: https://classichabbo.com/articles/22-trade-banned"));
        tradeBanned.getDetails().setTradeBanExpiration(DateUtil.getCurrentTimeSeconds() + TimeUnit.DAYS.toSeconds(7));
        ItemDao.saveTradeBanExpire(tradeBanned.getDetails().getId(), tradeBanned.getDetails().getTradeBanExpiration());
    }

    /**
     * Shows the trade ban alert to player.
     *
     * @param player the player
     */
    public static String showTradeBanAlert(Player player) {
        long uptime = (player.getDetails().getTradeBanExpiration() - DateUtil.getCurrentTimeSeconds()) * 1000;
        long days = (uptime / (1000 * 60 * 60 * 24));
        long hours = (uptime - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            /*long minutes = (uptime - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            long seconds = (uptime - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);*/

        return "You are temporarily banned from trading.<br>The ban will expire in " + days + " day(s) and " + hours + " hours(s)";

        //player.send(new ALERT("You are temporarily banned from trading.<br>The ban will expire in " + days + " day(s) and " + hours + " hours(s)"));
    }
}
