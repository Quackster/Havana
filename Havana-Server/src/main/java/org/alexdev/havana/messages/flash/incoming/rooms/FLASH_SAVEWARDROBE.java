package org.alexdev.havana.messages.flash.incoming.rooms;

import org.alexdev.havana.dao.mysql.WardrobeDao;
import org.alexdev.havana.game.player.Wardrobe;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.List;

public class FLASH_SAVEWARDROBE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int slotId = reader.readInt();
        String figure = reader.readString();
        String sex = reader.readString().equals("M") ? "M" : "F";

        if (slotId < 1 || slotId > 5) {
            return;
        }

        int userId = player.getDetails().getId();

        List<Wardrobe> wardrobeList = WardrobeDao.getWardrobe(userId);
        Wardrobe wardrobeData = wardrobeList.stream().filter(wardrobe -> wardrobe.getSlotId() == slotId).findFirst().orElse(null);

        if (wardrobeData == null) {
            WardrobeDao.addWardrobe(userId, slotId, figure, sex);
        } else {
            WardrobeDao.updateWardrobe(userId, slotId, figure, sex);
        }

    }
}
