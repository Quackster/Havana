package org.alexdev.havana.messages.outgoing.handshake;

import org.alexdev.havana.game.encryption.DiffieHellman;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class CRYPTO_PARAMETERS extends MessageComposer {
    private String token;

    public CRYPTO_PARAMETERS(String token) {
        this.token = token;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeString(this.token);
        response.writeBool(true);
    }

    @Override
    public short getHeader() {
        return 277;
    }
}
