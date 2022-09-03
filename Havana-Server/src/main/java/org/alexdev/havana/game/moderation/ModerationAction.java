package org.alexdev.havana.game.moderation;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.util.MalformedPacketException;

public interface ModerationAction {
    void performAction(Player player, Room room, String alertMessage, String notes, NettyRequest reader) throws MalformedPacketException;
}
