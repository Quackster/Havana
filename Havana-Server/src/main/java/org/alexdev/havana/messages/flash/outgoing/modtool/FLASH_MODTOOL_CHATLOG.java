package org.alexdev.havana.messages.flash.outgoing.modtool;

import org.alexdev.havana.game.moderation.ChatMessage;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;
import org.alexdev.havana.util.StringUtil;

import java.util.Calendar;
import java.util.List;

public class FLASH_MODTOOL_CHATLOG extends MessageComposer {
    private final List<ChatMessage> modChatlog;
    private final Room room;

    public FLASH_MODTOOL_CHATLOG(Room room, List<ChatMessage> modChatlog) {
        this.room = room;
        this.modChatlog = modChatlog;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeBool(this.room.isPublicRoom());
        response.writeInt(this.room.getId());
        response.writeString(this.room.getData().getName());
        response.writeInt(this.modChatlog.size());

        for (ChatMessage chatMessage : this.modChatlog) {
            response.writeInt(chatMessage.getCalendar().get(Calendar.HOUR_OF_DAY));
            response.writeInt(chatMessage.getCalendar().get(Calendar.MINUTE));
            response.writeInt(chatMessage.getPlayerId());
            response.writeString(chatMessage.getPlayerName());
            response.writeString(StringUtil.filterInput(chatMessage.getMessage(), true));
        }
    }

    @Override
    public short getHeader() {
        return 535;
    }
}
