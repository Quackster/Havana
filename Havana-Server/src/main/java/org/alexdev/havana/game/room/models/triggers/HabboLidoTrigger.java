package org.alexdev.havana.game.room.models.triggers;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.game.triggers.GenericTrigger;

public class HabboLidoTrigger extends GenericTrigger {
    @Override
    public void onRoomEntry(Entity entity, Room room, boolean firstEntry, Object... customArgs) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player)entity;

        /*if (player.getNetwork().isFlashConnected()) {
            if (player.getDetails().getPoolFigure().isEmpty()) {
                String[] swimColors = new String[] {
                        "250,56,49",
                        "253,146,160",
                        "42,199,210",
                        "53,51,44",
                        "239,255,146",
                        "198,255,152"
                };

                String swimColor = swimColors[ThreadLocalRandom.current().nextInt(swimColors.length)];
                player.getDetails().setPoolFigure(String.format("ch=s02/%s", swimColor));
                PlayerDao.saveDetails(player.getDetails().getId(), player.getDetails().getFigure(), player.getDetails().getPoolFigure(), player.getDetails().getSex());
            }
        }*/

        if (player.getRoomUser().getPosition().getZ() == 1.0) { // User entered room from the other pool
            player.getRoomUser().setStatus(StatusType.SWIM, "");
            player.getRoomUser().setNeedsUpdate(true);
        }
    }

    @Override
    public void onRoomLeave(Entity entity, Room room, Object... customArgs)  {

    }
}
