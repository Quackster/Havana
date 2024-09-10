package org.alexdev.havana.messages.flash.outgoing.navigator;

import org.alexdev.havana.game.events.EventsManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;
import org.alexdev.havana.util.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

public class FLASH_GET_GUEST_ROOM_RESULT extends MessageComposer {
    private final int forceDisplay;
    private final int opCode;
    private final List<Room> roomList;

    public FLASH_GET_GUEST_ROOM_RESULT(int forceDisplay, int opCode, List<Room> roomList) {
        this.forceDisplay = forceDisplay;
        this.opCode = opCode;
        this.roomList = roomList;
    }

    @Override
    public void compose(NettyResponse response) {
        /*response.writeInt(0);
        response.writeString("");
        response.writeInt(this.roomList.size());

        for (Room room : this.roomList) {*/
        response.writeInt(this.forceDisplay);
        response.writeInt(this.opCode);
        response.writeString("");

        response.writeInt(this.roomList.size());

        for (Room room : this.roomList) {
            serialise(response, room);
        }

       /* response.writeInt(1000); // Room ID
        response.writeBool(false);
        response.writeString("Room 1"); // Room name
        response.writeString("Alex"); // Room owner name
        response.writeInt(0); // Accesstype (0 = open, 1 = closed, 2 = password)
        response.writeInt(0); // Current visitor amount
        response.writeInt(25); // Max visitor amount
        response.writeString("Description"); // Room description
        response.writeInt(0);
        response.writeBool(false);
        response.writeInt(0);
        response.writeInt(0);
        response.writeString("tag1");
        response.writeString("tag2");
        response.writeInt(0);
        response.writeInt(0);
        response.writeInt(0);*/

        /*response.writeInt(0);
        response.writeInt(0);
        response.writeInt(0);
        response.writeInt(2); // Icon ID?
        response.writeString("Category"); // Room category name
        response.writeString("tag1");
        response.writeString("tag2");
        response.writeBool(false);

        response.writeInt(1001); // Room ID
        response.writeInt(0);
        response.writeString("Room 1"); // Room name
        response.writeString("Alex"); // Room owner name
        response.writeInt(0); // Accesstype (0 = open, 1 = closed, 2 = password)
        response.writeInt(0); // Current visitor amount
        response.writeInt(25); // Max visitor amount
        response.writeString("Description"); // Room description
        response.writeInt(0);
        response.writeInt(0);
        response.writeInt(0);
        response.writeInt(2); // Icon ID?
        response.writeString("Category"); // Room category name
        response.writeString("tag1");
        response.writeString("tag2");
        response.writeBool(false);*/
        //}
    }

    public static void serialise(NettyResponse response, Room room) {
        response.writeInt(room.getId()); // Room ID
        response.writeBool(false); // Is event
        response.writeString(room.getData().getName()); // Room name
        response.writeString(room.getData().getOwnerName()); // Room owner name
        response.writeInt(room.getData().getAccessTypeId()); // Accesstype (0 = open, 1 = closed, 2 = password)
        response.writeInt(room.getData().getVisitorsNow()); // Current visitor amount
        response.writeInt(room.getData().getVisitorsMax()); // Max visitor amount
        response.writeString(room.getData().getDescription()); // Room description
        response.writeInt(0);
        response.writeBool(room.getCategory().hasAllowTrading()); // can trade
        response.writeInt(0);
        response.writeInt(room.getData().getTags().size());

        for (String tag : room.getData().getTags()) {
            response.writeString(tag);
        }

        // Icon shit
        try {
            String[] iconData = room.getData().getIconData().split("\\|");

            int background = StringUtils.isNumeric(iconData[0]) ? Integer.parseInt(iconData[0]) : 0;
            int topLayer = StringUtils.isNumeric(iconData[1]) ? Integer.parseInt(iconData[1]) : 0;
            String[] items = iconData[2].split(" ");

            response.writeInt(background);
            response.writeInt(topLayer);
            response.writeInt(items.length);

            for (String data : items) {
                try {
                    String[] iconItems = data.split(",");

                    int iconPosition = StringUtils.isNumeric(iconItems[0]) ? Integer.parseInt(iconItems[0]) : 0;
                    int iconId = StringUtils.isNumeric(iconItems[1]) ? Integer.parseInt(iconItems[1]) : 0;

                    response.writeInt(iconPosition);
                    response.writeInt(iconId);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (Exception ex) {
            response.writeInt(0);
            response.writeInt(0);
            response.writeInt(0);
        }
    }

    @Override
    public short getHeader() {
        return 451;
    }
}
