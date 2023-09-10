package org.alexdev.havana.messages.incoming.handshake;

import org.alexdev.havana.game.GameScheduler;
import org.alexdev.havana.game.encryption.DiffieHellman;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.handshake.CRYPTO_PARAMETERS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.concurrent.TimeUnit;

public class INIT_CRYPTO implements MessageEvent {

    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.isLoggedIn()) {
            return;
        }

        String pToken = DiffieHellman.generateRandomNumString(24);

        player.send(new CRYPTO_PARAMETERS(pToken));
        player.getNetwork().setToken(pToken);
    }

}
