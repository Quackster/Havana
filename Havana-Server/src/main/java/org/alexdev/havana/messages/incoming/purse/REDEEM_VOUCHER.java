package org.alexdev.havana.messages.incoming.purse;

import org.alexdev.havana.game.catalogue.CatalogueItem;
import org.alexdev.havana.game.catalogue.voucher.VoucherManager;
import org.alexdev.havana.game.catalogue.voucher.VoucherRedeemMode;
import org.alexdev.havana.game.catalogue.voucher.VoucherRedeemStatus;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.outgoing.purse.VOUCHER_REDEEM_ERROR;
import org.alexdev.havana.messages.outgoing.purse.VOUCHER_REDEEM_OK;
import org.alexdev.havana.messages.outgoing.user.currencies.CREDIT_BALANCE;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.util.MalformedPacketException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class REDEEM_VOUCHER implements MessageEvent {

    @Override
    public void handle(Player player, NettyRequest reader) throws SQLException, MalformedPacketException {
        if (!player.isLoggedIn()) {
            return;
        }

        AtomicInteger redeemedCredits = new AtomicInteger(0);
        var redeemedItem = new ArrayList<CatalogueItem>();

        var voucherStatus = VoucherManager.getInstance().redeem(player.getDetails(), VoucherRedeemMode.IN_GAME, reader.readString(), redeemedItem, redeemedCredits);

        if (voucherStatus == VoucherRedeemStatus.FAILURE) {
            player.send(new VOUCHER_REDEEM_ERROR(VOUCHER_REDEEM_ERROR.RedeemError.INVALID));
            return;
        }

        if (voucherStatus == VoucherRedeemStatus.FAILURE_NEW_ACCOUNT) {
            player.send(new ALERT("Sorry, your account is too new and cannot redeem this voucher"));
            return;
        }

        player.send(new VOUCHER_REDEEM_OK(redeemedItem));

        if (redeemedCredits.get() > 0) {
            player.send(new CREDIT_BALANCE(player.getDetails().getCredits()));
        }

        if (redeemedItem.size() > 0) {
            player.getInventory().reload();

            if (player.getRoomUser().getRoom() != null)
                player.getInventory().getView("new");
        }
    }
}
