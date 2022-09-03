package org.alexdev.havana.game.games.snowstorm.messages;

import org.alexdev.havana.game.games.player.GamePlayer;
import org.alexdev.havana.game.games.snowstorm.SnowStormGame;
import org.alexdev.havana.game.games.snowstorm.messages.incoming.SnowStormAttackPlayerMessage;
import org.alexdev.havana.game.games.snowstorm.util.SnowStormEvent;
import org.alexdev.havana.game.games.snowstorm.util.SnowStormMessage;
import org.alexdev.havana.game.games.snowstorm.messages.incoming.SnowStormThrowLocationMessage;
import org.alexdev.havana.game.games.snowstorm.messages.incoming.SnowStormWalkMessage;
import org.alexdev.havana.game.games.snowstorm.messages.incoming.SnowstormCreateSnowballMessage;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.util.MalformedPacketException;

import java.util.HashMap;

public class SnowStormMessageHandler {
    private static SnowStormMessageHandler instance;
    private final HashMap<SnowStormEvent, SnowStormMessage> events;

    public SnowStormMessageHandler() {
        this.events = new HashMap<>();
        this.events.put(SnowStormEvent.WALK, new SnowStormWalkMessage());
        this.events.put(SnowStormEvent.CREATE_SNOWBALL, new SnowstormCreateSnowballMessage());
        this.events.put(SnowStormEvent.THROW_SNOWBALL_AT_LOCATION, new SnowStormThrowLocationMessage());
        this.events.put(SnowStormEvent.THROW_SNOWBALL_AT_PERSON, new SnowStormAttackPlayerMessage());
    }

    public void handleMessage(int messageId, NettyRequest request, SnowStormGame snowStormGame, GamePlayer player) throws MalformedPacketException {
        var event = SnowStormEvent.getEvent(messageId);
        
        if (event != null) {
            this.events.get(event).handle(request, snowStormGame, player);
        }
    }

    public static SnowStormMessageHandler getInstance() {
        if (instance == null) {
            instance = new SnowStormMessageHandler();
        }

        return instance;
    }
}
