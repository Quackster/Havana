package org.alexdev.havana.messages.incoming.rooms;

import org.alexdev.havana.game.ads.Advertisement;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.ads.AdManager;
import org.alexdev.havana.messages.flash.outgoing.FLASH_FLATINFO;
import org.alexdev.havana.messages.flash.outgoing.FLASH_ROOMENTRYINFO;
import org.alexdev.havana.messages.outgoing.rooms.*;
import org.alexdev.havana.messages.outgoing.rooms.user.USER_OBJECTS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.config.GameConfiguration;

import java.util.List;

public class GETROOMAD implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        String image = null;
        String url = null;

        if (room.isPublicRoom()) {
            Advertisement advertisement = AdManager.getInstance().getRandomAd(room.getId());

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
        }

        if (!GameConfiguration.getInstance().getBoolean("room.ads")) {
            image = null;
            url = null;
        }

        player.send(new ROOMAD(image, url));

        if (player.getNetwork().isFlashConnection()) {
            player.send(new OBJECTS_WORLD(room.getItemManager().getPublicItems()));
            player.send(new ITEMS(room));
            player.send(new ACTIVE_OBJECTS(room));

            player.getMessenger().sendStatusUpdate();

            player.send(new USER_OBJECTS(room.getEntities()));
            room.send(new USER_OBJECTS(player));

            player.send(new FLASH_ROOMENTRYINFO(player, room));

            if (!room.isPublicRoom()) {
                player.send(new FLASH_FLATINFO(player, room, false, true, false));
            }

            if (player.getNetwork().isBetaConnected()) {
                player.send(new HEIGHTMAP(player.getRoomUser().getRoom().getModel()));
                player.send(new FLOOR_MAP(player.getRoomUser().getRoom().getModel()));

                if (!player.getRoomUser().getRoom().isPublicRoom()) {
                    player.send(new HEIGHTMAP_UPDATE(player.getRoomUser().getRoom(), player.getRoomUser().getRoom().getModel()));
                }

                player.send(new USER_OBJECTS(List.of()));
            }

            new G_STAT().handle(player, null);
        }

        /*player.send(new MessageComposer() {
            @Override
            public void compose(NettyResponse response) {
                response.writeString("http://localhost/api/get_ad?roomId=123&picture=ad_lido_L.gif");
                response.writeString("http://localhost/");
            }

            @Override
            public short getHeader() {
                return 208;
            }
        });*/

        /*          response.writeString("http://localhost/c_images/billboards/getad.php?picture=ad_lido_L.gif");
                response.writeString("http://localhost/");*/
    }
}
