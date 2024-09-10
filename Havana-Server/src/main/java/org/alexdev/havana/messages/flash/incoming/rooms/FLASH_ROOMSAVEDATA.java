package org.alexdev.havana.messages.flash.incoming.rooms;

import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.dao.mysql.TagDao;
import org.alexdev.havana.game.navigator.NavigatorManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.netty.streams.NettyResponse;
import org.alexdev.havana.util.StringUtil;

public class FLASH_ROOMSAVEDATA implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int roomId = reader.readInt();

        Room room = RoomManager.getInstance().getRoomById(roomId);

        if (room == null || !room.isOwner(player.getDetails().getId())) {
            return;
        }

        String name = StringUtil.filterInput(reader.readString(), true);
        String description = StringUtil.filterInput(reader.readString(), true);
        int roomState = reader.readInt();
        String password = StringUtil.filterInput(reader.readString(), true);
        int maxUsers = reader.readInt();

        if (name.isBlank()) {
            return;
        }

        reader.readBoolean();
        reader.readBoolean();
        reader.readBoolean();

        int categoryId = reader.readInt();
        int tagCount = reader.readInt();

        if (maxUsers < 10 || maxUsers > 50) {
            maxUsers = 25;
        }

        var category = NavigatorManager.getInstance().getCategoryById(categoryId);

        if (category == null) {
            category = NavigatorManager.getInstance().getCategoryById(2);
        }

        if (category.getMinimumRoleSetFlat().getRankId() > player.getDetails().getRank().getRankId()) {
            category = NavigatorManager.getInstance().getCategoryById(2);
        }

        if (category.isNode() || category.isPublicSpaces()) {
            category = NavigatorManager.getInstance().getCategoryById(2);
        }

        categoryId = category.getId();

        if (roomState != 0 && roomState != 1 && roomState != 2) {
            roomState = 0;
        }

        TagDao.removeTags(0, roomId, 0);

        if (tagCount > 0 && tagCount < 3) {
            for (int i = 0; i < tagCount; i++) {
                String tag = StringUtil.isValidTag(StringUtil.filterInput(reader.readString(), true), 0, roomId, 0);

                if (tag == null) {
                    continue;
                }

                StringUtil.addTag(tag, 0, roomId, 0);
            }
        }

        room.getData().setName(name);
        room.getData().setDescription(description);
        room.getData().setAccessType(roomState);
        room.getData().setPassword(password);
        room.getData().setVisitorsMax(maxUsers);
        room.getData().setCategoryId(categoryId);
        RoomDao.save(room);

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

       // boolean overrideLock = room.isOwner(player.getDetails().getId());
        //player.send(new FLASH_FLATINFO(player, room, overrideLock, false, false));
    }
}
