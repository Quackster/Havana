package org.alexdev.havana.messages.outgoing.handshake;

import org.alexdev.havana.game.encryption.DiffieHellman;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class CRYPTO_PARAMETERS extends MessageComposer {

    @Override
    public void compose(NettyResponse response) {
        response.writeString(DiffieHellman.generateRandomNumString(32));
        response.writeInt(0);
    }

    @Override
    public short getHeader() {
        return 277;
    }
}
