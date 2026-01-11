package org.alexdev.http.controller.homes;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.CurrencyDao;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.server.rcon.messages.RconHeader;
import org.alexdev.http.dao.WidgetDao;
import org.alexdev.http.game.stickers.StickerCategory;
import org.alexdev.http.game.stickers.StickerManager;
import org.alexdev.http.game.stickers.StickerProduct;
import org.alexdev.http.game.stickers.StickerType;
import org.alexdev.http.util.RconUtil;
import org.alexdev.http.util.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StoreController {

    @GetMapping("/myhabbo/store")
    public String main(HttpSession session, HttpServletResponse response, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        if (playerDetails == null) {
            SessionHelper.logout(session);
            return "redirect:/";
        }

        List<StickerCategory> categories = StickerManager.getInstance().getCategories(playerDetails.getRank().getRankId());
        var stickerCategories = categories.stream().filter(category -> category.getCategoryType() == StickerCategory.STICKER_BACKGROUND_TYPE).sorted(Comparator.comparing(StickerCategory::getName)).collect(Collectors.toList());
        var backgroundCategories = categories.stream().filter(category -> category.getCategoryType() == StickerCategory.BACKGROUND_CATEGORY_TYPE).sorted(Comparator.comparing(StickerCategory::getName)).collect(Collectors.toList());

        int stickerCategory = -1;
        List<StickerProduct> products = new ArrayList<>();

        if (stickerCategories.size() > 0) {
            stickerCategory = stickerCategories.get(0).getId();
        } else if (backgroundCategories.size() > 0) {
            stickerCategory = backgroundCategories.get(0).getId();
        }

        if (stickerCategory != -1) {
            int finalStickerCategory = stickerCategory;
            products = StickerManager.getInstance().getCatalogueList().stream().filter(product -> product.getCategoryId() == finalStickerCategory).collect(Collectors.toList());
        }

        int emptyBoxes;

        if (products.size() > 20) {
            emptyBoxes = (int) (Math.ceil(products.size() / 5.0) * 5);
        } else {
            emptyBoxes = 20 - products.size();
        }

        StickerProduct product = null;

        if (products.size() > 0) {
            product = products.get(0);
            response.setHeader("X-JSON", "[[\"Inventory\",\"Web Store\"],[{\"itemCount\":" + product.getAmount() + ",\"previewCssClass\":\"" + product.getCssClass() + "\",\"titleKey\":\"\"}]]");
        } else {
            response.setHeader("X-JSON", "[[\"Inventory\",\"Web Store\"],[{\"itemCount\":0,\"titleKey\":\"\"}]]");
        }

        List<Object> emptyBox = new ArrayList<>();

        if (emptyBoxes > 0) {
            for (int i = 0; i < emptyBoxes; i++) {
                emptyBox.add(null);
            }
        }

        model.addAttribute("stickerCategories", stickerCategories);
        model.addAttribute("backgroundCategories", backgroundCategories);
        model.addAttribute("products", products);
        model.addAttribute("product", product);
        model.addAttribute("emptyBoxes", emptyBox);
        return "homes/store/main";
    }

    @PostMapping("/myhabbo/store/items")
    public String items(
            @RequestParam(value = "subCategoryId", defaultValue = "0") int subCategory,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        if (playerDetails == null) {
            SessionHelper.logout(session);
            return "redirect:/";
        }

        StickerCategory category = StickerManager.getInstance().getCategory(subCategory);

        if (category == null) {
            return "error/404";
        }

        List<StickerProduct> products = StickerManager.getInstance().getCatalogueList().stream().filter(product -> product.getCategoryId() == category.getId()).collect(Collectors.toList());

        int emptyBoxes;

        if (products.size() > 20) {
            emptyBoxes = (int) (Math.ceil(products.size() / 5.0) * 5);
        } else {
            emptyBoxes = 20 - products.size();
        }

        model.addAttribute("products", products);
        model.addAttribute("emptyProducts", emptyBoxes);
        return "homes/store/items";
    }

    @PostMapping("/myhabbo/store/preview")
    public String preview(
            @RequestParam(value = "productId", defaultValue = "0") int productId,
            HttpSession session,
            HttpServletResponse response,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        StickerProduct stickerProduct = StickerManager.getInstance().getCatalogueList().stream().filter(product -> product.getId() == productId).findFirst().orElse(null);

        if (stickerProduct == null) {
            return "error/404";
        }

        if (stickerProduct.getType() == StickerType.STICKER || stickerProduct.getType() == StickerType.NOTE) {
            response.setHeader("X-JSON", "[{\"itemCount\":" + stickerProduct.getAmount() + ",\"previewCssClass\":\"" + stickerProduct.getCssClass() + "\",\"titleKey\":\"" + stickerProduct.getName() + "\"}]");
        } else if (stickerProduct.getType() == StickerType.BACKGROUND) {
            response.setHeader("X-JSON", "[{\"bgCssClass\":\"b_" + stickerProduct.getData() + "\",\"itemCount\":" + stickerProduct.getAmount() + ",\"previewCssClass\":\"" + stickerProduct.getCssClass() + "\",\"titleKey\":\"" + stickerProduct.getName() + "\"}]");
        }

        model.addAttribute("product", stickerProduct);
        return "homes/store/preview";
    }

    @PostMapping("/myhabbo/store/purchaseconfirm")
    public String purchaseConfirm(
            @RequestParam(value = "productId", defaultValue = "0") int productId,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        StickerProduct stickerProduct = StickerManager.getInstance().getCatalogueList().stream().filter(product -> product.getId() == productId).findFirst().orElse(null);

        if (stickerProduct == null) {
            return "";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        model.addAttribute("product", stickerProduct);

        if (playerDetails.getCredits() < stickerProduct.getPrice()) {
            model.addAttribute("noCredits", true);
        } else {
            model.addAttribute("noCredits", false);
        }

        return "homes/store/purchase_confirm";
    }

    @GetMapping("/myhabbo/store/backgroundwarning")
    public String backgroundWarning(HttpSession session, Model model) {
        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        return "homes/store/background_warning";
    }

    @PostMapping("/myhabbo/store/purchasebackgrounds")
    @ResponseBody
    public String purchaseBackgrounds(
            @RequestParam(value = "selectedId", defaultValue = "0") int widgetId,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);

        StickerProduct stickerProduct = StickerManager.getInstance().getCatalogueList().stream().filter(product -> product.getId() == widgetId).findFirst().orElse(null);

        if (stickerProduct == null) {
            return "";
        }

        if (stickerProduct.getType() != StickerType.BACKGROUND) {
            return "";
        }

        PlayerDetails playerDetails = PlayerDao.getDetails(userId);

        if (playerDetails.getCredits() < stickerProduct.getPrice()) {
            return "";
        }

        for (int i = 0; i < stickerProduct.getAmount(); i++) {
            WidgetDao.purchaseWidget(userId, 0, 0, 0, 0, stickerProduct.getId(), "", 0, false);
        }

        CurrencyDao.decreaseCredits(playerDetails, stickerProduct.getPrice());

        RconUtil.sendCommand(RconHeader.REFRESH_CREDITS, new HashMap<>() {{
            put("userId", playerDetails.getId());
        }});

        return "OK";
    }

    @PostMapping("/myhabbo/store/purchasestickers")
    @ResponseBody
    public String purchaseStickers(
            @RequestParam(value = "selectedId", defaultValue = "0") int widgetId,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);

        StickerProduct stickerProduct = StickerManager.getInstance().getCatalogueList().stream().filter(product -> product.getId() == widgetId).findFirst().orElse(null);

        if (stickerProduct == null) {
            return "";
        }

        if (stickerProduct.getType() != StickerType.STICKER) {
            return "";
        }

        PlayerDetails playerDetails = PlayerDao.getDetails(userId);

        if (playerDetails.getCredits() < stickerProduct.getPrice()) {
            return "";
        }

        for (int i = 0; i < stickerProduct.getAmount(); i++) {
            WidgetDao.purchaseWidget(userId, 0, 0, 0, 0, stickerProduct.getId(), "", 0, false);
        }

        CurrencyDao.decreaseCredits(playerDetails, stickerProduct.getPrice());

        RconUtil.sendCommand(RconHeader.REFRESH_CREDITS, new HashMap<>() {{
            put("userId", playerDetails.getId());
        }});

        return "OK";
    }

    @PostMapping("/myhabbo/store/purchasestickienotes")
    @ResponseBody
    public String purchaseStickieNotes(
            @RequestParam(value = "selectedId", defaultValue = "0") int widgetId,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        int userId = SessionHelper.getUserId(session);

        StickerProduct stickerProduct = StickerManager.getInstance().getCatalogueList().stream().filter(product -> product.getId() == widgetId).findFirst().orElse(null);

        if (stickerProduct == null) {
            return "";
        }

        if (stickerProduct.getType() != StickerType.NOTE) {
            return "";
        }

        PlayerDetails playerDetails = PlayerDao.getDetails(userId);

        if (playerDetails.getCredits() < stickerProduct.getPrice()) {
            return "";
        }

        for (int i = 0; i < stickerProduct.getAmount(); i++) {
            WidgetDao.purchaseWidget(userId, 0, 0, 0, 0, stickerProduct.getId(), "", 0, false);
        }

        CurrencyDao.decreaseCredits(playerDetails, stickerProduct.getPrice());

        RconUtil.sendCommand(RconHeader.REFRESH_CREDITS, new HashMap<>() {{
            put("userId", playerDetails.getId());
        }});

        return "OK";
    }
}
