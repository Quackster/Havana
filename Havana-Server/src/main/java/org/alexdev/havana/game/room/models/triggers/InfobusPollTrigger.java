package org.alexdev.havana.game.room.models.triggers;

import org.alexdev.havana.dao.mysql.InfobusDao;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.infobus.InfobusManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.triggers.GenericTrigger;
import org.alexdev.havana.messages.outgoing.infobus.BUS_DOOR;
import org.alexdev.havana.messages.outgoing.infobus.POLL_QUESTION;

public class InfobusPollTrigger extends GenericTrigger {
    @Override
    public void onRoomEntry(Entity entity, Room room, boolean firstEntry, Object... customArgs) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        /*var infobusPoll = InfobusManager.getInstance().getCurrentPoll();

        if (infobusPoll != null) {
            if (!InfobusDao.hasAnswer(infobusPoll.getId(), player.getDetails().getId())) {
                player.send(new POLL_QUESTION(infobusPoll.getPollData().getQuestion(), infobusPoll.getPollData().getAnswers()));
            }
        }*/

        //player.send(new POLL_QUESTION("How about I fuck your shit up?", new String[] { "Yes please", "No please", "How about both?"}));
    }

    @Override
    public void onRoomLeave(Entity entity, Room room, Object... customArgs) {

    }
}
