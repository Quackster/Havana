package org.alexdev.havana.messages.flash.outgoing.navigator.beta;

import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.flash.outgoing.navigator.FLASH_GET_GUEST_ROOM_RESULT;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.List;
import java.util.Map;

public class BETA_FLASH_FRONTPAGERESULT extends MessageComposer {
    private final List<Room> roomList;
    private final int popularRoomCount;
    private final int friendsInRoomCount;
    private final int myRoomsCount;
    private final int favouriteRoomsCount;
    private final Map<String, Integer> categoryList;

    public BETA_FLASH_FRONTPAGERESULT(List<Room> roomList, int popularRoomCount, int friendsInRoomCount, int myRoomsCount, int favouriteRoomsCount, Map<String, Integer> categoryList) {
        this.roomList = roomList;
        this.popularRoomCount = popularRoomCount;
        this.friendsInRoomCount = friendsInRoomCount;
        this.myRoomsCount = myRoomsCount;
        this.favouriteRoomsCount = favouriteRoomsCount;
        this.categoryList = categoryList;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.roomList.size()); // Amount

        for (var room : this.roomList) {
            FLASH_GET_GUEST_ROOM_RESULT.serialise(response, room);
        }

        response.writeBool(false);
        response.writeInt(-1);
        response.writeBool(false);

        response.writeInt(4);

        response.writeInt(1); // nav category id
        response.writeInt(this.popularRoomCount); // room population

        response.writeInt(4); // nav category id
        response.writeInt(this.friendsInRoomCount); // room population

        response.writeInt(5); // nav category id
        response.writeInt(this.myRoomsCount); // room population

        response.writeInt(6); // nav category id
        response.writeInt(this.favouriteRoomsCount); // room population

        response.writeInt(this.categoryList.size()); // categories

        for (var kvp : this.categoryList.entrySet()) {
            response.writeString(kvp.getKey());
            response.writeInt(kvp.getValue());
        }
    }

    @Override
    public short getHeader() {
        return 450;
    }
}
