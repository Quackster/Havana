package org.alexdev.havana.messages.incoming.rooms.items;

import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.dao.mysql.TransactionDao;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.ItemManager;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.managers.RoomTradeManager;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.NettyPlayerNetwork;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.util.MalformedPacketException;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;

public class PLACESTUFF implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws SQLException, MalformedPacketException {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.hasRights(player.getDetails().getId()) && !player.hasFuse(Fuseright.MOD)) {
            return;
        }

        String content = reader.readString();
        String[] data = content.split(" ");

        if (data.length == 0) {
            return;
        }

        // Make sure provided data is numeric
        if (!StringUtils.isNumeric(data[0])) {
            return;
        }

        int itemId = Integer.parseInt(data[0]);
        Item item = player.getInventory().getItem(itemId);

        if (item == null || !item.isVisible()) {
            return;
        }

        // Prevent users from placing non-tradable objects in users' rooms
        if (!player.hasFuse(Fuseright.MUTE) && room.getData().getOwnerId() != player.getDetails().getId()) {
            if (!item.getDefinition().isTradable()) {
                return;
            }
        }

        if (!player.hasFuse(Fuseright.MUTE) && room.getData().getOwnerId() != player.getDetails().getId()) {
            if (player.getDetails().isTradeBanned()) {
                player.send(new ALERT(RoomTradeManager.showTradeBanAlert(player)));
                return;
            }
        }

        PlayerDetails ownerDetails = PlayerManager.getInstance().getPlayerData(room.getData().getOwnerId());

        // Giving credits to self on same IP is suspicious behaviour
        if (item.hasBehaviour(ItemBehaviour.REDEEMABLE) || ItemManager.getInstance().hasPresentBehaviour(item, ItemBehaviour.REDEEMABLE)) {
            if (!player.hasFuse(Fuseright.MUTE)
                    && room.getData().getOwnerId() != player.getDetails().getId()
                    && player.getDetails().getIpAddress().equals(ownerDetails.getIpAddress())) {
                RoomTradeManager.addTradeBan(player);
                return;
            }
        }

        var ownerData = PlayerManager.getInstance().getPlayerData(room.getData().getOwnerId());

        if (ownerData != null && ownerData.isTradeBanned()) {
            if (room.getData().getOwnerId() != player.getDetails().getId()) {
                player.send(new ALERT("The room owner is trade banned."));
                return;
            }
        }

        if (room.getData().getOwnerId() != player.getDetails().getId()) {
            TransactionDao.createTransaction(player.getDetails().getId(),
                    String.valueOf(item.getDatabaseId()), String.valueOf(item.getDefinition().getId()), 1,
                    "Placed item " + item.getDefinition().getName() + " into " + room.getData().getOwnerName() + "'s room: " + room.getData().getId(),
                    room.getId(), room.getData().getOwnerId(), false);
        }

        if (item.hasBehaviour(ItemBehaviour.WALL_ITEM)) {
            String wallPosition = content.substring(data[0].length() + 1);

            // if (!ValidationUtil.validateWallPosition(wallPosition)) {
            //     player.send(new CANNOT_PLACE_STUFF_FROM_STRIP(11));
            //     return;
            // }

            if (item.hasBehaviour(ItemBehaviour.POST_IT)) {
                String defaultColour = "FFFF33";

                Item sticky = new Item();
                sticky.setOwnerId(room.getData().getOwnerId());
                sticky.setDefinitionId(item.getDefinition().getId());
                sticky.setCustomData(defaultColour);
                sticky.setWallPosition(wallPosition);
                sticky.setRoomId(room.getId());

                ItemDao.newItem(sticky);
                room.getMapping().addItem(player, sticky);

                // Set custom data as 1 for 1 post-it, if for some reason they have no number for the post-it.
                if (!StringUtils.isNumeric(item.getCustomData())) {
                    item.setCustomData("1");
                }

                if (StringUtils.isNumeric(item.getCustomData())) {
                    int totalStickies = Integer.parseInt(item.getCustomData()) - 1;

                    if (totalStickies <= 0) {
                        player.getInventory().getItems().remove(item);
                        item.delete();
                    } else {
                        item.setCustomData(String.valueOf(totalStickies));
                        item.save();
                    }
                }
                return;
            }

            item.setWallPosition(wallPosition);
        } else {
            int x = Integer.parseInt(data[1]);
            int y = Integer.parseInt(data[2]);
            int rotation = Integer.parseInt(data[3]);

            if (item.hasBehaviour(ItemBehaviour.REDIRECT_ROTATION_0)) {
                rotation = 0;
            }

            if (item.hasBehaviour(ItemBehaviour.REDIRECT_ROTATION_2)) {
                rotation = 2;
            }

            if (item.hasBehaviour(ItemBehaviour.REDIRECT_ROTATION_4)) {
                rotation = 4;
            }

            // Validate placed item rotation
            if (!item.getDefinition().hasBehaviour(ItemBehaviour.WALL_ITEM)) {
                if (!item.getDefinition().getAllowedRotations().contains(rotation)) {
                    rotation = item.getDefinition().getAllowedRotations().get(0);
                }
            }

            if (!item.isValidMove(item, room, x, y, rotation)) {
                return;
            }

            if (room.getMapping().getTile(x, y) != null) {
                item.getPosition().setX(x);
                item.getPosition().setY(y);
                item.getPosition().setRotation(rotation);
            }
        }

        if (room.getItemManager().hasTooMany(player, item))
            return;

        room.getMapping().addItem(player, item);

        player.getInventory().getItems().remove(item);

        if (player.getNetwork().isFlashConnection()) {
            player.getInventory().getView("new");
        }

        player.getRoomUser().getTimerManager().resetRoomTimer();
    }
}
