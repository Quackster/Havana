package org.alexdev.havana.messages.incoming.handshake;

import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.alerts.LOCALISED_ERROR;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.util.MalformedPacketException;
import org.alexdev.havana.util.StringUtil;

public class TRY_LOGIN implements MessageEvent {

    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.isLoggedIn()) {
            return;
        }
        
        String username = StringUtil.filterInput(reader.readString(), true);
        String password = StringUtil.filterInput(reader.readString(), true);

        if (!PlayerDao.login(player.getDetails(), username, password)) {
            player.send(new LOCALISED_ERROR("Login incorrect"));
        } else {
            player.login();
        }
    }
}
