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

public class FLASH_FLAT_RESULTS extends MessageComposer {
    private final List<Room> roomList;
    private final int mode;
    private final boolean showEvents;

    public FLASH_FLAT_RESULTS(List<Room> roomList, int mode, boolean showEvents) {
        this.roomList = roomList;
        this.mode = mode;
        this.showEvents = showEvents;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(0);
        response.writeString("");
        response.writeInt(this.roomList.size());

        for (Room room : this.roomList) {
            response.writeInt(room.getData().getId());

            var event = EventsManager.getInstance().getEventByRoomId(room.getId());
            DateFormat timeFormatter = DateFormat.getTimeInstance(DateFormat.DEFAULT, Locale.US);

            if (this.showEvents && event != null) {
                response.writeBool(true);
                response.writeString(event.getName());
                response.writeString(room.getData().getOwnerName());
                response.writeInt(room.getData().getAccessTypeId()); // room state
                response.writeInt(room.getData().getVisitorsNow());
                response.writeInt(room.getData().getVisitorsMax());
                response.writeString(room.getData().getDescription());
                response.writeInt(1);
                response.writeBool(room.getCategory().hasAllowTrading()); // can trade?
                response.writeInt(room.getData().getRating());
                response.writeInt(event.getCategoryId());
                response.writeString(DateUtil.getDate(event.getExpireTime() - EventsManager.getEventLifetime(), "hh:mm a").replace(".", "").toUpperCase());

                var tags = room.getData().getTags();
                response.writeInt(tags.size());

                for (String tag : tags) {
                    response.writeString(tag);
                }
            } else {
                response.writeBool(false);
                response.writeString(room.getData().getName());
                response.writeString(room.getData().getOwnerName());
                response.writeInt(room.getData().getAccessTypeId()); // room state
                response.writeInt(room.getData().getVisitorsNow());
                response.writeInt(room.getData().getVisitorsMax());
                response.writeString(room.getData().getDescription());
                response.writeInt(1);
                response.writeBool(room.getCategory().hasAllowTrading()); // can trade?
                response.writeInt(room.getData().getRating());
                response.writeInt(room.getData().getCategoryId());
                response.writeString("");

                var tags = room.getData().getTags();
                response.writeInt(tags.size());

                for (String tag : tags) {
                    response.writeString(tag);
                }
            }


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

            //response.writeBool(false);
        }
    }

    @Override
    public short getHeader() {
        return 451;
    }
}
