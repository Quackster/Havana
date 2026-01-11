package org.alexdev.http.controller.site;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.TransactionDao;
import org.alexdev.havana.game.item.Transaction;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.http.util.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Controller
public class CreditsController {

    @GetMapping("/credits")
    public String credits(HttpSession session) {
        session.setAttribute("page", "credits");
        return "credits";
    }

    @GetMapping("/credits/history")
    public String transactions(
            @RequestParam(value = "period", required = false) String period,
            @RequestParam(value = "userId", required = false) Integer userIdParam,
            HttpSession session,
            Model model) {

        if (!SessionHelper.isAuthenticated(session)) {
            return "redirect:/";
        }

        PlayerDetails details = SessionHelper.getPlayer(session);
        if (details == null) {
            return "redirect:/";
        }

        Calendar presentDayCalendar = Calendar.getInstance();
        Calendar currentCalendar = Calendar.getInstance();
        Calendar futureCalendar = Calendar.getInstance();
        Calendar previousCalendar = Calendar.getInstance();

        int userId = details.getId();
        boolean viewAll = false;

        if (details.getRank().getRankId() >= PlayerRank.MODERATOR.getRankId()) {
            viewAll = true;
            if (userIdParam != null) {
                userId = userIdParam;
            }
        }

        boolean hasDateParameter = period != null && DateUtil.getFromFormat("yyyy-MM-dd", period) > 0;

        if (hasDateParameter) {
            long time = DateUtil.getFromFormat("yyyy-MM-dd", period);
            currentCalendar.setTimeInMillis(time * 1000);
            futureCalendar.setTimeInMillis(time * 1000);
            previousCalendar.setTimeInMillis(time * 1000);
        }

        previousCalendar.add(Calendar.MONTH, -1);
        futureCalendar.add(Calendar.MONTH, 1);

        int year = currentCalendar.get(Calendar.YEAR);
        int month = currentCalendar.get(Calendar.MONTH) + 1;
        List<Transaction> transactionsThisMonth = TransactionDao.getTransactions(userId, month, year, viewAll);

        model.addAttribute("canGoNext",
                currentCalendar.get(Calendar.MONTH) != presentDayCalendar.get(Calendar.MONTH) ||
                        currentCalendar.get(Calendar.YEAR) != presentDayCalendar.get(Calendar.YEAR));

        String previousMonth = new SimpleDateFormat("MMMM").format(previousCalendar.getTime());
        int previousYear = previousCalendar.get(Calendar.YEAR);
        int previousNumericalMonth = previousCalendar.get(Calendar.MONTH) + 1;

        model.addAttribute("previousYear", previousYear);
        model.addAttribute("previousMonth", previousMonth);
        model.addAttribute("previousNumericalMonth", previousNumericalMonth);

        String futureMonth = new SimpleDateFormat("MMMM").format(futureCalendar.getTime());
        int futureYear = futureCalendar.get(Calendar.YEAR);
        int futureNumericalMonth = futureCalendar.get(Calendar.MONTH) + 1;

        model.addAttribute("futureYear", futureYear);
        model.addAttribute("futureMonth", futureMonth);
        model.addAttribute("futureNumericalMonth", futureNumericalMonth);

        session.setAttribute("page", "credits");

        model.addAttribute("currentYear", year);
        model.addAttribute("currentMonth", new SimpleDateFormat("MMMM").format(currentCalendar.getTime()));
        model.addAttribute("transactions", transactionsThisMonth);

        return "credits_history";
    }
}
