package org.alexdev.havana.game.inventory;

import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.inventory.INVENTORY;
import org.alexdev.havana.server.netty.streams.NettyResponse;
import org.alexdev.havana.util.StringUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Inventory {
    private static final int MAX_ITEMS_PER_PAGE = 9;

    private int currentPage = 0;
    private Player player;

    private List<Item> items;
    private List<Item> displayedItems;
    private Map<Integer, List<Item>> paginatedItems;

    private int handStripPageIndex;

    public Inventory(Player player) {
        this.player = player;
        this.reload();
    }

    /**
     * Reload inventory.
     */
    public void reload() {
        this.handStripPageIndex = 0;
        this.items = ItemDao.getInventory(this.player.getDetails().getId());

        if (this.player.getRoomUser().getRoom() != null) {
            this.items.removeIf(x -> this.player.getRoomUser().getRoom().getItems().stream().anyMatch(item -> x.getDatabaseId() == item.getDatabaseId()));
        }

        this.refreshPagination();

        for (var item : this.items) {
            item.assignVirtualId();
        }
    }

    /**
     * Refreshes the pagination by making the most recently bought items appear first.
     */
    private void refreshPagination() {
        this.displayedItems = new CopyOnWriteArrayList<>();
        int orderId = 0;

        for (Item item : this.items) {
            // Don't show items if they are hidden
            if (!item.isVisible()) {
                continue;
            }

            // Don't show items if they are currently in trade window
            if (this.player.getRoomUser().getTradePartner() != null) {
                if (this.player.getRoomUser().getTradeItems().contains(item)) {
                    continue;
                }
            }

            if (orderId != item.getOrderId()) {
                item.setOrderId(orderId);
                item.save();
            }

            orderId++;
            this.displayedItems.add(item);
        }

        this.displayedItems.sort(Comparator.comparingInt(Item::getOrderId));
        this.paginatedItems = new ConcurrentHashMap<>(StringUtil.paginate(this.displayedItems, MAX_ITEMS_PER_PAGE));
    }

    /**
     * Get the view of the inventory.
     *
     * @param stripView the view type
     */
    public void getView(String stripView) {
        this.refreshPagination();
        //this.paginatedItems = StringUtil.paginate(this.items, MAX_ITEMS_PER_PAGE);
        this.changeView(stripView);

        Map<Integer, Item> casts = this.getCasts();
        this.player.send(new INVENTORY(this, casts));

    }

    /**
     * Get the inventory casts for opening hand.
     */
    private Map<Integer,Item> getCasts() {
        LinkedHashMap<Integer, Item> casts = new LinkedHashMap<>();

        if (this.player.getNetwork().isFlashConnection()) {
            int stripSlotId = 0;

            for (Item item : this.displayedItems) {
                addItemCast(casts, stripSlotId++, item);
            }
        }

        if (this.paginatedItems.containsKey(this.handStripPageIndex)) {
            int stripSlotId = this.handStripPageIndex * MAX_ITEMS_PER_PAGE;

            for (Item item : this.paginatedItems.get(this.handStripPageIndex)) {
                addItemCast(casts, stripSlotId++, item);
            }
        }

        return casts;
    }

    /**
     * Add item casts.
     */
    private void addItemCast(LinkedHashMap<Integer, Item> casts, int slotId, Item item) {
        /*if (this.player.getRoomUser().getRoom() != null) {
            var room = this.player.getRoomUser().getRoom();
            var roomItem = room.getItemManager().getByDatabaseId(item.getDatabaseId());

            // DO NOT add item into inventory if it's already in the room.
            if (roomItem != null) {
                System.out.println("Duplicate item.");
                return;
            }
        }*/

        casts.put(slotId, item);

    }

    /**
     * Change the inventory view over.
     *
     * @param stripView the strip view to change
     */
    private void changeView(String stripView) {
        if (stripView.equals("new")) {
            this.handStripPageIndex = 0;
        }

        if (stripView.equals("next")) {
            this.handStripPageIndex++;
        }

        if (stripView.equals("prev")) {
            this.handStripPageIndex--;
        }

        if (stripView.equals("last")) {
            this.handStripPageIndex = this.paginatedItems.size() - 1;
        }

        if (stripView.equals("current")) {
            if (this.handStripPageIndex > this.paginatedItems.size() - 1) {
                this.handStripPageIndex = this.paginatedItems.size() - 1;
            }
        }

        if (!this.paginatedItems.containsKey(this.handStripPageIndex)) {
            this.handStripPageIndex = 0;
        }
    }

    /**
     * Serialise item in hand.
     *
     * @param response the response to write the item to
     * @param item the item to use the data for the packet
     * @param stripSlotId the slot in the hand
     */
    public void serialise(NettyResponse response, Item item, int stripSlotId) {
        response.writeInt(item.getVirtualId());
        response.writeInt(stripSlotId);

        if (item.hasBehaviour(ItemBehaviour.WALL_ITEM)) {
            response.writeString("I");
        } else {
            response.writeString("S");
        }

        response.writeInt(item.getVirtualId());
        response.writeInt(item.getDefinition().getSpriteId());

        switch (item.getDefinition().getSprite()) {
            case "landscape":
                response.writeInt(4);
                break;
            case "wallpaper":
                response.writeInt(2);
                break;
            case "floor":
                response.writeInt(3);
                break;
            case "poster":
                response.writeInt(6);
                break;
            default:
                response.writeInt(1);
                break;
        }

        response.writeString((item.hasBehaviour(ItemBehaviour.PRESENT) || item.hasBehaviour(ItemBehaviour.ECO_BOX)) ? "" : item.getCustomData());
        response.writeBool(item.getDefinition().isRecyclable()); // recylecable
        response.writeBool(item.getDefinition().isTradable()); // tradeable
        response.writeBool(true); // groupable

        //response.writeBool(false); // Marketplace can sell
        response.writeInt(-1);

        if (!item.hasBehaviour(ItemBehaviour.WALL_ITEM)) {
            response.writeString("");
            response.writeInt(-1);
        }
    }

    /**
     * Get inventory item by id.
     *
     * @param itemId the id used to get the inventory item
     * @return the inventory item
     */
    public Item getItem(int itemId) {
        for (Item item : this.items) {
            if (item.getVirtualId() == itemId) {
                return item;
            }
        }

        return null;
    }

    /**
     * Get inventory item by id.
     *
     * @param itemId the id used to get the inventory item
     * @return the inventory item
     */
    public Item getItemByDatabaseId(long itemId) {
        for (Item item : this.items) {
            if (item.getDatabaseId() == itemId) {
                return item;
            }
        }

        return null;
    }

    /**
     * Get all soundset track IDs within the inventory.
     *
     * @return the list of soundsets
     */
    public List<Integer> getSoundsets() {
        List<Integer> handSoundsets = new ArrayList<>();

        for (Item item : player.getInventory().getItems()) {
            if (!item.isVisible()) {
                continue;
            }

            if (item.hasBehaviour(ItemBehaviour.SOUND_MACHINE_SAMPLE_SET)) {
                handSoundsets.add(Integer.parseInt(item.getDefinition().getSprite().split("_")[2]));
            }
        }

        return handSoundsets;
    }

    /**
     * Add the item to the start of items list.
     *
     * @param item the item
     */
    public void addItem(Item item) {
        this.items.remove(item);
        this.items.add(0, item);
    }

    /**
     * Get the list of inventory items.
     *
     * @return the list of items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Get the items that the user can see in their hand (non-hidden items).
     *
     * @return the list of displayed items
     */
    public List<Item> getDisplayedItems() {
        return displayedItems;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
