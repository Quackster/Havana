package org.alexdev.havana.messages.incoming.navigator;

import org.alexdev.havana.game.navigator.NavigatorCategory;
import org.alexdev.havana.game.navigator.NavigatorManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.navigator.USERFLATCATS;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GETUSERFLATCATS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        List<NavigatorCategory> categoryList = new ArrayList<>();

        for (NavigatorCategory category : NavigatorManager.getInstance().getCategories().values()) {
            if (category.isPublicSpaces()) {
                continue;
            }

            if (category.getMinimumRoleAccess().getRankId() > player.getDetails().getRank().getRankId()) {
                continue;
            }

            if (category.isNode()) {
                continue;
            }
            categoryList.add(category);
        }

        categoryList.sort(Comparator.comparingInt(NavigatorCategory::getOrderId));
        player.send(new USERFLATCATS(categoryList));
    }
}
