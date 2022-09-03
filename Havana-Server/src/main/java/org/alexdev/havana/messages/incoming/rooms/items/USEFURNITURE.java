package org.alexdev.havana.messages.incoming.rooms.items;

import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.mapping.RoomTile;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class USEFURNITURE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        int itemId = reader.readInt();
        int status = reader.readInt();

        Item item = room.getItemManager().getById(itemId);

        if (item == null ) {
            return;
        }

        if (item.hasBehaviour(ItemBehaviour.ROOMDIMMER)
                || item.hasBehaviour(ItemBehaviour.DICE)
                || item.hasBehaviour(ItemBehaviour.PRIZE_TROPHY)
                || item.hasBehaviour(ItemBehaviour.POST_IT)
                || item.hasBehaviour(ItemBehaviour.ROLLER)
                || item.hasBehaviour(ItemBehaviour.WHEEL_OF_FORTUNE)
                || item.hasBehaviour(ItemBehaviour.SOUND_MACHINE_SAMPLE_SET)) {
            return; // Prevent dice rigging, scripting trophies, post-its, etc.
        }

        if (item.hasBehaviour(ItemBehaviour.REQUIRES_RIGHTS_FOR_INTERACTION) && !room.hasRights(player.getDetails().getId()) && !player.hasFuse(Fuseright.MOD)) {
            return;
        }

        if (item.hasBehaviour(ItemBehaviour.REQUIRES_TOUCHING_FOR_INTERACTION)) {
            if (!item.getPosition().touches(player.getRoomUser().getPosition())) {
                Position nextPosition = item.getPosition().getSquareInFront();

                if (!RoomTile.isValidTile(room, player, nextPosition)) {
                    var tile = item.getTile();

                    if (tile != null) {
                        nextPosition = item.getTile().getNextAvailablePosition(player);
                    }
                }

                if (nextPosition != null) {
                    player.getRoomUser().walkTo(nextPosition.getX(), nextPosition.getY());
                }
                return;
            }
        }

        if (player.getRoomUser().hasItemDebug()) {
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
        }

        item.getDefinition().getInteractionType().getTrigger().onInteract(player, room, item, status);
    }
}
