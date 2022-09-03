package org.alexdev.havana.messages.incoming.rooms.user;

import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.pathfinder.Rotation;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class LOOKTO implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Item item = player.getRoomUser().getCurrentItem();

        // Don't allow LOOKTO to be handled on wobble squabble tiles.
        if (item != null) {
            if (item.getDefinition().getInteractionType() == InteractionType.WS_JOIN_QUEUE ||
                    item.getDefinition().getInteractionType() == InteractionType.WS_QUEUE_TILE ||
                    item.getDefinition().getInteractionType() == InteractionType.WS_TILE_START) {
                return; // Don't process :sit command on furniture that the user is already on.
            }
        }

        int x = 0;
        int y = 0;

        if (reader.contents().contains(" ")) {
            String[] contents = reader.contents().split(" ");
            x = Integer.parseInt(contents[0]);
            y = Integer.parseInt(contents[1]);
        } else {
            x = reader.readInt();
            y = reader.readInt();
        }

        Position lookDirection = new Position(x, y);

        if (player.getRoomUser().containsStatus(StatusType.LAY)) {
            return;
        }

        if (player.getRoomUser().getCurrentItem() != null) {
            if (player.getRoomUser().getCurrentItem().hasBehaviour(ItemBehaviour.NO_HEAD_TURN)) {
                return;
            }
        }

        if (player.getRoomUser().getPosition().equals(lookDirection)) {
            return;
        }

        int rotation = Rotation.calculateHumanDirection(
                player.getRoomUser().getPosition().getX(),
                player.getRoomUser().getPosition().getY(),
                lookDirection.getX(),
                lookDirection.getY());

        // When sitting calculate even rotation
        if (player.getRoomUser().containsStatus(StatusType.SIT)) {
            Position current = player.getRoomUser().getPosition();

            // If they're sitting on the ground also rotate body.
            //if (player.getRoomUser().isSittingOnGround()) {
            //    rotation = rotation / 2 * 2;
            //    player.getRoomUser().getPosition().setBodyRotation(rotation);
            //}

            // And now rotate their head for all sitting people.
            player.getRoomUser().getPosition().setHeadRotation(Rotation.getHeadRotation(current.getRotation(), current, lookDirection));

        } else {
            player.getRoomUser().getPosition().setRotation(rotation);
        }

        player.getRoomUser().setNeedsUpdate(true);

        player.getRoomUser().getTimerManager().beginLookTimer(); // 1 minute looking at them
        player.getRoomUser().getTimerManager().resetRoomTimer();
    }
}
