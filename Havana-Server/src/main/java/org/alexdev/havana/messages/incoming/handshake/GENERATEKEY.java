package org.alexdev.havana.messages.incoming.handshake;

import org.alexdev.havana.game.encryption.DiffieHellman;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.handshake.SECRET_KEY;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class GENERATEKEY implements MessageEvent {

    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.isLoggedIn()) {
            return;
        }

        String publicKey = reader.readString();

        player.getDiffieHellman().generateSharedKey(publicKey);
        player.setHasGenerateKey(true);

        player.send(new SECRET_KEY(DiffieHellman.generateRandomNumString(24)));//player.getDetails()));
    }
}
