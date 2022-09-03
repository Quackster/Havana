package org.alexdev.havana.messages.incoming.messenger;

import org.alexdev.havana.dao.mysql.MessengerDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.util.MalformedPacketException;

import java.sql.SQLException;

public class MESSENGER_MARKREAD implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws SQLException, MalformedPacketException {
        int messageId = reader.readInt();

        MessengerDao.markMessageRead(messageId);
        player.getMessenger().getOfflineMessages().remove(messageId);
    }
}
