package org.alexdev.havana.messages.incoming.effects;

import org.alexdev.havana.dao.mysql.CurrencyDao;
import org.alexdev.havana.game.catalogue.CatalogueItem;
import org.alexdev.havana.game.catalogue.CatalogueManager;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.catalogue.NO_CREDITS;
import org.alexdev.havana.messages.outgoing.user.currencies.ActivityPointNotification;
import org.alexdev.havana.messages.outgoing.user.currencies.CREDIT_BALANCE;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.util.DateUtil;

public class PURCHASE_AND_WEAR implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        reader.readInt();
        CatalogueItem item = CatalogueManager.getInstance().getCatalogueItem(reader.readInt());

        if (item == null || item.isHidden() || !item.getDefinition().hasBehaviour(ItemBehaviour.EFFECT)) {
            return;
        }

        int priceCoins = item.getPriceCoins();
        int pricePixels = item.getPricePixels();

        if (priceCoins > player.getDetails().getCredits()) {
            player.send(new NO_CREDITS(true, false));
            return;
        }

        if (pricePixels > player.getDetails().getPixels()) {
            player.send(new NO_CREDITS(false, true));
            return;
        }

        CatalogueManager.getInstance().purchase(player.getDetails(), item, "", null, DateUtil.getCurrentTimeSeconds());

        if (priceCoins > 0) {
            CurrencyDao.decreaseCredits(player.getDetails(), priceCoins);
            player.send(new CREDIT_BALANCE(player.getDetails().getCredits()));
        }

        if (pricePixels > 0) {
            CurrencyDao.decreasePixels(player.getDetails(), pricePixels);
            player.send(new ActivityPointNotification(player.getDetails().getPixels(), ActivityPointNotification.ActivityPointAlertType.PIXELS_SOUND));
        }

        ACTIVATE_AVATAR_EFFECT.doAction(player, Integer.parseInt(item.getItemSpecialId()));
        USE_AVATAR_EFFECT.doAction(player, Integer.parseInt(item.getItemSpecialId()));
    }

}
