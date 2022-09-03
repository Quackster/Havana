package org.alexdev.havana.messages.incoming.ecotron;

import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.game.catalogue.CatalogueManager;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.ItemManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.ecotron.RECYCLER_STATUS;
import org.alexdev.havana.messages.outgoing.rooms.items.ITEM_DELIVERED;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.config.GameConfiguration;

import java.util.ArrayList;
import java.util.List;

public class RECYCLE_ITEMS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int amount = reader.readInt();

        if (amount < 5) {
            return;
        }

        List<Item> itemsToDelete = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            Item item = player.getInventory().getItem(reader.readInt());

            if (item == null || !item.getDefinition().isRecyclable()) {
                continue;
            }

            if (itemsToDelete.stream().anyMatch(listItem -> listItem.getDatabaseId() == item.getDatabaseId())) {
                continue;
            }

            itemsToDelete.add(item);
        }

        if (itemsToDelete.size() != 5) {
            return;
        }

        /*int chance = 0;
        int[] chances = {2000, 200, 40, 5};

        for (int chanceCheck : chances) {
            int randomID = ThreadLocalRandom.current().nextInt(1, chanceCheck + 1);

            if (randomID == chanceCheck) {
                chance = chanceCheck;
                break;
            }
        }

        List<EcotronItem> potentialPrizes = EcotronManager.getInstance().getRewardsByChance(chance);

        if (potentialPrizes.isEmpty()) {
            return; // OOPS! Something happened
        }

        EcotronItem ecotronItem = potentialPrizes.get(ThreadLocalRandom.current().nextInt(0, potentialPrizes.size()));

        if (ecotronItem == null) {
            return;
        }

        ItemDefinition prize = ItemManager.getInstance().getDefinitionBySprite(ecotronItem.getSpriteName());

        if (prize == null) {
            return;
        }

        CatalogueItem catalogueItem = CatalogueManager.getInstance().getCatalogueBySprite(prize.getSpriteId());

        if (catalogueItem == null) {
            return;
        }*/


        Item present = new Item();
        present.setDefinitionId(ItemManager.getInstance().getDefinitionBySprite("ecotron_box").getId());
        present.setOwnerId(player.getDetails().getId());

        if (itemsToDelete.stream().anyMatch(x ->
                x.getDefinition().getSprite().startsWith("chair_plasty") ||
                x.getDefinition().getSprite().startsWith("chair_plasto") ||
                x.getDefinition().getSprite().startsWith("table_plasto"))) {
            final int percentage = 100;

            var chance = Math.random() * percentage;
            var rainbowChance = 1.0;

            if ((percentage - rainbowChance) < chance) {
                present.setCustomData("FLAG:RAINBOW_FURNI");
            }
        }

        ItemDao.newItem(present);

        //Item present = ItemManager.getInstance().createGift(player.getDetails().getId(), player.getDetails().getName(), catalogueItem.getSaleCode(), "", "", true);//new Item();

        for (Item item : itemsToDelete) {
            player.getInventory().getItems().remove(item);
            item.delete();
        }

        player.getInventory().addItem(present);
        player.getInventory().getView("last");

        player.send(new ITEM_DELIVERED());
        player.send(new RECYCLER_STATUS(1));
    }
}
