package org.alexdev.havana.game.item;

import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.dao.mysql.SongMachineDao;
import org.alexdev.havana.game.GameScheduler;
import org.alexdev.havana.game.catalogue.CatalogueManager;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.item.base.ItemDefinition;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.game.song.Song;
import org.alexdev.havana.log.Log;
import org.alexdev.havana.util.DateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class ItemManager {
    private static ItemManager instance;
    private static AtomicInteger virtualIdCounter = new AtomicInteger(0);

    private Map<Integer, ItemDefinition> itemDefinitionMap;

    public ItemManager() {
        this.itemDefinitionMap = ItemDao.getItemDefinitions();
    }

    /**
     * Get a item definition by the definition id.
     *
     * @param definitionId the definition id to get for
     * @return the item definition
     */
    public ItemDefinition getDefinition(int definitionId) {
        if (this.itemDefinitionMap.containsKey(definitionId)) {
            return this.itemDefinitionMap.get(definitionId);
        }

        return null;
    }

    /**
     * Quick and easy method for creating gifts.
     *
     * @param saleCode the sprite to give
     * @return the item as gift
     */
    public Item createGift(int ownerId, String receivedFrom, String saleCode, String presentLabel, String extraData) {
        int presentId = ThreadLocalRandom.current().nextInt(0, 7);
        String sprite = "present_gen";

        if (presentId > 0) {
            sprite += presentId;
        }

        ItemDefinition itemDef = ItemManager.getInstance().getDefinitionBySprite(sprite);

        Item item = new Item();
        item.setDefinitionId(itemDef.getId());
        item.setOwnerId(ownerId);
        item.setCustomData(CatalogueManager.getInstance().getCatalogueItem(saleCode).getId() +
                Item.PRESENT_DELIMETER + receivedFrom +
                Item.PRESENT_DELIMETER + presentLabel.replace(Item.PRESENT_DELIMETER, "") + //From Habbo" +
                Item.PRESENT_DELIMETER + extraData.replace(Item.PRESENT_DELIMETER, "") +
                Item.PRESENT_DELIMETER + DateUtil.getCurrentTimeSeconds());

        try {
            ItemDao.newItem(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return item;
    }

    /**
     * Get the saved jukebox tracks.
     *
     * @param itemId the jukebox item id
     * @return the list of saved tracks
     */
    public List<Song> getJukeboxTracks(long itemId) {
        List<Song> savedTracks = new ArrayList<>();

        for (var kvp : SongMachineDao.getTracks(itemId).entrySet()) {
            int slotId = kvp.getKey();
            int songId = kvp.getValue();

            Song song = SongMachineDao.getSong(songId);
            song.setSlotId(slotId);

            savedTracks.add(song);
        }

        return savedTracks;
    }

    /**
     * Checks for any expired rentals and removes them.
     */
    public void checkExpiredRentals() {
        for (Item rental : ItemDao.getExpiredItems()) {
            Item item = this.resolveItem(rental);

            if (item != null) {
                // Item is currently loaded in room, remove it!
                if (item.getRoom() != null) {
                    item.getRoom().getMapping().pickupItem(PlayerManager.getInstance().getPlayerById(item.getOwnerId()), item);
                } else {
                    // Item is in players' hands, remove it!
                    Player itemOwner = PlayerManager.getInstance().getPlayerById(item.getOwnerId());

                    if (itemOwner != null) {
                        itemOwner.getInventory().getItems().removeIf(i -> i.getDatabaseId() == item.getDatabaseId());
                    }
                }
            }

            GameScheduler.getInstance().queueDeleteItem(rental.getDatabaseId());
        }
    }

    /**
     * Get a item definition by sprite name.
     *
     * @param spriteName the name of the sprite to locate the definition
     * @return the item definition
     */
    public ItemDefinition getDefinitionBySprite(String spriteName) {
        for (ItemDefinition definition : this.itemDefinitionMap.values()) {
            if (definition.getSprite().equals(spriteName)) {
                return definition;
            }
        }

        return null;
    }

    /**
     * Handle bulk item saving.
     *
     * @param itemSavingQueue the queue that's used for saving items
     */
    public void performItemSaving(BlockingQueue<Item> itemSavingQueue) {
        try {
            if (itemSavingQueue.isEmpty()) {
                return;
            }

            List<Item> itemList = new ArrayList<>();
            itemSavingQueue.drainTo(itemList);

            if (itemList.size() > 0) {
                ItemDao.updateItems(itemList);
            }
        } catch (Exception ex) {
            Log.getErrorLogger().error("Error when attempting to save items: ", ex);
        }
    }

    /**
     * Handle bulk item deletion.
     *
     * @param itemDeletionQueue the queue that's used for deleting items
     */
    public void performItemDeletion(BlockingQueue<Long> itemDeletionQueue) {
        try {
            if (itemDeletionQueue.isEmpty()) {
                return;
            }

            List<Long> itemList = new ArrayList<>();
            itemDeletionQueue.drainTo(itemList);

            if (itemList.size() > 0) {
                ItemDao.deleteItems(itemList);
            }
        } catch (Exception ex) {
            Log.getErrorLogger().error("Error when attempting to save items: ", ex);
        }
    }

    /**
     * Get the {@link ItemManager} instance
     *
     * @return the item manager instance
     */
    public static ItemManager getInstance() {
        if (instance == null) {
            instance = new ItemManager();
        }

        return instance;
    }

    /**
     * Resets the item manager singleton.
     */
    public static void reset() {
        instance = null;
        ItemManager.getInstance();
    }

    /**
     * Get the virtual ID counter for handling more than the 32-bit integer limit of items
     *
     * @return the item counter
     */
    public AtomicInteger getVirtualIdCounter() {
        return virtualIdCounter;
    }

    /**
     * Resolve the active room item by database id.
     *
     * @param itemId the room item
     * @return the instance of the item, if found
     */
    public Item resolveItem(long itemId) {
        Item databaseItem = ItemDao.getItem(itemId);

        if (databaseItem == null) {
            return null;
        }

        return this.resolveItem(databaseItem);
    }

    /**
     * Resolve the active room item by database instance
     *
     * @param databaseItem the database item instance
     * @return the instance of the item, if found
     */
    public Item resolveItem(Item databaseItem) {
        if (RoomManager.getInstance().getRoomById(databaseItem.getRoomId()) != null) {
            Room room = databaseItem.getRoom();

            if (room != null) {
                return room.getItemManager().getByDatabaseId(databaseItem.getDatabaseId());
            }
        } else {
            Player itemOwner = PlayerManager.getInstance().getPlayerById(databaseItem.getOwnerId());

            if (itemOwner != null) {
                return itemOwner.getInventory().getItemByDatabaseId(databaseItem.getDatabaseId());
            }
        }

        return null;
    }

    /**
     * Get if the present inside has the same behaviour.
     *
     * @param item      the present
     * @param behaviour the behaviour to check
     * @return true, if successful
     */
    public boolean hasPresentBehaviour(Item item, ItemBehaviour behaviour) {
        if (item.getDefinition().hasBehaviour(ItemBehaviour.PRESENT)) {
            String[] presentData = item.getCustomData().split(Pattern.quote(Item.PRESENT_DELIMETER));
            int catalogueId = Integer.parseInt(presentData[0]);

            var catalogueItem = CatalogueManager.getInstance().getCatalogueItem(catalogueId);

            if (catalogueItem != null && catalogueItem.getDefinition() != null) {
                return catalogueItem.getDefinition().hasBehaviour(behaviour);
            }
        }

        return false;
    }

    /**
     * Calculate song length of trax data.
     *
     * @param song the trax data
     * @return the length in seconds
     */
    public int calculateSongLength(String song) {
        try {
            String songData = song.substring(0, song.length() - 1);
            songData = songData.replace(":4:", "|");
            songData = songData.replace(":3:", "|");
            songData = songData.replace(":2:", "|");
            songData = songData.replace("1:", "");

            String[] data = songData.split(Pattern.quote("|"));
            String[] tracks = new String[]{data[0], data[1], data[2], data[3]};

            int songLength = 0;

            for (String track : tracks) {
                String[] samples = track.split(Pattern.quote(";"));
                int trackLength = 0;

                for (String sample : samples) {
                    int sampleSeconds = Integer.parseInt(sample.split(Pattern.quote(","))[1]) * 2;
                    trackLength += sampleSeconds;
                }

                if (trackLength > songLength)
                    songLength = trackLength;
            }

            return songLength;
        } catch (Exception e) {
            return 0;
        }
    }
}
