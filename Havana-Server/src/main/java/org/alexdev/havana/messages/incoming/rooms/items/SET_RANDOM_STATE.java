package org.alexdev.havana.messages.incoming.rooms.items;

import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.mapping.RoomTile;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class SET_RANDOM_STATE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        int itemId = reader.readInt();
        Item item = room.getItemManager().getById(itemId);

        if (item == null) {
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

        if (item.hasBehaviour(ItemBehaviour.REQUIRES_RIGHTS_FOR_INTERACTION)
                && !room.hasRights(player.getDetails().getId())
                && !player.hasFuse(Fuseright.MOD)) {
            return;
        }

        if (item.hasBehaviour(ItemBehaviour.REQUIRES_TOUCHING_FOR_INTERACTION)) {
            if (!item.getPosition().touches(player.getRoomUser().getPosition())) {
                Position nextPosition = item.getPosition().getSquareInFront();

                if (!item.hasBehaviour(ItemBehaviour.TELEPORTER)) {
                    if (!RoomTile.isValidTile(room, player, nextPosition)) {
                        nextPosition = item.getTile().getNextAvailablePosition(player);
                    }

                    if (nextPosition != null) {
                        player.getRoomUser().walkTo(
                                nextPosition.getX(),
                                nextPosition.getY()
                        );
                    }

                    return;
                }
            }
        }

        item.getDefinition().getInteractionType().getTrigger().onInteract(player, room, item, -1);
    }
}
