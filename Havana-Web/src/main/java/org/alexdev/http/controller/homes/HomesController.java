package org.alexdev.http.controller.homes;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.*;
import org.alexdev.havana.game.badges.Badge;
import org.alexdev.havana.game.groups.Group;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.game.player.statistics.PlayerStatistic;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.dao.HomeEditDao;
import org.alexdev.http.dao.HomesDao;
import org.alexdev.http.dao.WidgetDao;
import org.alexdev.http.game.homes.Home;
import org.alexdev.http.game.homes.Widget;
import org.alexdev.http.game.stickers.StickerCategory;
import org.alexdev.http.game.stickers.StickerManager;
import org.alexdev.http.game.stickers.StickerType;
import org.alexdev.http.util.HomeUtil;
import org.alexdev.http.util.SessionHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
public class HomesController {

    @GetMapping("/home/{username}")
    public String home(
            @PathVariable String username,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        session.setAttribute("page", "me");
        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        PlayerDetails user = PlayerDao.getDetails(username);

        if (user == null) {
            return "error/404";
        }

        if (playerDetails == null || playerDetails.getRank().getRankId() < PlayerRank.MODERATOR.getRankId()) {
            if (!user.isProfileVisible() || user.isBanned() != null) {
                return "error/404";
            }
        }

        return renderHome(session, model, user, playerDetails);
    }

    @GetMapping("/home/{userId}/id")
    public String homeById(
            @PathVariable int userId,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "";
        }

        session.setAttribute("page", "me");
        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        String username = PlayerDao.getName(userId);

        if (username == null) {
            return "error/404";
        }

        PlayerDetails user = PlayerDao.getDetails(username);

        if (user == null) {
            return "error/404";
        }

        if (playerDetails == null || playerDetails.getRank().getRankId() < PlayerRank.MODERATOR.getRankId()) {
            if (!user.isProfileVisible() || user.isBanned() != null) {
                return "error/404";
            }
        }

        return renderHome(session, model, user, playerDetails);
    }

    private String renderHome(HttpSession session, Model model, PlayerDetails user, PlayerDetails playerDetails) {
        Home home = HomesDao.getHome(user.getId());
        boolean defaultWidgets = false;

        if (home == null) {
            home = new Home(user.getId(), "bg_pattern_abstract2");
            defaultWidgets = true;
        }

        boolean canAddFriend = false;
        int userId = -1;
        long sessionTime = -1;

        if (SessionHelper.isAuthenticated(session)) {
            userId = SessionHelper.getUserId(session);
            sessionTime = HomeEditDao.getSession(userId);

            if (sessionTime != -1 && userId == user.getId()) {
                session.removeAttribute("groupEditSession");
                session.setAttribute("homeEditSession", user);
            }

            if (userId != user.getId() && !MessengerDao.friendExists(userId, user.getId())) {
                canAddFriend = true;
            }
        }

        List<Widget> widgets;

        if (defaultWidgets) {
            widgets = StickerManager.getInstance().getDefaultWidgets(user.getId());
        } else {
            widgets = WidgetDao.getHomeWidgets(user.getId(), true);
        }

        var enabledBadges = BadgeDao.getBadges(user.getId()).stream().filter(Badge::isEquipped).sorted(Comparator.comparingInt(Badge::getSlotId)).collect(Collectors.toList());
        var guestbook = WidgetDao.getHomeWidgets(user.getId()).stream().filter(w -> w.getProduct().getData().equalsIgnoreCase("guestbookwidget")).findFirst().orElse(null);

        model.addAttribute("user", user);
        model.addAttribute("tags", TagDao.getUserTags(user.getId()));
        model.addAttribute("hasBadge", enabledBadges.size() > 0);
        model.addAttribute("editMode", sessionTime != -1 && userId == user.getId());
        model.addAttribute("stickers", widgets);
        model.addAttribute("homeBannerAd", HomeUtil.getRandomAd());
        model.addAttribute("home", home);
        model.addAttribute("canAddFriend", canAddFriend);
        model.addAttribute("guestbookSetting", guestbook != null ? guestbook.getGuestbookState() : "public");
        model.addAttribute("stickerLimit", HomeUtil.getStickerLimit(user.hasClubSubscription()));
        model.addAttribute("tagCloud", new ArrayList<String>());

        if (enabledBadges.size() > 0) {
            model.addAttribute("badgeCode", enabledBadges.get(0).getBadgeCode());
        }

        model.addAttribute("hasFavouriteGroup", false);

        if (user.getFavouriteGroupId() > 0) {
            Group group = GroupDao.getGroup(user.getFavouriteGroupId());

            if (group != null) {
                model.addAttribute("hasFavouriteGroup", true);
                model.addAttribute("group", group);
            }
        }

        if (SessionHelper.isAuthenticated(session)) {
            if (user.getId() == userId) {
                PlayerStatisticsDao.updateStatistic(userId, PlayerStatistic.GUESTBOOK_UNREAD_MESSAGES, 0);
            }
        }

        return "home";
    }

    @GetMapping("/myhabbo/inventory")
    public String inventory(HttpSession session, HttpServletResponse response, Model model) {
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

        List<Widget> widgetList = WidgetDao.getInventoryWidgets(playerDetails.getId(), 1);
        List<Widget> inventoryWidgets = new ArrayList<>();

        for (Widget widget : widgetList) {
            if (inventoryWidgets.stream().anyMatch(w -> w.getStickerId() == widget.getStickerId())) {
                Widget w = inventoryWidgets.stream().filter(inv -> inv.getStickerId() == widget.getStickerId()).findFirst().get();
                w.setAmount(w.getAmount() + 1);
            } else {
                inventoryWidgets.add(widget);
            }
        }

        inventoryWidgets.sort(Comparator.comparingInt(Widget::getId).reversed());

        int emptyBoxes;

        if (widgetList.size() > 0) {
            Widget widget = widgetList.get(0);
            response.setHeader("X-JSON", "[[\"Inventory\",\"Web Store\"],[\"" + widget.getProduct().getCssClass() + "\",\"" + widget.getProduct().getData() + "\",\"" + widget.getProduct().getName() + "\",\"Stickers\",null,1]]");
        } else {
            response.setHeader("X-JSON", "[[\"Inventory\",\"Web Store\"],[\"\",\"\",\"\",\"Stickers\",null,1]]");
        }

        if (widgetList.size() > 20) {
            emptyBoxes = (int) (Math.ceil(widgetList.size() / 4.0) * 4);
        } else {
            emptyBoxes = 20 - widgetList.size();
        }

        List<Object> emptyBox = new ArrayList<>();

        if (emptyBoxes > 0) {
            for (int i = 0; i < emptyBoxes; i++) {
                emptyBox.add(null);
            }
        }

        model.addAttribute("stickerCategories", stickerCategories);
        model.addAttribute("backgroundCategories", backgroundCategories);
        model.addAttribute("emptyBoxes", emptyBox);
        model.addAttribute("widgets", inventoryWidgets);
        return "homes/inventory/inventory";
    }

    @PostMapping("/myhabbo/inventory/items")
    public String inventoryItems(
            @RequestParam(value = "type", defaultValue = "") String type,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);

        if (!type.equalsIgnoreCase("widgets")) {
            int typeId = 0;

            if (type.equalsIgnoreCase("stickers")) {
                typeId = 1;
            }
            if (type.equalsIgnoreCase("backgrounds")) {
                typeId = 4;
            }
            if (type.equalsIgnoreCase("notes")) {
                typeId = 3;
            }

            List<Widget> widgetList = WidgetDao.getInventoryWidgets(userId, typeId);
            List<Widget> inventoryWidgets = new ArrayList<>();

            for (Widget widget : widgetList) {
                if (inventoryWidgets.stream().anyMatch(w -> w.getStickerId() == widget.getStickerId())) {
                    Widget w = inventoryWidgets.stream().filter(inv -> inv.getStickerId() == widget.getStickerId()).findFirst().get();
                    w.setAmount(w.getAmount() + 1);
                } else {
                    inventoryWidgets.add(widget);
                }
            }

            inventoryWidgets.sort(Comparator.comparingInt(Widget::getId).reversed());

            int emptyBoxes;

            if (widgetList.size() > 20) {
                emptyBoxes = (int) (Math.ceil(widgetList.size() / 4.0) * 4);
            } else {
                emptyBoxes = 20 - widgetList.size();
            }

            List<Object> emptyBox = new ArrayList<>();

            if (emptyBoxes > 0) {
                for (int i = 0; i < emptyBoxes; i++) {
                    emptyBox.add(null);
                }
            }

            model.addAttribute("emptyBoxes", emptyBox);
            model.addAttribute("widgets", inventoryWidgets);
            model.addAttribute("widgetMode", false);
        } else {
            List<Widget> widgetList;

            if (session.getAttribute("groupEditSession") != null) {
                int groupId = (Integer) session.getAttribute("groupEditSession");
                widgetList = WidgetDao.getGroupWidgets(groupId);
                widgetList = widgetList.stream().filter(widget -> widget.getProduct().getType() == StickerType.GROUP_WIDGET).collect(Collectors.toList());
            } else {
                widgetList = WidgetDao.getHomeWidgets(userId);
                widgetList = widgetList.stream().filter(widget -> widget.getProduct().getType() == StickerType.HOME_WIDGET).collect(Collectors.toList());
                widgetList.removeIf(widget -> widget.getProduct().getData().equalsIgnoreCase("profilewidget"));
            }

            model.addAttribute("widgetMode", true);
            model.addAttribute("widgets", widgetList);
        }

        return "homes/inventory/inventory_items";
    }

    @PostMapping("/myhabbo/inventory/preview")
    public String inventoryPreview(
            @RequestParam(value = "itemId", defaultValue = "0") int itemId,
            @RequestParam(value = "type", defaultValue = "") String type,
            HttpSession session,
            HttpServletResponse response,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);
        int typeId = 0;

        if (type.equalsIgnoreCase("stickers")) {
            typeId = 1;
        }
        if (type.equalsIgnoreCase("backgrounds")) {
            typeId = 4;
        }
        if (type.equalsIgnoreCase("notes")) {
            typeId = 3;
        }
        if (type.equalsIgnoreCase("widgets")) {
            typeId = session.getAttribute("groupEditSession") != null ? StickerType.GROUP_WIDGET.getTypeId() : StickerType.HOME_WIDGET.getTypeId();
        }

        Widget widget = null;

        if (typeId == StickerType.GROUP_WIDGET.getTypeId()) {
            int groupId = (Integer) session.getAttribute("groupEditSession");
            List<Widget> widgetList = WidgetDao.getGroupWidgets(groupId);
            widget = widgetList.stream().filter(w -> w.getId() == itemId).findFirst().orElse(null);
        } else if (typeId == StickerType.HOME_WIDGET.getTypeId()) {
            List<Widget> widgetList = WidgetDao.getHomeWidgets(userId);
            widget = widgetList.stream().filter(w -> w.getId() == itemId).findFirst().orElse(null);
        } else {
            List<Widget> widgetList = WidgetDao.getInventoryWidgets(userId, typeId);
            widget = widgetList.stream().filter(w -> w.getId() == itemId).findFirst().orElse(null);
        }

        if (widget != null && typeId == 1) {
            response.setHeader("X-JSON", "[\"" + widget.getProduct().getCssClass() + "\",\"" + widget.getProduct().getData() + "\",\"" + widget.getProduct().getName() + "\",\"Sticker\",null,1]");
        } else if (widget != null && typeId == 4) {
            response.setHeader("X-JSON", String.format("[\"%s\",\"b_%s\",\"%s\",\"%s\",null,1]", widget.getProduct().getCssClass(), widget.getProduct().getData(), widget.getProduct().getName(), "Background"));
        } else if (widget != null && typeId == 3) {
            response.setHeader("X-JSON", "[\"commodity_stickienote_pre\",null,\"Notes\",\"WebCommodity\",null,1]");
        } else if (widget != null && (typeId == StickerType.GROUP_WIDGET.getTypeId() || typeId == StickerType.HOME_WIDGET.getTypeId())) {
            response.setHeader("X-JSON", "[\"" + widget.getProduct().getCssClass() + "\",null,\"\",\"Widget\",\"true\",1]");
        } else {
            response.setHeader("X-JSON", "[\"\",\"\",\"\",\"Sticker\",null,1]");
        }

        return "homes/inventory/inventory_preview";
    }

    @GetMapping("/myhabbo/edit/{targetId}")
    public String startEditingSession(
            @PathVariable String targetId,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);

        if (!StringUtils.isNumeric(targetId)) {
            return "redirect:/me";
        }

        int targetIdNum = Integer.parseInt(targetId);

        if (targetIdNum != userId) {
            return "redirect:/me";
        }

        Home home = HomesDao.getHome(targetIdNum);

        if (home == null) {
            StickerManager.getInstance().createHome(targetIdNum);
        }

        if (!HomeEditDao.hasSession(userId)) {
            HomeEditDao.createSession(userId);
            session.setAttribute("homeEditSession", userId);
            session.removeAttribute("groupEditSession");
        }

        PlayerDetails playerDetails = PlayerDao.getDetails(userId);
        return "redirect:/home/" + playerDetails.getName();
    }

    @GetMapping("/myhabbo/cancel/{targetId}")
    public String cancelEditingSession(
            @PathVariable String targetId,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);

        if (!StringUtils.isNumeric(targetId)) {
            return "redirect:/me";
        }

        int targetIdNum = Integer.parseInt(targetId);

        if (targetIdNum != userId) {
            return "redirect:/me";
        }

        if (HomeEditDao.hasSession(userId)) {
            HomeEditDao.delete(userId);
            session.removeAttribute("homeEditSession");
            session.removeAttribute("groupEditSession");
        }

        PlayerDetails playerDetails = PlayerDao.getDetails(userId);
        return "redirect:/home/" + playerDetails.getName();
    }

    @PostMapping("/myhabbo/save")
    @ResponseBody
    public String save(
            @RequestParam(value = "background", required = false) String background,
            @RequestParam(value = "stickers", required = false) String stickers,
            @RequestParam(value = "widgets", required = false) String widgets,
            @RequestParam(value = "stickienotes", required = false) String stickienotes,
            HttpSession session) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        if (playerDetails == null) {
            SessionHelper.logout(session);
            return "";
        }

        int userId = SessionHelper.getUserId(session);

        if (!HomeEditDao.hasSession(userId)) {
            return "";
        }

        Home home = HomesDao.getHome(userId);
        List<Widget> homeWidgets = WidgetDao.getHomeWidgets(userId, true);

        try {
            if (background != null) {
                int backgroundId = Integer.parseInt(background.split(":")[0]);

                List<Widget> widgetList = WidgetDao.getInventoryWidgets(playerDetails.getId());
                Widget widget = widgetList.stream().filter(w -> w.getId() == backgroundId).findFirst().orElse(null);

                if (widget != null) {
                    home.setBackground(widget.getProduct().getData());
                    home.saveBackground();
                }
            }

            if (stickers != null) {
                String[] stickerData = stickers.split(Pattern.quote("/"));

                if (stickerData.length >= HomeUtil.getStickerLimit(playerDetails.hasClubSubscription())) {
                    return "";
                }

                for (String sticker : stickerData) {
                    int stickerId = Integer.parseInt(sticker.split(":")[0]);
                    String[] coordData = sticker.replace(stickerId + ":", "").split(",");

                    int x = Integer.parseInt(coordData[0]);
                    int y = Integer.parseInt(coordData[1]);
                    int z = Integer.parseInt(coordData[2]);

                    Widget widget = homeWidgets.stream().filter(w -> w.getId() == stickerId).findFirst().orElse(null);

                    if (widget != null) {
                        widget.setX(x);
                        widget.setY(y);
                        widget.setZ(z);
                        widget.save();
                    }
                }
            }

            if (widgets != null) {
                String[] stickerData = widgets.split(Pattern.quote("/"));

                for (String sticker : stickerData) {
                    int stickerId = Integer.parseInt(sticker.split(":")[0]);
                    String[] coordData = sticker.replace(stickerId + ":", "").split(",");

                    int x = Integer.parseInt(coordData[0]);
                    int y = Integer.parseInt(coordData[1]);
                    int z = Integer.parseInt(coordData[2]);

                    Widget widget = homeWidgets.stream().filter(w -> w.getId() == stickerId).findFirst().orElse(null);

                    if (widget != null) {
                        widget.setX(x);
                        widget.setY(y);
                        widget.setZ(z);
                        widget.save();
                    }
                }
            }

            if (stickienotes != null) {
                String[] stickerData = stickienotes.split(Pattern.quote("/"));

                for (String sticker : stickerData) {
                    int stickerId = Integer.parseInt(sticker.split(":")[0]);
                    String[] coordData = sticker.replace(stickerId + ":", "").split(",");

                    int x = Integer.parseInt(coordData[0]);
                    int y = Integer.parseInt(coordData[1]);
                    int z = Integer.parseInt(coordData[2]);

                    Widget widget = homeWidgets.stream().filter(w -> w.getId() == stickerId).findFirst().orElse(null);

                    if (widget != null) {
                        widget.setX(x);
                        widget.setY(y);
                        widget.setZ(z);
                        widget.save();
                    }
                }
            }
        } catch (Exception ex) {
            // ignore
        }

        HomeEditDao.delete(userId);
        session.removeAttribute("homeEditSession");

        return "<script language=\"JavaScript\" type=\"text/javascript\">\n" +
                "waitAndGo('" + GameConfiguration.getInstance().getString("site.path") + "/home/" + playerDetails.getName() + "');\n" +
                "</script>\n";
    }

    @PostMapping("/myhabbo/taglist")
    public String tagList(
            @RequestParam(value = "accountId", defaultValue = "0") int accountId,
            HttpSession session,
            Model model) {

        int userId = SessionHelper.getUserId(session);

        if (userId < 1) {
            return "";
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);

        if (playerDetails == null) {
            return "";
        }

        List<String> tags = TagDao.getUserTags(accountId);

        model.addAttribute("tags", tags);
        model.addAttribute("user", playerDetails);
        return "homes/widget/habblet/taglist";
    }
}
