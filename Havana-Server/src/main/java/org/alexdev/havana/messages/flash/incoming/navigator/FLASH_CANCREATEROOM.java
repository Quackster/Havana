package org.alexdev.havana.messages.flash.incoming.navigator;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class FLASH_CANCREATEROOM implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        player.send(new MessageComposer() {
            @Override
            public void compose(NettyResponse response) {
                response.writeBool(false);
                response.writeInt(25);
            }

            @Override
            public short getHeader() {
                return 512;
            }
        });
    }
}
