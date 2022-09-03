package org.alexdev.havana.messages.incoming.rooms;

import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.rooms.FLATPROPERTY;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.util.MalformedPacketException;

import java.sql.SQLException;

public class FLATPROPBYITEM implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws SQLException, MalformedPacketException {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.isOwner(player.getDetails().getId()) && !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            return;
        }

        //String contents = reader.contents();
        //String property = contents.split("/")[0];

        int itemId = reader.readInt();//Integer.parseInt(contents.split("/")[1]);

        Item item = player.getInventory().getItem(itemId);

        if (item == null) {
            return;
        }

        String property = item.getDefinition().getSprite();
        String value = item.getCustomData();

        if (property.equals("wallpaper")) {
            room.getData().setWallpaper(Integer.parseInt(value));
        }

        if (property.equals("floor")) {
            room.getData().setFloor(Integer.parseInt(value));
        }

        if (property.equals("landscape")) {
            room.getData().setLandscape(value);
        }

        item.delete();
        RoomDao.saveDecorations(room);

        room.send(new FLATPROPERTY(property, value));

        player.getInventory().getItems().remove(item);
        player.getInventory().getView("new");
    }
}
