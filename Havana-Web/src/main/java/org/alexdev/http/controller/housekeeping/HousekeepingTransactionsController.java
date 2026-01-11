package org.alexdev.http.controller.housekeeping;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.TransactionDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.http.Routes;
import org.alexdev.http.game.housekeeping.HousekeepingManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/" + Routes.HOUSEKEEPING_PATH)
public class HousekeepingTransactionsController {

    @GetMapping("/transactions")
    public String searchGet(
            @RequestParam(value = "searchQuery", defaultValue = "") String searchQuery,
            HttpSession session,
            Model model) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "transaction/lookup")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());

        if (!searchQuery.isEmpty()) {
            var transactions = TransactionDao.getTransactionsPastMonth(searchQuery, true);
            model.addAttribute("transactions", transactions);
        }

        model.addAttribute("pageName", "Transaction Lookup");

        session.removeAttribute("alertMessage");

        return "housekeeping/transaction_lookup";
    }

    @PostMapping("/transactions")
    public String searchPost(
            @RequestParam(value = "searchQuery", defaultValue = "") String searchQuery,
            HttpSession session,
            Model model) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "transaction/lookup")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());

        if (!searchQuery.isEmpty()) {
            var transactions = TransactionDao.getTransactionsPastMonth(searchQuery, true);
            model.addAttribute("transactions", transactions);
        }

        model.addAttribute("pageName", "Transaction Lookup");

        session.removeAttribute("alertMessage");

        return "housekeeping/transaction_lookup";
    }

    @GetMapping("/transactions/item")
    public String itemLookup(
            @RequestParam(value = "id", defaultValue = "") String itemId,
            HttpSession session,
            Model model) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "transaction/lookup")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());

        int id = StringUtils.isNumeric(itemId) ? Integer.parseInt(itemId) : 0;
        var transactions = TransactionDao.getTransactionByItem(id);
        model.addAttribute("transactions", transactions);

        model.addAttribute("pageName", "Transaction Lookup");

        session.removeAttribute("alertMessage");

        return "housekeeping/transaction_item_lookup";
    }
}
