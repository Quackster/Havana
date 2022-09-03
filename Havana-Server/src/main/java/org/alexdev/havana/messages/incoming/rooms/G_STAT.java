package org.alexdev.havana.messages.incoming.rooms;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.games.Game;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.outgoing.effects.USER_AVATAR_EFFECT;
import org.alexdev.havana.messages.outgoing.games.GAMESTART;
import org.alexdev.havana.messages.outgoing.rooms.groups.GROUP_BADGES;
import org.alexdev.havana.messages.outgoing.rooms.groups.GROUP_MEMBERSHIP_UPDATE;
import org.alexdev.havana.messages.outgoing.rooms.items.DICE_VALUE;
import org.alexdev.havana.messages.outgoing.rooms.items.SHOWPROGRAM;
import org.alexdev.havana.messages.outgoing.rooms.items.STUFFDATAUPDATE;
import org.alexdev.havana.messages.outgoing.rooms.user.*;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class G_STAT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        if (player.getRoomUser().getGamePlayer() != null && player.getRoomUser().getGamePlayer().isSpectator()) {
            player.send(new YOUARESPECTATOR());

            Game game = player.getRoomUser().getGamePlayer().getGame();

            if (game.isGameStarted()) {
                player.send(new GAMESTART(game.getTotalSecondsLeft().get()));
            }
            return;
        }

        if (player.getRoomUser().getGamePlayer() != null && player.getRoomUser().getGamePlayer().isInGame()) {
            return; // Not needed for game arenas
        }

        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        room.getEntityManager().tryRoomEntry(player);

        /*if (!(room.getEntityManager().getPlayers().size() > 1)) {
            room.getEntityManager().tryInitialiseRoom();

            boolean isCancelled = PluginManager.getInstance().callEvent(PluginEvent.ROOM_FIRST_ENTRY_EVENT, new LuaValue[]{
                    CoerceJavaToLua.coerce(player),
                    CoerceJavaToLua.coerce(room)
            });

            if (isCancelled) {
                room.getEntityManager().leaveRoom(player, true);
            }
        }*/

        player.send(new USER_OBJECTS(room.getEntities()));
        room.send(new USER_OBJECTS(player), List.of(player));
        player.send(new USER_STATUSES(room.getEntities()));

        if (player.getRoomUser().isUsingEffect()) {
            room.send(new USER_AVATAR_EFFECT(player.getRoomUser().getInstanceId(), player.getRoomUser().getEffectId()));
        }

        for (Entity roomEntity : room.getEntities()) {
            if (roomEntity.getDetails().getFavouriteGroupId() > 0 && roomEntity.getDetails().getId() != player.getDetails().getId()) {
                var groupMember = roomEntity.getDetails().getGroupMember();
                player.send(new GROUP_MEMBERSHIP_UPDATE(roomEntity.getRoomUser().getInstanceId(), groupMember.getGroupId(), groupMember.getMemberRank().getClientRank()));
            }

            if (roomEntity.getRoomUser().isUsingEffect()) {
                player.send(new USER_AVATAR_EFFECT(roomEntity.getRoomUser().getInstanceId(), roomEntity.getRoomUser().getEffectId()));
            }

            if (roomEntity.getRoomUser().isDancing()) {
                player.send(new USER_DANCE(roomEntity.getRoomUser().getInstanceId(), roomEntity.getRoomUser().getDanceId()));
            }

            if (roomEntity.getRoomUser().isSleeping()) {
                player.send(new USER_SLEEP(roomEntity.getRoomUser().getInstanceId(), roomEntity.getRoomUser().isSleeping()));
            }
        }

        for (Item item : room.getItems()) {
            if (item.getCurrentProgramValue().length() > 0) {
                player.send(new SHOWPROGRAM(new String[] { item.getCurrentProgram(), item.getCurrentProgramValue() }));
            }

            if (item.hasBehaviour(ItemBehaviour.INVISIBLE)) {
                continue;
            }

            // If item is requiring an update, apply animations etc
            if (item.getRequiresUpdate()) {
                // For some reason the wheel of fortune doesn't spin when the custom data on initial road equals -1, thus we send it again
                if (item.hasBehaviour(ItemBehaviour.WHEEL_OF_FORTUNE)) {
                    player.send(new STUFFDATAUPDATE(item));
                }

                // Dices use a separate packet for rolling animation
                if (item.hasBehaviour(ItemBehaviour.DICE)) {
                    player.send(new DICE_VALUE(item.getVirtualId(), true, 0));
                }
            }
        }

        if (player.getDetails().getFavouriteGroupId() > 0) {
            var groupMember = player.getDetails().getGroupMember();

            room.send(new GROUP_BADGES(new HashMap<>() {{
                put(groupMember.getGroupId(), player.getJoinedGroup(player.getDetails().getFavouriteGroupId()).getBadge());
            }}));

            room.send(new GROUP_MEMBERSHIP_UPDATE(player.getRoomUser().getInstanceId(), groupMember.getGroupId(), groupMember.getMemberRank().getClientRank()));
        }

        if (RoomManager.getInstance().getRoomEntryBadges().containsKey(room.getId())) {
            for (String badge : RoomManager.getInstance().getRoomEntryBadges().get(room.getId())) {
                player.getBadgeManager().tryAddBadge(badge, null);
            }
        }

        List<Item> tempItems = new ArrayList<Item>(player.getInventory().getItems());
        boolean updateInventory = false;

        for (Item item : tempItems) {
           for (Item roomItem : room.getItems()) {
               if (roomItem.getDatabaseId() == item.getDatabaseId()) {
                   player.getInventory().getItems().removeIf(y -> y.getDatabaseId() == item.getDatabaseId());
                   updateInventory = true;
               }
           }
        }


        if (updateInventory) {
            player.getInventory().getView("new");
        }

        /*player.send(new MessageComposer() {
            @Override
            public void compose(NettyResponse response) {
                response.writeInt(1);
                response.writeString("We're currently collecting data through an online poll on how you found the server. Would you help answer our poll?");
            }

            @Override
            public short getHeader() {
                return 316; // #handle_poll_offer
            }
        });*/
    }
}
