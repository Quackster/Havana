package org.alexdev.havana.messages.outgoing.purse;

import org.alexdev.havana.game.catalogue.CatalogueItem;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.List;
import java.util.stream.Collectors;

public class VOUCHER_REDEEM_OK extends MessageComposer {
    private final List<CatalogueItem> redeemableItems;

    public VOUCHER_REDEEM_OK(List<CatalogueItem> redeemableItems) {
        this.redeemableItems = redeemableItems;
    }

    @Override
    public void compose(NettyResponse response) {
//        tProductName = tConn.GetStrFrom()
//        if tProductName <> "" then
//                tResultStr = getText("purse_vouchers_furni_success") & "\r" & "\r"
//        repeat while tProductName <> ""
//        tDescription = tConn.GetStrFrom()
//        tResultStr = tResultStr & tProductName & "\r"
//        tProductName = tConn.GetStrFrom()
//        end repeat
//        -- UNK_65 1
//        return(executeMessage(#alert, [#Msg:tResultStr]))
//        else
//        -- UNK_65 1
//        return(executeMessage(#alert, [#Msg:"purse_vouchers_success"]))
//        end if

        if (this.redeemableItems != null && this.redeemableItems.size() > 0) {
            if (this.redeemableItems.size() == 1) {
                response.writeString(this.redeemableItems.get(0).getDefinition().getName());
                response.writeString(this.redeemableItems.get(0).getDefinition().getDescription());
            } else {
                response.writeString(this.redeemableItems.stream().map(item -> item.getDefinition().getName()).collect(Collectors.joining(", " )));
                response.writeString("");
            }
        }
    }

    @Override
    public short getHeader() {
        return 212; // "CT"
    }
}