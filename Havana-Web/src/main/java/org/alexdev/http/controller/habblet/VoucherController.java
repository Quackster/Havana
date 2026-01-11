package org.alexdev.http.controller.habblet;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.game.catalogue.CatalogueItem;
import org.alexdev.havana.game.catalogue.voucher.VoucherManager;
import org.alexdev.havana.game.catalogue.voucher.VoucherRedeemMode;
import org.alexdev.havana.game.catalogue.voucher.VoucherRedeemStatus;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.http.util.RconUtil;
import org.alexdev.http.util.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class VoucherController {

    @PostMapping("/credits/redeemVoucher")
    public String redeemVoucher(
            @RequestParam(value = "voucherCode", defaultValue = "") String voucherCode,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        AtomicInteger redeemedCredits = new AtomicInteger(0);
        List<CatalogueItem> redeemedItems = new ArrayList<>();

        var voucherStatus = VoucherManager.getInstance().redeem(playerDetails, VoucherRedeemMode.IN_GAME, voucherCode, redeemedItems, redeemedCredits);

        if (voucherStatus == VoucherRedeemStatus.FAILURE) {
            model.addAttribute("voucherResult", "error");
        }

        if (voucherStatus == VoucherRedeemStatus.FAILURE_NEW_ACCOUNT) {
            model.addAttribute("voucherResult", "too_new");
        }

        if (voucherStatus == VoucherRedeemStatus.SUCCESS) {
            model.addAttribute("voucherResult", "success");
        }

        if (redeemedItems.size() > 0) {
            RconUtil.sendCommand(RconHeader.REFRESH_HAND, new HashMap<>() {{
                put("userId", playerDetails.getId());
            }});
        }

        if (redeemedCredits.get() > 0) {
            RconUtil.sendCommand(RconHeader.REFRESH_CREDITS, new HashMap<>() {{
                put("userId", playerDetails.getId());
            }});
        }

        return "habblet/redeemvoucher";
    }
}
