package org.alexdev.havana.game.commands.registered.moderation;

import org.alexdev.havana.game.catalogue.CatalogueItem;
import org.alexdev.havana.game.catalogue.CatalogueManager;
import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.item.ItemManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE;

import java.util.List;

public class GiftRoomCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.ADMINISTRATOR);
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

        CatalogueItem catalogueItem = CatalogueManager.getInstance().getCatalogueItem(args[0]);

        if (catalogueItem == null) {
            player.getRoomUser().talk("Catalogue item not found: " + args[0], CHAT_MESSAGE.ChatMessageType.WHISPER, List.of(player));
            return;
        }

        for (Player user : player.getRoomUser().getRoom().getEntityManager().getPlayers()) {
            if (user.getDetails().getRank().getRankId() >= PlayerRank.COMMUNITY_MANAGER.getRankId()) {
                continue;
            }

            var item = ItemManager.getInstance().createGift(user.getDetails().getId(), player.getDetails().getName(), catalogueItem.getSaleCode(), "You have just received a gift from Classic Habbo", "");

            user.getInventory().addItem(item);
            user.getInventory().getView("new");
        }

        player.getRoomUser().talk("Everybody (except community managers or higher) has been gifted a " + catalogueItem.getDefinition().getName(), CHAT_MESSAGE.ChatMessageType.WHISPER, List.of(player));
    }

    @Override
    public String getDescription() {
        return "Sends a gift from catalogue to users in the room";
    }
}
