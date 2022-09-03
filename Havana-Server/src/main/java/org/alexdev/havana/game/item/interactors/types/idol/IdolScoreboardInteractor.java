package org.alexdev.havana.game.item.interactors.types.idol;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.entities.RoomEntity;
import org.alexdev.havana.game.triggers.GenericTrigger;
import org.alexdev.havana.messages.incoming.rooms.idol.OPEN_PERFORMER_GUI;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.HashMap;
import java.util.Map;

public class IdolScoreboardInteractor extends GenericTrigger {
    @Override
    public void onEntityStop(Entity entity, RoomEntity roomEntity, Item item, boolean isRotation) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        /*player.getRoomUser().getPosition().setRotation(item.getPosition().getRotation());
        player.getRoomUser().setNeedsUpdate(true);*/
        
        if (isRotation) {
            return;
        }

        Player player = (Player) entity;
        Map<Item, String> userDisks = new HashMap<>();

        if (roomEntity.getRoom().getItemManager().getSoundMachine() != null && roomEntity.getRoom().getItemManager().getSoundMachine().hasBehaviour(ItemBehaviour.SOUND_MACHINE)) {
            for (Item disk : player.getInventory().getItems()) {
                if (!item.isVisible()) {
                    continue;
                }

                if (!disk.hasBehaviour(ItemBehaviour.SONG_DISK)) {
                    continue;
                }

                userDisks.put(disk, disk.getCustomData().split(Character.toString((char) 10))[5]);
            }
        }

        player.send(new OPEN_PERFORMER_GUI(userDisks));

        roomEntity.getRoom().getIdolManager().resetChairs();
        roomEntity.getRoom().getIdolManager().updatePerformer();

        if (!item.getCustomData().equals("-1")) {
            item.setCustomData("-1");
            item.updateStatus();
            item.save();
        }
    }

    @Override
    public void onEntityLeave(Entity entity, RoomEntity roomEntity, Item item, Object... customArgs) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;
        player.send(new MessageComposer() {
            @Override
            public void compose(NettyResponse response) {
            }

            @Override
            public short getHeader() {
                return 492;
            }
        });

        roomEntity.getRoom().getIdolManager().resetChairs();
        roomEntity.getRoom().getIdolManager().updatePerformer();
        roomEntity.getRoom().getIdolManager().forceStop();
    }
}
