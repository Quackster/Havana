package org.alexdev.havana.messages.incoming.rooms;

import org.alexdev.havana.game.ads.AdManager;
import org.alexdev.havana.game.ads.Advertisement;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.rooms.INTERSITIALDATA;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.config.GameConfiguration;

import java.util.concurrent.ThreadLocalRandom;

public class GETINTEREST implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (!GameConfiguration.getInstance().getBoolean("room.intersitial.ads")) {
            player.send(new INTERSITIALDATA(null, null));
            return;
        }

        if (player.getRoomUser().isTeleporting()) {
            player.send(new INTERSITIALDATA(null, null));
            return;
        }
        
        if (ThreadLocalRandom.current().nextInt(5 + 1) != 5) {
            player.send(new INTERSITIALDATA(null, null));
            return;
        }

        String image = null;
        String url = null;

        Advertisement advertisement = AdManager.getInstance().getRandomLoadingAd();

        if (advertisement != null) {
            /*image = GameConfiguration.getInstance().getString("advertisement.api");
            image = image.replace("{roomId}", String.valueOf(room.getId()));
            image = image.replace("{pictureName}", advertisement.getImage());*/

            if (advertisement.getImage() != null) {
                image = GameConfiguration.getInstance().getString("site.path").replace("https", "http") + "/api/advertisement/get_img?ad=" + advertisement.getId();
            }

            if (advertisement.getUrl() != null) {
                url = GameConfiguration.getInstance().getString("site.path").replace("https", "http") + "/api/advertisement/get_url?ad=" + advertisement.getId();
            }
        }

        player.send(new INTERSITIALDATA(image, url));
    }
}
