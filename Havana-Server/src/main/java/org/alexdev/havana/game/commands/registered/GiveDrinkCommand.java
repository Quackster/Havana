package org.alexdev.havana.game.commands.registered;

import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.game.texts.TextsManager;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE.ChatMessageType;
import org.apache.commons.lang3.StringUtils;

public class GiveDrinkCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.NORMAL);
    }

    @Override
    public void addArguments() {
        this.arguments.add("user");
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Player targetUser = PlayerManager.getInstance().getPlayerByName(args[0]);

        if (targetUser == null ||
                targetUser.getRoomUser().getRoom() == null ||
                targetUser.getRoomUser().getRoom().getId() != player.getRoomUser().getRoom().getId()) {
            player.send(new ALERT("Could not find user: " + args[0]));
            return;
        }

        if (!player.getRoomUser().isCarrying() || player.getRoomUser().containsStatus(StatusType.CARRY_ITEM)) {
            player.send(new ALERT("You are not carrying any food or drinks to give."));
            return;
        }

        String drinkName = null;
        int drinkId = 0;

        /*if (player.getRoomUser().getCarryId() > 0) {
            drinkId = player.getRoomUser().getCarryId();
        }*/

        if (player.getRoomUser().containsStatus(StatusType.CARRY_DRINK)) {
            var value  = player.getRoomUser().getStatus(StatusType.CARRY_DRINK).getValue();

            if (StringUtils.isNumeric(value)) {
                drinkId = Integer.parseInt(value);
            } else {
                drinkName = value;
            }
        }

        if (drinkId > 0 || drinkName != null) {
            if (targetUser.getRoomUser().isSleeping()) {
                player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), targetUser.getDetails().getName() + " is sleeping.", 0));
                return;
            }

            if (targetUser.getRoomUser().isUsingEffect()) {
                player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), targetUser.getDetails().getName() + " can't hold a drink, they're using an effect.", 0));
                return;
            }

            // Give drink to user if they're not already having a drink or food, and they're not dancing
            if (targetUser.getRoomUser().isCarrying()) {
                player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), targetUser.getDetails().getName() + " is already enjoying a drink.", 0));
                return;
            }

            if (targetUser.getRoomUser().isDancing()) {
                player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "Can't hand drink to " + targetUser.getDetails().getName() + ", because he/she is dancing.", 0));
                return;
            }

            targetUser.getRoomUser().carryItem(drinkId, drinkName);
            String carryName = drinkName;

            if (drinkName == null) {
                carryName = TextsManager.getInstance().getValue("handitem" + drinkId);
            }

            targetUser.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, targetUser.getRoomUser().getInstanceId(), player.getDetails().getName() + " handed you a " + carryName + ".", 0));

            player.getRoomUser().stopCarrying();
            player.getRoomUser().setNeedsUpdate(true);
        }
    }

    @Override
    public String getDescription() {
        return "Gives a user your own drink";
    }
}
