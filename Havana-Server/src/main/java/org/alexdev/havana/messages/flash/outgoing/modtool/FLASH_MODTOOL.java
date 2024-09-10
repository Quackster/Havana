package org.alexdev.havana.messages.flash.outgoing.modtool;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.List;

public class FLASH_MODTOOL extends MessageComposer {
    private List<String> userMessagePresets;
    private List<String> roomMessagePresets;

    public FLASH_MODTOOL() {
        // TODO: Remove this yucky hardcoded bullshit
        this.userMessagePresets = List.of("test");
        this.roomMessagePresets = List.of("room preset 1");
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(-1);
        response.writeInt(this.userMessagePresets.size());

        for (String preset : this.userMessagePresets) {
            response.writeString(preset);
        }

        response.writeInt(0);
        response.writeInt(14);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);

        response.writeInt(this.roomMessagePresets.size());

        for (String preset : this.roomMessagePresets) {
            response.writeString(preset);
        }

        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeString("test");
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeString("test");
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(1);
        response.writeString("test");
    }

    @Override
    public short getHeader() {
        return 531;
    }
}
