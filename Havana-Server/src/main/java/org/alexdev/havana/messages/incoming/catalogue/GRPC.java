package org.alexdev.havana.messages.incoming.catalogue;

import org.alexdev.havana.dao.mysql.CurrencyDao;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.dao.mysql.TransactionDao;
import org.alexdev.havana.game.catalogue.*;
import org.alexdev.havana.game.catalogue.collectables.CollectablesManager;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.ItemManager;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.item.base.ItemDefinition;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.game.room.managers.RoomTradeManager;
import org.alexdev.havana.game.texts.TextsManager;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.outgoing.alerts.NO_USER_FOUND;
import org.alexdev.havana.messages.outgoing.catalogue.NO_CREDITS;
import org.alexdev.havana.messages.outgoing.openinghours.INFO_HOTEL_CLOSING;
import org.alexdev.havana.messages.outgoing.user.currencies.ActivityPointNotification;
import org.alexdev.havana.messages.outgoing.user.currencies.CREDIT_BALANCE;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.NettyPlayerNetwork;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.util.MalformedPacketException;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.HexValidator;
import org.alexdev.havana.util.StringUtil;
import org.alexdev.havana.util.config.GameConfiguration;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class GRPC implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws SQLException, MalformedPacketException {
        reader.readInt();

        if (PlayerManager.getInstance().isMaintenance()) {
            player.send(new INFO_HOTEL_CLOSING(PlayerManager.getInstance().getMaintenanceAt()));
            return;
        }

        CatalogueItem item = CatalogueManager.getInstance().getCatalogueItem(reader.readInt());

        if (item == null || item.isHidden()) {
            System.out.println("Failed to buy reason (" + player.getDetails().getName() + "): 0");
            return;
        }

        final CatalogueItem finalItem = item;
        CatalogueItem seasonalItem = CatalogueManager.getInstance().getSeasonalItems().stream()
                .filter(seasonal -> seasonal.getId() == finalItem.getId()).findFirst().orElse(null);

        if (seasonalItem != null) {
            item = seasonalItem;
        } else {
            // If the item is not a buyable special rare, then check if they can actually buy it
            if (!CollectablesManager.getInstance().isCollectable(item) || (RareManager.getInstance().getCurrentRare() != null && item != RareManager.getInstance().getCurrentRare())) {
                CataloguePage page = CatalogueManager.getInstance().getCataloguePages().stream().filter(p -> finalItem.hasPage(p.getId())).findFirst().orElse(null);

                if (page == null) {// || pageStream.get().getMinRole().getRankId() > player.getDetails().getRank().getRankId()) {
                    return;
                }

                if (page.getMinRole().getRankId() > player.getDetails().getRank().getRankId()) {
                    return;
                }

                if (page.isClubOnly() && !player.getDetails().hasClubSubscription()) {
                    return;
                }
            }
        }

        List<Item> items = new ArrayList<>();

        int priceCoins = item.getPriceCoins();
        int pricePixels = item.getPricePixels();

        var currentRare = RareManager.getInstance().getCurrentRare();

        if (currentRare != null && currentRare == item) {
            if (!player.hasFuse(Fuseright.CREDITS)) {
                priceCoins = RareManager.getInstance().getRareCost().get(currentRare);
            }
        }

        if (!(player.getDetails().getRank().getRankId() >= PlayerRank.COMMUNITY_MANAGER.getRankId())) {
            if (CollectablesManager.getInstance().isCollectable(item)) {
                priceCoins = CollectablesManager.getInstance().getCollectableDataByItem(item.getId()).getActiveItem().getPriceCoins();
                pricePixels = CollectablesManager.getInstance().getCollectableDataByItem(item.getId()).getActiveItem().getPricePixels();
            }
        }

        if (priceCoins > player.getDetails().getCredits()) {
            player.send(new NO_CREDITS(true, false));
            return;
        }

        if (pricePixels > player.getDetails().getPixels()) {
            player.send(new NO_CREDITS(false, true));
            return;
        }

        /*player.send(new ActivityPointNotification(player.getDetails().getPixels(), ActivityPointNotification.ActivityPointAlertType.PIXELS_RECEIVED)); // Alert pixels received*/

        String extraData = reader.readString();
        boolean isGift = reader.readBoolean();

        String giftedUser = null;
        PlayerDetails giftedUserDetails = null;

        if (isGift) { // It's a gift!
            giftedUser = reader.readString();

            if (player.getDetails().getRank().getRankId() < PlayerRank.MODERATOR.getRankId() && !giftedUser.equalsIgnoreCase(player.getDetails().getName())) {
                if ((player.getRoomUser().getLastGiftTime() + TimeUnit.MINUTES.toSeconds(1)) > DateUtil.getCurrentTimeSeconds()) {
                    player.send(new ALERT("Not so fast! Please wait until you can send a gift again."));
                    return;
                }
            }


            giftedUserDetails = PlayerManager.getInstance().getPlayerData(PlayerDao.getId(giftedUser));

            //if (!data[6].toLowerCase().equals(player.getDetails().getName().toLowerCase())) {
            if (giftedUserDetails == null) {
                player.send(new NO_USER_FOUND(giftedUser));
                return;
            }
            //}

            if (!player.hasFuse(Fuseright.MUTE) && giftedUserDetails.getId() != player.getDetails().getId()) {
                if (player.getDetails().isTradeBanned()) {
                    player.send(new ALERT(RoomTradeManager.showTradeBanAlert(player)));
                    return;
                }
            }

            // Giving credits to self on same IP is suspicious behaviour
            if (item.getDefinition() != null) {
                if (item.getDefinition().hasBehaviour(ItemBehaviour.EFFECT)) {
                    return;
                }

                if (item.getDefinition().hasBehaviour(ItemBehaviour.REDEEMABLE)) {
                if (!player.hasFuse(Fuseright.MUTE)
                        && giftedUserDetails.getId() != player.getDetails().getId()
                        && giftedUserDetails.getIpAddress().equals(player.getDetails().getIpAddress())) {
                    RoomTradeManager.addTradeBan(player);
                    return;
                }
                }
            }

            String presentNote = reader.readString();

            if (presentNote == null || presentNote.isEmpty()) {
                presentNote = "";
            }

            extraData = extraData.replace(Item.PRESENT_DELIMETER, "");
            presentNote = presentNote.replace(Item.PRESENT_DELIMETER, "");

            if (!item.getItemSpecialId().isBlank() && StringUtils.isNumeric(item.getItemSpecialId())) {
                extraData = item.getItemSpecialId();
            }

            Item present = ItemManager.getInstance().createGift(giftedUserDetails.getId(), player.getDetails().getName(), item.getSaleCode(), StringUtil.filterInput(presentNote, true), extraData);//new Item();
            Player receiver = PlayerManager.getInstance().getPlayerById(giftedUserDetails.getId());

            if (receiver != null) {
                receiver.getInventory().addItem(present);
                receiver.getInventory().getView("new");
                //receiver.send(new ITEM_DELIVERED());
            }

            items.add(present);
            player.send(new ALERT(TextsManager.getInstance().getValue("successfully_purchase_gift_for").replace("%user%", giftedUserDetails.getName())));
            player.getRoomUser().setLastGiftTime(DateUtil.getCurrentTimeSeconds());
            //player.send(new DELIVER_PRESENT(present));
        } else {
            if (!item.isPackage() &&
                    item.getDefinition() != null &&
                    item.getDefinition().getInteractionType() == InteractionType.PET_NEST) {
                if (extraData != null) {
                    String[] petData = extraData.split(Character.toString((char) 10));
                    String hex = petData[2];

                    if (hex.length() > 6) {
                        hex = hex.substring(0, 6);
                    }

                    String color = StringUtil.filterInput(hex, true);

                    if (!HexValidator.validate(color)) {
                        return;
                    }
                }
            }

            items = CatalogueManager.getInstance().purchase(player.getDetails(), item, extraData, null, DateUtil.getCurrentTimeSeconds());

            // Don't charge if nothing was given.
            var itemDefinition = item.getDefinition();
            if (items.size() == 0 && !(itemDefinition != null && (itemDefinition.hasBehaviour(ItemBehaviour.EFFECT) || itemDefinition.getSprite().equals("film"))))
                return;

            boolean showItemDelivered = player.getRoomUser().getRoom() != null;

            // Don't send item delivered if they just buy film
            if (item.getDefinition() != null && item.getDefinition().getSprite().equals("film")) {
                showItemDelivered = false;
            }

            if (item.getDefinition() != null && item.getDefinition().hasBehaviour(ItemBehaviour.EFFECT)) {
                showItemDelivered = false;
            }

            if (showItemDelivered) {
                //player.send(new ITEM_DELIVERED());
                player.getInventory().getView("new");
            }
        }

        if (priceCoins > 0) {
            CurrencyDao.decreaseCredits(player.getDetails(), priceCoins);
            player.send(new CREDIT_BALANCE(player.getDetails().getCredits()));
        }

        if (pricePixels > 0) {
            CurrencyDao.decreasePixels(player.getDetails(), pricePixels);
            player.send(new ActivityPointNotification(player.getDetails().getPixels(), ActivityPointNotification.ActivityPointAlertType.PIXELS_SOUND));
        }

        String transactionDscription = getTransactionDescription(item);

        if (transactionDscription != null) {
            boolean isCollectable = CollectablesManager.getInstance().isCollectable(item);

            if (isCollectable) {
                TransactionDao.createTransaction(player.getDetails().getId(),
                        items.stream().map(e -> String.valueOf(e.getDatabaseId())).collect(Collectors.joining(",")),
                        item.getId() + "",
                        item.getAmount(),
                        "Collectible - " + transactionDscription, priceCoins, pricePixels, true);
            } else {
                TransactionDao.createTransaction(player.getDetails().getId(),
                        items.stream().map(e -> String.valueOf(e.getDatabaseId())).collect(Collectors.joining(",")),
                        item.getId() + "",
                        item.getAmount(), transactionDscription, priceCoins, pricePixels, true);
            }

            if (giftedUserDetails != null) {
                TransactionDao.createTransaction(giftedUserDetails.getId(),
                        items.stream().map(e -> String.valueOf(e.getDatabaseId())).collect(Collectors.joining(",")),
                        item.getId() + "",
                        item.getAmount(),
                        "Gift purchase from " + player.getDetails().getName() + " for " + giftedUser + " - " + transactionDscription, priceCoins, pricePixels, true);

                TransactionDao.createTransaction(player.getDetails().getId(),
                        items.stream().map(e -> String.valueOf(e.getDatabaseId())).collect(Collectors.joining(",")),
                        item.getId() + "",
                        item.getAmount(),
                        "Gift purchase from " + player.getDetails().getName() + " for " + giftedUser + " - " + transactionDscription, priceCoins, pricePixels, true);
            }
        }

        if (CatalogueManager.getInstance().getBadgeRewards().containsKey(item.getSaleCode())) {
            for (var badgeCode : CatalogueManager.getInstance().getBadgeRewards().get(item.getSaleCode())) {
                player.getBadgeManager().tryAddBadge(badgeCode, null);
            }
        }

        boolean canRedeemPoints = true;

        if (item.getDefinition() != null && (item.getDefinition().hasBehaviour(ItemBehaviour.REDEEMABLE) || item.getDefinition().hasBehaviour(ItemBehaviour.PRESENT))) {
            canRedeemPoints = false;
        }
    }

    public static String getTransactionDescription(CatalogueItem item) {
        if (!item.isPackage()) {
            return getItemDescription(item.getDefinition(), item.getAmount());
        } else {
            List<String> descriptions = new ArrayList<>();

            for (CataloguePackage cataloguePackage : item.getPackages()) {
                var description = getItemDescription(cataloguePackage.getDefinition(), 1);

                if (description != null) {
                    descriptions.add(description);
                }
            }

            return "Package purchase (" + String.join(", ", descriptions) + ")";
        }
    }

    private static String getItemDescription(ItemDefinition definition, int amount) {
        if (definition == null) {
            return null;
        }

        if (definition.hasBehaviour(ItemBehaviour.EFFECT)) {
            return "Effect " + definition.getSprite().replace("avatar_effect", "") + " purchase";
        }

        if (definition.getSprite().equals("film")) {
            return "Film purchase";
        }

        return definition.getName() + " purchase";
    }
}
