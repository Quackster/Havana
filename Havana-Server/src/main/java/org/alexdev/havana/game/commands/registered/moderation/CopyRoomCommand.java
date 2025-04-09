package org.alexdev.havana.game.commands.registered.moderation;

import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.dao.mysql.NavigatorDao;
import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerRank;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CopyRoomCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.COMMUNITY_MANAGER);
    }

    @Override
    public void addArguments() {

    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        // :givebadge Alex NL1

        // should refuse to give badges that belong to ranks
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        var room = player.getRoomUser().getRoom();

        if (room.isPublicRoom()) {
            return;
        }

        var roomName = room.getData().getName() + " (2)";
        var roomModel = room.getModel().getName();
        var roomShowName = room.getData().showOwnerName();
        var accessType = room.getData().getAccessTypeId();

        int roomId = -1;
        try {
            roomId = NavigatorDao.createRoom(player.getDetails().getId(), roomName, roomModel, roomShowName, accessType);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (roomId == -1)
            return;

        var copyRoom = RoomDao.getRoomById(roomId);

        copyRoom.getData().setWallpaper(room.getData().getWallpaper());
        copyRoom.getData().setFloor(room.getData().getFloor());
        copyRoom.getData().setLandscape(room.getData().getLandscape());

        RoomDao.saveDecorations(copyRoom);

        List<Item> items = new ArrayList<>();

        for (Item item : room.getItems()) {
            if (item.hasBehaviour(ItemBehaviour.TELEPORTER)) {
                continue;
            }

            var copyItem = new Item();
            copyItem.setOwnerId(player.getDetails().getId());
            copyItem.setDefinitionId(item.getDefinition().getId());
            copyItem.setCustomData(item.getCustomData());
            copyItem.setRoomId(roomId);

            if (item.hasBehaviour(ItemBehaviour.WALL_ITEM))
                copyItem.setWallPosition(item.getWallPosition());
            else {
                copyItem.getPosition().setX(item.getPosition().getX());
                copyItem.getPosition().setY(item.getPosition().getY());
                copyItem.getPosition().setZ(item.getPosition().getZ());
                copyItem.getPosition().setRotation(item.getPosition().getRotation());
            }

            try {
                ItemDao.newItem(copyItem);
                items.add(copyItem);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        ItemDao.updateItems(items);

        copyRoom.forward(player, false);
     }

    @Override
    public String getDescription() {
        return "Creates a copy of the room";
    }
}