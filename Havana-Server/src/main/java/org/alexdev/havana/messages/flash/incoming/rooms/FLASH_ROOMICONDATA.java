package org.alexdev.havana.messages.flash.incoming.rooms;

import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.flash.outgoing.FLASH_FLATINFO;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.HashMap;
import java.util.Map;

public class FLASH_ROOMICONDATA implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int roomId = reader.readInt();

        Room room = RoomManager.getInstance().getRoomById(roomId);

        if (room == null || !room.isOwner(player.getDetails().getId())) {
            return;
        }

        Map<Integer, Integer> items = new HashMap<>();

        //int background = reader.readInt();
        int background = reader.readInt();
        int topLayer = reader.readInt();
        int itemAmount = reader.readInt();

        for (int i = 0; i < itemAmount; i++) {
            int position = reader.readInt();
            int item = reader.readInt();

            if (position < 0 || position > 10)
                return;

            if (item < 1 || item > 27)
                return;

            if (items.containsKey(position)) {
                return;
            }

            items.put(position, item);
        }

        if (background < 1 || background > 24)
            return;

        if (topLayer < 0 || topLayer > 11)
            return;

        StringBuilder formattedItems = new StringBuilder();
        formattedItems.append(background).append("|");
        formattedItems.append(topLayer).append("|");

        int i = 0;
        for (var kvp : items.entrySet()) {
            i++;

            formattedItems.append(kvp.getKey());
            formattedItems.append(",");
            formattedItems.append(kvp.getValue());

            if (i != items.size()) {
                formattedItems.append(" ");
            }
        }

        room.getData().setIconData(formattedItems.toString());
        RoomDao.saveIcon(room.getId(), formattedItems.toString());

        player.send(new MessageComposer() {
            @Override
            public void compose(NettyResponse response) {
                response.writeInt(roomId);
            }

            @Override
            public short getHeader() {
                return 467;
            }
        });

        player.send(new MessageComposer() {
            @Override
            public void compose(NettyResponse response) {
                response.writeInt(roomId);
            }

            @Override
            public short getHeader() {
                return 456;
            }
        });

        //player.send(new FLASH_FLATINFO(player, room, overrideLock, false, false));
    }
}
