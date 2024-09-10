package org.alexdev.havana.messages.flash.incoming.rooms;

import org.alexdev.havana.dao.mysql.WardrobeDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.flash.outgoing.rooms.WARDROBE;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class FLASH_GETWARDROBE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        player.send(new WARDROBE(WardrobeDao.getWardrobe(player.getDetails().getId())));
    }
}
