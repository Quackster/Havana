package org.alexdev.havana.messages.incoming.handshake;

import org.alexdev.havana.game.GameScheduler;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.handshake.CRYPTO_PARAMETERS;
import org.alexdev.havana.messages.outgoing.handshake.SESSION_PARAMETERS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.concurrent.TimeUnit;

public class INIT_CRYPTO implements MessageEvent {

    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.isLoggedIn()) {
            return;
        }

        //String prime = Util.getRSA().sign(dh.getPrime().toString());
        //String generator = Util.getRSA().sign(dh.getGenerator().toString());

        //player.sendObject("DAQBHIIIKHJIPAIQAdd-MM-yyyy\u0002SAHPB/client\u0002QBHIJWVVVSNKQCFUBJASMSLKUUOJCOLJQPNSBIRSVQBRXZQOTGPMNJIHLVJCRRULBLUO" + (char)1);

        player.send(new CRYPTO_PARAMETERS());

        // Try again
        this.retrySend(player);
    }

    /**
     * Retry sending the crypto parameters if after a second we received no response from the client.
     *
     * @param player the player to send the parameters to
     */
    private void retrySend(Player player) {
        GameScheduler.getInstance().getService().schedule(() -> {
            if (player.isDisconnected()) {
                return;
            }

            if (player.hasGenerateKey()) {
                return;
            }

            player.send(new CRYPTO_PARAMETERS());
        }, 1, TimeUnit.SECONDS);
    }
}
