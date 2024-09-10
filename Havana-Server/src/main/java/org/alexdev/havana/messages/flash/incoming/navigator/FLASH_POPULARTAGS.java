package org.alexdev.havana.messages.flash.incoming.navigator;

import org.alexdev.havana.dao.mysql.TagDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.concurrent.atomic.AtomicInteger;

public class FLASH_POPULARTAGS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getNetwork().isBetaConnected()) {
            return;
        }

        var popularTags = TagDao.getRoomTagData(50);

        player.send(new MessageComposer() {
            @Override
            public void compose(NettyResponse response) {
                response.writeInt(popularTags.size());

                var i = new AtomicInteger(1);

                for (var tagData : popularTags.entrySet()) {
                    response.writeString(tagData.getKey());
                    response.writeInt(tagData.getValue());
                }
            }

            @Override
            public short getHeader() {
                return 452;
            }
        });
    }
}
