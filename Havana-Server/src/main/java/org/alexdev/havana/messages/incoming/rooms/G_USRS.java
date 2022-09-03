package org.alexdev.havana.messages.incoming.rooms;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.rooms.ACTIVE_OBJECTS;
import org.alexdev.havana.messages.outgoing.rooms.OBJECTS_WORLD;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class G_USRS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        player.send(new OBJECTS_WORLD(room.getItemManager().getPublicItems()));

        if (room.getModel().getName().equals("park_a")) {
            player.sendObject("@`SGSBMRDPBPA0.0\u0002I2\u0002Mqueue_tile2\u0002JMPGRAH0.0\u0002I2\u0002Mqueue_tile2\u0002SAMPFSAJ0.0\u0002I2\u0002Mqueue_tile2\u0002QBMRFSAPA0.0\u0002I2\u0002Mqueue_tile2\u0002SFMSERBJ0.0\u0002I2\u0002Mqueue_tile2\u0002SCMRFPBPA0.0\u0002I2\u0002Mqueue_tile2\u0002REMPGQBH0.0\u0002I2\u0002Mqueue_tile2\u0002PGMPFRBH0.0\u0002I2\u0002Mqueue_tile2\u0002PCMPEPBH0.0\u0002I2\u0002Mqueue_tile2\u0002QGMRFRBJ0.0\u0002I2\u0002Mqueue_tile2\u0002QDMRDQBPA0.0\u0002I2\u0002Mqueue_tile2\u0002RFMRERBJ0.0\u0002I2\u0002Mqueue_tile2\u0002PFMSDRBJ0.0\u0002I2\u0002Mqueue_tile2\u0002PDMPGPBH0.0\u0002I2\u0002Mqueue_tile2\u0002RGMSFRBJ0.0\u0002I2\u0002Mqueue_tile2\u0002RAMRESAPA0.0\u0002I2\u0002Mqueue_tile2\u0002RBMPGSAH0.0\u0002I2\u0002Mqueue_tile2\u0002SDMREQBPA0.0\u0002I2\u0002Mqueue_tile2\u0002QEMRFQBPA0.0\u0002I2\u0002Mqueue_tile2\u0002RCMPFPBH0.0\u0002I2\u0002Mqueue_tile2\u0002KMRDSAPA0.0\u0002I2\u0002Mqueue_tile2\u0002PAMPESAJ0.0\u0002I2\u0002Mqueue_tile2\u0002PBMQFSAJ0.0\u0002I2\u0002Mqueue_tile2\u0002IMPGQAH0.0\u0002I2\u0002Mqueue_tile2\u0002SEMRDRBJ0.0\u0002I2\u0002Mqueue_tile2\u0002QCMREPBPA0.0\u0002I2\u0002Mqueue_tile2\u0002SGMPGRBH0.0\u0002I2\u0002Mqueue_tile2\u0002QAMQESAJ0.0\u0002I2\u0002Mqueue_tile2\u0002QFMPERBH0.0\u0002I2\u0002Mqueue_tile2\u0002RDMPEQBH0.0\u0002I2\u0002Mqueue_tile2\u0002PEMPFQBH0.0\u0002I2\u0002Mqueue_tile2\u0002" + (char)1);
        } else {
            player.send(new ACTIVE_OBJECTS(room));
        }

        player.getMessenger().sendStatusUpdate();

        /*Bot bot = new Bot();
        bot.getDetails().fill(0, "Test", player.getDetails().getFigure(), "Hello loser!", "M");
        room.getEntityManager().enterRoom(bot, null);

        for (Bot n : room.getEntityManager().getEntitiesByClass(Bot.class)) {
            n.getRoomUser().talk("Hello, nerd!", CHAT_MESSAGE.ChatMessageType.WHISPER, List.of(player));
        }*/


 /*       if (!(room.getEntityManager().getPlayers().size() > 1)) {
            room.getEntityManager().tryInitialiseRoom();

            boolean isCancelled = PluginManager.getInstance().callEvent(PluginEvent.ROOM_FIRST_ENTRY_EVENT, new LuaValue[]{
                    CoerceJavaToLua.coerce(player),
                    CoerceJavaToLua.coerce(room)
            });

            if (isCancelled) {
                room.getEntityManager().leaveRoom(player, true);
            }
        }

        player.send(new USER_OBJECTS(room.getEntities()));
        room.send(new USER_OBJECTS(player), List.of(player));*/
    }
}
