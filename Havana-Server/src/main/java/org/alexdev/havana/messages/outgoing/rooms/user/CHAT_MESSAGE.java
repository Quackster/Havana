package org.alexdev.havana.messages.outgoing.rooms.user;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class CHAT_MESSAGE extends MessageComposer {
    public enum ChatMessageType {
        CHAT (24), // @X
        SHOUT (26), // @Z
        WHISPER (25); // @Y

        private final short header;

        ChatMessageType(int header) {
            this.header = (short) header;
        }

        public short getHeader() {
            return header;
        }
    }

    private final ChatMessageType type;
    private final int instanceId;
    private String message;
    private final int gestureId;

    public CHAT_MESSAGE(ChatMessageType type, int instanceId, String message, int gestureId) {
        this.type = type;
        this.instanceId = instanceId;
        this.message = message;
        this.gestureId = gestureId;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.instanceId);
        response.writeString(this.message);
        response.writeInt(this.gestureId);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public short getHeader() {
        return this.type.getHeader();
    }
}
