package org.alexdev.havana.game.room.managers;

import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.mapping.RoomMapping;
import org.alexdev.havana.game.texts.TextsManager;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.outgoing.rooms.items.CANNOT_PLACE_STUFF_FROM_STRIP;

import java.util.ArrayList;
import java.util.List;

public class RoomItemManager {
    private Room room;

    private Item soundMachine;
    private Item moodlight;
    private Item idolScoreboard;

    public RoomItemManager(Room room) {
        this.room = room;
    }

    /**
     * Silently reset item extra data states in a room.
     * Will not function if the room is a battleball/snowstorm arena.
     */
    public void resetItemStates() {
        if (this.room.isGameArena() || this.room.getData().isCustomRoom()) {
            return;
        }

        List<Item> itemsToSave = new ArrayList<>();

        for (Item item : this.room.getItems()) {
            if (RoomMapping.resetExtraData(item, true)) {
                itemsToSave.add(item);
            }
        }

        ItemDao.updateItems(itemsToSave);
    }

    /**
     * Get if the room has too many of a certain item.
     *
     * @param player the player placing the item
     * @param item the item being placed
     * @return true, if successful
     */
    public boolean hasTooMany(Player player, Item item) {
        if (this.room.getItemManager().getSoundMachine() != null && (item.hasBehaviour(ItemBehaviour.SOUND_MACHINE) || item.hasBehaviour(ItemBehaviour.JUKEBOX))) {
            player.send(new CANNOT_PLACE_STUFF_FROM_STRIP(23));
            return true;
        }

        if (this.room.getItemManager().getMoodlight() != null && (item.hasBehaviour(ItemBehaviour.ROOMDIMMER))) {
            player.send(new ALERT(TextsManager.getInstance().getValue("roomdimmer_furni_limit")));
            return true;
        }


        if (this.room.getItemManager().getIdolScoreboard() != null && (item.getDefinition().getInteractionType() == InteractionType.IDOL_SCOREBOARD)) {
            player.send(new ALERT("You can only place one American Idol scoreboard per room"));
            return true;
        }

        /*if (((this.room.getItemManager().getSoundMachine() == null) || !this.room.getItemManager().getSoundMachine().hasBehaviour(ItemBehaviour.SOUND_MACHINE) && item.getDefinition().getInteractionType() == InteractionType.IDOL_SCOREBOARD)) {
            player.send(new ALERT("You must have a trax machine in your room before you place the American Idol hotspot"));
            return true;
        }*/

        int postItSize = 25;
        int rollerSize = 35;
        int petNest = 3;

        if (player.getDetails().hasClubSubscription()) {
            rollerSize = 60;
        }

        if (this.getItemsByDefinition(ItemBehaviour.POST_IT).size() >= postItSize && (item.hasBehaviour(ItemBehaviour.POST_IT))) {
            player.send(new CANNOT_PLACE_STUFF_FROM_STRIP(12));
            return true;
        }

        if (this.getItemsByDefinition(ItemBehaviour.ROLLER).size() >= rollerSize && (item.hasBehaviour(ItemBehaviour.ROLLER))) {
            player.send(new CANNOT_PLACE_STUFF_FROM_STRIP(22));
            return true;
        }

        if (this.getItemsByDefinition(InteractionType.PET_NEST).size() >= petNest && (item.getDefinition().getInteractionType() == InteractionType.PET_NEST)) {
            player.send(new CANNOT_PLACE_STUFF_FROM_STRIP(21));
            return true;
        }

        return false;
    }

    /**
     * Get an item in the room by its item id.
     *
     * @param itemId the item id to retrieve the item instance.
     * @return the new item
     */
    public Item getById(int itemId) {
        for (Item item : this.room.getItems()) {
            if (item.getVirtualId() == itemId) {
                return item;
            }
        }

        return null;
    }

    /**
     * Get an item in the room by its item id.
     *
     * @param itemId the item id to retrieve the item instance.
     * @return the new item
     */
    public Item getByDatabaseId(long itemId) {
        for (Item item : this.room.getItems()) {
            if (item.getDatabaseId() == itemId) {
                return item;
            }
        }

        return null;
    }

    /**
     * Get the list of public items, used for rendering public room items.
     * @return the list of public room items
     */
    public List<Item> getPublicItems() {
        List<Item> items = new ArrayList<>();

        for (Item item : this.room.getItems()) {
            if (item.hasBehaviour(ItemBehaviour.INVISIBLE)) {
                continue;
            }

            if (item.hasBehaviour(ItemBehaviour.PRIVATE_FURNITURE)) {
                continue;
            }

            if (!item.hasBehaviour(ItemBehaviour.PUBLIC_SPACE_OBJECT)) {
                continue;
            }

            items.add(item);
        }

        return items;
    }

    /**
     * Get the list of floor items, used for rendering purchased floor items through the catalogue.
     * @return the list of floor items
     */
    public List<Item> getFloorItems() {
        List<Item> items = new ArrayList<>();

        for (Item item : this.room.getItems()) {
            if (item.hasBehaviour(ItemBehaviour.PUBLIC_SPACE_OBJECT)) {
                continue;
            }

            if (item.hasBehaviour(ItemBehaviour.WALL_ITEM)) {
                continue;
            }

            items.add(item);
        }

        return items;
    }

    /**
     * Get the list of wall items, used for rendering purchased wall items through the catalogue.
     * @return the list of wall items
     */
    public List<Item> getWallItems() {
        List<Item> items = new ArrayList<>();

        for (Item item : this.room.getItems()) {
            if (item.hasBehaviour(ItemBehaviour.PUBLIC_SPACE_OBJECT)) {
                continue;
            }

            if (!item.hasBehaviour(ItemBehaviour.WALL_ITEM)) {
                continue;
            }

            items.add(item);
        }

        return items;
    }

    /**
     * Get a list of items by item behaviour
     * @return the items
     */
    public List<Item> getItemsByDefinition(ItemBehaviour itemBehaviour) {
        List<Item> items = new ArrayList<>();

        for (Item item : this.room.getItems()) {
            if (item.hasBehaviour(itemBehaviour)) {
                items.add(item);
            }
        }

        return items;
    }

    /**
     * Get a list of items by interaction type
     * @return the items
     */
    public List<Item> getItemsByDefinition(InteractionType interactionType) {
        List<Item> items = new ArrayList<>();

        for (Item item : this.room.getItems()) {
            if (item.getDefinition().getInteractionType() == interactionType) {
                items.add(item);
            }
        }

        return items;
    }

    /**
     * Get the rooms' trax machine instance.
     *
     * @return the trax machine
     */
    public Item getSoundMachine() {
        return soundMachine;
    }

    /**
     * Set the rooms' trax instance.
     *
     * @param soundMachine the trax machine instance
     */
    public void setSoundMachine(Item soundMachine) {
        this.soundMachine = soundMachine;
    }

    /**
     * Get the rooms' moodlight instance.
     *
     * @return the moodlight
     */
    public Item getMoodlight() {
        return moodlight;
    }

    /**
     * Set the moodlight instance.
     *
     * @param moodlight the moodlight instance
     */
    public void setMoodlight(Item moodlight) {
        this.moodlight = moodlight;
    }

    /**
     * Get the idol scoreboard in the room
     *
     * @return the scoreboard
     */
    public Item getIdolScoreboard() {
        return idolScoreboard;
    }

    /**
     * Set the scoreboard.
     *
     * @param idolScoreboard the scoreboard to set
     */
    public void setIdolScoreboard(Item idolScoreboard) {
        this.idolScoreboard = idolScoreboard;
    }
}