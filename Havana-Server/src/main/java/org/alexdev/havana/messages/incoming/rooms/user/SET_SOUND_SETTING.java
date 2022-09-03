package org.alexdev.havana.messages.incoming.rooms.user;

import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class SET_SOUND_SETTING implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        boolean enabled = reader.readBoolean();
        player.getDetails().setSoundSetting(enabled);

        PlayerDao.saveSoundSetting(player.getDetails().getId(), player.getDetails().getSoundSetting());
    }
}
