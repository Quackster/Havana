package org.alexdev.havana.messages.flash.outgoing.navigator;

import org.alexdev.havana.game.navigator.NavigatorManager;
import org.alexdev.havana.game.navigator.NavigatorStyle;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.List;

public class FLASH_FRONTPAGERESULT extends MessageComposer {
    private final List<Room> roomList;

    public FLASH_FRONTPAGERESULT(List<Room> roomList) {
        this.roomList = roomList;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(1);
        response.writeString("Public Rooms");

        response.writeInt(this.roomList.size());

        for (Room room : this.roomList) {
            NavigatorStyle navigatorStyle = NavigatorManager.getInstance().getNavigatorStyle(room.getId());
            response.writeString(room.getData().getName());
            response.writeString(navigatorStyle.getDescription());
            response.writeInt(navigatorStyle.getThumbnailLayout()); // type??
            response.writeString(room.getData().getName());
            response.writeString(navigatorStyle.getThumbnailUrl());
            response.writeInt(room.getData().getTotalVisitorsNow()); // users now
            response.writeInt(3); // public listing type
            response.writeString(navigatorStyle.getThumbnailUrl());
            response.writeInt(room.getData().getId());
            response.writeInt(0);
            response.writeString(room.getData().getCcts());
            response.writeInt(room.getData().getTotalVisitorsMax()); // max users
            response.writeInt(room.getId()); // room id
        }

    }

    @Override
    public short getHeader() {
        return 450;
    }
}
