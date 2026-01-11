package org.alexdev.http.controller.site;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.http.game.news.NewsArticle;
import org.alexdev.http.scheduler.WatchdogScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CommunityController {

    @GetMapping("/community")
    public String community(HttpSession session, Model model) {
        session.setAttribute("page", "community");

        NewsArticle[] articles = new NewsArticle[5];
        List<NewsArticle> articleList = WatchdogScheduler.NEWS;

        int i = 0;
        if (articleList != null) {
            for (var article : articleList) {
                if (i < 5) articles[i++] = article;
            }
        }

        for (i = 0; i < 5; i++) {
            if (articles[i] == null) {
                articles[i] = new NewsArticle(0, "No news", 0, "", "", "",
                        DateUtil.getCurrentTimeSeconds(), "attention_topstory.png", "", "", "0", true, 0, false);
            }
            model.addAttribute("article" + (i + 1), articles[i]);
        }

        model.addAttribute("recommendedRooms", WatchdogScheduler.RECOMMENDED_ROOMS);
        model.addAttribute("hiddenRecommendedRooms", WatchdogScheduler.HIDDEN_RECOMMENDED_ROOMS);
        model.addAttribute("randomHabbos", PlayerDao.getRandomHabbos(18));
        model.addAttribute("tagCloud", WatchdogScheduler.TAG_CLOUD_10);
        model.addAttribute("recentTopics", WatchdogScheduler.RECENT_DISCUSSIONS);
        model.addAttribute("recentHiddenTopics", WatchdogScheduler.NEXT_RECENT_DISCUSSIONS);

        return "community";
    }
}
