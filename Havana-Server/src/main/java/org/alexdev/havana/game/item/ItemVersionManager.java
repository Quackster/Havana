package org.alexdev.havana.game.item;

import org.alexdev.havana.dao.mysql.ItemDao;

import java.util.Map;

public class ItemVersionManager {
    private static ItemVersionManager instance;
    private Map<String, Integer> itemVersions;

    public ItemVersionManager() {
        itemVersions = ItemDao.getItemVersions();
    }

    /**
     * Get the {@link ItemVersionManager} instance
     *
     * @return the item manager instance
     */
    public static ItemVersionManager getInstance() {
        if (instance == null) {
            instance = new ItemVersionManager();
        }

        return instance;
    }

    /**
     * Resets the item manager singleton.
     */
    public static void reset() {
        instance = null;
        ItemVersionManager.getInstance();
    }

    /**
     * Get item versions.
     *
     * @return the item versions
     */
    public Map<String, Integer> getItemVersions() {
        return itemVersions;
    }
}
