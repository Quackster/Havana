package org.alexdev.havana.messages.incoming.rooms.items;

import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.rooms.items.MOVE_FLOORITEM;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class MOVESTUFF implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.hasRights(player.getDetails().getId()) && !player.hasFuse(Fuseright.MOD)) {
            return;
        }

        //String content = reader.contents();
        //String[] data = content.split(" ");

        int itemId = reader.readInt();//Integer.parseInt(data[0]);
        Item item = room.getItemManager().getById(itemId);

        if (item == null) {
            return;
        }

        if (item.hasBehaviour(ItemBehaviour.WALL_ITEM)) {
            return;
        }

        if (item.getRequiresUpdate()) {
            return;
        }

        int x = reader.readInt();//(int) Double.parseDouble(data[1]);
        int y = reader.readInt();//(int) Double.parseDouble(data[2]);
        int rotation = reader.readInt();//(int) Double.parseDouble(data[3]);

        Position oldPosition = item.getPosition().copy();

        boolean isRotation = false;

        if (item.getPosition().equals(new Position(x, y)) && item.getPosition().getRotation() != rotation) {
            isRotation = true;
        }

        if (isRotation) {
            if (item.getRollingData() != null) {
                return; // Don't allow rotating when rolling.
            }
        }

        if ((oldPosition.getX() == x &&
                oldPosition.getY() == y &&
                oldPosition.getRotation() == rotation) || !item.isValidMove(item, room, x, y, rotation)) {
            // Send item update even though we cancelled, otherwise the client will be confused.
            player.send(new MOVE_FLOORITEM(item));
            return;
        }


        // Validate placed item rotation
        if (!item.getDefinition().hasBehaviour(ItemBehaviour.WALL_ITEM)) {
            if (!item.getDefinition().getAllowedRotations().contains(rotation)) {
                return;
            }
        }

        room.getMapping().moveItem(player, item, new Position(x, y, item.getPosition().getZ(), rotation, rotation), oldPosition);
        player.getRoomUser().getTimerManager().resetRoomTimer();
    }
}
