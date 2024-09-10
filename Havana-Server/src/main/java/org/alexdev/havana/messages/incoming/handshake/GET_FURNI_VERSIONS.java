package org.alexdev.havana.messages.incoming.handshake;

import org.alexdev.havana.game.item.ItemVersionManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class GET_FURNI_VERSIONS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getNetwork().isFlashConnection())
            return;

        var itemVersions = ItemVersionManager.getInstance().getItemVersions();

        player.send(new MessageComposer() {
            @Override
            public void compose(NettyResponse response) {
                response.writeInt(itemVersions.size());

                for (var kvp : itemVersions.entrySet()) {
                    response.writeString(kvp.getKey());
                    response.writeInt(kvp.getValue());
                }
            }

            @Override
            public short getHeader() {
                return 495;
            }
        });
    }
}
