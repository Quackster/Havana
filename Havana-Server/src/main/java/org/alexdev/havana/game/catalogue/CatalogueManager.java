package org.alexdev.havana.game.catalogue;

import org.alexdev.havana.dao.mysql.*;
import org.alexdev.havana.game.catalogue.collectables.CollectableData;
import org.alexdev.havana.game.catalogue.collectables.CollectablesManager;
import org.alexdev.havana.game.effects.Effect;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.item.base.ItemDefinition;
import org.alexdev.havana.game.item.extradata.ExtraDataManager;
import org.alexdev.havana.game.item.extradata.types.TrophyExtraData;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.pets.PetManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;
import org.alexdev.havana.game.player.statistics.PlayerStatisticManager;
import org.alexdev.havana.game.room.mapping.RoomMapping;
import org.alexdev.havana.messages.outgoing.effects.AVATAR_EFFECTS;
import org.alexdev.havana.messages.outgoing.effects.AVATAR_EFFECT_ADDED;
import org.alexdev.havana.messages.outgoing.user.currencies.FILM;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.HexValidator;
import org.alexdev.havana.util.StringUtil;
import org.alexdev.havana.util.config.GameConfiguration;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CatalogueManager {
    public static final int XP_BOX_COST = 100;
    public static final int DAILY_LIMIT_XP = 300;//150;

    private static CatalogueManager instance;

    private List<CataloguePage> cataloguePageList;
    private List<CatalogueItem> catalogueItemList;
    private List<CataloguePackage> cataloguePackageList;

    private Map<String, List<String>> badgeRewards;

    public CatalogueManager() {
        this.cataloguePageList = CatalogueDao.getPages();
        this.cataloguePackageList = CatalogueDao.getPackages();
        this.catalogueItemList = CatalogueDao.getItems();
        this.loadPackages();
        this.reloadBadgeRewards();
    }

    /**
     * Get the badge rewards upon purchasing an item
     */
    public void reloadBadgeRewards() {
        this.badgeRewards = CatalogueDao.getBadgeRewards();
    }

    /**
     * Gets the child tabs.
     *
     * @param parentId the parent id
     * @param rank the rank
     * @return the child tabs
     */
    public List<CataloguePage> getChildPages(int parentId, int rank, boolean hasClub) {
        List<CataloguePage> pageList = new ArrayList<>();

        for (var page : this.cataloguePageList) {
            if (page.getParentId() != parentId) {
                continue;
            }

            if (page.getMinRole().getRankId() > rank) {
                continue;
            }

            if (page.isClubOnly() && !hasClub) {
                continue;
            }

            if (!page.isValidSeasonal()) {
                if (PlayerRank.COMMUNITY_MANAGER.getRankId() > rank) {
                    continue;
                }
            }

            pageList.add(page);
        }

        return pageList;


        /*try {
            var pageList = this.cataloguePageList.stream().filter(tab -> (tab.getMinRole().getRankId() <= rank) && tab.getParentId() == parentId && tab.isValidSeasonal()).collect(Collectors.toList());
            pageList.removeIf(tab -> tab.isClubOnly() && !hasClub);

            return pageList;
        } catch (Exception ex) {
            return new ArrayList<>();
        }*/
    }

    /**
     * Load catalogue packages for all catalogue items.
     */
    private void loadPackages() {
        for (CatalogueItem catalogueItem : this.catalogueItemList) {
            if (!catalogueItem.isPackage()) {
                continue;
            }

            for (CataloguePackage cataloguePackage : this.cataloguePackageList) {
                if (catalogueItem.getSaleCode().equals(cataloguePackage.getSaleCode())) {
                    catalogueItem.getPackages().add(cataloguePackage);
                }
            }
        }
    }

    /**
     * Get a page by the page index
     *
     * @param pageIndex the index of the page to get for
     * @return the catalogue page
     */
    public CataloguePage getCataloguePage(int pageIndex) {
        for (CataloguePage cataloguePage : this.cataloguePageList) {
            if (cataloguePage.getId() == pageIndex) {
                return cataloguePage;
            }
        }

        return null;
    }

    /**
     * Get an item by it's sale code.
     *
     * @param saleCode the item sale code identifier
     * @return the item, if successful
     */
    public CatalogueItem getCatalogueItem(String saleCode) {
        for (CatalogueItem catalogueItem : this.catalogueItemList) {
            if (catalogueItem.getSaleCode().equals(saleCode)) {
                return catalogueItem;
            }
        }

        return null;
    }

    /**
     * Get an item by its catalogue item id.
     *
     * @return the item, if successful
     */
    public CatalogueItem getCatalogueItem(int itemId) {
        for (CatalogueItem catalogueItem : this.catalogueItemList) {
            if (catalogueItem.getId() == itemId) {
                return catalogueItem;
            }
        }

        return null;
    }

    /**
     * Get an item by its catalogue sprite id.
     *
     * @return the item, if successful
     */
    public CatalogueItem getCatalogueBySprite(int spriteId) {
        for (CatalogueItem catalogueItem : this.catalogueItemList) {
            if (catalogueItem == null || catalogueItem.getDefinition() == null) {
                continue;
            }

            if (catalogueItem.getDefinition().getSpriteId() == spriteId) {
                return catalogueItem;
            }
        }

        return null;
    }

    /**
     * Get catalogue page list for a certain rank
     *
     * @return the list of catalogue pages
     */
    public List<CatalogueItem> getSeasonalItems() {
        List<CatalogueItem> itemList = new ArrayList<>();

        for (CatalogueItem catalogueItem : this.catalogueItemList) {
            if (catalogueItem.isActive()) {
                var item = catalogueItem.copy();
                item.setPriceCoins(item.getSeasonalCoins());
                item.setPricePixels(item.getSeasonalPixels());
                itemList.add(item);
            }
        }

        return itemList;
    }

    /**
     * Get a list of items for the catalogue page.
     *
     * @param pageId the id of the page to get the items for
     * @param rawItems if this is set to true, pages like the rares page and collectables page will not return any items
     * @return the list of items
     */
    public List<CatalogueItem> getCataloguePageItems(int pageId, boolean rawItems) {
        List<CatalogueItem> items = new ArrayList<>();

        // Ignore any game logic checks when requesting catalogue pages
        if (!rawItems) {
            if (pageId == GameConfiguration.getInstance().getInteger("rare.cycle.page.id")) {
                var itemList = getSeasonalItems();

                if (itemList.size() > 0) {
                    return itemList;
                }
            }

            // Do collectables
            CollectableData collectableData = CollectablesManager.getInstance().getCollectableDataByPage(pageId);

            if (collectableData != null) {
                CatalogueItem catalogueItem = collectableData.getActiveItem();
                return List.of(catalogueItem);
            }
        }

        for (CatalogueItem catalogueItem : this.catalogueItemList) {
            if (catalogueItem.isHidden()) {
                continue;
            }

            if (catalogueItem.hasPage(pageId)) {
                items.add(catalogueItem);
            }
        }

        items.sort(Comparator.comparingInt(CatalogueItem::getOrderId));
        return items;
    }

    /**
     * Purchase handler for player details.
     *
     * @param playerDetails the details for player
     * @param item the item the user is buying
     * @param extraData the extra data attached to item
     * @param overrideName the override name (used for trophies)
     * @param timestamp the time of purchase
     * @return the list of items bought
     * @throws SQLException the sql exception
     */
    public List<Item> purchase(PlayerDetails playerDetails, CatalogueItem item, String extraData, String overrideName, long timestamp) throws SQLException {
        List<Item> itemsBought = new ArrayList<>();

        if (!item.isPackage()) {
            for (int i = 0; i < item.getAmount(); i++) {
                Item newItem = purchase(playerDetails, item.getDefinition(), extraData, item.getItemSpecialId(), overrideName, timestamp);

                if (newItem != null) {
                    itemsBought.add(newItem);
                }
            }
        } else {
            for (CataloguePackage cataloguePackage : item.getPackages()) {
                for (int i = 0; i < cataloguePackage.getAmount(); i++) {
                    Item newItem = purchase(playerDetails, cataloguePackage.getDefinition(), null, cataloguePackage.getSpecialSpriteId(), overrideName, timestamp);

                    if (newItem != null) {
                        itemsBought.add(newItem);
                    }
                }
            }
        }

        return itemsBought;
    }

    /**
     * The player purchase handler but purchase single item.
     *
     * @param playerDetails the details of the player
     * @param def the definition of the item
     * @param extraData the extra data attached to the item
     * @param specialSpriteId the special sprite id used for posters
     * @param overrideName the override name - used for trophies
     * @param timestamp the time of purchase
     * @return the item bought
     * @throws SQLException the sql exception
     */
    public Item purchase(PlayerDetails playerDetails, ItemDefinition def, String extraData, String specialSpriteId, String overrideName, long timestamp) throws SQLException {
        Player player = PlayerManager.getInstance().getPlayerById(playerDetails.getId());

        if (def.hasBehaviour(ItemBehaviour.EFFECT)) {
            return purchaseEffect(playerDetails, Integer.parseInt(specialSpriteId));
        }

        // If the item is film, just give the user film
        if (def.getSprite().equals("film")) {
            CurrencyDao.increaseFilm(playerDetails, 5);

            if (player != null) {
                player.send(new FILM(playerDetails));
            }
            return null;
        }

        String customData = "";

        if (extraData != null) {
            if (def.hasBehaviour(ItemBehaviour.DECORATION)) {
                customData = extraData;
            } else {
                if (specialSpriteId.length() > 0) {
                    customData = specialSpriteId;
                }
            }

            if (def.hasBehaviour(ItemBehaviour.POST_IT)) {
                customData = "20";
            }

            if (def.getInteractionType() == InteractionType.SCOREBOARD) {
                customData = "x";
            }

            if (def.hasBehaviour(ItemBehaviour.PRIZE_TROPHY)) {
                var trophyName = (overrideName != null ? overrideName : playerDetails.getName());
                var trophyUserData = PlayerManager.getInstance().getPlayerData(trophyName);

                var jsonInstance = new TrophyExtraData(trophyUserData.getId(), StringUtil.filterInput(extraData, true), timestamp);
                customData = ExtraDataManager.getGson().toJson(jsonInstance);
            }

            if (def.hasBehaviour(ItemBehaviour.ROOMDIMMER)) {
                customData = Item.DEFAULT_ROOMDIMMER_CUSTOM_DATA;
            }
        }

        if (customData.isEmpty()) {
            customData = "0";
        }

        Item item = new Item();
        item.setOwnerId(playerDetails.getId());
        item.setDefinitionId(def.getId());
        item.setCustomData(customData);

        RoomMapping.resetExtraData(item, false);

        if (item.getDefinition().getRentalTime() > 0) {
            item.setExpireTime(DateUtil.getCurrentTimeSeconds() + item.getDefinition().getRentalTime());
        }

        // If the item is a camera, give them 2 free film.
        if (def.getSprite().equals("camera")) {
            CurrencyDao.increaseFilm(playerDetails, 2);

            if (player != null) {
                player.send(new FILM(playerDetails));
            }
        }

        if (def.getInteractionType() == InteractionType.PET_NEST) {
            if (extraData != null) {

                if (!extraData.contains(Character.toString((char) 10))) {
                    return null;
                }

                String[] petData = extraData.split(Character.toString((char) 10));

                if (petData.length != 3 || !StringUtils.isNumeric(petData[1])) {
                    return null;
                }

                String name = StringUtil.filterInput(petData[0], true);
                String type = def.getSprite().replace("pets", "");
                int race = Integer.parseInt(petData[1]);
                String color = StringUtil.filterInput(petData[2], true);

                if (HexValidator.validate(color) && !color.startsWith("#")) {

                    if (PetManager.getInstance().isValidName(playerDetails.getName(), name) < 1) {
                        ItemDao.newItem(item);
                        PetDao.createPet(item.getDatabaseId(), name, type, race, color);
                    } else {
                        return null;
                    }
                }
            }
        }
        else {
            ItemDao.newItem(item);
        }

        if (def.hasBehaviour(ItemBehaviour.TELEPORTER)) {
            Item linkedTeleporterItem = new Item();
            linkedTeleporterItem.setOwnerId(playerDetails.getId());
            linkedTeleporterItem.setDefinitionId(def.getId());
            linkedTeleporterItem.setCustomData(customData);

            if (linkedTeleporterItem.getDefinition().getRentalTime() > 0) {
                linkedTeleporterItem.setExpireTime(DateUtil.getCurrentTimeSeconds() + item.getDefinition().getRentalTime());
            }

            ItemDao.newItem(linkedTeleporterItem);

            if (player != null) {
                player.getInventory().addItem(linkedTeleporterItem);
            }

            TeleporterDao.addPair(linkedTeleporterItem.getDatabaseId(), item.getDatabaseId());
            TeleporterDao.addPair(item.getDatabaseId(), linkedTeleporterItem.getDatabaseId());
        }

        if (player != null) {
            player.getInventory().addItem(item);
        }

        return item;
    }

    private static Item purchaseEffect(PlayerDetails playerDetails, int specialSpriteId) {
        Effect effect = EffectDao.newEffect(playerDetails.getId(), specialSpriteId, -1, false);

        if (effect == null) {
            return null;
        }

        Player player = PlayerManager.getInstance().getPlayerById(playerDetails.getId());

        if (player != null) {
            player.getEffects().add(effect);

            player.send(new AVATAR_EFFECT_ADDED(effect));
            player.send(new AVATAR_EFFECTS(player.getEffects()));
        }

        return null;
    }

    /**
     * Get catalogue page list.
     *
     * @return the list of catalogue pages
     */
    public List<CataloguePage> getCataloguePages() {
        return this.cataloguePageList;
    }

    /**
     * Get catalogue page list for a certain rank
     *
     * @return the list of catalogue pages
     */
    public List<CataloguePage> getPagesForRank(PlayerRank minimumRank) {
        List<CataloguePage> cataloguePagesForRank = new ArrayList<>();

        for (CataloguePage page : this.cataloguePageList) {
            if (minimumRank.getRankId() >= page.getMinRole().getRankId()) {
                cataloguePagesForRank.add(page);
            }
        }

        return cataloguePagesForRank;
    }

    /**
     * Get catalogue items list.
     *
     * @return the list of items packages
     */
    public List<CatalogueItem> getCatalogueItems() {
        return catalogueItemList;
    }

    /**
     * Get the {@link CatalogueManager} instance
     *
     * @return the catalogue manager instance
     */
    public static CatalogueManager getInstance() {
        if (instance == null) {
            instance = new CatalogueManager();
        }

        return instance;
    }

    /**
     * Resets the catalogue manager singleton.
     */
    public static void reset() {
        instance = null;
        CatalogueManager.getInstance();
    }

    /**
     * Get the badge reward list
     * @return the list of badge rewards
     */
    public Map<String, List<String>> getBadgeRewards() {
        return badgeRewards;
    }
}
