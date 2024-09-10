package org.alexdev.havana.messages.flash.outgoing;

import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.messages.types.PlayerMessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;
import org.apache.commons.lang3.StringUtils;

public class FLASH_FLATINFO extends PlayerMessageComposer {
    private final Player player;
    private final Room room;
    private final boolean isLoading;
    private final boolean checkEntry;
    private final boolean overrideLock;

    public FLASH_FLATINFO(Player player, Room room, boolean overrideLock, boolean isLoading, boolean checkEntry) {
        this.player = player;
        this.room = room;
        this.overrideLock = overrideLock;
        this.isLoading = isLoading;
        this.checkEntry = checkEntry;
    }

    @Override
    public void compose(NettyResponse response) {
        if (player.getNetwork().isBetaConnected()) {
            response.writeBool(this.isLoading);
            response.writeInt(this.room.getId());
            response.writeBool(this.checkEntry);

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
        } else {
            response.writeBool(this.isLoading);
            response.writeInt(this.room.getId());
            response.writeBool(this.checkEntry);

            response.writeString(this.room.getData().getName());

            if (this.room.isOwner(player.getDetails().getId()) || this.room.getData().showOwnerName() || this.player.hasFuse(Fuseright.SEE_ALL_ROOMOWNERS)) {
                response.writeString(this.room.getData().getOwnerName());
            } else {
                response.writeString("-");
            }

            response.writeInt(overrideLock ? 0 : this.room.getData().getAccessTypeId());
            response.writeInt(this.room.getData().getVisitorsNow());
            response.writeInt(this.room.getData().getVisitorsMax());
            response.writeString(this.room.getData().getDescription());
            response.writeBool(true);
            response.writeBool(this.room.getCategory().hasAllowTrading()); // Allow trading

            response.writeInt(this.room.getData().getRating()); // Is called "marker" in Lingo code
            response.writeInt(this.room.getData().getCategoryId());
            response.writeString("");

            var tags = room.getData().getTags();
            response.writeInt(tags.size());

            for (String tag : tags) {
                response.writeString(tag);
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
                    String[] iconItems = data.split(",");

                    int iconPosition = StringUtils.isNumeric(iconItems[0]) ? Integer.parseInt(iconItems[0]) : 0;
                    int iconId = StringUtils.isNumeric(iconItems[1]) ? Integer.parseInt(iconItems[1]) : 0;

                    response.writeInt(iconPosition);
                    response.writeInt(iconId);
                }
            } catch (Exception ex) {
                response.writeInt(0);
                response.writeInt(0);
                response.writeInt(0);
            }

            response.writeBool(true);
        }
    }

    @Override
    public short getHeader() {
        return 454;
    }
}
