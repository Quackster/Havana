package org.alexdev.havana.messages.incoming.infobus;

import org.alexdev.havana.dao.mysql.InfobusDao;
import org.alexdev.havana.game.infobus.InfobusManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.infobus.POLL_QUESTION;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class VOTE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null || !player.getRoomUser().getRoom().getModel().getName().equals("park_b")) {
            return;
        }

        if (InfobusManager.getInstance().getCurrentPoll() == null) {
            return;
        }

        int choice = reader.readInt();
        var currentPoll = InfobusManager.getInstance().getCurrentPoll();

        if (choice <= 0 || choice > currentPoll.getPollData().getAnswers().size()) {
            return;
        }

        if (InfobusDao.hasAnswer(currentPoll.getId(), player.getDetails().getId())) {
           return;
        }

        InfobusDao.addAnswer(currentPoll.getId(), choice - 1, player.getDetails().getId());

        if (InfobusManager.getInstance().canUpdateResults()) {
            InfobusManager.getInstance().showPollResults(InfobusManager.getInstance().getCurrentPoll().getId());
        }
    }
}
