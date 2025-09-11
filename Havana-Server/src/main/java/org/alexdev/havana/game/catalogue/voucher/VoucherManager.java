package org.alexdev.havana.game.catalogue.voucher;

import org.alexdev.havana.dao.Storage;
import org.alexdev.havana.dao.mysql.CurrencyDao;
import org.alexdev.havana.dao.mysql.PlayerStatisticsDao;
import org.alexdev.havana.dao.mysql.VoucherDao;
import org.alexdev.havana.game.catalogue.CatalogueItem;
import org.alexdev.havana.game.catalogue.CatalogueManager;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.misc.purse.Voucher;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;
import org.alexdev.havana.util.DateUtil;
import org.oldskooler.simplelogger4j.SimpleLog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class VoucherManager {
    private static VoucherManager instance;

    public VoucherManager() {

    }

    public VoucherRedeemStatus redeem(PlayerDetails playerDetails, VoucherRedeemMode voucherRedeemMode, String voucherCode, List<CatalogueItem> redeemedItems, AtomicInteger redeemedCredits) {
        //Check and get voucher
        Voucher voucher = VoucherDao.redeemVoucher(voucherCode, playerDetails.getId());

        //No voucher was found
        if (voucher == null) {
            return VoucherRedeemStatus.FAILURE;
        }

        if (!voucher.isAllowNewUsers()) {
            int daysSince = (int) Math.floor(TimeUnit.SECONDS.toHours(PlayerStatisticsDao.getStatisticLong(playerDetails.getId(), PlayerStatistic.ONLINE_TIME)));

            if (daysSince < 1) {
                return VoucherRedeemStatus.FAILURE_NEW_ACCOUNT;
            }
        }

        //Redeem items
        List<Item> items = new ArrayList<>();

        for (String catalogueSaleCode : voucher.getItems()) {
            var catalogueItem = CatalogueManager.getInstance().getCatalogueItem(catalogueSaleCode);

            if (catalogueItem == null) {
                SimpleLog.of(VoucherManager.class).error("Could not redeem voucher " + voucherCode + " with sale code: " + catalogueSaleCode);
                continue;
            }

            redeemedItems.add(catalogueItem);

            try {
                items.addAll(CatalogueManager.getInstance().purchase(playerDetails, catalogueItem, "", null, DateUtil.getCurrentTimeSeconds()));
            } catch (Exception ex) {

            }
        }

        /*if (items.size() > 0) {
            if (voucherRedeemMode == VoucherRedeemMode.IN_GAME) {
                if (player != null) {
                    player.getInventory().getView("new");
                }
            }
        }*/

        VoucherDao.logVoucher(voucherCode, playerDetails.getId(), voucher.getCredits(), redeemedItems);

        //This voucher gives credits, so increase credits balance
        if (voucher.getCredits() > 0) {
            CurrencyDao.increaseCredits(playerDetails, voucher.getCredits());
            redeemedCredits.set(voucher.getCredits());
        }

        return VoucherRedeemStatus.SUCCESS;
    }


    /**
     * Get the {@link VoucherManager} instance
     *
     * @return the catalogue manager instance
     */
    public static VoucherManager getInstance() {
        if (instance == null) {
            instance = new VoucherManager();
        }

        return instance;
    }

    /**
     * Resets the catalogue manager singleton.
     */
    public static void reset() {
        instance = null;
        VoucherManager.getInstance();
    }
}
