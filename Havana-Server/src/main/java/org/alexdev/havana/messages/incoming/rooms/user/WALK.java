package org.alexdev.havana.messages.incoming.rooms.user;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.mapping.RoomTile;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.util.MalformedPacketException;

public class WALK implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (!player.getRoomUser().isWalkingAllowed()) {
            return;
        }

        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        int goalX = reader.readInt();
        int goalY = reader.readInt();

        if (player.getRoomUser().getRoom().getModel().getName().equals("md_a")) {
            if (reader.contents().equals("@U@S")|| reader.contents().equals("@U@G")) {
                if (player.getRoomUser().getCurrentItem() != null && player.getRoomUser().getCurrentItem().getDefinition().getSprite().equals("wsQueueTile")) {
                    return;
                }
            }
            if (reader.contents().equals("@U@S")) { // If using older CCT
                player.getRoomUser().walkTo(21, 19);
            }

            if (reader.contents().equals("@U@G")) { // If using older CCT
                player.getRoomUser().walkTo(21, 7);
            }
        }


        if (player.getRoomUser().getRoom().getModel().getName().equals("park_a")) {
            if (goalX == 28 && goalY == 4) {
                return;
            }
        }

        RoomTile roomTile = player.getRoomUser().getRoom().getMapping().getTile(goalX, goalY);

        /*if (roomTile != null && roomTile.getHighestItem() != null) {
            var highestItem = roomTile.getHighestItem();

            if (highestItem.getLastPlacedTime() > 0) {
                if (System.currentTimeMillis() - highestItem.getLastPlacedTime() < 50) {
                    return; // Woah there buddy, that's wayyy too quick to request an item being placed!
                }
            }
        }*/

        if (player.getRoomUser().hasItemDebug()) {
            if (roomTile != null && roomTile.getHighestItem() != null) {
                var item = roomTile.getHighestItem();
                StringBuilder alert = new StringBuilder();
                alert.append("Name: " + item.getDefinition().getName());
                alert.append("<br>");
                alert.append("Description: " + item.getDefinition().getDescription());
                alert.append("<br>");
                alert.append("Definition ID: " + item.getDefinition().getId());
                alert.append("<br>");
                alert.append("Sprite ID: " + item.getDefinition().getSpriteId());
                alert.append("<br>");
                alert.append("Sprite Name: " + item.getDefinition().getSprite());
                alert.append("<br>");
                alert.append("Top Height: " + item.getDefinition().getTopHeight());
                alert.append("<br>");
                alert.append("Total Height: " + item.getTotalHeight());
                alert.append("<br>");
                alert.append("Z Height: " + item.getPosition().getZ());
                alert.append("<br>");
                alert.append("X Y: " + item.getPosition().toString());
                player.send(new ALERT(alert.toString()));
                return;
            }
        }

        player.getRoomUser().walkTo(goalX, goalY);
    }
}
