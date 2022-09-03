package org.alexdev.havana.messages.incoming.rooms.items;

import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.game.catalogue.CatalogueItem;
import org.alexdev.havana.game.catalogue.CatalogueManager;
import org.alexdev.havana.game.ecotron.EcotronItem;
import org.alexdev.havana.game.ecotron.EcotronManager;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.ItemManager;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.item.base.ItemDefinition;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.catalogue.DELIVER_PRESENT;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.DateUtil;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

public class PRESENTOPEN implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.isOwner(player.getDetails().getId()) && !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            return;
        }

        int itemId = reader.readInt();//Integer.parseInt(reader.contents());
        Item item = room.getItemManager().getById(itemId);

        if (item == null) {
            return;
        }

        if (item.hasBehaviour(ItemBehaviour.ECO_BOX) && item.getCustomData().startsWith("FLAG:")) {
            String[] possibleFurni = null;

            switch (item.getCustomData().replace("FLAG:", "")) {
                case "RAINBOW_FURNI":
                {
                    possibleFurni = new String[] {
                            "table_plasto_sq_lm1",
                            "table_plasto_round_lm1",
                            "table_plasto_bigsq_lm1",
                            "table_plasto_4leg_lm1",
                            "chair_plasty_lm1",
                            "chair_plasto_lm1",

                    };

                    break;
                }
            }

            if (possibleFurni == null)
                return;

            String saleCode = possibleFurni[ThreadLocalRandom.current().nextInt(possibleFurni.length)];
            CatalogueItem catalogueItem = CatalogueManager.getInstance().getCatalogueItem(saleCode);

            if (catalogueItem != null) {
                room.getMapping().removeItem(player, item);

                item.setDefinitionId(catalogueItem.getDefinition().getId());
                ItemDao.updateItem(item);

                player.send(new DELIVER_PRESENT(item));

                player.getInventory().addItem(item);
                player.getInventory().getView("new");

                return;
            }
        }

        if (item.hasBehaviour(ItemBehaviour.PRESENT)) {
            String[] presentData = item.getCustomData().split(Pattern.quote(Item.PRESENT_DELIMETER));

            int saleCode = Integer.parseInt(presentData[0]);
            String receivedFrom = presentData[1];
            String extraData = presentData[3];
            long timestamp = DateUtil.getCurrentTimeSeconds();

            try {
                timestamp = Long.parseLong(presentData[4]);
            } catch (Exception ex) {

            }
            //System.out.println("Present data: " + String.join(",", presentData));
            //System.out.println("Custom data: " + item.getCustomData());
            //System.out.println(receivedFrom);

            CatalogueItem catalogueItem = CatalogueManager.getInstance().getCatalogueItem(saleCode);

            if (catalogueItem == null) {
                room.getMapping().removeItem(player, item);
                item.delete();
                return;
            }

            // Don't create a new item instance, reuse if the item isn't a trophy or teleporter, etc
            if (!catalogueItem.isPackage() && !catalogueItem.getDefinition().hasBehaviour(ItemBehaviour.PRIZE_TROPHY) &&
                    !catalogueItem.getDefinition().hasBehaviour(ItemBehaviour.TELEPORTER) &&
                    (catalogueItem.getDefinition().getInteractionType() != InteractionType.PET_NEST) &&
                    !catalogueItem.getDefinition().hasBehaviour(ItemBehaviour.ROOMDIMMER) &&
                    !catalogueItem.getDefinition().hasBehaviour(ItemBehaviour.DECORATION) &&
                    !catalogueItem.getDefinition().hasBehaviour(ItemBehaviour.POST_IT) &&
                    !catalogueItem.getDefinition().getSprite().equalsIgnoreCase("film")) {
                room.getMapping().removeItem(player, item);

                item.setDefinitionId(catalogueItem.getDefinition().getId());
                item.setCustomData(extraData);

                ItemDao.updateItem(item);

                player.send(new DELIVER_PRESENT(item));

                player.getInventory().addItem(item);
                player.getInventory().getView("new");
            } else {
                List<Item> itemList = CatalogueManager.getInstance().purchase(player.getDetails(), catalogueItem, extraData, receivedFrom, timestamp);

                if (!itemList.isEmpty()) {
                    player.send(new DELIVER_PRESENT(itemList.get(0)));
                    player.getInventory().getView("new");
                }

                room.getMapping().removeItem(player, item);
                item.delete();
            }
        }

        if (item.hasBehaviour(ItemBehaviour.ECO_BOX)) {
            int chance = 0;
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
            }

            room.getMapping().removeItem(player, item);

            item.setDefinitionId(catalogueItem.getDefinition().getId());
            item.setCustomData("");

            ItemDao.updateItem(item);
            player.send(new DELIVER_PRESENT(item));

            player.getInventory().addItem(item);
            player.getInventory().getView("new");

        }
    }
}