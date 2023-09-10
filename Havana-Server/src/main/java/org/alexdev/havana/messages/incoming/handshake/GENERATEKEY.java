package org.alexdev.havana.messages.incoming.handshake;

import org.alexdev.havana.game.encryption.HugeInt15;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.handshake.SECRET_KEY;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.Arrays;

public class GENERATEKEY implements MessageEvent {

    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.isLoggedIn()) {
            return;
        }

        String publicKey = reader.readString();
        player.getDiffieHellman().generateSharedKey(publicKey);

        player.send(new SECRET_KEY(player.getDiffieHellman().getPublicKey().toString()));
        player.setDecoder(player.getDiffieHellman().getSharedKey());

        //System.out.println("publicKey: " + publicKey);
        //System.out.println("adobeClientSharedKey: " + adobeClientSharedKey.getString());

        //System.out.println("sharedKey: " + player.getDiffieHellman().getSharedKey());
        //System.out.println("sharedKey byte array: " + Arrays.toString(HugeInt15.getByteArray(player.getDiffieHellman().getSharedKey())));
    }
}
