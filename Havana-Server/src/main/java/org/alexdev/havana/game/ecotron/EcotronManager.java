package org.alexdev.havana.game.ecotron;

import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.game.item.ItemManager;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.item.base.ItemDefinition;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class EcotronManager {
    private static EcotronManager instance;
    private List<EcotronItem> ecotronItems;

    private EcotronManager() {
        this.ecotronItems = ItemDao.getEcotronItems();
    }

    /**
     * Append the list of ecotron rewards.
     *
     * @param response the response to append to
     */
    public void appendRewards(NettyResponse response) {
        response.writeInt(5);

        for (int i = 5; i > 0; i--) {
            AtomicInteger chanceMax = new AtomicInteger(0);

            if (i == 5)
                chanceMax.set(2000);
            else if (i == 4) {
                chanceMax.set(200);
            } else if (i == 3) {
                chanceMax.set(40);
            } else if (i == 2) {
                chanceMax.set(5);
            }

            List<EcotronItem> itemList = this.ecotronItems.stream().filter(ecotronItem -> ecotronItem.getChance() == chanceMax.get()).collect(Collectors.toList());

            response.writeInt(i);
            response.writeInt(chanceMax.get());
            response.writeInt(itemList.size());

            for (EcotronItem ecotronItem : itemList) {
                ItemDefinition definition = ItemManager.getInstance().getDefinitionBySprite(ecotronItem.getSpriteName());

                if (definition == null) {
                    continue;
                }

                response.writeString(definition.hasBehaviour(ItemBehaviour.WALL_ITEM) ? "i" : "s");
                response.writeInt(definition.getSpriteId());
            }
        }
    }

    /**
     * Get the list of ecotron rewards by chance
     * @param chance the chance
     *
     * @return the list of ecotron rewards
     */
    public List<EcotronItem> getRewardsByChance(int chance) {
        return this.ecotronItems.stream().filter(ecotronItem -> ecotronItem.getChance() == chance).collect(Collectors.toList());
    }

   /**
     * Get instance of {@link EcotronManager}
     *
     * @return the manager instance
     */
    public static EcotronManager getInstance() {
        if (instance == null) {
            instance = new EcotronManager();
        }

        return instance;
    }
}
