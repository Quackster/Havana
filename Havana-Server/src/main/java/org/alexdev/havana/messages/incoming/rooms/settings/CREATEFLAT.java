package org.alexdev.havana.messages.incoming.rooms.settings;

import org.alexdev.havana.dao.mysql.NavigatorDao;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.models.RoomModelManager;
import org.alexdev.havana.game.texts.TextsManager;
import org.alexdev.havana.game.wordfilter.WordfilterManager;
import org.alexdev.havana.messages.outgoing.rooms.settings.GOTO_FLAT;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.util.MalformedPacketException;
import org.alexdev.havana.util.StringUtil;

import java.sql.SQLException;

public class CREATEFLAT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws SQLException, MalformedPacketException {
        String roomName = WordfilterManager.filterSentence(StringUtil.filterInput(reader.readString(), true));
        String roomModel = reader.readString();
        String roomAccess = reader.remainingBytes().length > 0 ? reader.readString() : "";
        boolean roomShowName = reader.remainingBytes().length <= 0 || reader.readBoolean();

        if (roomName.replace(" ", "").isEmpty()) {
            player.send(new ALERT(TextsManager.getInstance().getValue("roomatic_givename")));
            return;
        }

        if (!roomModel.startsWith("model_")) {
            return;
        }

        if (RoomModelManager.getInstance().getModel(roomModel) == null) {
            return;
        }

        String modelType = roomModel.replace("model_", "");

        if (!modelType.equals("a") &&
                !modelType.equals("b") &&
                !modelType.equals("c") &&
                !modelType.equals("d") &&
                !modelType.equals("e") &&
                !modelType.equals("f") &&
                !modelType.equals("i") &&
                !modelType.equals("j") &&
                !modelType.equals("k") &&
                !modelType.equals("l") &&
                !modelType.equals("m") &&
                !modelType.equals("n") &&
                !player.hasFuse(Fuseright.USE_SPECIAL_ROOM_LAYOUTS)) {
            return; // Fuck off, scripter.
        }

        int accessType = 0;

        if (roomAccess.equals("password")) {
            accessType = 2;
        }

        if (roomAccess.equals("closed")) {
            accessType = 1;
        }

        int roomId = NavigatorDao.createRoom(player.getDetails().getId(), roomName, roomModel, roomShowName, accessType);

        player.getRoomUser().setAuthenticateId(roomId);
        player.send(new GOTO_FLAT(roomId, roomName));
    }
}
