package org.alexdev.http.controller.homes.widgets;

import jakarta.servlet.http.HttpSession;
import org.alexdev.http.dao.RatingDao;
import org.alexdev.http.dao.WidgetDao;
import org.alexdev.http.game.homes.Widget;
import org.alexdev.http.util.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RateController {

    @GetMapping("/myhabbo/rating/rate")
    public String rate(
            @RequestParam(value = "ratingId", defaultValue = "0") int widgetId,
            @RequestParam(value = "givenRate", defaultValue = "0") int rating,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);

        if (rating < 1 || rating > 5) {
            return "";
        }

        Widget widget = WidgetDao.getWidget(widgetId);

        if (widget == null) {
            return "";
        }

        int homeId = widget.getUserId();

        if (homeId == userId) {
            return "";
        }

        if (RatingDao.hasRated(userId, homeId)) {
            return "";
        }

        RatingDao.rate(userId, homeId, rating);

        model.addAttribute("sticker", widget);
        return "homes/widget/habblet/rate";
    }

    @GetMapping("/myhabbo/rating/reset")
    public String resetRating(
            @RequestParam(value = "ratingId", defaultValue = "0") int widgetId,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        int userId = SessionHelper.getUserId(session);

        Widget widget = WidgetDao.getWidget(widgetId);

        if (widget == null) {
            return "";
        }

        int homeId = widget.getUserId();

        if (homeId != userId) {
            return "";
        }

        RatingDao.deleteRating(homeId);

        model.addAttribute("sticker", widget);
        return "homes/widget/habblet/rate";
    }
}
